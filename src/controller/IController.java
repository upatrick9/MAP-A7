package controller;

import models.PrgState;
import models.exceptions.*;

import java.util.List;

public interface IController {
    public void allStep() throws MyException;


    List<PrgState> getPrgList();
}
