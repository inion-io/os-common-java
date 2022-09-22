package io.inion.os.common.discovery.remote.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.communication.interpreter.SiCommunicationInterpreter;
import io.inion.os.common.discovery.remote.communication.SiExecuteCellSkillInterpreter.SiExecuteCellSkillInterpreterObject;
import io.inion.os.common.discovery.remote.communication.payload.SiErrorPayload;
import io.inion.os.common.discovery.remote.communication.payload.SiExecuteCellSkillPayload;
import io.inion.os.common.types.SiJsonObject;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import org.apache.commons.lang3.ArrayUtils;

@CellType(
    objectClass = SiExecuteCellSkillInterpreterObject.class,
    type = SiExecuteCellSkillInterpreter.CELL_TYPE,
    uuid = SiExecuteCellSkillInterpreter.CELL_UUID
)
public interface SiExecuteCellSkillInterpreter extends SiCommunicationInterpreter {

  String COMMAND = "execute-cell-skill";
  String CELL_TYPE = "execute-cell-skill-communication-interpreter";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SiExecuteCellSkillInterpreterObject extends SiCommunicationInterpreterObject implements
      SiExecuteCellSkillInterpreter {

    @Cell(value = COMMAND)
    private SiString command;

    @Override
    public SiString getCommand() {
      return command;
    }

    @Override
    public SiCommunicationMessage processRequest(SiCommunicationMessage message) {
      /* TODO: Error Handling
         Fehler catchen, damit saubere Response erfolgen kann */

      SiCommunicationMessage response = createTransientCell(SiCommunicationMessage.class);

      if (!message.getPayload().getCellType().equals(SiExecuteCellSkillPayload.CELL_TYPE)) {
        response.setCommand(createTransientCell(SiString.class, "error"));
        response.setPayload(createTransientCell(SiErrorPayload.class, "invalid message payload"));

        return response;
      }

      SiExecuteCellSkillPayload payload = (SiExecuteCellSkillPayload) message.getPayload();
      SiURI cellURI = payload.getURI();
      SiString skillName = payload.getSkillName();
      SiList<?> parameters = payload.getParameters();

      if (cellURI == null || !cellURI.hasCellValue() || skillName == null
          || !skillName.hasCellValue()) {
        response.setCommand(createTransientCell(SiString.class, "error"));
        response.setPayload(createTransientCell(SiErrorPayload.class, "invalid message payload"));

        return response;
      }

      SiCell<?, ?> cell = root().findSubCell(cellURI.getCellValue());

      if (cell != null) {
        try {
          Class<?>[] parameterTypes = null;
          Object[] args = null;

          /*if (payloadJsonObject.has("parameters")) {
            JsonArray parametersJsonArray = payloadJsonObject.getAsJsonArray("parameters");

            for (int a = 0; a < parametersJsonArray.size(); a++) {
              JsonObject parameterCellJsonObject = parametersJsonArray.get(a).getAsJsonObject();

              String parameterCellTypeName = parameterCellJsonObject.get(
                  SiCell.PROPERTY_CELL_TYPE).getAsString();
              String parameterCellTypeUUID = parameterCellJsonObject.get(
                  SiCell.PROPERTY_CELL_UUID).getAsString();

              SiCell<?, ?> parameterCell = createTransientCell(parameterCellTypeName);

              parameterCell.restore(
                  createTransientCell(SiJsonObject.class, parameterCellJsonObject));

              args = ArrayUtils.add(args, parameterCell);
              parameterTypes = ArrayUtils.add(parameterTypes, parameterCell.getCellClass());
            }
          }*/

          Method method;

          if (parameterTypes == null) {
            method = cell.getClass().getDeclaredMethod(skillName.getCellValue());
          } else {
            method = cell.getClass().getDeclaredMethod(skillName.getCellValue(), parameterTypes);
          }

          if (!method.getReturnType().equals(void.class)) {
            SiCell<?, ?> responseCell;

            if (method.getParameterTypes().length == 0) {
              responseCell = (SiCell<?, ?>) method.invoke(cell);
            } else {
              responseCell = (SiCell<?, ?>) method.invoke(cell, args);
            }

            response.setCommand(createTransientCell(SiString.class, COMMAND));
            response.setPayload(responseCell);

            return response;

          } else {
            if (method.getParameterTypes().length == 0) {
              method.invoke(cell);
            } else {
              method.invoke(cell, args);
            }
          }
        } catch (NoSuchMethodException noSuchMethodException) {
          log().error("{}", noSuchMethodException);
        } catch (InvocationTargetException invocationTargetException) {
          log().error("{}", invocationTargetException);
        } catch (IllegalAccessException illegalAccessException) {
          log().error("{}", illegalAccessException);
        }
      } else {
        response.setCommand(createTransientCell(SiString.class, "error"));
        response.setPayload(createTransientCell(SiErrorPayload.class,
            "No cell found for uri " + cellURI.getCellValue()));
      }

      return response;
    }

    @Override
    public SiCommunicationMessage processResponse(SiCommunicationMessage message) {
      if (log().isInfoEnabled()) {
        log().info("Receive response: " + message.toString());
      }

      return message;
    }
  }
}
