package io.inion.os.common.discovery.properties;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiProperty;
import io.inion.os.common.types.SiString;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@CellType(
    objectClass = SiPropertiesManager.SiPropertiesManagerObject.class,
    type = SiPropertiesManager.CELL_TYPE,
    uuid = SiPropertiesManager.CELL_UUID
)
public interface SiPropertiesManager extends
    SiCell<SiPropertiesManager, SiList<SiRemoteDiscovery>> {

  String CELL_TYPE = "properties-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiProperty getProperty(SiString key);

  SiList<SiProperty> getProperties(SiString key);

  Map<String, Map<String, String>> getPropertiesMap(SiString key);

  class SiPropertiesManagerObject extends
      SiCellObject<SiPropertiesManager, SiList<SiRemoteDiscovery>> implements
      SiPropertiesManager {

    Properties properties = new Properties();

    @Override
    public SiPropertiesManager create() {
      try {
        properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        log().info("Reading application.properties");
      } catch (Exception e) {
        log().warn(
            "Application configuration file not found. Running discovery with default settings.");
      }

      setCreated(true);

      return this.getSelf();
    }

    @Override
    public SiProperty getProperty(SiString key) {
      SiProperty property = createTransientCell(SiProperty.class);
      property.setKey(key);
      property.setCellValue(
          createTransientCell(SiString.class, properties.getProperty(key.getCellValue())));

      return property;
    }

    @Override
    public SiList<SiProperty> getProperties(SiString key) {
      SiList<SiProperty> list = createTransientCell(SiList.class);

      for (String name : properties.stringPropertyNames()) {
        if (name.startsWith(key.getCellValue())) {
          SiProperty property = createTransientCell(SiProperty.class);
          property.setKey(createTransientCell(SiString.class, name));
          property.setCellValue(createTransientCell(SiString.class, properties.getProperty(name)));

          list.add(property);
        }
      }

      return list;
    }

    @Override
    public Map<String, Map<String, String>> getPropertiesMap(SiString key) {
      SiList<SiProperty> properties = getProperties(key);

      Map<String, Map<String, String>> storesPropertiesMap = new HashMap<>();

      if (properties.hasCellValue()) {

        for (SiProperty property : properties.getCellValue()) {
          String storeKey = "default";
          String propertyKey;
          String[] parts = property.getKey().getCellValue().split("\\.");

          if (parts.length < 4) {
            continue;
          } else if (parts.length == 4) {
            propertyKey = parts[3];
          } else {
            storeKey = parts[3];
            propertyKey = parts[4];
          }

          Map<String, String> storePropertiesMap = storesPropertiesMap.computeIfAbsent(storeKey,
              k -> new HashMap<>());

          storePropertiesMap.put(propertyKey, property.getCellValue().toString());
        }
      }

      return storesPropertiesMap;

    }
  }
}
