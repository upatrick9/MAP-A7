package controller;

import models.PrgState;
import models.adts.MyIHeap;
import models.exceptions.*;
import models.values.Value;
import models.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller implements IController{
    private final IRepository repo;

    private boolean displayFlag = false;

    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        List<Integer> reachable = new ArrayList<>(symTableAddr);
        boolean changed;

        do{
            changed = false;
            List<Integer> newAdddr = heap.entrySet().stream()
                    .filter(e -> reachable.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getAddress())
                    .filter(a -> !reachable.contains(a))
                    .collect(Collectors.toList());

            if(!newAdddr.isEmpty()){
                reachable.addAll(newAdddr);
                changed = true;
            }
        }while(changed);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }



    private void oneStepForAllPrg(List<PrgState> prgList) {
        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());

        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new MyException("Interrupted: " + e.getMessage());
                        } catch (ExecutionException e) {
                            throw new MyException("Execution error: " + e.getCause().getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MyException("Interrupted while invoking all: " + e.getMessage());
        }

        prgList.addAll(newPrgList);

        if (!prgList.isEmpty()) {
            MyIHeap<Value> heap = prgList.getFirst().getHeap();
            List<Integer> symTableAddr = prgList.stream()
                    .flatMap(p -> getAddrFromSymTable(p.getSymTable().getContent().values()).stream())
                    .distinct()
                    .collect(Collectors.toList());

            heap.setContent(safeGarbageCollector(symTableAddr, heap.getContent()));
        }

        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        if (displayFlag) {
            prgList.forEach(System.out::println);
        }

        repo.setPrgList(prgList);
    }

    @Override
    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (!prgList.isEmpty()) {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

}
