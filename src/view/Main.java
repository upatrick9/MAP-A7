package view;

import models.expressions.*;
import models.statements.*;
import models.types.*;
import models.values.*;
import view.command.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        AtomicBoolean displayFlag = new AtomicBoolean(false);

        IStmt ex1 =
                new CompStmt(new VarDeclStmt("varf", new StringType()),
                        new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                                new CompStmt(new OpenRFile(new VarExp("varf")),
                                        new CompStmt(new VarDeclStmt("varc", new IntType()),
                                                new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                                new CloseRFile(new VarExp("varf"))))))))));

        IStmt ex2 =
                new PrintStmt(new RelExp(">", new ArithExp('+', new ValueExp(new IntValue(2)),
                        new ValueExp(new IntValue(3))),
                        new ValueExp(new IntValue(4))));

        IStmt ex3 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(4))),
                                new CompStmt(
                                        new WhileStmt(
                                                new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v",
                                                                new ArithExp('-',
                                                                        new VarExp("v"),
                                                                        new ValueExp(new IntValue(1))))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                );

        IStmt ex4 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new NewStmt("a", new VarExp("v")),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new VarExp("a"))
                                                )
                                        )
                                )
                        )
                );

        IStmt ex5 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                        new CompStmt(
                                                new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(
                                                        new ArithExp('+',
                                                                new ReadHeapExp(new VarExp("v")),
                                                                new ValueExp(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                );

        IStmt ex6 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(0))),
                                new CompStmt(
                                        new WhileStmt(
                                                new RelExp("<",
                                                        new VarExp("v"),
                                                        new ValueExp(new IntValue(3))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v",
                                                                new ArithExp('+',
                                                                        new VarExp("v"),
                                                                        new ValueExp(new IntValue(1))))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                );

        IStmt ex7 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(
                                        new AssignStmt("v", new ValueExp(new IntValue(10))),
                                        new CompStmt(
                                                new NewStmt("a", new ValueExp(new IntValue(22))),
                                                new CompStmt(
                                                        new forkStmt(
                                                                new CompStmt(
                                                                        new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                                        new CompStmt(
                                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("v")),
                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                        )
                                                )
                                        )
                                )
                        )
                );

        IStmt ex8 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new AssignStmt("v", new ValueExp(new BoolValue(true)))
                );

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new SetDisplayCommand("d", "toggle display (1.On / 2.Off)", displayFlag));
        menu.addCommand(new RunExample("1", ex1.toString(), ex1, "log1.txt", displayFlag));
        menu.addCommand(new RunExample("2", ex2.toString(), ex2, "log2.txt", displayFlag));
        menu.addCommand(new RunExample("3", ex3.toString(), ex3, "log3.txt", displayFlag));
        menu.addCommand(new RunExample("4", ex4.toString(), ex4, "log4.txt", displayFlag));
        menu.addCommand(new RunExample("5", ex5.toString(), ex5, "log5.txt", displayFlag));
        menu.addCommand(new RunExample("6", ex6.toString(), ex6, "log6.txt", displayFlag));
        menu.addCommand(new RunExample("7", ex7.toString(), ex7, "log7.txt", displayFlag));
        menu.addCommand(new RunExample("8", ex8.toString(), ex8, "log8.txt", displayFlag));
        menu.show();
    }
}