package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.expressions.Exp;
import models.types.RefType;
import models.types.Type;
import models.values.RefValue;
import models.values.Value;

public class NewStmt implements IStmt{
    private final String varName;
    private final Exp expression;

    public NewStmt(String varName, Exp expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        MyIDictionary<String, Value> symTable = prgState.getSymTable();
        MyIHeap<Value> heap = prgState.getHeap();

        if(!symTable.isDefined(varName)){
            throw new VariableNotDefined(varName);
        }

        Value varValue = symTable.lookup(varName);
        if(!(varValue.getType() instanceof RefType refType)){
            throw new MyException("Variable " + varName + " is not a ref type");
        }

        Value evalResult = expression.eval(symTable, heap);

        Type locationType = refType.getInner();
        if(!evalResult.getType().equals(locationType)){
            throw new MyException("Type mismatch in new(" + varName + ", exp): "
                    + evalResult.getType() + " != " + locationType);
        }
        int addr = heap.allocate(evalResult);
        symTable.update(varName, new RefValue(addr, locationType));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        if(!typeEnv.isDefined(varName)){
            throw new VariableNotDefined(varName);
        }
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp))){
            return typeEnv;
        }
        throw new MyException("Right hand side and left hand side have different types");
    }

    @Override
    public IStmt deepCopy(){
        return new NewStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + varName + ", " + expression.toString() + ")";
    }

}
