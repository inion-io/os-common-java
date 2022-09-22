package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.net.MalformedURLException;
import java.net.URL;
import io.inion.os.common.types.SiURL.SiURLObject;

@CellType(
    objectClass = SiURLObject.class,
    type = "url",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiURL extends SiCell<SiURL, URL> {

  class SiURLObject extends SiCellObject<SiURL, URL> implements SiURL {

    @Override
    public SiURL setCellValueAsString(String value) {
      if (value == null || value.isEmpty()) {
        return getSelf();
      }
      try {
        this.cellValue = new URL(value);
      } catch (MalformedURLException malformedURLException)  {
      }

      return getSelf();
    }
  }
}
