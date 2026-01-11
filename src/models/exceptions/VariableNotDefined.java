package models.exceptions;

public class VariableNotDefined extends MyException {
    public VariableNotDefined(String id) {
        super("Variable " + id + " not defined");
    }
}
