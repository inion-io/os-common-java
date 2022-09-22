package io.inion.os.common.discovery.injection;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.skills.SkCheckAndInject;
import io.inion.os.common.discovery.injection.skills.SkCheckAndInjectRemoteCells;
import io.inion.os.common.discovery.injection.skills.SkFinish;
import io.inion.os.common.discovery.injection.skills.SkInjectCellReferences;
import io.inion.os.common.discovery.injection.skills.SkInjectCells;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiBoolean;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiCellInjector.SiCellInjectorObject.class,
    type = SiCellInjector.CELL_TYPE,
    uuid = SiCellInjector.CELL_UUID
)
public interface SiCellInjector extends SiCell<SiCellInjector, Void> {

  String CELL_TYPE = "cell-injector";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  String LOST_REFERENCE_HANDLING_REMOTE_DISCOVERY = "remote-discovery";

  String LOST_REFERENCE_HANDLING_REMOTE_SELF_TCP = "remote-self-tcp";

  String LOST_REFERENCE_HANDLING_REMOTE_SELF_WS = "remote-self-ws";

  void checkAndInject(SiCell<?, ?> cell);

  void checkAndInjectRemoteCells(SiRemoteDiscovery remoteDiscovery);

  void finish() throws IllegalStateException;

  void injectCellReferences(SiCell<?, ?> cell);

  void injectCells(SiCell<?, ?> cell);

  SiString getLostReferenceHandling();

  SiCellInjector setLostReferenceHandling(SiString lostReferenceHandling);

  SiBoolean isFinished();

  class SiCellInjectorObject extends SiCellObject<SiCellInjector, Void> implements SiCellInjector {

    private SiBoolean finished;
    private SiString lostReferenceHandling;
    private SkCheckAndInject skCheckAndInject;
    private SkCheckAndInjectRemoteCells skCheckAndInjectRemoteCells;
    private SkFinish skFinish;
    private SkInjectCellReferences skInjectCellReferences;
    private SkInjectCells skInjectCells;

    @Override
    public void checkAndInject(SiCell<?, ?> cell) {
      try {
        skCheckAndInject
            .createTransientInstance()
            .setCell(cell)
            .runAndDestroy();
      } catch (CellRunException e) {
        log().info(e);
      }
    }

    @Override
    public void checkAndInjectRemoteCells(SiRemoteDiscovery remoteDiscovery) {
      try {
        skCheckAndInjectRemoteCells
            .createTransientInstance()
            .setRemoteDiscovery(remoteDiscovery)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        // TODO Exception Handling
        log().info("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public SiCellInjector create() {
      skCheckAndInject = buildCell(SkCheckAndInject.class, "checkAndInject");
      skInjectCellReferences = buildCell(SkInjectCellReferences.class, "injectCellReferences");
      skInjectCells = buildCell(SkInjectCells.class, "injectCells");
      skCheckAndInjectRemoteCells = buildCell(SkCheckAndInjectRemoteCells.class,
          "skCheckAndInjectRemoteCells");
      skFinish = buildCell(SkFinish.class, "skFinish");
      finished = buildCell(SiBoolean.class).setCellValue(Boolean.FALSE);
      lostReferenceHandling = buildCell(SiString.class, "lostReferenceHandling").setCellValue(
          SiCellInjector.LOST_REFERENCE_HANDLING_REMOTE_DISCOVERY);

      setCreated(true);

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
            .returnAndDestroy();
      } catch (CellRunException cellRunException) {
        // TODO Exception Handling
        log().info("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public void injectCellReferences(SiCell<?, ?> cell) {
      try {
        skInjectCellReferences
            .createTransientInstance()
            .setCell(cell)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        // TODO Exception Handling
        log().info("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public void injectCells(SiCell<?, ?> cell) {
      try {
        skInjectCells
            .createTransientInstance()
            .setCell(cell)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        // TODO Exception Handling
        log().info("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public SiString getLostReferenceHandling() {
      return this.lostReferenceHandling;
    }

    @Override
    public SiCellInjector setLostReferenceHandling(SiString lostReferenceHandling) {
      return this.swapSubCell("lostReferenceHandling", lostReferenceHandling);
    }

    @Override
    public SiBoolean isFinished() {
      return finished;
    }
  }
}
