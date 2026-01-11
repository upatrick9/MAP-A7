package models.exceptions;

public class EmptyStack extends MyException {
    public EmptyStack() {
        super("Stack is empty");
    }
}
