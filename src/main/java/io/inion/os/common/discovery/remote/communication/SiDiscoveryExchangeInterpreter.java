package io.inion.os.common.discovery.remote.communication;

import io.inion.os.common.SiDiscoveryManager;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryManager;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryExchangeInterpreter.SiDiscoveryExchangeInterpreterObject;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiDiscoveryExchangeInterpreterObject.class,
    type = SiDiscoveryExchangeInterpreter.CELL_TYPE,
    uuid = SiDiscoveryExchangeInterpreter.CELL_UUID
)
public interface SiDiscoveryExchangeInterpreter extends SiCommunicationInterpreter {

  String COMMAND = "discovery-exchange";
  String CELL_TYPE = "discovery-exchange-communication-interpreter";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiDiscoveryExchangeInterpreterObject extends
      SiCommunicationInterpreterObject implements
      SiDiscoveryExchangeInterpreter {

    @Cell(value = COMMAND)
    private SiString command;

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCommunicationMessage processRequest(SiCommunicationMessage message) {
      if (log().isDebugEnabled()) {
        //log().debug("Receive response from " + message.getSender().getCellValue());
      }

      SiCommunicationMessage response = createTransientCell(SiCommunicationMessage.class);
      response.setCommand(createTransientCell(SiString.class, COMMAND));
      response.setPayload(createTransientCell(SiRemoteDiscovery.class).restore(root().toJsonObject()));

      SiRemoteDiscoveryManager remoteDiscoveryManager = rootSkill(SiRemoteDiscoveryManager.class);
      remoteDiscoveryManager.processRemoteDiscovery(message.getSender(),
          (SiRemoteDiscovery) message.getPayload());

      return response;
    }

    @Override
    public SiCommunicationMessage processResponse(SiCommunicationMessage message) {
      if (log().isDebugEnabled()) {
        //log().debug("Receive response from " + message.getSender().getCellValue());
      }

      SiRemoteDiscoveryManager remoteDiscoveryManager = rootSkill(SiRemoteDiscoveryManager.class);
      //remoteDiscoveryManager.processRemoteDiscovery(message.getSender(),
          //(SiRemoteDiscovery) message.getPayload());

      return message;
    }
  }
}
