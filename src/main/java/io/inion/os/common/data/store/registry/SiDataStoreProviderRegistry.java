package io.inion.os.common.data.store.registry;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.data.store.SiDataStoreProvider;
import io.inion.os.common.data.store.registry.SiDataStoreProviderRegistry.SiDataStoreProviderRegistryObject;
import io.inion.os.common.data.store.registry.skills.SkGetProviders;
import io.inion.os.common.data.store.registry.skills.SkProcessClasses;
import io.inion.os.common.discovery.context.SiContextScanner;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiClass;
import io.inion.os.common.types.SiList;

@CellType(
    objectClass = SiDataStoreProviderRegistryObject.class,
    type = SiDataStoreProviderRegistry.CELL_TYPE,
    uuid = SiDataStoreProviderRegistry.CELL_UUID
)
public interface SiDataStoreProviderRegistry extends SiCell<SiDataStoreProviderRegistry, Void> {

  String CELL_TYPE = "data-store-provider-registry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  SiList<SiDataStoreProvider<?>> getProviders();

  class SiDataStoreProviderRegistryObject extends
      SiCellObject<SiDataStoreProviderRegistry, Void> implements
      SiDataStoreProviderRegistry {

    private SiContextScanner contextScanner;
    private SkProcessClasses skProcessClasses;
    private SkGetProviders skGetProviders;

    @Override
    public void afterCreate() {
      processClasses();
    }

    @Override
    public SiDataStoreProviderRegistry create() {
      contextScanner = rootSkill(SiContextScanner.class);
      skProcessClasses = buildCell(SkProcessClasses.class, "skProcessClasses");
      skGetProviders = buildCell(SkGetProviders.class, "skGetProviders");

      setCreated(true);

      processClasses();

      return getSelf();
    }

    @Override
    public SiList<SiDataStoreProvider<?>> getProviders() {
      try {
        return skGetProviders
            .createTransientInstance()
            .returnAndDestroy();
      } catch (CellRunException e) {
        log().error(e);
      }

      return null;
    }

    private void processClasses() {
      try {
        skProcessClasses
            .createTransientInstance()
            .setCellValue(contextScanner.scan(
                createTransientCell(SiClass.class, CellType.class)))
            .runAndDestroy();
      } catch (CellRunException e) {
        log().error(e);
      }
    }
  }
}
