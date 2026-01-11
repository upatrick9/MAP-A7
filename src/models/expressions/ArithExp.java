package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.types.IntType;
import models.types.Type;
import models.values.IntValue;
import models.values.Value;

public class ArithExp implements Exp {
    private final char op;
    private final Exp exp1, exp2;

    public ArithExp(char op, Exp exp1, Exp exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl,MyIHeap<Value> heap) throws InvalidIntegerOperand, InvalidArithmeticOperator, DivisionByZero {
        Value v1 = exp1.eval(tbl,heap);
        if(!v1.getType().equals(new IntType())) {
            throw new InvalidIntegerOperand();
        }
        Value v2 = exp2.eval(tbl,heap);
        if(!v2.getType().equals(new IntType())) {
            throw new InvalidIntegerOperand();
        }

        int n1 = ((IntValue)v1).getValue();
        int n2 = ((IntValue)v2).getValue();

        return switch (op){
            case '+' -> new IntValue(n1 + n2);
            case '-' -> new IntValue(n1 - n2);
            case '*' -> new IntValue(n1 * n2);
            case '/' -> {
                if(n2 == 0) {
                    throw new DivisionByZero();
                }
                yield new IntValue(n1 / n2);
            }
            default -> throw new InvalidArithmeticOperator();
        };

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = exp1.typecheck(typeEnv);
        Type typ2 = exp2.typecheck(typeEnv);

        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new IntType();
            }else{
                throw new InvalidIntegerOperand();
            }
        } else{
            throw new InvalidIntegerOperand();
        }
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return exp1.toString() + op + exp2.toString();
    }
}
