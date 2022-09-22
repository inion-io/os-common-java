package io.inion.os.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.cache.SiCacheManager;
import io.inion.os.common.communication.SiCommunicationManager;
import io.inion.os.common.data.store.SiDataStoreManager;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistry;
import io.inion.os.common.discovery.banner.SiConsoleBanner;
import io.inion.os.common.discovery.context.SiContextScanner;
import io.inion.os.common.discovery.environment.SiEnvironment;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.discovery.messages.SiDiscoveryInitialized;
import io.inion.os.common.discovery.messages.SiDiscoveryRestored;
import io.inion.os.common.discovery.properties.SiPropertiesManager;
import io.inion.os.common.discovery.registry.SiCellRegistry;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryManager;
import io.inion.os.common.exception.CellDeleteException;
import io.inion.os.common.exception.CellSaveException;
import io.inion.os.common.logging.SiLogManager;
import io.inion.os.common.logging.SiLogger;
import io.inion.os.common.messaging.eventbus.SiEventBus;
import io.inion.os.common.types.SiBoolean;
import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiProperty;
import io.inion.os.common.types.SiString;
import java.net.URI;
import java.util.UUID;

public interface SiDiscoveryManager extends
    SiCell<SiDiscoveryManager, Void> {

  String CELL_TYPE = "discovery-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiString getDiscoveryName();

  SiString getHost();

  SiInteger getPort();

  SiBoolean isInitialized();

  SiBoolean isRestored();

  SiLogger getLogger(SiCell<?, ?> cell);

  SiProperty getProperty(SiString key);

  SiList<SiProperty> getProperties(SiString key);

  void processSave(SiCell<?, ?> cell) throws CellSaveException;

  void processDelete(SiCell<?, ?> cell) throws CellDeleteException;

  void restore(String... args);

  class SiDiscoveryManagerObject extends SiCellObject<SiDiscoveryManager, Void> implements
      SiDiscoveryManager {

    private SiCellAnalyzerRegistry cellAnalyzerRegistry;
    private SiCellInjector cellInjector;
    private SiCellRegistry cellRegistry;
    @Cell(type = SiCommunicationManager.CELL_TYPE)
    private SiCommunicationManager<?> communicationManager;
    @Cell
    private SiConsoleBanner consoleBanner;
    private SiContextScanner contextScanner;
    @Cell
    private SiDiscoveryInitialized discoveryInitialized;
    @Cell(prop = "discovery.name", value = "Inion OS Discovery")
    private SiString discoveryName;
    @Cell
    private SiDiscoveryRestored discoveryRestored;
    private SiEnvironment environment;
    @Cell(type = SiEventBus.CELL_TYPE)
    private SiEventBus<?> eventBus;
    @Cell(type = SiCacheManager.CELL_TYPE)
    private SiCacheManager<?> cacheManager;
    private SiBoolean initialized;
    @Cell
    private SiRemoteDiscoveryManager remoteDiscoveryManager;
    private SiDataStoreManager dataStoreManager;
    private SiPropertiesManager propertiesManager;
    private SiBoolean restored;
    private SiLogManager logManager;

    @Override
    public void afterCreate() {
      getSubCells().forEach(subCell -> {
        if (!subCell.isCreated()) {
          subCell.create();
        }
      });

      cellInjector.finish();
      cellAnalyzerRegistry.finish();

      String cellURI = "discovery://"
          + environment.getHost().getCellValue()
          + ":"
          + environment.getPort().getCellValue();

      setCellURI(URI.create(cellURI));

      getSubCells().forEach(SiCell::buildCellURI);

      if (communicationManager != null) {
        if (log().isInfoEnabled()) {
          log().info("Start Communication Channel on Port " + environment.getPort().getCellValue());
        }

        communicationManager.listen(environment.getHost(), environment.getPort());
        remoteDiscoveryManager.start();
      }

      initialized.setCellValue(Boolean.TRUE);

      if (log().isInfoEnabled()) {
        log().info("Discovery Initialized");
      }

      consoleBanner.print();

      if (eventBus != null) {
        eventBus.publish(discoveryInitialized);
      }
    }

    @Override
    public SiDiscoveryManager create() {
      setRootCell(this);
      setCellName("discovery-manager");
      setCellURI(URI.create("localhost"));
      setCellClass(SiDiscoveryManager.class);
      setCellType(SiDiscoveryManager.CELL_TYPE);
      setCellUUID(UUID.fromString(SiDiscoveryManager.CELL_UUID));

      logManager = createCell(SiLogManager.class, "logManager");

      if (log().isInfoEnabled()) {
        log().info("Start Discovery Initialization");
      }

      propertiesManager = createCell(SiPropertiesManager.class, "propertiesManager");
      initialized = buildCell(SiBoolean.class, "initialized").setCellValue(false);
      restored = buildCell(SiBoolean.class, "restored").setCellValue(false);

      contextScanner = createCell(SiContextScanner.class, "contextScanner");
      cellInjector = createCell(SiCellInjector.class, "cellInjector");

      // TODO: BasePackages für Context Scanner müssen noch dynamischer implementiert werden
      contextScanner.addBasePackage(buildTransientCell(SiString.class, "io.inion"));

      cellAnalyzerRegistry = createCell(SiCellAnalyzerRegistry.class, "cellAnalyzerRegistry");
      cellRegistry = createCell(SiCellRegistry.class, "cellRegistry");
      environment = createCell(SiEnvironment.class, "environment");
      dataStoreManager = createCell(SiDataStoreManager.class, "dataStoreManager");

      return super.create();
    }

    @Override
    public SiString getDiscoveryName() {
      return discoveryName;
    }

    @Override
    public SiString getHost() {
      return environment.getHost();
    }

    @Override
    public SiInteger getPort() {
      return environment.getPort();
    }

    @Override
    public SiBoolean isInitialized() {
      return initialized;
    }

    @Override
    public SiBoolean isRestored() {
      return restored;
    }

    @Override
    public SiLogger getLogger(SiCell<?, ?> cell) {
      if (logManager == null) {
        return null;
      }

      return logManager.getLogger(cell);
    }

    @Override
    public SiProperty getProperty(SiString key) {
      return propertiesManager.getProperty(key);
    }

    @Override
    public SiList<SiProperty> getProperties(SiString key) {
      return propertiesManager.getProperties(key);
    }

    @Override
    public void processSave(SiCell<?, ?> cell) throws CellSaveException {
      if (cell.isTransient()) {
        throw new CellSaveException("Transient cells are not savable.");
      }
      if (!this.isInitialized().getCellValue()) {
        throw new CellSaveException(new IllegalStateException());
      }

      try {
        dataStoreManager.processSave(cell);
      } catch (Exception e) {
        log().error(e);

        throw new CellSaveException(e);
      }
    }

    @Override
    public void processDelete(SiCell<?, ?> cell) throws CellDeleteException {
      try {
        if (this.isInitialized().getCellValue() && !cell.isTransient()) {
          dataStoreManager.processDelete(cell);
        }
      } catch (Exception e) {
        log().error(e);

        throw new CellDeleteException(e);
      }
    }

    @Override
    public SiLogger log() {
      return getLogger(this);
    }

    @Override
    public void restore(String... args) {
      if (log().isInfoEnabled()) {
        log().info("Start Discovery Restore");
      }

      restored.setCellValue(Boolean.TRUE);

      if (log().isInfoEnabled()) {
        log().info("Discovery Restored");
      }

      if (eventBus != null) {
        eventBus.publish(discoveryRestored);
      }
    }

    @Override
    public JsonObject toJsonObject() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty(SiCell.PROPERTY_CELL_NAME, getCellName());
      jsonObject.addProperty(SiCell.PROPERTY_CELL_TYPE, getCellType());
      jsonObject.addProperty(SiCell.PROPERTY_CELL_UUID, getCellUUID().toString());
      jsonObject.addProperty(SiCell.PROPERTY_CELL_CLASS, getCellClass().getName());
      jsonObject.addProperty(SiCell.PROPERTY_CELL_URI,
          getCellURI() != null ? getCellURI().toString() : "");
      jsonObject.addProperty(SiCell.PROPERTY_CELL_VALUE, getCellValueAsString());

      if (getSubCells().size() > 0) {
        JsonArray subCellsJsonArray = new JsonArray();
        jsonObject.add(SiCell.PROPERTY_SUB_CELLS, subCellsJsonArray);

        getSubCells().forEach(subCell -> {
          if (subCell.getCellType() != SiCellReference.CELL_TYPE) {
            subCellsJsonArray.add(subCell.toJsonObject());
          }
        });
      }

      return jsonObject;
    }
  }
}
