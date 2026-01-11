package repository;

import models.PrgState;
import models.exceptions.*;

import java.util.List;

public interface IRepository {

    void logPrgStateExec(PrgState prg) throws MyException;

    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}
