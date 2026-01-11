package models.exceptions;

public class InvalidBooleanOperand extends MyException {
    public InvalidBooleanOperand() {
        super("Invalid, operand not boolean");
    }
}
