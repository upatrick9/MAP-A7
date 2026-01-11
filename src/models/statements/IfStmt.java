package models.statements;

import models.PrgState;
import models.adts.MyIStack;
import models.adts.MyIDictionary;
import models.exceptions.ConditionNotBoolean;
import models.exceptions.MyException;
import models.expressions.Exp;
import models.types.Type;
import models.types.BoolType;
import models.values.BoolValue;
import models.values.Value;

public class IfStmt implements IStmt{
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value cond  = exp.eval(symTbl, state.getHeap());
        if(!cond.getType().equals(new BoolType()))
            throw new ConditionNotBoolean();
        BoolValue b = (BoolValue) cond;
        if(b.getValue())
            stk.push(thenS);
        else
            stk.push(elseS);
        return null;
    }

    public MyIDictionary<String, Type> cloneTypeEnv(MyIDictionary<String, Type> typeEnv){
        MyIDictionary<String, Type> newEnv = new models.adts.MyDictionary<>();
        typeEnv.getContent().forEach((k, v) -> newEnv.update(k, v.deepCopy()));
        return newEnv;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp = exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            thenS.typeCheck(cloneTypeEnv(typeEnv));
            elseS.typeCheck(cloneTypeEnv(typeEnv));
            return typeEnv;
        }
        throw new ConditionNotBoolean();
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS, this.elseS);
    }

    @Override
    public String toString() {
        return "If(" + exp.toString() + ") THEN(" + thenS + ") ELSE(" + elseS + ")";
    }
}
