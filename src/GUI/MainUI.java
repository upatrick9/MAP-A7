package GUI;

import controller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PrgState;
import models.statements.IStmt;
import models.values.StringValue;
import models.values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainUI {
    private final Stage stage;
    private final Controller controller;

    private final TextField nrPrgStatesField = new TextField();

    private final TableView<Map.Entry<Integer, Value>> heapTable = new TableView<>();
    private final ListView<String> outList = new ListView<>();
    private final ListView<String> fileTableList = new ListView<>();
    private final ListView<Integer> prgIdList = new ListView<>();
    private final TableView<Map.Entry<String, Value>> symTable = new TableView<>();
    private final ListView<String> exeStackList = new ListView<>();

    public MainUI(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void show() {
        nrPrgStatesField.setEditable(false);

        heapTable.getColumns().setAll(
                col("Address", (Map.Entry<Integer, Value> e) -> e.getKey()),
                col("Value", (Map.Entry<Integer, Value> e) -> String.valueOf(e.getValue()))
        );

        symTable.getColumns().setAll(
                col("Var", (Map.Entry<String, Value> e) -> e.getKey()),
                col("Value", (Map.Entry<String, Value> e) -> String.valueOf(e.getValue()))
        );

        Button runOneStep = new Button("Run one step");
        runOneStep.setOnAction(e -> {
            try {
                List<PrgState> active = controller.removeCompletedPrg(controller.getPrgList());
                if (!active.isEmpty()) {
                    controller.oneStepForAllPrg(active);
                }
                refresh();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });

        prgIdList.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> refreshSelected());

        VBox left = new VBox(
                new Label("PrgState IDs"), prgIdList,
                new Label("ExeStack"), exeStackList
        );

        VBox center = new VBox(
                new Label("Nr of PrgStates"), nrPrgStatesField,
                runOneStep,
                new Label("Heap"), heapTable,
                new Label("SymTable (selected)"), symTable
        );

        VBox right = new VBox(
                new Label("Out"), outList,
                new Label("FileTable"), fileTableList
        );

        SplitPane root = new SplitPane(left, center, right);

        stage.setTitle("Main");
        stage.setScene(new Scene(root, 1100, 700));
        refresh();
    }

    private <T, R> TableColumn<T, R> col(String name, java.util.function.Function<T, R> f) {
        TableColumn<T, R> c = new TableColumn<>(name);
        c.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(f.apply(p.getValue())));
        return c;
    }

    private void refresh() {
        List<PrgState> prgList = controller.getPrgList();
        nrPrgStatesField.setText(String.valueOf(prgList.size()));

        prgIdList.setItems(FXCollections.observableArrayList(prgList.stream().map(PrgState::getId).toList()));
        if (!prgIdList.getItems().isEmpty() && prgIdList.getSelectionModel().getSelectedItem() == null) {
            prgIdList.getSelectionModel().selectFirst();
        }

        if (prgList.isEmpty()) {
            heapTable.setItems(FXCollections.observableArrayList());
            outList.setItems(FXCollections.observableArrayList());
            fileTableList.setItems(FXCollections.observableArrayList());
            symTable.setItems(FXCollections.observableArrayList());
            exeStackList.setItems(FXCollections.observableArrayList());
            return;
        }

        PrgState any = prgList.getFirst();

        heapTable.setItems(FXCollections.observableArrayList(any.getHeap().getContent().entrySet()));
        outList.setItems(FXCollections.observableArrayList(any.getOut().getList().stream().map(String::valueOf).toList()));
        fileTableList.setItems(FXCollections.observableArrayList(
                any.getFileTable().getContent().keySet().stream().map(StringValue::getVal).toList()
        ));

        refreshSelected();
    }

    private void refreshSelected() {
        Integer id = prgIdList.getSelectionModel().getSelectedItem();
        if (id == null) return;

        PrgState selected = controller.getPrgList().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (selected == null) return;

        symTable.setItems(FXCollections.observableArrayList(selected.getSymTable().getContent().entrySet()));

        List<IStmt> stack = selected.getExeStack().getContent();
        List<String> topFirst = new ArrayList<>();
        for (int i = stack.size() - 1; i >= 0; i--) {
            topFirst.add(String.valueOf(stack.get(i)));
        }
        exeStackList.setItems(FXCollections.observableArrayList(topFirst));
        symTable.setItems(FXCollections.observableArrayList(selected.getSymTable().getContent().entrySet()));
        symTable.refresh();

    }
}