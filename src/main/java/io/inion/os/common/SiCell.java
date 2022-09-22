package io.inion.os.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.analyzer.SiCellAnalyzerRegistry;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.discovery.registry.SiCellRegistry;
import io.inion.os.common.discovery.registry.SiCellRegistryEntry;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryClass;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeName;
import io.inion.os.common.discovery.registry.SiCellRegistryEntryTypeUUID;
import io.inion.os.common.exception.CellDeleteException;
import io.inion.os.common.exception.CellDestroyException;
import io.inion.os.common.exception.CellExportException;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.exception.CellSaveException;
import io.inion.os.common.exception.DuplicatedSubCellException;
import io.inion.os.common.logging.SiLogger;
import io.inion.os.common.utils.CellHelper;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SiCell<C extends SiCell<?, ?>, V> extends Serializable {

  String PROPERTY_CELL_NAME = "cellName";
  String PROPERTY_CELL_TYPE = "cellType";
  String PROPERTY_CELL_UUID = "cellUUID";
  String PROPERTY_CELL_URI = "cellURI";
  String PROPERTY_CELL_CLASS = "cellClass";
  String PROPERTY_CELL_VALUE = "cellValue";
  String PROPERTY_SUB_CELLS = "subCells";
  String PROPERTY_CELL_DISPLAY_NAME = "cellDisplayName";

  void addSubCell(SiCell<?, ?> cell) throws DuplicatedSubCellException;

  void afterAddSubCell(SiCell<?, ?> cell);

  void afterBuild();

  void afterCellsSet();

  void afterCreate();

  <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass);

  <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass, String name);

  <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> buildCell(String cellType, String name);

  SiCell<?, ?> buildCell(String cellType, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> buildCell(UUID cellUUID, String name);

  SiCell<?, ?> buildCell(UUID cellUUID, SiCell<?, ?> parentCell, String name);

  void buildCellURI();

  <T extends SiCell<?, ?>> T buildTransientCell(Class<T> cellClass);

  SiCell<?, ?> buildTransientCell(String cellType);

  SiCell<?, ?> buildTransientCell(UUID cellUUID);

  <T extends SiCell<?, ?>> T buildTransientCell(Class<T> cellClass, Object value);

  SiCell<?, ?> buildTransientCell(String cellType, Object value);

  SiCell<?, ?> buildTransientCell(UUID cellUUID, Object value);

  C buildTransientInstance();

  C create();

  <T extends SiCell<?, ?>> T createCell(Class<T> cellClass);

  <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, SiCell<?, ?> parentCell);

  <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, String name);

  <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> createCell(String cellType, String name);

  SiCell<?, ?> createCell(String cellType, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> createCell(UUID cellUUID, String name);

  SiCell<?, ?> createCell(UUID cellUUID, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> createCell(String cellType);

  SiCell<?, ?> createCell(String cellType, SiCell<?, ?> parentCell);

  SiCell<?, ?> createCell(UUID cellUUID);

  SiCell<?, ?> createCell(UUID cellUUID, SiCell<?, ?> parentCell);

  <T extends SiCell<?, ?>> T createTransientCell(Class<T> cellClass);

  SiCell<?, ?> createTransientCell(String cellType);

  SiCell<?, ?> createTransientCell(UUID cellUUID);

  <T extends SiCell<?, ?>> T createTransientCell(Class<T> cellClass, Object value);

  SiCell<?, ?> createTransientCell(String cellType, Object value);

  SiCell<?, ?> createTransientCell(UUID cellUUID, Object value);

  C createTransientInstance();

  void destroy() throws CellDestroyException;

  void destroy(SiCell<?, ?> cell) throws CellDestroyException;

  void destroy(URI longName) throws CellDestroyException;

  void destroySubCells() throws CellDestroyException;

  void destroySubCells(Class<? extends SiCell<?, ?>> cellClass) throws CellDestroyException;

  boolean equals(Object object);

  Class<? extends SiCell<?, ?>> findCellClass(String cellType);

  Class<? extends SiCell<?, ?>> findCellClass(UUID cellUUID);

  SiCell<?, ?> findSubCell(String cellType);

  SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, String cellType);

  SiCell<?, ?> findSubCell(UUID uuid);

  SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, UUID uuid);

  SiCell<?, ?> findSubCell(URI cellURI);

  SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, URI cellURI);

  Class<? extends SiCell<?, ?>> getCellClass();

  String getCellClassName();

  String getCellDisplayName();

  String getCellName();

  String getCellType();

  UUID getCellUUID();

  URI getCellURI();

  SiCell<?, ?> getController();

  Field getControllerField();

  C getSelf();

  <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass);

  <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, SiCell<?, ?> parentCell);

  <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, String name);

  <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, SiCell<?, ?> parentCell, String name);

  SiCell<?, ?> getSubCell(String name);

  SiCell<?, ?> getSubCell(SiCell<?, ?> parentCell, String name);

  List<SiCell<?, ?>> getSubCells();

  <T extends SiCell<?, ?>> List<T> getSubCells(Class<T> cellClass);

  List<SiCell<?, ?>> getSubCells(String cellType);

  List<SiCell<?, ?>> getSubCells(UUID cellUUID);

  V getCellValue();

  V getCellValueAndDestroy();

  String getCellValueAsString();

  boolean hasSubCellValue(Class<? extends SiCell<?, ?>> cellClass);

  boolean hasCellValue();

  boolean hasCellValue(SiCell<?, ?> cell);

  boolean isControlled();

  boolean isControlled(SiCell<?, ?> cell);

  boolean isCreated();

  boolean isSame(SiCell<?, ?> cell);

  boolean isTransient();

  boolean isValid();

  void removeSubCell(SiCell<?, ?> cell);

  void removeSubCells();

  void removeSubCells(Class<? extends SiCell<?, ?>> cellClass);

  C restore(JsonObject data);

  C restore(String data);

  V returnAndDestroy() throws CellRunException;

  SiDiscoveryManager root();

  <T extends SiCell<?, ?>> T rootSkill(Class<T> cellClass);

  SiCell<?, ?> rootSkill(String cellType);

  SiCell<?, ?> rootSkill(UUID uuid);

  SiLogger log();

  C run() throws CellRunException;

  void runAndDestroy() throws CellRunException;

  C export() throws CellExportException;

  C save() throws CellSaveException;

  C delete() throws CellDeleteException;

  void setCellClass(Class<? extends SiCell<?, ?>> cellClass);

  void setCellDisplayName(String displayName);

  void setCellName(String name);

  void setCellURI(URI cellURI);

  void setController(SiCell<?, ?> cell);

  void setControllerField(Field field);

  void setCreated(boolean created);

  void setRootCell(SiDiscoveryManager cell);

  void setTransient(boolean tranzient);

  void setCellType(String cellType);

  void setCellUUID(UUID uuid);

  C setCellValue(V value);

  C setCellValueAsObject(Object value);

  C setCellValueAsString(String value);

  C swapSubCell(SiCell<?, ?> oldCell, SiCell<?, ?> newCell);

  C swapSubCell(String cellName, SiCell<?, ?> newCell);

  JsonObject toJsonObject();

  String toJsonString();

  class SiCellObject<C extends SiCell<?, ?>, V> implements SiCell<C, V> {

    private static final Logger LOG = LoggerFactory.getLogger(SiCell.class);
    protected V cellValue;
    private Class<? extends SiCell<?, ?>> cellClass;
    private URI cellURI;
    private SiCell<?, ?> controller;
    private boolean created;
    private String cellDisplayName;
    private Field field;
    private String cellName;
    private SiDiscoveryManager rootCell;
    private List<SiCell<?, ?>> subCells = new ArrayList<>();
    private boolean tranzient;
    private String cellType;
    private UUID cellUUID;

    @Override
    public void addSubCell(SiCell<?, ?> cell) throws DuplicatedSubCellException {
      if (getSubCell(cell.getCellName()) != null) {
        throw new DuplicatedSubCellException(
            this.getClass().toString() + " - " + cell.getCellName());
      }

      if (cell.isTransient()) {
        cell.setTransient(false);
        cell.setController(this);
      }

      if (cell.isControlled(this)) {
        cell.buildCellURI();
      }

      subCells.add(cell);
    }

    @Override
    public void afterAddSubCell(SiCell<?, ?> cell) {

    }

    @Override
    public void afterBuild() {
    }

    @Override
    public void afterCellsSet() {

    }

    @Override
    public void afterCreate() {
    }

    @Override
    public <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass) {
      return buildCell(cellClass, null, UUID.randomUUID().toString());
    }

    @Override
    public <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass, String name) {
      return buildCell(cellClass, null, name);
    }

    @Override
    public <T extends SiCell<?, ?>> T buildCell(Class<T> cellClass, SiCell<?, ?> parentCell,
        String name) {

      try {
        parentCell = parentCell != null ? parentCell : this;

        CellType annotation = cellClass.getAnnotation(CellType.class);

        // TODO: Prüfung, ob ein einfaches null return genügt.
        // Es gibt Fälle, bei denen die Sjill Interfaces genutzt werden und die Implementierung
        // von einem anderen Paket kommt. Siehe SiGitProvider und SiGitRepository

        if (annotation == null) {
          return null;
        }

        T cell = cellClass.cast(annotation.objectClass().newInstance());
        cell.setCellName(name);
        cell.setCellType(annotation.type());
        cell.setCellUUID(UUID.fromString(annotation.uuid()));
        cell.setRootCell(rootCell);
        cell.setController(parentCell);
        cell.setCellClass(cellClass);
        cell.buildCellURI();
        cell.afterBuild();

        parentCell.addSubCell(cell);

        return cell;

      } catch (Exception e) {
        if (log() == null) {
          LOG.error("", e);
        } else {
          log().error(e);
        }
      }

      return null;
    }

    @Override
    public SiCell<?, ?> buildCell(String cellType, String name) {
      return buildCell(cellType, null, name);
    }

    @Override
    public SiCell<?, ?> buildCell(String cellType, SiCell<?, ?> parentCell, String name) {
      Class<? extends SiCell<?, ?>> cellClass = findCellClass(cellType);

      if (cellClass != null) {
        return buildCell(cellClass, parentCell, name);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> buildCell(UUID cellUUID, String name) {
      return buildCell(cellUUID, null, name);
    }

    @Override
    public SiCell<?, ?> buildCell(UUID cellUUID, SiCell<?, ?> parentCell, String name) {
      Class<? extends SiCell<?, ?>> cellClass = findCellClass(cellUUID);

      if (cellClass != null) {
        return buildCell(cellClass, parentCell, name);
      }

      return null;
    }

    @Override
    public void buildCellURI() {
      String uri = "";

      if (isControlled()) {
        uri = getController().getCellURI().toString();
      } else if (isTransient()) {
        uri = "transient:/";
      }

      if (getCellName() != null) {
        uri = uri + "/" + getCellName();
      } else {
        uri = uri + "/" + getCellType();
      }

      setCellURI(URI.create(uri));
      getSubCells().forEach(subCell -> {
        if (subCell.isControlled(this)) {
          subCell.buildCellURI();
        }
      });
    }

    @Override
    public <T extends SiCell<?, ?>> T buildTransientCell(Class<T> cellClass) {
      return buildTransientCell(cellClass, null);
    }

    @Override
    public SiCell<?, ?> buildTransientCell(String cellType) {
      return buildTransientCell(cellType, null);
    }

    @Override
    public SiCell<?, ?> buildTransientCell(UUID cellUUID) {
      return buildTransientCell(cellUUID, null);
    }

    @Override
    public <T extends SiCell<?, ?>> T buildTransientCell(Class<T> cellClass, Object value) {
      try {
        CellType annotation = cellClass.getAnnotation(CellType.class);

        // TODO: Prüfung, ob ein einfaches null return genügt.
        // Es gibt Fälle, bei denen die Sjill Interfaces genutzt werden und die Implementierung
        // von einem anderen Paket kommt. Siehe SiGitProvider und SiGitRepository

        if (annotation == null) {
          return null;
        }

        T cell = cellClass.cast(annotation.objectClass().newInstance());
        cell.setCellType(annotation.type());
        cell.setCellUUID(UUID.fromString(annotation.uuid()));
        cell.setRootCell(rootCell);
        cell.setTransient(true);
        cell.setCellClass(cellClass);
        cell.buildCellURI();
        cell.afterBuild();

        if (value != null) {
          cell.setCellValueAsObject(value);
        }

        return cell;

      } catch (Exception e) {
        log().error(e.getMessage(), e);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> buildTransientCell(String cellType, Object value) {
      Class<? extends SiCell<?, ?>> typeCellClass = findCellClass(cellType);

      if (typeCellClass != null) {
        return buildTransientCell(typeCellClass, value);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> buildTransientCell(UUID cellUUID, Object value) {
      Class<? extends SiCell<?, ?>> uuidCellClass = findCellClass(cellUUID);

      if (uuidCellClass != null) {
        return buildTransientCell(uuidCellClass, value);
      }

      return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C buildTransientInstance() {
      return (C) buildTransientCell(this.getCellClass());
    }

    @Override
    public C create() {
      SiCellInjector cellInjector = (SiCellInjector) rootSkill(SiCellInjector.CELL_TYPE);
      SiCellAnalyzerRegistry analyzerRegistry = (SiCellAnalyzerRegistry) rootSkill(
          SiCellAnalyzerRegistry.CELL_TYPE);

      cellInjector.checkAndInject(this);
      analyzerRegistry.process(this);

      this.created = true;

      afterCreate();

      return getSelf();
    }

    @Override
    public <T extends SiCell<?, ?>> T createCell(Class<T> cellClass) {
      return createCell(cellClass, UUID.randomUUID().toString());
    }

    @Override
    public <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, SiCell<?, ?> parentCell) {
      return createCell(cellClass, parentCell, UUID.randomUUID().toString());
    }

    @Override
    public <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, String name) {
      return createCell(cellClass, null, name);
    }

    @Override
    public <T extends SiCell<?, ?>> T createCell(Class<T> cellClass, SiCell<?, ?> parentCell,
        String name) {

      SiCell<?, ?> cell = buildCell(cellClass, parentCell, name);

      if (cell != null) {
        cell.create();
      }

      return cellClass.cast(cell);
    }

    @Override
    public SiCell<?, ?> createCell(String cellType, String name) {
      return createCell(cellType, null, name);
    }

    @Override
    public SiCell<?, ?> createCell(String cellType, SiCell<?, ?> parentCell, String name) {
      Class<? extends SiCell<?, ?>> typeCellClass = findCellClass(cellType);

      if (typeCellClass != null) {
        return createCell(typeCellClass, parentCell, name);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> createCell(UUID cellUUID, String name) {
      return createCell(cellUUID, null, name);
    }

    @Override
    public SiCell<?, ?> createCell(UUID cellUUID, SiCell<?, ?> parentCell, String name) {
      Class<? extends SiCell<?, ?>> uuidCellClass = findCellClass(cellUUID);

      if (uuidCellClass != null) {
        return createCell(uuidCellClass, parentCell, name);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> createCell(String cellType) {
      return createCell(cellType, UUID.randomUUID().toString());
    }

    @Override
    public SiCell<?, ?> createCell(String cellType, SiCell<?, ?> parentCell) {
      return createCell(cellType, parentCell, UUID.randomUUID().toString());
    }

    @Override
    public SiCell<?, ?> createCell(UUID cellUUID) {
      return createCell(cellUUID, UUID.randomUUID().toString());
    }

    @Override
    public SiCell<?, ?> createCell(UUID cellUUID, SiCell<?, ?> parentCell) {
      return createCell(cellUUID, parentCell, UUID.randomUUID().toString());
    }

    @Override
    public <T extends SiCell<?, ?>> T createTransientCell(Class<T> cellClass) {
      return createTransientCell(cellClass, null);
    }

    @Override
    public SiCell<?, ?> createTransientCell(String cellType) {
      return createTransientCell(cellType, null);
    }

    @Override
    public SiCell<?, ?> createTransientCell(UUID cellUUID) {
      return createTransientCell(cellUUID, null);
    }

    @Override
    public <T extends SiCell<?, ?>> T createTransientCell(Class<T> cellClass, Object value) {
      T cell = buildTransientCell(cellClass);

      if (cell != null) {
        cell.create();

        if (value != null) {
          cell.setCellValueAsObject(value);
        }
      }

      return cell;
    }

    @Override
    public SiCell<?, ?> createTransientCell(String cellType, Object value) {
      Class<? extends SiCell<?, ?>> cellClass = findCellClass(cellType);

      if (cellClass != null) {
        return createTransientCell(cellClass, value);
      }

      return null;
    }

    @Override
    public SiCell<?, ?> createTransientCell(UUID cellUUID, Object value) {
      Class<? extends SiCell<?, ?>> cellClass = findCellClass(cellUUID);

      if (cellClass != null) {
        return createTransientCell(cellClass, value);
      }

      return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C createTransientInstance() {
      C transientCell = buildTransientInstance();
      transientCell.setCellName(getCellName());
      transientCell.setCellValueAsObject(getCellValue());
      transientCell.setController(getController());
      transientCell.buildCellURI();

      getSubCells().forEach(subCell -> {
        SiCell<?, ?> transientSubCell;

        if (subCell.isControlled(this)) {
          transientSubCell = subCell.createTransientInstance();
          transientSubCell.setController(transientCell);
          transientSubCell.setCellName(subCell.getCellName());
          transientSubCell.setCellDisplayName(subCell.getCellDisplayName());
          transientSubCell.setCellValueAsObject(subCell.getCellValue());
          transientSubCell.setControllerField(subCell.getControllerField());
          transientSubCell.buildCellURI();

          if (transientSubCell.getControllerField() != null) {
            try {
              Field field = this.getClass()
                  .getDeclaredField(transientSubCell.getControllerField().getName());
              field.setAccessible(true);

              if (transientSubCell instanceof SiCellReference) {
                field.set(transientCell, transientSubCell.getCellValue());
              } else {
                field.set(transientCell, transientSubCell);
              }
            } catch (Exception ignored) {
              log().info("TODO: Exception Handling", ignored);
            }
          }
        } else {
          transientSubCell = subCell;
        }
        try {
          transientCell.addSubCell(transientSubCell);
        } catch (DuplicatedSubCellException duplicatedSubCellException) {
          log().info("TODO: Exception Handling", duplicatedSubCellException);
        }
      });

      return transientCell;
    }

    @Override
    public void destroy() throws CellDestroyException {
      destroy(this);
    }

    @Override
    public void destroy(SiCell<?, ?> cell) throws CellDestroyException {
      SiCell<?, ?> finalCell = cell;

      cell.getSubCells().forEach(subCell -> {
        if (subCell.isControlled(finalCell)) {
          try {
            subCell.destroy();
          } catch (CellDestroyException destroyException) {
            log().error(destroyException);
          }
        }
      });
      cell.removeSubCells();

      cell = null;
    }

    @Override
    public void destroy(URI longName) throws CellDestroyException {
      throw new CellDestroyException("Method not implemented");
    }

    @Override
    public void destroySubCells() throws CellDestroyException {
      getSubCells().forEach(subCell -> {
        if (subCell.isControlled(this)) {
          try {
            subCell.destroy();
          } catch (CellDestroyException e) {
            log().error(e);
          }
        }
      });
      removeSubCells();
    }

    @Override
    public void destroySubCells(Class<? extends SiCell<?, ?>> cellClass)
        throws CellDestroyException {
      removeSubCells(cellClass);

      getSubCells(cellClass).forEach(subCell -> {
        if (subCell.isControlled(this)) {
          try {
            subCell.destroy();
          } catch (CellDestroyException e) {
            log().error(e);
          }
        }
      });
    }

    @Override
    public boolean equals(Object object) {
      if (!(object instanceof SiCell)) {
        return false;
      }

      SiCell<?, ?> cell = (SiCell<?, ?>) object;

      if (!isSame(cell)) {
        return false;
      }

      if (getController() == null && cell.getController() != null) {
        return false;
      }

      if (getController() != null && cell.getController() == null) {
        return false;
      }

      if (getController() != null && cell.getController() != null && !getController().equals(
          cell.getController())) {
        return false;
      }

      return Objects.equals(this.cellName, cell.getCellName()) &&
          Objects.equals(this.cellURI, cell.getCellURI());
    }

    @Override
    public Class<? extends SiCell<?, ?>> findCellClass(String cellType) {

      for (SiCell<?, ?> cell : rootSkill(SiCellRegistry.class).getSubCells(
          SiCellRegistryEntry.class)) {

        String entryTypeName = cell.getSubCell(SiCellRegistryEntryTypeName.class).getCellValue();

        if (entryTypeName.equals(cellType)) {
          return cell.getSubCell(SiCellRegistryEntryClass.class).getCellValue();
        }
      }

      return null;
    }

    @Override
    public Class<? extends SiCell<?, ?>> findCellClass(UUID cellUUID) {
      for (SiCell<?, ?> cell : rootSkill(SiCellRegistry.class).getSubCells(
          SiCellRegistryEntry.class)) {

        UUID entryTypeUUID = ((SiCellRegistryEntryTypeUUID) cell.findSubCell(
            SiCellRegistryEntryTypeUUID.CELL_TYPE)).getCellValue();

        if (entryTypeUUID.equals(cellUUID)) {
          return ((SiCellRegistryEntryClass) cell.findSubCell(
              SiCellRegistryEntryClass.CELL_TYPE)).getCellValue();
        }
      }

      return null;
    }

    @Override
    public SiCell<?, ?> findSubCell(String cellType) {
      return findSubCell(null, cellType);
    }

    @Override
    public SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, String cellType) {
      if (cellType == null || cellType.isEmpty()) {
        return null;
      }

      parentCell = parentCell != null ? parentCell : this;

      for (SiCell<?, ?> subCell : parentCell.getSubCells()) {
        if (subCell.getCellType().equals(cellType)) {
          return subCell;
        }
      }

      return null;
    }

    @Override
    public SiCell<?, ?> findSubCell(UUID uuid) {
      return findSubCell(null, uuid);
    }

    @Override
    public SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, UUID uuid) {
      if (uuid == null) {
        return null;
      }

      parentCell = parentCell != null ? parentCell : this;

      for (SiCell<?, ?> subCell : parentCell.getSubCells()) {
        if (subCell.getCellUUID().equals(uuid)) {
          return subCell;
        }
      }

      return null;
    }

    @Override
    public SiCell<?, ?> findSubCell(URI cellURI) {
      return findSubCell(null, cellURI);
    }

    @Override
    public SiCell<?, ?> findSubCell(SiCell<?, ?> parentCell, URI cellURI) {
      if (cellURI == null) {
        return null;
      }

      parentCell = parentCell != null ? parentCell : this;

      for (SiCell<?, ?> subCell : parentCell.getSubCells()) {
        if (subCell.getCellURI().equals(cellURI)) {
          return subCell;
        }
      }

      return null;
    }

    @Override
    public Class<? extends SiCell<?, ?>> getCellClass() {
      return this.cellClass;
    }

    @Override
    public String getCellClassName() {
      return getCellClass().getName();
    }

    @Override
    public String getCellDisplayName() {
      return this.cellDisplayName;
    }

    @Override
    public String getCellName() {
      return this.cellName;
    }

    @Override
    public String getCellType() {
      return this.cellType;
    }

    @Override
    public UUID getCellUUID() {
      return this.cellUUID;
    }

    @Override
    public URI getCellURI() {
      return cellURI;
    }

    @Override
    public SiCell<?, ?> getController() {
      return controller;
    }

    @Override
    public Field getControllerField() {
      return this.field;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C getSelf() {
      return (C) this;
    }

    @Override
    public <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass) {
      return cellClass.cast(getSubCell(cellClass, null, null));
    }

    @Override
    public <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, SiCell<?, ?> parentCell) {
      return getSubCell(cellClass, parentCell, null);
    }

    @Override
    public <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, String name) {
      return getSubCell(cellClass, null, name);
    }

    @Override
    public <T extends SiCell<?, ?>> T getSubCell(Class<T> cellClass, SiCell<?, ?> parentCell,
        String name) {

      if (cellClass == null) {
        return null;
      }

      parentCell = parentCell != null ? parentCell : this;

      for (SiCell<?, ?> subCell : parentCell.getSubCells()) {

        if (subCell.getCellClass().equals(cellClass) &&
            (name == null || subCell.getCellName().equals(name))) {

          return cellClass.cast(subCell);
        }
      }

      return null;
    }

    @Override
    public SiCell<?, ?> getSubCell(String name) {
      return getSubCell((SiCell<?, ?>) null, name);
    }

    @Override
    public SiCell<?, ?> getSubCell(SiCell<?, ?> parentCell, String name) {
      if (name == null || name.isEmpty()) {
        return null;
      }

      parentCell = parentCell != null ? parentCell : this;

      for (SiCell<?, ?> subCell : parentCell.getSubCells()) {
        if (subCell.getCellName().equals(name)) {

          return subCell;
        }
      }

      return null;
    }

    @Override
    public List<SiCell<?, ?>> getSubCells() {
      return this.subCells;
    }

    @Override
    public <T extends SiCell<?, ?>> List<T> getSubCells(Class<T> cellClass) {
      // TODO: List generic implementieren -> wie getSubCell

      return (List<T>) getSubCells().stream().filter(cell -> cell.getCellClass().equals(cellClass))
          .collect(Collectors.toList());
    }

    @Override
    public List<SiCell<?, ?>> getSubCells(String cellType) {
      return getSubCells().stream().filter(cell -> cell.getCellType().equals(cellType)).collect(
          Collectors.toList());
    }

    @Override
    public List<SiCell<?, ?>> getSubCells(UUID cellUUID) {
      return getSubCells().stream().filter(cell -> cell.getCellUUID().equals(cellUUID)).collect(
          Collectors.toList());
    }

    @Override
    public V getCellValue() {
      return this.cellValue;
    }

    @Override
    public V getCellValueAndDestroy() {
      V tempValue = getCellValue();

      try {
        this.destroy();
      } catch (Exception e) {
        log().error(e);
      }

      return tempValue;
    }

    @Override
    public String getCellValueAsString() {
      return hasCellValue() ? getCellValue().toString() : "";
    }

    @Override
    public boolean hasSubCellValue(Class<? extends SiCell<?, ?>> cellClass) {

      SiCell<?, ?> cell = getSubCell(cellClass);

      if (cell == null) {
        return false;
      }

      return cell.hasCellValue();
    }

    @Override
    public boolean hasCellValue() {
      return getCellValue() != null;
    }

    @Override
    public boolean hasCellValue(SiCell<?, ?> cell) {
      return cell != null && cell.hasCellValue();
    }

    @Override
    public boolean isControlled() {
      return this.controller != null;
    }

    @Override
    public boolean isControlled(SiCell<?, ?> cell) {
      return getController() == cell;
    }

    @Override
    public boolean isCreated() {
      return this.created;
    }

    @Override
    public boolean isSame(SiCell<?, ?> cell) {
      if (this == cell) {
        return true;
      }
      if (cell == null || getClass() != cell.getClass()) {
        return false;
      }

      return Objects.equals(this.cellType, cell.getCellType()) &&
          Objects.equals(this.cellUUID, cell.getCellUUID()) &&
          Objects.equals(this.cellClass, cell.getCellClass());
    }

    @Override
    public boolean isTransient() {
      return tranzient;
    }

    @Override
    public boolean isValid() {
      return false;
    }

    @Override
    public void removeSubCell(SiCell<?, ?> cell) {
      if (cell == null) {
        return;
      }

      subCells.remove(cell);

      if (cell.isControlled(this)) {
        if (cell.getControllerField() != null) {
          try {
            Field field = CellHelper.getField(cell.getControllerField().getName(), getClass());
            field.setAccessible(true);
            field.set(this, null);
          } catch (Exception e) {
            if (log().isTraceEnabled()) {
              // TODO
              // log().debug(e);
            }
          }
        }

        cell = null;

      } else {
        try {
          Field field = CellHelper.getField(cell.getCellName(), getClass());
          field.setAccessible(true);
          if (cell.equals(field.get(this))) {
            field.set(this, null);
          }
        } catch (Exception e) {
          if (log().isDebugEnabled()) {
            // TODO
            // log().debug(e);
          }
        }
      }
    }

    @Override
    public void removeSubCells() {
      for (SiCell<?, ?> subCell : new ArrayList<>(this.subCells)) {
        removeSubCell(subCell);
      }
      this.subCells.clear();
    }

    @Override
    public void removeSubCells(Class<? extends SiCell<?, ?>> cellClass) {
      getSubCells(cellClass).forEach(this::removeSubCell);
    }

    @Override
    public C restore(JsonObject jsonObject) {

      if (jsonObject.has(SiCell.PROPERTY_CELL_VALUE)) {
        JsonElement cellValueJsonElement = jsonObject.get(SiCell.PROPERTY_CELL_VALUE);

        if (cellValueJsonElement.isJsonPrimitive()) {
          setCellValueAsString(jsonObject.get(SiCell.PROPERTY_CELL_VALUE).getAsString());
        } else if (cellValueJsonElement.isJsonObject()) {
          setCellValueAsString(
              new Gson().toJson(jsonObject.get(SiCell.PROPERTY_CELL_VALUE).getAsJsonObject()));
        } else if (cellValueJsonElement.isJsonArray()) {
          setCellValueAsString(
              new Gson().toJson(jsonObject.get(SiCell.PROPERTY_CELL_VALUE).getAsJsonArray()));
        }
      }

      removeSubCells();

      JsonArray subCellsJsonArray = jsonObject.getAsJsonArray(SiCell.PROPERTY_SUB_CELLS);

      if (subCellsJsonArray != null) {
        for (int a = 0; a < subCellsJsonArray.size(); a++) {
          JsonObject subCellJsonObject = subCellsJsonArray.get(a).getAsJsonObject();

          if (!subCellJsonObject.has(SiCell.PROPERTY_CELL_TYPE)) {
            if (log().isTraceEnabled()) {
              log().trace("Cell type for sub cell is null or empty. Skipping entry.");
            }
            continue;
          }

          String subCellType = subCellJsonObject.get(SiCell.PROPERTY_CELL_TYPE).getAsString();
          String subCellName = subCellJsonObject.get(SiCell.PROPERTY_CELL_NAME).getAsString();
          String subCellUUID = subCellJsonObject.get(SiCell.PROPERTY_CELL_UUID).getAsString();

          SiCell<?, ?> subCell = createTransientCell(subCellType);

          if (subCell == null) {
            if (log().isWarnEnabled()) {
              log().warn("Cell type [ " + subCellType + " ] not present in discovery. " +
                  "Can not add cell as sub cell.");
            }

            continue;
          }

          subCell.setCellName(subCellName);
          subCell.restore(subCellJsonObject);

          swapSubCell(subCellName, subCell);
        }
      }

      for (String key : jsonObject.keySet()) {
        if (key.equals(SiCell.PROPERTY_CELL_TYPE) || key.equals(SiCell.PROPERTY_CELL_URI)
            || key.equals(SiCell.PROPERTY_CELL_UUID) || key.equals(SiCell.PROPERTY_CELL_VALUE) ||
            key.equals(SiCell.PROPERTY_CELL_CLASS) || key.equals(SiCell.PROPERTY_CELL_NAME) ||
            key.equals(SiCell.PROPERTY_CELL_DISPLAY_NAME) || key.equals(
            SiCell.PROPERTY_SUB_CELLS)) {
          continue;
        }

        Field field = null;
        SiCell<?, ?> subCell;
        String fieldCellType, subCellType = null;

        try {
          field = CellHelper.getField(key, getClass());
          field.setAccessible(true);

          if (field.getType().getAnnotation(CellType.class) == null) {
            if (log().isTraceEnabled()) {
              log().trace("Field with name " + key
                  + " does not have CellType annotation. Skipping assignment of the sub cell to the field.");
            }

            continue;
          }

          fieldCellType = field.getType().getAnnotation(CellType.class).type();

          if (field.get(this) != null) {
            if (log().isTraceEnabled()) {
              log().trace("Field with name " + key
                      + " is not null. Skipping assignment of the sub cell to the field. [{0}]",
                  field.get(this));
            }
          }
        } catch (Exception e) {
          if (log().isTraceEnabled()) {
            if (e instanceof NoSuchFieldException) {
              log().trace("Field with name " + key + " does not exit in cell.");
            } else {
              if (field != null) {
                log().trace("" + field.getType());
              } else {
                log().trace(e);
              }

            }
          }

          continue;
        }

        JsonElement jsonElement = jsonObject.get(key);

        if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject()
            .has(SiCell.PROPERTY_CELL_TYPE)) {

          subCellType = jsonElement.getAsJsonObject().get(SiCell.PROPERTY_CELL_TYPE)
              .getAsString();
        }

        if (subCellType != null && !fieldCellType.equals(subCellType)) {

          if (log().isTraceEnabled()) {
            log().trace("Field cell type [{0}] does not equal to sub cell type [{1}]. "
                + "Skipping assignment of the sub cell to the field.", fieldCellType, subCellType);
          }

          continue;
        }

        subCell = createTransientCell(fieldCellType);

        if (subCell == null) {
          if (log().isWarnEnabled()) {
            log().warn("Cell type " + subCellType + " not present in discovery. " +
                "Skipping assignment of the sub cell to the field.");
          }
          continue;
        }

        if (jsonElement.isJsonObject()) {
          subCell.restore(jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonPrimitive()) {
          subCell.setCellValueAsString(jsonElement.getAsString());
        } else if (jsonElement.isJsonArray()) {
          subCell.setCellValueAsString(
              new Gson().toJson(jsonElement.getAsJsonArray()));
        }

        swapSubCell(key, subCell);
      }

      return getSelf();
    }

    @Override
    public C restore(String data) {
      return restore(new Gson().fromJson(data, JsonObject.class));
    }

    @Override
    public V returnAndDestroy() throws CellRunException {
      this.run();

      V val = getCellValue();

      try {
        this.destroy();
      } catch (Exception e) {
        throw new CellRunException(this, e);
      }

      return val;
    }

    @Override
    public SiDiscoveryManager root() {
      if (rootCell == null) {
        // TODO: Vorerst entfernt -> Sorgt dafür, dass bei einfachen Tests komplette Discovery
        // gestartet wird

        //SiDiscoveryManager discoveryManager = new SiDiscoveryManagerImpl();
        //discoveryManager.create();

        //setRootCell(discoveryManager);
      }

      return this.rootCell;
    }

    @Override
    public <T extends SiCell<?, ?>> T rootSkill(Class<T> cellClass) {
      return getSubCell(cellClass, root());
    }

    @Override
    public SiCell<?, ?> rootSkill(String cellType) {
      return findSubCell(root(), cellType);
    }

    @Override
    public SiCell<?, ?> rootSkill(UUID uuid) {
      return findSubCell(root(), uuid);
    }

    @Override
    public SiLogger log() {
      return root().getLogger(this);
    }

    @Override
    public C run() throws CellRunException {
      return getSelf();
    }

    @Override
    public void runAndDestroy() throws CellRunException {
      run();

      try {
        this.destroy();
      } catch (Exception e) {
        throw new CellRunException(this, e);
      }
    }

    @Override
    public C export() throws CellExportException {
      throw new CellExportException("Method not implemented");
    }

    @Override
    public C save() throws CellSaveException {
      root().processSave(this);
      return getSelf();
    }

    @Override
    public C delete() throws CellDeleteException {
      root().processDelete(this);
      return getSelf();
    }

    @Override
    public void setCellClass(Class<? extends SiCell<?, ?>> cellClass) {
      this.cellClass = cellClass;
    }

    @Override
    public void setCellDisplayName(String displayName) {
      this.cellDisplayName = displayName;
    }

    @Override
    public void setCellName(String name) {
      this.cellName = name;
    }

    @Override
    public void setCellURI(URI cellURI) {
      this.cellURI = cellURI;
    }

    @Override
    public void setController(SiCell<?, ?> cell) {
      this.controller = cell;
    }

    @Override
    public void setControllerField(Field field) {
      this.field = field;
    }

    @Override
    public void setCreated(boolean created) {
      this.created = created;
    }

    @Override
    public void setRootCell(SiDiscoveryManager cell) {
      this.rootCell = cell;
    }

    @Override
    public void setTransient(boolean tranzient) {
      this.tranzient = tranzient;
    }

    @Override
    public void setCellType(String cellType) {
      this.cellType = cellType;
    }

    @Override
    public void setCellUUID(UUID uuid) {
      this.cellUUID = uuid;
    }

    @Override
    public C setCellValue(V value) {
      this.cellValue = value;
      return getSelf();
    }

    @Override
    public C setCellValueAsObject(Object value) {
      try {
        setCellValue((V) value);
      } catch (ClassCastException ignored) {
      }
      return getSelf();
    }

    @Override
    public C setCellValueAsString(String value) {
      try {
        setCellValue((V) value);
      } catch (ClassCastException ignored) {
      }
      return getSelf();
    }

    @Override
    public C swapSubCell(SiCell<?, ?> oldCell, SiCell<?, ?> newCell) {
      if (newCell == null) {
        log().error("Error while swapping cell. New cell is null.");

        return getSelf();
      }

      if (oldCell == null) {
        log().error("Error while swapping cell. Old cell is null.");

        return getSelf();
      }

      SiCell<?, ?> subCell = getSubCell(oldCell.getCellName());

      if (subCell == null || !subCell.equals(oldCell)) {
        log().error(
            "Error while swapping cell. Old cell is null or does not equal to existing sub cell.");

        return getSelf();

      } else if (!subCell.isSame(newCell)) {
        log().error(
            "Error while swapping cell. The cells are not of the same type: [{0}] x [{1}]",
            subCell.getCellType(), newCell.getCellType());

        return getSelf();
      }

      if (newCell.isTransient()) {
        newCell.setCellName(subCell.getCellName());
        newCell.setController(this);
        newCell.setTransient(false);
      }

      Field controllerField = subCell.getControllerField();
      removeSubCell(subCell);

      if (controllerField != null) {
        try {
          controllerField.setAccessible(true);
          controllerField.set(this, newCell);
        } catch (Exception ignored) {
          LOG.debug(controllerField.getName(), ignored);
        }

        if (newCell.isControlled(this)) {
          newCell.setControllerField(controllerField);
        }
      }

      try {
        addSubCell(newCell);
      } catch (DuplicatedSubCellException duplicatedSubCellException) {
        log().error(duplicatedSubCellException);
      }

      return getSelf();
    }

    @Override
    public C swapSubCell(String cellName, SiCell<?, ?> newCell) {
      if (cellName == null || cellName.isEmpty()) {
        log().error("Error while swapping cell. cell name is null or empty.");

        return getSelf();
      }

      SiCell<?, ?> subCell = getSubCell(cellName);

      if (subCell != null) {
        return swapSubCell(subCell, newCell);
      } else {
        if (newCell.isTransient()) {
          newCell.setCellName(cellName);
          newCell.setController(this);
        }

        try {
          Field field = CellHelper.getField(cellName, getClass());
          field.setAccessible(true);
          if (field.get(this) == null) {
            field.set(this, newCell);
            if (newCell.isControlled(this)) {
              newCell.setControllerField(field);
            }
          }
        } catch (Exception ignored) {
          LOG.debug(newCell.getCellName(), ignored);
        }

        try {
          addSubCell(newCell);
        } catch (DuplicatedSubCellException duplicatedSubCellException) {
          log().error(duplicatedSubCellException);
        }
      }

      return getSelf();
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

      if (subCells.size() > 0) {
        JsonArray subCellsJsonArray = new JsonArray();
        jsonObject.add(SiCell.PROPERTY_SUB_CELLS, subCellsJsonArray);

        getSubCells().forEach(subCell -> {
          JsonObject subCellJsonObject = subCell.toJsonObject();
          subCellsJsonArray.add(subCellJsonObject);

          if (subCell.isControlled(this) && subCell.getControllerField() != null) {
            jsonObject.add(subCell.getControllerField().getName(), subCellJsonObject);
          }
        });
      }

      return jsonObject;
    }

    @Override
    public String toJsonString() {
      return new Gson().toJson(toJsonObject());
    }

    @Override
    public String toString() {
      return toJsonString();
    }

    protected void checkRunValues(Object... objects) throws CellRunException {
      // TODO: Exception Message formatieren, damit alle leeren Zellen ausgeben werden
      // org.springframework.util.Assert checken für saubere Implementierung als Zelle

      for (Object object : objects) {
        if (object == null) {
          throw new CellRunException(this);
        }
        if ((object instanceof SiCell) && !((SiCell<?, ?>) object).hasCellValue()) {
          throw new CellRunException(this);
        }
      }
    }

    protected void checkRunValuesForNull(Object... objects) throws CellRunException {
      // TODO: Exception Message formatieren, damit alle lerren Zellen ausgeben werden
      // org.springframework.util.Assert checken für saubere Implementierung als Zelle

      for (Object object : objects) {
        if (object == null) {
          throw new CellRunException(this);
        }
      }
    }
  }
}

