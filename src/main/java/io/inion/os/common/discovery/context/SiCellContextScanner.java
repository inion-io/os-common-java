package io.inion.os.common.discovery.context;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

@Deprecated
public interface SiCellContextScanner extends SiCell<SiCellContextScanner, List<Class<?>>> {

  void setBasePackages(String... basePackages);

  class SiCellContextScannerObject extends SiCellObject<SiCellContextScanner, List<Class<?>>>
      implements SiCellContextScanner {

    private String[] basePackages;
    private Reflections reflections;

    @Override
    public void afterCreate() {
      ConfigurationBuilder builder =
          new ConfigurationBuilder().setScanners(new SubTypesScanner(),
              new TypeAnnotationsScanner());

      builder.addUrls(ClasspathHelper.forPackage("org.ourplant"));

      if (basePackages != null) {
        Arrays.stream(basePackages).forEach(basePackage ->
            builder.addUrls(ClasspathHelper.forPackage(basePackage)));
      }

      reflections = new Reflections(builder);
    }

    @Override
    public List<Class<?>> getCellValue() {
      if (cellValue != null) {
        return cellValue;
      }

      cellValue = reflections.getTypesAnnotatedWith(CellType.class)
          .stream()
          .filter(Class::isInterface)
          .filter(SiCell.class::isAssignableFrom)
          .collect(Collectors.toList());

      return cellValue;
    }

    @Override
    public void setBasePackages(String... basePackages) {
      this.basePackages = basePackages;
    }
  }
}
