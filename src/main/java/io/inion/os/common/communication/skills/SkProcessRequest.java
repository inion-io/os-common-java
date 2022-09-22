package io.inion.os.common.communication.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistry;
import io.inion.os.common.communication.skills.SkProcessRequest.SkProcessRequestObject;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SkProcessRequestObject.class,
    type = SkProcessRequest.CELL_TYPE,
    uuid = SkProcessRequest.CELL_UUID
)
public interface SkProcessRequest extends
    SiCell<SkProcessRequest, SiCommunicationMessage> {

  String CELL_TYPE = "communication-manager-process-request-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessRequestObject extends
      SiCellObject<SkProcessRequest, SiCommunicationMessage> implements
      SkProcessRequest {

    @Override
    public SkProcessRequest run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      // TODO: Check nach duplicated Commands und Exception werfen

      SiCommunicationInterpreterRegistry registry = getController().getSubCell(
          SiCommunicationInterpreterRegistry.class);

      SiCommunicationInterpreter interpreter = registry.getInterpreter(getCellValue());
      SiCommunicationMessage response;

      if (interpreter != null) {
        response = interpreter.createTransientInstance().processRequest(getCellValue());

        if (response == null) {
          response = createTransientCell(SiCommunicationMessage.class);
          response.setCommand(
              createTransientCell(SiString.class, getCellValue().getCommand().getCellValue()));
          response.setPayload(createTransientCell(SiString.class, "void"));
        }
      } else {
        response = createTransientCell(SiCommunicationMessage.class);
        response.setCommand(createTransientCell(SiString.class, "error"));
        response.setPayload(createTransientCell(SiString.class,
            "no interpreter found for command: " + getCellValue().getCommand().getCellValue()));
      }

      setCellValue(response);

      return getSelf();
    }
  }
}
