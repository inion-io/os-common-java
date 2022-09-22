package io.inion.os.common.communication.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistry;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistryEntry;
import io.inion.os.common.communication.skills.SkRegisterCommunicationInterpreter.SkRegisterCommunicationInterpreterObject;
import io.inion.os.common.exception.CellRunException;

@CellType(
    objectClass = SkRegisterCommunicationInterpreterObject.class,
    type = SkRegisterCommunicationInterpreter.CELL_TYPE,
    uuid = SkRegisterCommunicationInterpreter.CELL_UUID
)
public interface SkRegisterCommunicationInterpreter extends
    SiCell<SkRegisterCommunicationInterpreter, SiCommunicationInterpreter> {

  String CELL_TYPE = "communication-manager-register-interpreter-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkRegisterCommunicationInterpreterObject extends
      SiCellObject<SkRegisterCommunicationInterpreter, SiCommunicationInterpreter> implements
      SkRegisterCommunicationInterpreter {

    @Override
    public SkRegisterCommunicationInterpreter run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      // TODO: Check nach duplicated Commands und Exception werfen

      SiCommunicationInterpreterRegistry registry = getController().getSubCell(
          SiCommunicationInterpreterRegistry.class);

      SiCommunicationInterpreterRegistryEntry entry = registry.createCell(
          SiCommunicationInterpreterRegistryEntry.class);
      entry.setCommand(getCellValue().getCommand());
      entry.setCellValue(getCellValue());

      return getSelf();
    }
  }
}
