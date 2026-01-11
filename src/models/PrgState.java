package models;

import models.adts.*;
import models.exceptions.*;
import models.statements.IStmt;
import java.io.BufferedReader;
import models.values.StringValue;
import models.values.Value;

public class PrgState {
    private static int lastId = 0;

    private static synchronized int newId() {
        lastId++;
        return lastId;
    }

    private final int id;

    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private final MyIFileTable<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;
    private final MyIHeap<Value> heap;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIFileTable<StringValue, BufferedReader> fileTable, IStmt originalProgram) {
        this.id = newId();
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = new MyHeap<>();
        exeStack.push(originalProgram);
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIFileTable<StringValue, BufferedReader> fileTable, MyIHeap<Value> heap,IStmt originalProgram) {
        this.id = newId();
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = heap;
        exeStack.push(originalProgram);
    }

    public int getId() {
        return id;
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new EmptyStack();
        }
        IStmt crtStmt;
        try {
            crtStmt = exeStack.pop();
        } catch (PopEmptyStack e) {
            throw new MyException(e.getMessage());
        }
        return crtStmt.execute(this);
    }


    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Value> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                "\nStack: " + exeStack +
                "\nSymTable: " + symTable +
                "\nHeap: " + heap +
                "\nOut: " + out +
                "\nFileTable: " + fileTable +
                "\n";
    }
}
