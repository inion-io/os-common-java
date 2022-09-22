package io.inion.os.common.discovery.remote.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiString;
import java.net.URI;

@CellType(
    objectClass = SkRegisterRemoteDiscovery.SkRegisterRemoteDiscoveryObject.class,
    type = SkRegisterRemoteDiscovery.CELL_TYPE,
    uuid = SkRegisterRemoteDiscovery.CELL_UUID
)
public interface SkRegisterRemoteDiscovery extends
    SiCell<SkRegisterRemoteDiscovery, SiRemoteDiscovery> {

  String CELL_TYPE = "remote-discovery-manager-register-remote-discovery-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkRegisterRemoteDiscovery setCellURI(SiString cellURI);

  class SkRegisterRemoteDiscoveryObject extends
      SiCellObject<SkRegisterRemoteDiscovery, SiRemoteDiscovery> implements
      SkRegisterRemoteDiscovery {

    @Cell
    private SiString cellURI;

    @Override
    public SkRegisterRemoteDiscovery run() throws CellRunException {
      checkRunValues(cellURI);

      // TODO: Check wegen Duplikaten

      SiRemoteDiscovery remoteDiscovery = getController().createCell(SiRemoteDiscovery.class);
      remoteDiscovery.setCellURI(URI.create(cellURI.getCellValue()));

      log().info(cellURI.getCellValue());

      setCellValue(remoteDiscovery);

      return getSelf();
    }

    @Override
    public SkRegisterRemoteDiscovery setCellURI(SiString cellURI) {
      return this.swapSubCell(this.cellURI, cellURI);
    }
  }
}
