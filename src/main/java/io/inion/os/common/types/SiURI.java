package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.net.URI;

@CellType(
    objectClass = SiURI.SiURIObject.class,
    type = "uri",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiURI extends SiCell<SiURI, URI> {

  class SiURIObject extends SiCellObject<SiURI, URI> implements SiURI {

    @Override
    public SiURI setCellValueAsString(String value) {
      this.cellValue = URI.create(value);

      return getSelf();
    }

    @Override
    public SiURI setCellValueAsObject(Object value) {
      if (!(value instanceof URI)) {
        return getSelf();
      }

      this.cellValue = (URI) value;

      return getSelf();
    }

    @Override
    public String toString() {
      return hasCellValue() ? getCellValue().toString() : "";
    }
  }
}
