package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiClass.SiClassObject;

@CellType(
    objectClass = SiClassObject.class,
    type = "class",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiClass<T> extends SiCell<SiClass<T>, Class<T>> {

  class SiClassObject<T> extends SiCellObject<SiClass<T>, Class<T>> implements SiClass<T> {

  }
}
