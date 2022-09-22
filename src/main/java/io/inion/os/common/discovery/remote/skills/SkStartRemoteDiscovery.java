package io.inion.os.common.discovery.remote.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryNetworkScanner;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryExchanger;
import io.inion.os.common.discovery.remote.skills.SkStartRemoteDiscovery.SkStartRemoteDiscoveryObject;
import io.inion.os.common.exception.CellRunException;

@CellType(
    objectClass = SkStartRemoteDiscoveryObject.class,
    type = SkStartRemoteDiscovery.CELL_TYPE,
    uuid = SkStartRemoteDiscovery.CELL_UUID
)
public interface SkStartRemoteDiscovery extends SiCell<SkStartRemoteDiscovery, Void> {

  String CELL_TYPE = "remote-discovery-manager-start-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  class SkStartRemoteDiscoveryObject extends SiCellObject<SkStartRemoteDiscovery, Void> implements
      SkStartRemoteDiscovery {

    @Override
    public SkStartRemoteDiscovery run() throws CellRunException {

      SiRemoteDiscoveryNetworkScanner remoteDiscoveryNetworkScanner = getController().getSubCell(
          SiRemoteDiscoveryNetworkScanner.class);

      SiRemoteDiscoveryExchanger remoteDiscoveryOrganismExchanger =
          getController().getSubCell(SiRemoteDiscoveryExchanger.class);

      if (log().isInfoEnabled()) {
        log().info("Scan network for other discoveries...");
      }

      remoteDiscoveryNetworkScanner.run();

      int size = getController().getSubCells(SiRemoteDiscovery.class).size();

      if (log().isInfoEnabled()) {
        log().info(
            "Found " + size + " more " + (size == 1 ? "discovery" : "discoveries")
                + " in the network");
      }

      if (size > 0) {
        if (log().isInfoEnabled()) {
          log().info("Exchange discovery data and build cell connections...");
        }
        remoteDiscoveryOrganismExchanger.run();
      }

      return getSelf();
    }
  }
}
