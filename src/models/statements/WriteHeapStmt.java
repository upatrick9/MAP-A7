package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.MyException;
import models.exceptions.VariableNotDefined;
import models.expressions.Exp;
import models.values.RefValue;
import models.values.Value;
import models.types.Type;
import models.types.RefType;

public class WriteHeapStmt implements IStmt{
    private final String varName;
    private final Exp expression;

    public WriteHeapStmt(String varName, Exp expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        MyIDictionary<String, Value> symTable = prgState.getSymTable();
        MyIHeap<Value> heap = prgState.getHeap();

        if(!symTable.isDefined(varName)){
            throw new MyException("Variable " + varName + " not defined");
        }

        Value varValue = symTable.lookup(varName);
        if(!(varValue instanceof RefValue refVar)){
            throw new MyException("Variable " + varName + " is not a RefValue");
        }

        int addr = refVar.getAddress();
        if(!heap.containsKey(addr)){
            throw new MyException("Address " + addr + " not in heap");
        }

        Value evalResult = expression.eval(symTable, heap);
        Type locationType = refVar.getLocationType();

        if(!evalResult.getType().equals(locationType)){
            throw new MyException("Type mismatch in wH(" + varName + ", exp): "
                    + evalResult.getType() + " != " + locationType);
        }

        heap.put(addr, evalResult);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!typeEnv.isDefined(varName)) {
            throw new VariableNotDefined(varName);
        }
        Type typeVar = typeEnv.lookup(varName);
        if (!(typeVar instanceof RefType refT)) {
            throw new MyException(varName + " is not a Ref type");
        }
        Type typeExp = expression.typecheck(typeEnv);
        if (refT.getInner().equals(typeExp)) {
            return typeEnv;
        }
        throw new MyException("Right hand side and left hand side have different types");
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "wH(" + varName + ", " + expression.toString() + ")";
    }
}
