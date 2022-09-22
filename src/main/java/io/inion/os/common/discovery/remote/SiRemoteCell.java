package io.inion.os.common.discovery.remote;

import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;
import io.inion.os.common.types.SiUUID;
import java.net.URI;
import java.util.UUID;

@CellType(
    objectClass = SiRemoteCell.SiRemoteCellObject.class,
    type = SiRemoteCell.CELL_TYPE,
    uuid = SiRemoteCell.CELL_UUID
)
public interface SiRemoteCell extends SiCell<SiRemoteCell, JsonObject> {

  String CELL_TYPE = "remote-cell";
  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiRemoteCell setRemoteCellType(SiString remoteCellType);

  SiRemoteCell setRemoteCellURI(SiURI remoteCellURI);

  SiRemoteCell setRemoteCellUUID(SiUUID remoteCellUUID);

  SiString getRemoteCellType();

  SiURI getRemoteCellURI();

  SiUUID getRemoteCellUUID();

  class SiRemoteCellObject extends SiCellObject<SiRemoteCell, JsonObject> implements SiRemoteCell {

    @Cell
    private SiString remoteCellType;
    @Cell
    private SiURI remoteCellURI;
    @Cell
    private SiUUID remoteCellUUID;

    @Override
    public SiRemoteCell setRemoteCellType(SiString remoteCellType) {
      return swapSubCell(this.remoteCellType, remoteCellType);
    }

    @Override
    public SiRemoteCell setRemoteCellURI(SiURI remoteCellURI) {
      return swapSubCell(this.remoteCellURI, remoteCellURI);
    }

    @Override
    public SiRemoteCell setRemoteCellUUID(SiUUID remoteCellUUID) {
      return swapSubCell(this.remoteCellUUID, remoteCellUUID);
    }

    @Override
    public SiUUID getRemoteCellUUID() {
      return this.remoteCellUUID;
    }

    @Override
    public SiString getRemoteCellType() {
      return this.remoteCellType;
    }

    @Override
    public SiURI getRemoteCellURI() {
      return this.remoteCellURI;
    }

    @Override
    public SiRemoteCell restore(JsonObject jsonObject) {
      if (!jsonObject.has(SiCell.PROPERTY_CELL_TYPE)) {
        return super.restore(jsonObject);
      }

      String cellType = jsonObject.get(SiCell.PROPERTY_CELL_TYPE).getAsString();

      if (cellType.equals(SiRemoteCell.CELL_TYPE)) {
        return super.restore(jsonObject);
      }

      UUID cellUUID = UUID.fromString(jsonObject.get(SiCell.PROPERTY_CELL_UUID).getAsString());
      URI cellURI = URI.create(jsonObject.get(SiCell.PROPERTY_CELL_URI).getAsString());

      setRemoteCellType(createTransientCell(SiString.class, cellType));
      setRemoteCellUUID(createTransientCell(SiUUID.class, cellUUID));
      setRemoteCellURI(createTransientCell(SiURI.class, cellURI));

      return this.getSelf();
    }
  }
}
