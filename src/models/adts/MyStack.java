package models.adts;

import models.exceptions.PopEmptyStack;

import java.util.Stack;
import java.util.List;

public class MyStack<T> implements MyIStack<T>{
    private final Stack<T> stack = new Stack<>();

    @Override
    public T pop() throws PopEmptyStack {
        if(stack.isEmpty())
            throw new PopEmptyStack();
        return stack.pop();
    }

    @Override
    public void push(T v){
        stack.push(v);
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public List<T> getContent(){
        return stack;
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
