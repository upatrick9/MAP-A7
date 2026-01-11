package models.adts;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements MyIHeap<V>{
    private final Map <Integer, V> content;
    private int freeLocation;

    public MyHeap(){
        content = new HashMap<>();
        freeLocation = 1;
    }

    private int getFreeLocation(){
        while(content.containsKey(freeLocation)){
            freeLocation++;
        }
        return freeLocation;
    }

    @Override
    public int allocate(V value){
        int addr = getFreeLocation();
        content.put(addr, value);
        freeLocation++;
        return addr;
    }

    @Override
    public V get(int address){
        return content.get(address);
    }

    @Override
    public void put(int address, V value){
        content.put(address, value);
    }

    @Override
    public boolean containsKey(int address){
        return content.containsKey(address);
    }

    @Override
    public void remove(int address){
        content.remove(address);
    }

    @Override
    public Map<Integer, V> getContent(){
        return content;
    }

    @Override
    public void setContent(Map<Integer, V> newContent) {
        content.clear();
        content.putAll(newContent);
    }

    @Override
    public String toString(){
        return content.toString();
    }
}
