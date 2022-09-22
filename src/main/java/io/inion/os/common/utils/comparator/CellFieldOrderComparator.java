package io.inion.os.common.utils.comparator;

import io.inion.os.common.annotation.Cell;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

public class CellFieldOrderComparator implements Comparator<Field>, Serializable {

  private boolean _asc;

  public CellFieldOrderComparator() {
    _asc = false;
  }

  public CellFieldOrderComparator(boolean asc) {
    _asc = asc;
  }

  public int compare(Field field1, Field field2) {

    int value = Integer.compare(field1.getAnnotation(Cell.class).order(),
        field2.getAnnotation(Cell.class).order());

    if (_asc) {
      return value;
    }
    return -value;

  }
}