package models.types;

import models.values.RefValue;
import models.values.Value;

public class RefType implements Type{
    private final Type inner;

    public RefType(Type inner){
        this.inner = inner;
    }

    public Type getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if(another == this)
            return true;
        if(!(another instanceof RefType other))
            return false;
        return inner.equals(other.inner);
    }

    @Override
    public Value defaultValue(){
        return new RefValue(0, inner);
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Type deepCopy(){
        return new RefType(inner.deepCopy());
    }
}
