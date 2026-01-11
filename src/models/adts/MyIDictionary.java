package models.adts;

import java.util.Map;

public interface MyIDictionary<K,V> {
    void update(K key, V value);
    boolean isDefined(K key);
    V lookup(K key);
    Map<K,V> getContent();
}
