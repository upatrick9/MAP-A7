package repository;

import models.PrgState;
import models.exceptions.MyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgList = new ArrayList<>();

    private final String logFilePath;

    public Repository(PrgState prg, String logFilePath) {
        prgList.add(prg);
        this.logFilePath = logFilePath;
        try(PrintWriter pw = new PrintWriter(this.logFilePath)) {
            pw.print("");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgList = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws MyException {
        try (PrintWriter logFile =
                     new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {

            logFile.println("Id:");
            logFile.println(prg.getId());

            logFile.println("ExeStack:");
            logFile.println(prg.getExeStack().toString());

            logFile.println("SymTable:");
            prg.getSymTable().getContent().forEach((k, v) -> logFile.println(k + " --> " + v));

            logFile.println("Heap:");
            prg.getHeap().getContent().forEach((k, v) -> logFile.println(k + " --> " + v));

            logFile.println("Out:");
            prg.getOut().getList().forEach(o -> logFile.println(o));

            logFile.println("FileTable:");
            prg.getFileTable().getContent().keySet().forEach(k -> logFile.println(k.getVal()));

            logFile.println();
        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }

}
