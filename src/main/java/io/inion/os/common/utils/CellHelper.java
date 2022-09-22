package io.inion.os.common.utils;

import io.inion.os.common.SiCell;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CellHelper {

  private static void createFieldsList(Class<?> clazz, Class<? extends Annotation> annotationClass,
      List<Field> fields) {

    Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.getType().isInterface())
        .filter(field -> field.isAnnotationPresent(annotationClass))
        .forEach(fields::add);

    if (SiCell.class.isAssignableFrom(clazz.getSuperclass())) {
      createFieldsList(clazz.getSuperclass(), annotationClass, fields);
    }
  }

  public static List<Field> getFields(Class<?> clazz, Class<? extends Annotation> annotationClass,
      Comparator<Field> comparator) {

    List<Field> fields = new ArrayList<>();

    createFieldsList(clazz, annotationClass, fields);

    if (comparator != null) {
      Collections.sort(fields, comparator);
    }

    return fields;
  }

  public static Field getField(String name, Class<?> clazz) {
    if (name == null || name.isEmpty()) {
      return null;
    }

    for (Field field : getAllFields(new ArrayList<>(), clazz)) {
      if (field.getName().equals(name)) {

        return field;
      }
    }

    return null;
  }

  private static List<Field> getAllFields(List<Field> fields, Class<?> clazz) {
    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

    if (clazz.getSuperclass() != null) {
      getAllFields(fields, clazz.getSuperclass());
    }

    return fields;
  }

  public static boolean hasField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
    return !getFields(clazz, annotationClass, null).isEmpty();
  }
}
