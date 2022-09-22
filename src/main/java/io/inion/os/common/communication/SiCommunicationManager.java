package io.inion.os.common.communication;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistry;
import io.inion.os.common.communication.skills.SkProcessRequest;
import io.inion.os.common.communication.skills.SkProcessResponse;
import io.inion.os.common.communication.skills.SkRegisterCommunicationInterpreter;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;

public interface SiCommunicationManager<C extends SiCell<?, ?>> extends SiCell<C, Void> {

  String CELL_TYPE = "communication-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  void dispose();

  void listen(SiString host, SiInteger port);

  SiCommunicationMessage processRequest(SiCommunicationMessage message);

  SiCommunicationMessage processResponse(SiCommunicationMessage message);

  void registerInterpreter(SiCommunicationInterpreter interpreter);

  SiCommunicationMessage requestResponse(SiString host, SiInteger port,
      SiCommunicationMessage message);

  SiCommunicationMessage requestResponse(SiURI cellURI, SiCommunicationMessage message);

  abstract class SiCommunicationManagerObject<C extends SiCell<?, ?>> extends SiCellObject<C, Void> implements
      SiCommunicationManager<C> {

    @Cell
    private SiCommunicationInterpreterRegistry communicationInterpreterRegistry;
    @Cell
    private SkProcessRequest skProcessRequest;
    @Cell
    private SkProcessResponse skProcessResponse;
    @Cell
    private SkRegisterCommunicationInterpreter skRegisterCommunicationInterpreter;

    @Override
    public SiCommunicationMessage processRequest(SiCommunicationMessage message) {
      try {
        return skProcessRequest
            .createTransientInstance()
            .setCellValue(message)
            .returnAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }

      return null;
    }

    @Override
    public SiCommunicationMessage processResponse(SiCommunicationMessage message) {
      try {
        return skProcessResponse
            .createTransientInstance()
            .setCellValue(message)
            .returnAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }

      return null;
    }

    @Override
    public void registerInterpreter(SiCommunicationInterpreter interpreter) {
      try {
        skRegisterCommunicationInterpreter
            .createTransientInstance()
            .setCellValue(interpreter)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }
  }
}
