package models.values;

import models.types.BoolType;
import models.values.BoolValue;
import models.types.Type;

public class BoolValue implements Value {
    private final boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof BoolValue other)) return false;
        return this.val == other.val;
    }


    public boolean getValue() {
        return val;
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
