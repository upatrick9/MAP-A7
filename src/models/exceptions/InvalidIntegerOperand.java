package models.exceptions;

public class InvalidIntegerOperand extends MyException {
    public InvalidIntegerOperand() {
        super("Invalid, operand not integer");
    }
}
