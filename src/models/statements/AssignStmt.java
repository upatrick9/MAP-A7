package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.exceptions.TypeMismatch;
import models.exceptions.VariableNotDefined;
import models.expressions.Exp;
import models.exceptions.MyException;
import models.types.Type;
import models.values.Value;

public class AssignStmt implements IStmt{
    private final String id;
    private final Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if(!symTable.isDefined(id))
            throw new VariableNotDefined(id);

        Value val = exp.eval(symTable, state.getHeap());
        Type typeId = symTable.lookup(id).getType();

        if(!val.getType().equals(typeId))
            throw new TypeMismatch(id);

        symTable.update(id, val);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(id))
            throw new VariableNotDefined(id);
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(typeExp)){
            return typeEnv;
        }
        throw new TypeMismatch(id);
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.exp.deepCopy());
    }

    @Override
    public String toString(){
        return id + " = " + exp.toString();
    }
}
