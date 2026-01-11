package models.exceptions;

public class VariableAlreadyExists extends MyException {
    public VariableAlreadyExists() {
        super("Variable already exists");
    }
}
