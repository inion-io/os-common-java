package io.inion.os.common.discovery.analyzer;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.analyzer.skills.SkFinish;
import io.inion.os.common.discovery.analyzer.skills.SkProcess;
import io.inion.os.common.discovery.analyzer.skills.SkProcessClasses;
import io.inion.os.common.discovery.context.SiContextScanner;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiBoolean;
import io.inion.os.common.types.SiClass;

@CellType(
    objectClass = SiCellAnalyzerRegistry.SiCellAnalyzerRegistryObject.class,
    type = SiCellAnalyzerRegistry.CELL_TYPE,
    uuid = SiCellAnalyzerRegistry.CELL_UUID
)
public interface SiCellAnalyzerRegistry extends SiCell<SiCellAnalyzerRegistry, Void> {

  String CELL_TYPE = "cell-analyzer-registry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  void finish() throws IllegalStateException;

  void process(SiCell<?, ?> cell);

  void register(SiCellAnalyzer analyzer);

  SiBoolean isFinished();

  class SiCellAnalyzerRegistryObject extends SiCellObject<SiCellAnalyzerRegistry, Void> implements
      SiCellAnalyzerRegistry {

    private SiContextScanner contextScanner;
    private SkFinish skFinish;
    private SkProcess skProcess;
    private SkProcessClasses skProcessClasses;
    private SiBoolean finished;

    @Override
    public void afterCreate() {
      processClasses();
    }

    @Override
    public SiCellAnalyzerRegistry create() {
      contextScanner = rootSkill(SiContextScanner.class);
      skProcess = buildCell(SkProcess.class, "skProcess");
      skFinish = buildCell(SkFinish.class, "skFinish");
      skProcessClasses = buildCell(SkProcessClasses.class, "skProcessClasses");
      finished = buildCell(SiBoolean.class).setCellValue(Boolean.FALSE);

      setCreated(true);

      processClasses();

      return getSelf();
    }

    @Override
    public void finish() throws IllegalStateException {
      if (finished.getCellValue()) {
        throw new IllegalStateException("TODO: Excepion Handling. Discovery not initialized");
      }

      finished.setCellValue(Boolean.TRUE);

      try {
        skFinish
            .createTransientInstance()
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        // TODO Exception Handling
        log().info("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public void process(SiCell<?, ?> cell) {
      try {
        skProcess
            .createTransientInstance()
            .setCellValue(cell)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public void register(SiCellAnalyzer analyzer) {

    }

    @Override
    public SiBoolean isFinished() {
      return finished;
    }

    private void processClasses() {
      try {
        skProcessClasses
            .createTransientInstance()
            .setCellValue(contextScanner.scan(
                createTransientCell(SiClass.class, CellType.class)))
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public JsonObject toJsonObject() {
      return new JsonObject();
    }
  }
}
