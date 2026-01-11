package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.exceptions.*;
import models.types.Type;


public interface IStmt {
    PrgState execute(PrgState state) throws MyException;

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    IStmt deepCopy();
}
