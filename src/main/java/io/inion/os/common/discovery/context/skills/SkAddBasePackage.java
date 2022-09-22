package io.inion.os.common.discovery.context.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;
import io.inion.os.common.discovery.context.SiContextScannerBasePackage;
import io.inion.os.common.exception.CellRunException;

@CellType(
    objectClass = SkAddBasePackage.SkAddBasePackageObject.class,
    type = SkAddBasePackage.CELL_TYPE,
    uuid = SkAddBasePackage.CELL_UUID
)
public interface SkAddBasePackage extends SiCell<SkAddBasePackage, Void> {

  String CELL_TYPE = "context-scanner-add-base-package-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkAddBasePackage setBasePackage(SiString basePackage);

  class SkAddBasePackageObject extends SiCellObject<SkAddBasePackage, Void> implements
      SkAddBasePackage {

    @Cell(isTransient = true)
    private SiString basePackage;

    @Override
    public SkAddBasePackage run() throws CellRunException {
      checkRunValuesForNull(basePackage);

      SiContextScannerBasePackage entry = getController().buildCell(
          SiContextScannerBasePackage.class);
      entry.setCellValue(basePackage.getCellValue());

      return getSelf();
    }

    @Override
    public SkAddBasePackage setBasePackage(SiString basePackage) {
      return swapSubCell("basePackage", basePackage);
    }
  }
}
