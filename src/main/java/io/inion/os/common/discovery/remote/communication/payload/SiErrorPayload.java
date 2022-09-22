package io.inion.os.common.discovery.remote.communication.payload;

import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiErrorPayload.SiErrorPayloadObject.class,
    type = SiErrorPayload.CELL_TYPE,
    uuid = SiErrorPayload.CELL_UUID
)
public interface SiErrorPayload extends SiString {

  String CELL_TYPE = "error-communication-message-payload";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiErrorPayloadObject extends SiStringObject implements SiErrorPayload {}
}
