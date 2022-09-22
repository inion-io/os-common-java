package io.inion.os.common.types;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiKeyValuePair.SiKeyValuePairObject;

@CellType(
    objectClass = SiKeyValuePairObject.class,
    type = "key-value-pair",
    uuid = "9FF8DA22-9C7E-46DB-B782-1D04FC5BFD26"
)
public interface SiKeyValuePair extends SiCell<SiKeyValuePair, JsonObject> {

  SiString getKey();

  SiString getKeyValue();

  JsonObject getCellValue();

  SiKeyValuePair setKey(SiString key);

  SiKeyValuePair setKey(String key);

  SiKeyValuePair setKeyValue(SiString keyValue);

  SiKeyValuePair setKeyValue(String keyValue);

  class SiKeyValuePairObject extends SiCellObject<SiKeyValuePair, JsonObject> implements
      SiKeyValuePair {

    @Cell
    private SiString key;
    @Cell
    private SiString keyValue;

    @Override
    public SiString getKey() {
      return key;
    }

    @Override
    public SiString getKeyValue() {
      return keyValue;
    }

    @Override
    public JsonObject getCellValue() {
      return cellValue;
    }

    @Override
    public boolean hasCellValue() {
      return getKey() != null && getKey().hasCellValue() && getKeyValue() != null
          && getKeyValue().hasCellValue();
    }

    @Override
    public SiKeyValuePair setKey(String key) {
      this.key.setCellValue(key);
      return this.getSelf();
    }

    @Override
    public SiKeyValuePair setKey(SiString key) {
      return this.swapSubCell(this.key, key);
    }

    @Override
    public SiKeyValuePair setKeyValue(String keyValue) {
      this.keyValue.setCellValue(keyValue);
      return this.getSelf();
    }

    @Override
    public SiKeyValuePair setKeyValue(SiString keyValue) {
      return this.swapSubCell(this.keyValue, keyValue);
    }
  }
}
