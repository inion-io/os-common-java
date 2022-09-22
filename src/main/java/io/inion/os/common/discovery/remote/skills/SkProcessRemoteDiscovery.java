package io.inion.os.common.discovery.remote.skills;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.injection.SiCellInjector;
import io.inion.os.common.discovery.remote.SiRemoteDiscovery;
import io.inion.os.common.discovery.remote.SiRemoteDiscoveryManager;
import io.inion.os.common.discovery.remote.skills.SkProcessRemoteDiscovery.SkProcessRemoteDiscoveryObject;
import io.inion.os.common.exception.CellRunException;

@CellType(
    objectClass = SkProcessRemoteDiscoveryObject.class,
    type = SkProcessRemoteDiscovery.CELL_TYPE,
    uuid = SkProcessRemoteDiscovery.CELL_UUID
)
public interface SkProcessRemoteDiscovery extends SiCell<SkProcessRemoteDiscovery, Void> {

  String CELL_TYPE = "remote-discovery-manager-process-remote-discovery-skill";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SkProcessRemoteDiscovery setRemoteDiscovery(SiRemoteDiscovery remoteDiscovery);

  class SkProcessRemoteDiscoveryObject extends
      SiCellObject<SkProcessRemoteDiscovery, Void> implements
      SkProcessRemoteDiscovery {

    @Cell
    private SiRemoteDiscovery remoteDiscovery;

    @Override
    public SkProcessRemoteDiscovery run() throws CellRunException {
      checkRunValuesForNull(remoteDiscovery);

      SiRemoteDiscoveryManager remoteDiscoveryManager = (SiRemoteDiscoveryManager) getController();
      SiRemoteDiscovery curRemoteDiscovery = remoteDiscoveryManager.getRemoteDiscovery(
          remoteDiscovery.getRemoteDiscoveryURI());
      SiCellInjector cellInjector = rootSkill(SiCellInjector.class);

      // TODO: Nur eine Prüfung auf die exchangeTime, damit keine doppelten Exchanges nach Reboot
      // einer anderen Discovery gemacht werden. Hier müssen konkrete Abgleichmechanismen
      // implementiert werden. Jeder Exchange, egal wann und wie oft, muss einen sauberen
      // Aktualisierungsprozess durchziehen

      if (curRemoteDiscovery == null || (curRemoteDiscovery.getExchangeTime() == null
          || curRemoteDiscovery.getExchangeTime().getCellValue() == null)) {

        if (curRemoteDiscovery == null) {
          curRemoteDiscovery = remoteDiscoveryManager.registerRemoteDiscovery(
              remoteDiscovery.getRemoteDiscoveryURI());
        }

        curRemoteDiscovery.restore(remoteDiscovery.toJsonObject());
        cellInjector.checkAndInjectRemoteCells(remoteDiscovery);
      }

      return getSelf();
    }

    @Override
    public SkProcessRemoteDiscovery setRemoteDiscovery(SiRemoteDiscovery remoteDiscovery) {
      return this.swapSubCell(this.remoteDiscovery, remoteDiscovery);
    }
  }
}
