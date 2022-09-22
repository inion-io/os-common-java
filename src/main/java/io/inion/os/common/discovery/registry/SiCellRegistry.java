package io.inion.os.common.discovery.registry;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.context.SiContextScanner;
import io.inion.os.common.discovery.registry.skills.SkProcessClasses;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiClass;

@CellType(
    objectClass = SiCellRegistry.SiCellRegistryObject.class,
    type = SiCellRegistry.CELL_TYPE,
    uuid = SiCellRegistry.CELL_UUID
)
public interface SiCellRegistry extends SiCell<SiCellRegistry, JsonObject> {

  String CELL_TYPE = "cell-registry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiCellRegistryObject extends SiCellObject<SiCellRegistry, JsonObject> implements
      SiCellRegistry {

    private SiContextScanner contextScanner;
    private SkProcessClasses skProcessClasses;

    @Override
    public SiCellRegistry create() {
      contextScanner = rootSkill(SiContextScanner.class);
      skProcessClasses = buildCell(SkProcessClasses.class, "skProcessClasses");

      return super.create();
    }

    @Override
    public void afterCreate() {
      processClasses();
    }

    private void processClasses() {
      try {
        skProcessClasses
            .createTransientInstance()
            .setClasses(contextScanner.scan(
                createTransientCell(SiClass.class, CellType.class)))
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }
  }
}
