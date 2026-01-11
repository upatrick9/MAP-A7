package models.exceptions;

public class TypeMismatch extends MyException {
    public TypeMismatch(String id) {
        super("Type mismatch for variable " + id);
    }
}
