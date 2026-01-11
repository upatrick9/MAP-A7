package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.InvalidArithmeticOperator;
import models.exceptions.InvalidIntegerOperand;
import models.exceptions.MyException;
import models.types.BoolType;
import models.types.IntType;
import models.types.Type;
import models.values.*;

public class RelExp implements Exp {
    private final String op;
    private final Exp e1, e2;

    public RelExp(String op, Exp e1, Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws MyException {
        Value v1 = e1.eval(tbl, heap);
        Value v2 = e2.eval(tbl, heap);
        if (!(v1.getType() instanceof IntType) || !(v2.getType() instanceof IntType))
            throw new InvalidIntegerOperand();

        int n1 = ((IntValue)v1).getValue();
        int n2 = ((IntValue)v2).getValue();

        return switch (op) {
            case "<"  -> new BoolValue(n1 <  n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">"  -> new BoolValue(n1 >  n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default   -> throw new InvalidArithmeticOperator();
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = e1.typecheck(typeEnv);
        Type typ2 = e2.typecheck(typeEnv);

        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new BoolType();
            }else{
                throw new InvalidIntegerOperand();
            }
        } else {
            throw new InvalidIntegerOperand();
        }
    }

    @Override
    public Exp deepCopy(){ return new RelExp(op, e1.deepCopy(), e2.deepCopy()); }

    @Override
    public String toString(){ return e1 + " " + op + " " + e2; }
}
