package view.command;

import controller.Controller;

import models.PrgState;
import models.adts.*;
import models.statements.IStmt;
import models.types.Type;
import models.values.StringValue;
import models.values.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunExample extends Command {
    private final IStmt program;
    private final String logFilePath;
    private final AtomicBoolean displayFlag;


    public RunExample(String key, String desc, IStmt program, String logFilePath, AtomicBoolean displayFlag){
        super(key, desc);
        this.program = program;
        this.logFilePath = logFilePath;
        this.displayFlag = displayFlag;
    }
    @Override
    public void execute(){
        try{
            var typeEnv = new MyDictionary<String, Type>();
            program.typeCheck(typeEnv);

            var stk = new MyStack<IStmt>();
            var sym = new MyDictionary<String, Value>();
            var out = new MyList<Value>();
            var ft = new FileTable<StringValue, BufferedReader>();

            PrgState prg = new PrgState(stk, sym, out, ft, program);
            IRepository repo = new Repository(prg, logFilePath);
            Controller ctr = new Controller(repo);
            ctr.setDisplayFlag(displayFlag.get());

            ctr.allStep();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
