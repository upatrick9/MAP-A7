package models.values;

import models.types.Type;

public interface Value {
    Type getType();
    String toString();
    Value deepCopy();
}
