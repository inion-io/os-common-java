package io.inion.os.common.discovery.remote.communication.payload;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;

@CellType(
    objectClass = SiExecuteCellSkillPayload.SiExecuteCellSkillPayloadObject.class,
    type = SiExecuteCellSkillPayload.CELL_TYPE,
    uuid = SiExecuteCellSkillPayload.CELL_UUID
)
public interface SiExecuteCellSkillPayload extends SiCell<SiExecuteCellSkillPayload, Void> {

  String CELL_TYPE = "execute-cell-skill-communication-payload";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiList<?> getParameters();

  SiString getSkillName();

  SiURI getURI();

  SiExecuteCellSkillPayload setParameters(SiList<?> parameters);

  SiExecuteCellSkillPayload setSkillName(SiString skillName);

  SiExecuteCellSkillPayload setURI(SiURI uri);

  class SiExecuteCellSkillPayloadObject extends
      SiCellObject<SiExecuteCellSkillPayload, Void> implements SiExecuteCellSkillPayload {

    @Cell
    private SiList<?> parameters;
    @Cell
    private SiString skillName;
    @Cell
    private SiURI uri;

    @Override
    public SiList<?> getParameters() {
      return parameters;
    }

    @Override
    public SiString getSkillName() {
      return skillName;
    }

    @Override
    public SiURI getURI() {
      return uri;
    }

    @Override
    public SiExecuteCellSkillPayload setParameters(SiList<?> parameters) {
      return swapSubCell(this.parameters, parameters);
    }

    @Override
    public SiExecuteCellSkillPayload setSkillName(SiString skillName) {
      return swapSubCell(this.skillName, skillName);
    }

    @Override
    public SiExecuteCellSkillPayload setURI(SiURI uri) {
      return swapSubCell(this.uri, uri);
    }
  }
}
