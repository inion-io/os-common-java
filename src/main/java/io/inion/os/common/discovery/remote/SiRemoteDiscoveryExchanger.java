package io.inion.os.common.discovery.remote;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationManager;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryExchanger.SiRemoteDiscoveryExchangerObject;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryExchangeInterpreter;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiJsonObject;
import io.inion.os.common.types.SiString;
import java.net.URI;

@CellType(
    objectClass = SiRemoteDiscoveryExchangerObject.class,
    type = SiRemoteDiscoveryExchanger.CELL_TYPE,
    uuid = SiRemoteDiscoveryExchanger.CELL_UUID
)
public interface SiRemoteDiscoveryExchanger extends
    SiCell<SiRemoteDiscoveryExchanger, Void> {

  String CELL_TYPE = "remote-discovery-exchanger";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiRemoteDiscoveryExchangerObject extends
      SiCellObject<SiRemoteDiscoveryExchanger, Void> implements
      SiRemoteDiscoveryExchanger {

    @Override
    public SiRemoteDiscoveryExchanger run() throws CellRunException {

      SiCommunicationManager<?> communicationManager = (SiCommunicationManager<?>) rootSkill(
          SiCommunicationManager.CELL_TYPE);

      getController().getSubCells(SiRemoteDiscovery.class).forEach(remoteDiscovery -> {
        URI cellURI = remoteDiscovery.getCellURI();

        SiString host = createTransientCell(SiString.class, cellURI.getHost());
        SiInteger port = createTransientCell(SiInteger.class, cellURI.getPort());
        SiString command = createTransientCell(SiString.class, SiDiscoveryExchangeInterpreter.COMMAND);
        SiRemoteDiscovery payload = createTransientCell(SiRemoteDiscovery.class).restore(root().toJsonObject());
        SiCommunicationMessage request = createTransientCell(SiCommunicationMessage.class);

        request.setCommand(command);
        request.setPayload(payload);

        communicationManager.requestResponse(host, port, request);
      });

      return getSelf();
    }
  }
}
