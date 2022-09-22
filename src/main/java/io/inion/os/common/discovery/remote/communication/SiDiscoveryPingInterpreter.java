package io.inion.os.common.discovery.remote.communication;

import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryManager;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryPingInterpreter.SiDiscoveryPingInterpreterObject;
import io.inion.os.common.discovery.remote.communication.payload.SiPingPayload;
import io.inion.os.common.discovery.remote.communication.payload.SiPongPayload;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiDiscoveryPingInterpreterObject.class,
    type = SiDiscoveryPingInterpreter.CELL_TYPE,
    uuid = SiDiscoveryPingInterpreter.CELL_UUID
)
public interface SiDiscoveryPingInterpreter extends SiCommunicationInterpreter {

  String COMMAND = "discovery-ping";
  String CELL_TYPE = "discovery-ping-communication-interpreter";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiDiscoveryPingInterpreterObject extends
      SiCommunicationInterpreterObject implements
      SiDiscoveryPingInterpreter {

    @Cell(value = COMMAND)
    private SiString command;

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCommunicationMessage processRequest(SiCommunicationMessage message) {
      if (log().isDebugEnabled()) {
        log().debug("Receive request: " + message.toString());
      }

      if (message.getPayload().getCellType().equals(SiPingPayload.CELL_TYPE)) {
        SiCommunicationMessage response = createTransientCell(SiCommunicationMessage.class);
        response.setCommand(createTransientCell(SiString.class, COMMAND));
        response.setPayload(createTransientCell(SiPongPayload.class));

        SiRemoteDiscoveryManager remoteDiscoveryManager = (SiRemoteDiscoveryManager) getController();
        remoteDiscoveryManager.registerRemoteDiscovery(message.getSender());

        return response;
      }

      return null;
    }

    @Override
    public SiCommunicationMessage processResponse(SiCommunicationMessage message) {
      if (log().isDebugEnabled()) {
        log().debug("Receive response: " + message.toString());
      }

      if (message.getPayload().getCellType().equals(SiPongPayload.CELL_TYPE) ) {
        SiRemoteDiscoveryManager remoteDiscoveryManager = (SiRemoteDiscoveryManager) getController();
        remoteDiscoveryManager.registerRemoteDiscovery(message.getSender());
      }

      return message;
    }
  }
}
