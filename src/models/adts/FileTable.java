package models.adts;

import java.util.Map;
import java.util.HashMap;

public class FileTable<K,V> implements MyIFileTable<K,V> {
    private final Map<K,V> table = new HashMap<>();

    @Override
    public void put(K k, V v){
        table.put(k,v);
    }

    @Override
    public boolean isDefined(K k){
        return table.containsKey(k);
    }

    @Override
    public V lookup(K k){
        return table.get(k);
    }

    @Override
    public void remove(K k){
        table.remove(k);
    }

    @Override
    public Map<K,V> getContent(){
        return table;
    }
}
