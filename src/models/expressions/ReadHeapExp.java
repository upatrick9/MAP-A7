package models.expressions;

import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.MyException;
import models.types.RefType;
import models.types.Type;
import models.values.RefValue;
import models.values.Value;


public class ReadHeapExp implements Exp{
    private final Exp exp;

    public ReadHeapExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Value> heap) throws MyException{
        Value v = exp.eval(tbl, heap);
        if(!(v instanceof RefValue refV)){
            throw new MyException("rH argument is not a RefValue: " + v);
        }
        int addr = refV.getAddress();
        if(!heap.containsKey(addr)){
            throw new MyException("Address " + addr + " not in heap");
        }
        return heap.get(addr);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType refT){
            return refT.getInner();
        }
        throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
