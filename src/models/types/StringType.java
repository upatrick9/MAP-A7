package models.types;

import models.values.StringValue;
import models.values.Value;

public class StringType implements Type {
    @Override
    public Value defaultValue() { return new StringValue(""); }
    @Override
    public boolean equals(Object obj){ return obj instanceof StringType; }
    @Override
    public String toString(){ return "string"; }
    @Override
    public Type deepCopy() { return new StringType(); }
}
