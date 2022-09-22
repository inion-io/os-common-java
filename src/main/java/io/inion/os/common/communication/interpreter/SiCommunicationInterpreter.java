package io.inion.os.common.communication.interpreter;

import io.inion.os.common.SiCell;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.types.SiString;

public interface SiCommunicationInterpreter extends
    SiCell<SiCommunicationInterpreter, SiCommunicationMessage> {

  String CELL_TYPE = "communication-interpreter";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiString getCommand();

  SiCommunicationInterpreter setMessage(SiCommunicationMessage message);

  SiCommunicationMessage processRequest(SiCommunicationMessage message);

  SiCommunicationMessage processResponse(SiCommunicationMessage message);

  abstract class SiCommunicationInterpreterObject extends
      SiCellObject<SiCommunicationInterpreter, SiCommunicationMessage> implements
      SiCommunicationInterpreter {

    protected SiCommunicationMessage message;

    @Override
    public SiCommunicationInterpreter setMessage(SiCommunicationMessage message) {
      this.message = message;
      return getSelf();
    }
  }
}
