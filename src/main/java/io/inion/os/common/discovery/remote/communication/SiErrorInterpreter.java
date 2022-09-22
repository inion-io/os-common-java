package io.inion.os.common.discovery.remote.communication;

import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.discovery.remote.communication.SiErrorInterpreter.SiErrorInterpreterObject;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiErrorInterpreterObject.class,
    type = SiErrorInterpreter.CELL_TYPE,
    uuid = SiErrorInterpreter.CELL_UUID
)
public interface SiErrorInterpreter extends SiCommunicationInterpreter {

  String COMMAND = "error";
  String CELL_TYPE = "communication-error-interpreter";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiErrorInterpreterObject extends
      SiCommunicationInterpreterObject implements
      SiErrorInterpreter {

    @Cell(value = COMMAND)
    private SiString command;

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCommunicationMessage processRequest(SiCommunicationMessage message) {
      //log().info("Receive Request: " + message.toString());
      return null;
    }

    @Override
    public SiCommunicationMessage processResponse(SiCommunicationMessage message) {
      //log().info("Receive Response: " + message.toString());
      return null;
    }
  }
}
