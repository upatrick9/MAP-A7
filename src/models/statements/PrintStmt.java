package models.statements;

import models.PrgState;
import models.adts.MyIList;
import models.adts.MyIDictionary;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.values.Value;
import models.types.Type;

public class PrintStmt implements IStmt{
    private final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        out.add(exp.eval(symTable, state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Print(" + exp + ")";
    }
}
