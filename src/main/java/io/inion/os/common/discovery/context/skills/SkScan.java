package io.inion.os.common.discovery.context.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiClass;
import io.inion.os.common.types.SiList;
import io.inion.os.common.discovery.context.SiContextScanner;
import io.inion.os.common.discovery.context.SiContextScannerBasePackage;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

@CellType(
    objectClass = SkScan.SkScanObject.class,
    type = SkScan.CELL_TYPE,
    uuid = SkScan.CELL_UUID
)
public interface SkScan extends SiCell<SkScan, SiList<Class<?>>> {

  String CELL_TYPE = "context-scanner-scan-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkScan setAnnotationClass(SiClass<? extends Annotation> annotationClass);

  class SkScanObject extends SiCellObject<SkScan, SiList<Class<?>>> implements
      SkScan {

    @Cell(isTransient = true)
    private SiClass<? extends Annotation> annotationClass;

    @Override
    public SiList<Class<?>> getCellValue() {
      if (annotationClass == null) {
        // TODO: Exception Handling and Uncheck Casts
        return null;
      }

      if (((SiContextScanner) getController()).getCellValue().get(annotationClass) != null) {
        return ((SiContextScanner) getController()).getCellValue().get(annotationClass);
      }

      ConfigurationBuilder builder =
          new ConfigurationBuilder().setScanners(new SubTypesScanner(),
              new TypeAnnotationsScanner());

      getController().getSubCells(SiContextScannerBasePackage.class).forEach(basePackage -> {
        builder.addUrls(
            ClasspathHelper.forPackage(((SiContextScannerBasePackage) basePackage).getCellValue()));
      });

      Reflections reflections = new Reflections(builder);

      List<Class<?>> classes = reflections.getTypesAnnotatedWith(annotationClass.getCellValue())
          .stream()
          .filter(Class::isInterface)
          .filter(SiCell.class::isAssignableFrom)
          .collect(Collectors.toList());

      ((SiContextScanner) getController()).getCellValue()
          .put(annotationClass, createTransientCell(SiList.class, classes));

      return ((SiContextScanner) getController()).getCellValue().get(annotationClass);
    }


    @Override
    public SkScan setAnnotationClass(SiClass<? extends Annotation> annotationClass) {
      return swapSubCell("annotationClass", annotationClass);
    }
  }
}
