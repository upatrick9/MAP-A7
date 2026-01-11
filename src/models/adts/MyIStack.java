package models.adts;

import java.util.List;
import models.exceptions.*;

public interface MyIStack<T> {
    T pop() throws PopEmptyStack;
    void push(T v);
    boolean isEmpty();
    List<T> getContent();
}
