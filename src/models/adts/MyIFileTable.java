package models.adts;
import java.util.Map;

public interface MyIFileTable<K,V> {
    void put(K key, V value);
    boolean isDefined(K key);
    V lookup(K key);
    void remove(K key);
    Map<K,V> getContent();
}
