package models.adts;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    private final Map<K,V> map = new HashMap<>();

    @Override
    public void update(K key, V value){
        map.put(key,value);
    }

    @Override
    public boolean isDefined(K key){
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key){
        return map.get(key);
    }

    @Override
    public Map<K,V> getContent() {
        return map;
    }

    @Override
    public String toString(){
        return map.toString();
    }
}
