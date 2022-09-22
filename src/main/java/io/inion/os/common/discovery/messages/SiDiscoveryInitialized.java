package io.inion.os.common.discovery.messages;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.messaging.SiEventMessage;
import io.inion.os.common.discovery.messages.SiDiscoveryInitialized.SiDiscoveryInitializedObject;

@CellType(
    objectClass = SiDiscoveryInitializedObject.class,
    type = SiDiscoveryInitialized.CELL_TYPE,
    uuid = SiDiscoveryInitialized.CELL_UUID
)
public interface SiDiscoveryInitialized extends SiEventMessage<SiDiscoveryInitialized> {

  String CELL_TYPE = "event-message-discovery-initialized";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiDiscoveryInitializedObject extends
      SiEventMessageObject<SiDiscoveryInitialized> implements
      SiDiscoveryInitialized {

  }
}
