package models.types;

import models.values.Value;

public interface Type {
    boolean equals(Object another);
    Value defaultValue();
    String toString();
    Type deepCopy();
}
