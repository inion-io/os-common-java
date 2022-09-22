package io.inion.os.common.communication.interpreter;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiCommunicationInterpreterRegistryEntry.SiCommunicationInterpreterRegistryEntryObject.class,
    type = SiCommunicationInterpreterRegistryEntry.CELL_TYPE,
    uuid = SiCommunicationInterpreterRegistryEntry.CELL_UUID
)
public interface SiCommunicationInterpreterRegistryEntry extends
    SiCell<SiCommunicationInterpreterRegistryEntry, SiCommunicationInterpreter> {

  String CELL_TYPE = "communication-interpreter-registry-entry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  SiString getCommand();

  SiCommunicationInterpreterRegistryEntry setCommand(SiString command);

  class SiCommunicationInterpreterRegistryEntryObject extends
      SiCellObject<SiCommunicationInterpreterRegistryEntry, SiCommunicationInterpreter> implements
      SiCommunicationInterpreterRegistryEntry {

    @Cell
    private SiString command;

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCommunicationInterpreterRegistryEntry setCommand(SiString command) {
      return this.swapSubCell(this.command, command);
    }
  }
}
