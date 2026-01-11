package models.exceptions;

import models.exceptions.MyException;

public class PopEmptyStack extends MyException{
    public PopEmptyStack(){
        super("Can't pop empty stack");
    }
}
