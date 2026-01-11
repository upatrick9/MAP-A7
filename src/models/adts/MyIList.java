package models.adts;

import java.util.List;

public interface MyIList<T> {
    void add(T v);
    List<T> getList();
}
