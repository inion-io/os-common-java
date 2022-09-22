package io.inion.os.common.discovery.remote.communication.payload;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiPingPayload.SiPingPayloadObject.class,
    type = SiPingPayload.CELL_TYPE,
    uuid = SiPingPayload.CELL_UUID
)
public interface SiPingPayload extends SiString {

  String PING = "ping";
  String CELL_TYPE = "discovery-ping-communication-message-payload-ping";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiPingPayloadObject extends SiStringObject implements SiPingPayload {

    @Override
    public String getCellValue() {
      return SiPingPayload.PING;
    }
  }
}
