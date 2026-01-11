package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.values.Value;
import models.types.Type;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws MyException;

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    Exp deepCopy();
}
