package io.inion.os.common.discovery.context;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.context.skills.SkAddBasePackage;
import io.inion.os.common.discovery.context.skills.SkScan;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiClass;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@CellType(
    objectClass = SiContextScanner.SiContextScannerObject.class,
    type = SiContextScanner.CELL_TYPE,
    uuid = SiContextScanner.CELL_UUID
)
public interface SiContextScanner extends
    SiCell<SiContextScanner, Map<SiClass<? extends Annotation>, SiList<Class<?>>>> {

  String CELL_TYPE = "context-scanner";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  void addBasePackage(SiString basePackage);

  SiList<Class<?>> scan(SiClass<? extends Annotation> annotationClass);

  class SiContextScannerObject extends
      SiCellObject<SiContextScanner, Map<SiClass<? extends Annotation>, SiList<Class<?>>>>
      implements SiContextScanner {

    private SkAddBasePackage skAddBasePackage;
    private SkScan skScan;

    @Override
    public void afterBuild() {
      cellValue = new HashMap<>();
    }

    @Override
    public void addBasePackage(SiString basePackage) {
      try {
        skAddBasePackage
            .createTransientInstance()
            .setBasePackage(basePackage)
            .run();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public SiContextScanner create() {
      skAddBasePackage = buildCell(SkAddBasePackage.class, "skAddBasePackage");
      skScan = buildCell(SkScan.class, "skScan");

      setCreated(true);

      return getSelf();
    }

    @Override
    public SiList<Class<?>> scan(SiClass<? extends Annotation> annotationClass) {

      return skScan
          .createTransientInstance()
          .setAnnotationClass(annotationClass)
          .getCellValueAndDestroy();
    }
  }
}
