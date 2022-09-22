package io.inion.os.common.discovery.remote.communication.payload;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiPongPayload.SiPongPayloadObject.class,
    type = SiPongPayload.CELL_TYPE,
    uuid = SiPongPayload.CELL_UUID
)
public interface SiPongPayload extends SiString {

  String PONG = "pong";
  String CELL_TYPE = "discovery-ping-communication-message-payload-pong";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiPongPayloadObject extends SiStringObject implements SiPongPayload {

    @Override
    public String getCellValue() {
      return SiPongPayload.PONG;
    }
  }
}
