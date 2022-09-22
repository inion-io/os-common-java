package io.inion.os.common.communication.interpreter;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistry.SiCommunicationInterpreterRegistryObject;

@CellType(
    objectClass = SiCommunicationInterpreterRegistryObject.class,
    type = SiCommunicationInterpreterRegistry.CELL_TYPE,
    uuid = SiCommunicationInterpreterRegistry.CELL_UUID
)
public interface SiCommunicationInterpreterRegistry extends
    SiCell<SiCommunicationInterpreterRegistry, Void> {

  String CELL_TYPE = "communication-interpreter-registry";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  SiCommunicationInterpreter getInterpreter(SiCommunicationMessage message);

  class SiCommunicationInterpreterRegistryObject extends
      SiCellObject<SiCommunicationInterpreterRegistry, Void> implements
      SiCommunicationInterpreterRegistry {

    @Override
    public SiCommunicationInterpreter getInterpreter(SiCommunicationMessage message) {

      // TODO: Auf Performance bauen: Ggf. mit noch zu implementierender Local-Cache Logik arbeiten

      for (SiCommunicationInterpreterRegistryEntry entry : getSubCells(
          SiCommunicationInterpreterRegistryEntry.class)) {

        if (entry.getCommand().getCellValue().equals(message.getCommand().getCellValue())) {
          return entry.getCellValue();
        }
      }

      return null;
    }
  }
}
