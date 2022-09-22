package io.inion.os.common.discovery.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.inion.os.common.SiCell;
import io.inion.os.common.SiDiscoveryManager;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiLong;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiURI;
import io.inion.os.common.types.SiUUID;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

@CellType(
    objectClass = SiRemoteDiscovery.SiRemoteDiscoveryObject.class,
    type = SiRemoteDiscovery.CELL_TYPE,
    uuid = SiRemoteDiscovery.CELL_UUID
)
public interface SiRemoteDiscovery extends SiCell<SiRemoteDiscovery, Void> {

  String CELL_TYPE = "remote-discovery";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  String PROPERTY_EXCHANGE_TIME = "exchangeTime";

  String PROPERTY_REMOTE_DISCOVERY_URI = "remoteDiscoveryURI";

  SiLong getExchangeTime();

  SiString getRemoteDiscoveryURI();

  class SiRemoteDiscoveryObject extends SiCellObject<SiRemoteDiscovery, Void> implements
      SiRemoteDiscovery {

    @Cell
    private SiLong exchangeTime;
    @Cell
    private SiString remoteDiscoveryURI;

    @Override
    public SiLong getExchangeTime() {
      return exchangeTime;
    }

    @Override
    public SiString getRemoteDiscoveryURI() {
      return remoteDiscoveryURI;
    }

    @Override
    public SiRemoteDiscovery restore(JsonObject jsonObject) {

      if (!jsonObject.has(SiCell.PROPERTY_CELL_TYPE)) {
        return super.restore(jsonObject);
      }

      String cellType = jsonObject.get(SiCell.PROPERTY_CELL_TYPE).getAsString();
      URI cellURI = URI.create(jsonObject.get(SiCell.PROPERTY_CELL_URI).getAsString());
      UUID cellUUID = UUID.fromString(jsonObject.get(SiCell.PROPERTY_CELL_UUID).getAsString());

      removeSubCells();

      if (cellType.equals(SiDiscoveryManager.CELL_TYPE)) {
        swapSubCell(SiRemoteDiscovery.PROPERTY_EXCHANGE_TIME,
            createTransientCell(SiLong.class, new Date().getTime()));
        swapSubCell(SiRemoteDiscovery.PROPERTY_REMOTE_DISCOVERY_URI,
            createTransientCell(SiString.class, cellURI.toString()));
      } else if (cellType.equals(SiRemoteDiscovery.CELL_TYPE)) {
        if (jsonObject.has(SiRemoteDiscovery.PROPERTY_EXCHANGE_TIME)) {
          swapSubCell(SiRemoteDiscovery.PROPERTY_EXCHANGE_TIME,
              createTransientCell(SiLong.class).restore(
                  jsonObject.get(SiRemoteDiscovery.PROPERTY_EXCHANGE_TIME).getAsJsonObject()));
        }
        if (jsonObject.has(SiRemoteDiscovery.PROPERTY_REMOTE_DISCOVERY_URI)) {
          swapSubCell(SiRemoteDiscovery.PROPERTY_REMOTE_DISCOVERY_URI,
              createTransientCell(SiString.class).restore(
                  jsonObject.get(SiRemoteDiscovery.PROPERTY_REMOTE_DISCOVERY_URI).getAsJsonObject()));
        }
      }

      JsonArray subCellsJsonArray = jsonObject.getAsJsonArray(SiCell.PROPERTY_SUB_CELLS);

      if (subCellsJsonArray != null) {
        for (int a = 0; a < subCellsJsonArray.size(); a++) {
          JsonObject subCellJsonObject = subCellsJsonArray.get(a).getAsJsonObject();

          if (!subCellJsonObject.has(SiCell.PROPERTY_CELL_TYPE)) {
            log().debug("Cell type for sub cell is null or empty. Skipping entry.");
            continue;
          }

          String subCellName = subCellJsonObject.get(SiCell.PROPERTY_CELL_NAME).getAsString();

          if (subCellName.equals(SiRemoteDiscovery.PROPERTY_EXCHANGE_TIME) ||
              subCellName.equals(SiRemoteDiscovery.PROPERTY_REMOTE_DISCOVERY_URI)) {
            continue;
          }

          SiRemoteCell remoteCell = this.createCell(SiRemoteCell.class, subCellName);
          remoteCell.restore(subCellJsonObject);
        }
      }

      return getSelf();
    }
  }
}
