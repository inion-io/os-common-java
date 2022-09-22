package io.inion.os.common.discovery.context;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.DiscoveryApplication;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

@Deprecated
public interface SiApplicationContextScanner extends SiCell<SiApplicationContextScanner, Class<?>> {

  class SiApplicationContextScannerObject extends
      SiCellObject<SiApplicationContextScanner, Class<?>>
      implements SiApplicationContextScanner {

    protected Class<?> value;
    private Reflections reflections;

    @Override
    public void afterCreate() {

      ConfigurationBuilder builder = new ConfigurationBuilder().setScanners(
          new TypeAnnotationsScanner());

      // TODO: Suche nach OurPlantApplication darf sich nicht nur auf org.ourplant beziehen
      //builder.addUrls(ClasspathHelper.forPackage("org.ourplant"));
      builder.setUrls(ClasspathHelper.forJavaClassPath());

      reflections = new Reflections(builder);
    }

    @Override
    public Class<?> getCellValue() {
      if (value != null) {
        return value;
      }

      List<Class<?>> classes =
          reflections.getTypesAnnotatedWith(DiscoveryApplication.class)
              .stream()
              .filter(clazz -> !clazz.isLocalClass())
              .filter(clazz -> !clazz.isAnonymousClass())
              .filter(clazz -> !clazz.isInterface())
              .filter(clazz -> !clazz.isAnnotation())
              .filter(clazz -> !clazz.isArray())
              .filter(clazz -> !clazz.isEnum())
              .filter(clazz -> !clazz.isPrimitive())
              .filter(clazz -> !clazz.isSynthetic())
              .collect(Collectors.toList());

      if (classes.size() > 1) {
        log().warn("Found multiple classes annotated with OurPlantApplication.");

        IntStream.range(0, classes.size()).filter(index -> index > 0).forEach(
            index -> log().warn("Ignoring Class: " + classes.get(index)));
      }

      value = classes.isEmpty() ? null : classes.get(0);

      return value;
    }
  }
}
