package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;

@CellType(
    objectClass = SiProperty.SiPropertyObject.class,
    type = "property",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiProperty extends SiCell<SiProperty, SiString> {

  SiString getKey();

  SiProperty setKey(SiString key);

  String[] getStringValues();

  class SiPropertyObject extends SiCellObject<SiProperty, SiString> implements
      SiProperty {

    @Cell
    private SiString key;

    @Override
    public SiString getKey() {
      return key;
    }

    @Override
    public boolean hasCellValue() {
      return getKey() != null && getKey().hasCellValue() && getCellValue() != null
          && getCellValue().hasCellValue();
    }

    @Override
    public SiProperty setKey(SiString key) {
      return swapSubCell(this.key, key);
    }

    @Override
    public String[] getStringValues() {
      return hasCellValue() ? getCellValue().getCellValue().split("," ) : new String[0];
    }
  }
}
