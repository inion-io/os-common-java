package io.inion.os.common.messaging.eventbus;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.messaging.SiEventMessage;

public interface SiEventBus<C extends SiCell<?, ?>> extends SiCell<C, JsonObject> {

  String CELL_TYPE = "event-bus";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  void checkAndRegister(SiCell<?, ?> cell);

  void publish(SiEventMessage<?> message);

  void register(SiCell<?, ?> listener);

  void unregister(SiCell<?, ?> listener);
}
