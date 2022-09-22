package io.inion.os.common.injection;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtPrimitiveType;
import javassist.LoaderClassPath;
import org.junit.jupiter.api.Test;


public class RemoteCellInjectionTest {

  @Test
  void createNewCell() throws Exception {

    ClassPool pool = ClassPool.getDefault();
    ClassLoader classLoader = this.getClass().getClassLoader();
    pool.appendClassPath(new LoaderClassPath(classLoader));

    CtClass interfaceClass = pool.get("org.ourplant.dba.cfi.layout.SiCfiLayoutService");
    CtClass cc = pool.makeClass("org.ourplant.dba.cfi.layout.SiCfiLayoutServiceRemoteWrapper");
    cc.setInterfaces(new CtClass[] { interfaceClass });


    //pool.importPackage("org.ourplant.dxp.c");

    String methodBody = "{System.out.println(\"HALLO\"); return null;}";

    //CtClass apiClass = pool.get("com.formula.FormulaAPI");
    //CtClass returnTypeClass = pool.get(SiCfiLayout.class.getName());

    //CtMethod m = new CtMethod(returnTypeClass,"getLayout",null,cc);
    //m.setBody(methodBody);
    //cc.addMethod(m);

    //cc.toClass();
    //System.out.println(obj);

    //SiCfiLayoutService layoutService = (SiCfiLayoutService)obj;
    //System.out.println(layoutService.getLayout());

    Class<?> clazz = Class.forName("org.ourplant.dba.cfi.layout.SiCfiLayoutServiceRemoteWrapper");
    System.out.println(clazz);

  }
}
