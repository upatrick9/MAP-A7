package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.values.Value;
import models.types.Type;

public class VarExp implements Exp{
    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws MyException{
        if(!tbl.isDefined(id))
            throw new VariableNotDefined(id);
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!typeEnv.isDefined(id))
            throw new VariableNotDefined(id);
        return typeEnv.lookup(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
