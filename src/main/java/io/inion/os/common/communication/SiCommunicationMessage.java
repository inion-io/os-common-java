package io.inion.os.common.communication;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiCommunicationMessage.SiCommunicationMessageObject.class,
    type = SiCommunicationMessage.CELL_TYPE,
    uuid = SiCommunicationMessage.CELL_UUID
)
public interface SiCommunicationMessage extends SiCell<SiCommunicationMessage, Void> {

  String CELL_TYPE = "communication-message";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiString getCommand();

  SiCell<?, ?> getPayload();

  SiString getSender();

  SiCommunicationMessage setCommand(SiString command);

  SiCommunicationMessage setPayload(SiCell<?, ?> payload);

  class SiCommunicationMessageObject extends SiCellObject<SiCommunicationMessage, Void> implements
      SiCommunicationMessage {

    @Cell
    private SiString command;
    private SiCell<?, ?> payload;
    @Cell
    private SiString sender;

    @Override
    public void afterCreate() {
      sender.setCellValue(root().getCellURI().toString());
    }

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCell<?, ?> getPayload() {
      return payload;
    }

    @Override
    public SiString getSender() {
      return sender;
    }

    @Override
    public SiCommunicationMessage setCommand(SiString command) {
      return swapSubCell(this.command, command);
    }

    @Override
    public SiCommunicationMessage setPayload(SiCell<?, ?> payload) {
      if (payload == null) {
        return getSelf();
      }
      return swapSubCell("payload", payload.createTransientInstance());
    }
  }
}
