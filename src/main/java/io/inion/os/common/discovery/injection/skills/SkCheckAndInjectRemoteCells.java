package io.inion.os.common.discovery.injection.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.SiDiscoveryManager;
import io.inion.os.common.annotation.CellReference;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationManager;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.discovery.injection.SiCellInjectorLostCellReference;
import io.inion.os.common.discovery.remote.SiRemoteCell;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.discovery.remote.communication.SiExecuteCellSkillInterpreter;
import io.inion.os.common.discovery.remote.communication.payload.SiExecuteCellSkillPayload;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;
import io.inion.os.common.utils.CellHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtPrimitiveType;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;

@CellType(
    objectClass = SkCheckAndInjectRemoteCells.SkCheckAndInjectRemoteCellsObject.class,
    type = SkCheckAndInjectRemoteCells.CELL_TYPE,
    uuid = SkCheckAndInjectRemoteCells.CELL_UUID
)
public interface SkCheckAndInjectRemoteCells extends SiCell<SkCheckAndInjectRemoteCells, Void> {

  String CELL_TYPE = "cell-injector-check-and-inject-remote-cells-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkCheckAndInjectRemoteCells setRemoteDiscovery(SiRemoteDiscovery remoteDiscovery);

  class SkCheckAndInjectRemoteCellsObject extends
      SiCellObject<SkCheckAndInjectRemoteCells, Void> implements
      SkCheckAndInjectRemoteCells {

    private SiRemoteDiscovery remoteDiscovery;

    @Override
    public SkCheckAndInjectRemoteCells run() throws CellRunException {
      checkRunValuesForNull(remoteDiscovery);

      // TODO: See: https://www.baeldung.com/javassist
      // See: https://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Proxy.html
      //  See: https://stackoverflow.com/questions/1268207/how-to-create-an-interface-at-runtime
      //  See: https://stackoverflow.com/questions/60693436/in-javassist-how-to-set-return-type-of-method-to-java-lang-double-when-creating

      for (SiCellInjectorLostCellReference cellReference : getController().getSubCells(
          SiCellInjectorLostCellReference.class)) {
        for (Field field : CellHelper.getFields(cellReference.getCellValue().getClass(),
            CellReference.class, null)) {
          field.setAccessible(true);

          try {
            if (field.get(cellReference.getCellValue()) != null) {
              continue;
            }
          } catch (IllegalAccessException illegalAccessException) {
            log().info("TODO: Exception Handling!", illegalAccessException);
          }

          Class<SiCell<?, ?>> cellClass = (Class<SiCell<?, ?>>) field.getType();

          String cellUUID = field.getAnnotation(CellReference.class).uuid();
          String cellType = field.getAnnotation(CellReference.class).type();

          remoteDiscovery.getSubCells(SiRemoteCell.class).forEach(remoteCell -> {
            if (cellType != null && !cellType.isEmpty() && remoteCell.getRemoteCellType()
                .getCellValue().equals(cellType)) {

              Class<?> wrapperClass = null;
              String wrapperClassName = cellClass.getName() + "RemoteWrapper";

              try {
                wrapperClass = Class.forName(wrapperClassName);
              } catch (ClassNotFoundException classNotFoundException) {
                try {
                  ClassPool pool = ClassPool.getDefault();
                  ClassLoader classLoader = this.getClass().getClassLoader();
                  pool.appendClassPath(new LoaderClassPath(classLoader));

                  pool.importPackage("java.lang.reflect");
                  pool.importPackage("com.google.gson");
                  pool.importPackage("io.inion.os.common");
                  pool.importPackage("io.inion.os.common.communication");
                  pool.importPackage("io.inion.os.common.discovery.remote.communication.payload");
                  pool.importPackage("io.inion.os.common.types");

                  CtClass interfaceClass = pool.get(cellClass.getName());
                  CtClass ctClass = pool.makeClass(wrapperClassName);
                  ctClass.setInterfaces(new CtClass[]{interfaceClass});

                  createGetAndSetField(SiCell.PROPERTY_CELL_CLASS, Class.class, ctClass,
                      pool, null, null);
                  createGetAndSetField(SiCell.PROPERTY_CELL_DISPLAY_NAME, String.class,
                      ctClass, pool, null, null);
                  createGetAndSetField(SiCell.PROPERTY_CELL_NAME, String.class, ctClass,
                      pool, null, null);
                  createGetAndSetField(SiCell.PROPERTY_CELL_URI, URI.class, ctClass, pool,
                      null, null);
                  createGetAndSetField("controllerField", Field.class, ctClass, pool,
                      null, null);
                  createGetAndSetField("created", boolean.class, ctClass, pool, null,
                      null);
                  createGetAndSetField("rootCell", SiDiscoveryManager.class, ctClass,
                      pool, "root", null);

                  for (Method method : cellClass.getDeclaredMethods()) {
                    createCellRemoteMethod(method, ctClass, pool);
                  }

                  SiCell<?, ?> remoteCellWrapper = (SiCell<?, ?>) ctClass.toClass()
                      .newInstance();
                  remoteCellWrapper.setCellClass(cellClass);
                  remoteCellWrapper.setCellDisplayName(remoteCell.getCellDisplayName());
                  remoteCellWrapper.setCellName(field.getName());
                  remoteCellWrapper.setCellURI(remoteCell.getRemoteCellURI().getCellValue());
                  remoteCellWrapper.setRootCell(root());

                  //subCell.getCellValue().addSubCell(remoteCellWrapper);

                  try {
                    field.setAccessible(true);
                    field.set(cellReference.getCellValue(), remoteCellWrapper);
                    remoteCellWrapper.setControllerField(field);
                  } catch (IllegalAccessException illegalAccessException) {
                    log().info("TODO: Exception Handling!", illegalAccessException);
                  }

                } catch (IllegalAccessException illegalAccessException) {
                  log().debug("TODO: Exception Handling!", illegalAccessException);
                } catch (InstantiationException instantiationException) {
                  log().debug("TODO: Exception Handling!", instantiationException);
                } catch (CannotCompileException cannotCompileException) {
                  log().debug("TODO: Exception Handling!", cannotCompileException);
                  //} catch (DuplicatedSubCellException duplicatedSubCellException) {
                  //log().info("TODO: Exception Handling!", duplicatedSubCellException);
                } catch (NotFoundException notFoundException) {
                  log().debug("TODO: Exception Handling!", notFoundException);
                }
              }
            }
          });
        }
      }

      return getSelf();
    }

    private void createGetAndSetField(String fieldName, Class<?> fieldType, CtClass ctClass,
        ClassPool pool, String getMethodName, String setMethodName)
        throws CannotCompileException, NotFoundException {

      CtClass typeClass = pool.get(fieldType.getName());
      CtField field = new CtField(typeClass, fieldName, ctClass);
      field.setModifiers(Modifier.PRIVATE);
      ctClass.addField(field, (CtField.Initializer) null);

      String getMethodBody = "{return this." + fieldName + ";}";
      getMethodName = getMethodName == null ?
          "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
          : getMethodName;

      CtMethod getMethod = new CtMethod(typeClass, getMethodName, null, ctClass);
      getMethod.setBody(getMethodBody);
      ctClass.addMethod(getMethod);

      String setMethodBody = "{this." + fieldName + " = $1;}";
      setMethodName = setMethodName == null ?
          "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
          : setMethodName;

      CtMethod setMethod = new CtMethod(CtPrimitiveType.voidType, setMethodName,
          new CtClass[]{typeClass}, ctClass);
      setMethod.setBody(setMethodBody);
      ctClass.addMethod(setMethod);
    }

    private void createCellRemoteMethod(Method method, CtClass ctClass, ClassPool pool)
        throws CannotCompileException, NotFoundException {

      boolean isVoid = method.getReturnType().equals(void.class);

      CtClass[] ctClasses = null;
      StringBuilder methodBody = new StringBuilder();
      methodBody.append("{");
      methodBody.append("SiCommunicationManager communicationManager = (SiCommunicationManager) root().findSubCell(\"" + SiCommunicationManager.CELL_TYPE + "\");");
      methodBody.append("SiString command = root().createTransientCell(SiString.class, \"" + SiExecuteCellSkillInterpreter.COMMAND + "\");");
      methodBody.append("SiExecuteCellSkillPayload payload = root().createTransientCell(SiExecuteCellSkillPayload.class);");
      methodBody.append("SiCommunicationMessage request = root().createTransientCell(SiCommunicationMessage.class);");
      methodBody.append("SiURI uri = root().createTransientCell(SiURI.class, getCellURI());");
      methodBody.append("SiString skillName = root().createTransientCell(SiString.class, \"" + method.getName() + "\");");
      methodBody.append("SiList parameters = root().createTransientCell(SiList.class);");
      methodBody.append("payload.setURI(uri);");
      methodBody.append("payload.setSkillName(skillName);");
      methodBody.append("payload.setParameters(parameters);");

      methodBody.append("request.setCommand(command);");
      methodBody.append("request.setPayload(payload);");

      if (method.getParameterTypes().length > 0) {
        for (int a = 1; a <= method.getParameterTypes().length; a++) {
          Class<?> parameterClass = method.getParameterTypes()[a - 1];
          ctClasses = ArrayUtils.add(ctClasses, pool.get(parameterClass.getName()));
          methodBody.append("parameters.add($a);");
        }
      }

      methodBody.append("SiCommunicationMessage response = communicationManager.requestResponse(uri, request);");

      if (!isVoid) {
        methodBody.append("return (" + method.getReturnType().getTypeName() + ") response.getPayload();");
      }

      methodBody.append("}");

      log().debug(methodBody.toString());

      CtClass returnTypeClass = pool.get(method.getReturnType().getName());

      CtMethod ctMethod = new CtMethod(returnTypeClass, method.getName(), ctClasses, ctClass);
      ctMethod.setBody(methodBody.toString());
      ctClass.addMethod(ctMethod);
    }

    @Override
    public SkCheckAndInjectRemoteCells setRemoteDiscovery(SiRemoteDiscovery remoteDiscovery) {
      this.remoteDiscovery = remoteDiscovery;
      return getSelf();
    }
  }
}
