package models.values;

import models.types.IntType;
import models.types.Type;

public class IntValue implements Value{
    private final int val;

    public IntValue(int v) {
        this.val = v;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof IntValue other)) return false;
        return this.val == other.val;
    }


    public int getValue(){
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString(){
        return Integer.toString(val);
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
