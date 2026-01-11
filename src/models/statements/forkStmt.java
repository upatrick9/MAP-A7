package models.statements;

import models.adts.*;
import models.PrgState;
import models.exceptions.*;
import models.values.Value;
import models.types.Type;

public class forkStmt implements IStmt {
    private final IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        state.getSymTable().getContent().forEach((k, v) -> newSymTable.update(k, v.deepCopy()));

        return new PrgState(
                new MyStack<>(),
                newSymTable,
                state.getOut(),
                state.getFileTable(),
                state.getHeap(),
                stmt
        );

    }

    private MyIDictionary<String, Type> cloneTypeEnv(MyIDictionary<String, Type> typeEnv) {
        MyIDictionary<String, Type> newEnv = new MyDictionary<>();
        typeEnv.getContent().forEach((k, v) -> newEnv.update(k, v.deepCopy()));
        return newEnv;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typeCheck(cloneTypeEnv(typeEnv));
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
