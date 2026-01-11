package models.types;

import models.values.IntValue;
import models.values.Value;


public class IntType implements Type {
    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
