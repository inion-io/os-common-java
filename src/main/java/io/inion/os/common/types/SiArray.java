package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiArray.SiArrayObject;

@CellType(
    objectClass = SiArrayObject.class,
    type = "array",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiArray<V> extends SiCell<SiArray<V>, V[]> {

  class SiArrayObject<V> extends SiCellObject<SiArray<V>, V[]> implements SiArray<V> {}
}
