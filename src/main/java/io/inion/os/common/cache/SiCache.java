package io.inion.os.common.cache;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.types.SiString;

public interface SiCache<K, V extends SiCell<?, ?>> extends SiCell<SiCache<K, V>, Void> {

  String CELL_TYPE = "cache";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  V get(K key);

  SiString getCacheName();

  void put(K key, V value);

  SiCache<K, V> setCacheName(SiString cacheName);

  abstract class SiCacheObject<K, V extends SiCell<?, ?>> extends
      SiCellObject<SiCache<K, V>, Void> implements SiCache<K, V> {

    @Cell
    private SiString cacheName;

    @Override
    public SiString getCacheName() {
      return cacheName;
    }

    @Override
    public SiCache<K, V> setCacheName(SiString cacheName) {
      return swapSubCell(this.cacheName, cacheName);
    }
  }
}
