package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.UUID;

@CellType(
    objectClass = SiUUID.SiUUIDObject.class,
    type = "uuid",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiUUID extends SiCell<SiUUID, UUID> {

  class SiUUIDObject extends SiCellObject<SiUUID, UUID> implements SiUUID {

  }
}
