package models.values;

import models.types.StringType;
import models.types.Type;

public class StringValue implements Value {
    private final String val;
    public StringValue(String v){ this.val = v; }
    public String getVal(){ return val; }

    @Override
    public Type getType(){ return new StringType(); }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof StringValue other)) return false;
        return val.equals(other.val);
    }

    @Override
    public String toString(){ return "\"" + val + "\""; }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
