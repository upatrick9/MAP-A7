package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.MyException;
import models.values.Value;
import models.types.Type;

public class ValueExp implements Exp{
    private final Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public String toString() {
        return e.toString();
    }

}
