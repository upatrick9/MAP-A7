package models.adts;

import java.util.Map;

public interface MyIHeap<V> {
    int allocate(V value);
    V get(int address);
    void put(int address, V value);
    boolean containsKey(int address);
    void remove(int address);

    Map<Integer, V> getContent();
    void setContent(Map<Integer, V> newContent);
}
