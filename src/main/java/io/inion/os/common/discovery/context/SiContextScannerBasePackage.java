package io.inion.os.common.discovery.context;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiContextScannerBasePackage.SiContextScannerBasePackageObject.class,
    type = SiContextScannerBasePackage.CELL_TYPE,
    uuid = SiContextScannerBasePackage.CELL_UUID
)
public interface SiContextScannerBasePackage extends SiString {

  String CELL_TYPE = "context-scanner-base-package";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiContextScannerBasePackageObject extends SiStringObject implements
      SiContextScannerBasePackage {

  }
}
