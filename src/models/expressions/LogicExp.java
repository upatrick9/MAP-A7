package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.types.Type;
import models.types.BoolType;
import models.values.BoolValue;
import models.values.Value;

public class LogicExp implements Exp{
    private final String op;
    private final Exp exp1,exp2;

    public LogicExp(String op, Exp exp1, Exp exp2){
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws MyException {
        Value v1 = exp1.eval(tbl, heap);
        if(!v1.getType().equals(new BoolType())){
            throw new InvalidBooleanOperand();
        }
        Value v2 = exp2.eval(tbl, heap);
        if(!v2.getType().equals(new BoolType())){
            throw new InvalidBooleanOperand();
        }

        boolean b1 = ((BoolValue)v1).getValue();
        boolean b2 = ((BoolValue)v2).getValue();

        return switch(op) {
            case "and" -> new BoolValue(b1 && b2);
            case "or" -> new BoolValue(b1 || b2);
            default -> throw new InvalidLogicOperator();
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = exp1.typecheck(typeEnv);
        Type typ2 = exp2.typecheck(typeEnv);

        if(typ1.equals(new BoolType())){
            if(typ2.equals(new BoolType())){
                return new BoolType();
            }else{
                throw new InvalidBooleanOperand();
            }
        }else{
            throw new InvalidBooleanOperand();
        }
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(op, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }
}
