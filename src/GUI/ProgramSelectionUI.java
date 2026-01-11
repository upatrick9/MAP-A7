package GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.statements.IStmt;
import view.command.RunExample;

import java.util.ArrayList;
import java.util.List;

public class ProgramSelectionUI extends Application {
    public record Example(String name, IStmt program, String logFilePath){
        @Override
        public String toString(){
            return name;
        }
    }

    private static List<Example> examples = new ArrayList<>();

    public static void setExamples(List<Example> examples){
        ProgramSelectionUI.examples = new ArrayList<>(examples);
    }

    @Override
    public void start(Stage stage){
        ListView<Example> listView = new ListView<>(FXCollections.observableArrayList(examples));


        Button openButton = new Button("Open");
        openButton.setOnAction(e -> {
            Example selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null){
                new Alert(Alert.AlertType.ERROR, "Please select an example").showAndWait();
                return;
            }
            Controller controller = RunExample.buildController(selected.program(), selected.logFilePath(), false);
            new MainUI(stage, controller).show();
        });

        VBox root = new VBox(listView, openButton);

        stage.setTitle("Program Selection");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}
