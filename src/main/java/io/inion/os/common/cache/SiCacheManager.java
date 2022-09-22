package io.inion.os.common.cache;

import io.inion.os.common.SiCell;
import io.inion.os.common.types.SiClass;
import io.inion.os.common.types.SiString;

public interface SiCacheManager<C extends SiCell<?, ?>> extends SiCell<C, Void> {

  String CELL_TYPE = "cache-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  <K, V extends SiCell<?, ?>> SiCache<K, V> createCache(SiString cacheName, SiClass<K> key,
      SiClass<V> value);

}
