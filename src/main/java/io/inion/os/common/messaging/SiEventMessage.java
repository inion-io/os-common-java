package io.inion.os.common.messaging;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.messaging.SiEventMessage.SiEventMessageObject;

@CellType(
    objectClass = SiEventMessageObject.class,
    type = SiEventMessage.CELL_TYPE,
    uuid = SiEventMessage.CELL_UUID
)
public interface SiEventMessage<C extends SiCell<?, ?>> extends SiCell<C, JsonObject> {

  String CELL_TYPE = "event-message";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiEventMessageObject<C extends SiCell<?, ?>> extends SiCellObject<C, JsonObject> implements
      SiEventMessage<C> {

  }
}
