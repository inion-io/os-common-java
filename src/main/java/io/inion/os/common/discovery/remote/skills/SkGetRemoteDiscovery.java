package io.inion.os.common.discovery.remote.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SkGetRemoteDiscovery.SkGetRemoteDiscoveryObject.class,
    type = SkGetRemoteDiscovery.CELL_TYPE,
    uuid = SkGetRemoteDiscovery.CELL_UUID
)
public interface SkGetRemoteDiscovery extends SiCell<SkGetRemoteDiscovery, SiRemoteDiscovery> {

  String CELL_TYPE = "remote-discovery-manager-get-remote-discovery-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkGetRemoteDiscovery setCellURI(SiString cellURI);

  class SkGetRemoteDiscoveryObject extends
      SiCellObject<SkGetRemoteDiscovery, SiRemoteDiscovery> implements SkGetRemoteDiscovery {

    @Cell
    private SiString cellURI;

    @Override
    public SkGetRemoteDiscovery run() throws CellRunException {
      checkRunValues(cellURI);

      for (SiRemoteDiscovery remoteDiscovery : getController().getSubCells(
          SiRemoteDiscovery.class)) {
        if (remoteDiscovery.getCellURI().toString().equals(cellURI.getCellValue())) {
          setCellValue(remoteDiscovery);
          break;
        }
      }

      return getSelf();
    }

    @Override
    public SkGetRemoteDiscovery setCellURI(SiString cellURI) {
      return this.swapSubCell(this.cellURI, cellURI);
    }
  }
}
