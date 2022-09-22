package io.inion.os.common.discovery.messages;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.messaging.SiEventMessage;
import io.inion.os.common.discovery.messages.SiDiscoveryRestored.SiDiscoveryRestoredObject;

@CellType(
    objectClass = SiDiscoveryRestoredObject.class,
    type = SiDiscoveryRestored.CELL_TYPE,
    uuid = SiDiscoveryRestored.CELL_UUID
)
public interface SiDiscoveryRestored extends SiEventMessage<SiDiscoveryRestored> {

  String CELL_TYPE = "event-message-discovery-restored";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiDiscoveryRestoredObject extends SiEventMessageObject<SiDiscoveryRestored> implements
      SiDiscoveryRestored {
  }
}
