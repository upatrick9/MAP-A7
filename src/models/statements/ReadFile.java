package models.statements;

import models.PrgState;
import models.adts.MyIDictionary;
import models.adts.MyIFileTable;
import models.exceptions.InvalidIntegerOperand;
import models.exceptions.MyException;
import models.exceptions.VariableNotDefined;
import models.expressions.Exp;
import models.types.IntType;
import models.types.StringType;
import models.types.Type;
import models.values.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private final Exp exp;
    private final String varName;

    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symTable.isDefined(varName) ||
                !(symTable.lookup(varName).getType() instanceof IntType))
            throw new MyException(varName + " is not an int variable!");

        Value value = exp.eval(symTable, state.getHeap());
        if (!(value.getType() instanceof StringType))
            throw new MyException("expression is not a string!");

        StringValue fileName = (StringValue) value;

        if (!fileTable.isDefined(fileName))
            throw new MyException("file not opened: " + fileName.getVal());

        BufferedReader br = fileTable.lookup(fileName);

        try {
            String line = br.readLine();
            int number;
            if (line == null || line.trim().isEmpty()){
                number = 0;
            }
            else{
                number = Integer.parseInt(line.trim());
            }

            symTable.update(varName, new IntValue(number));

        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(models.adts.MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!typeEnv.isDefined(varName)) {
            throw new VariableNotDefined(varName);
        }
        Type typeVar = typeEnv.lookup(varName);
        if (!typeVar.equals(new IntType())) {
            throw new InvalidIntegerOperand();
        }
        Type typeExp = exp.typecheck(typeEnv);
        if (!typeExp.equals(new StringType())) {
            throw new MyException("expression is not a string");
        }
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
