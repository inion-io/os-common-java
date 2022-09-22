package io.inion.os.common.communication.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreterRegistry;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SkProcessResponse.SkProcessResponseObject.class,
    type = SkProcessResponse.CELL_TYPE,
    uuid = SkProcessResponse.CELL_UUID
)
public interface SkProcessResponse extends SiCell<SkProcessResponse, SiCommunicationMessage> {

  String CELL_TYPE = "communication-manager-process-response-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkProcessResponseObject extends
      SiCellObject<SkProcessResponse, SiCommunicationMessage> implements
      SkProcessResponse {

    @Override
    public SkProcessResponse run() throws CellRunException {
      checkRunValuesForNull(getCellValue());

      SiCommunicationInterpreterRegistry registry = getController().getSubCell(
          SiCommunicationInterpreterRegistry.class);

      SiCommunicationInterpreter interpreter = registry.getInterpreter(getCellValue());
      SiCommunicationMessage response;

      if (interpreter != null) {
        response = interpreter.createTransientInstance()
            .processResponse(getCellValue());

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
            "no interpreter found for message: " + getCellValue().toString()));
      }

      setCellValue(response);

      return getSelf();
    }
  }
}
