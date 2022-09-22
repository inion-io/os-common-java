package io.inion.os.common.discovery.remote;

import io.inion.os.common.SiCell;
import io.inion.os.common.SiDiscoveryManager;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationManager;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryExchangeInterpreter;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryPingInterpreter;
import io.inion.os.common.discovery.remote.communication.SiErrorInterpreter;
import io.inion.os.common.discovery.remote.communication.SiExecuteCellSkillInterpreter;
import io.inion.os.common.discovery.remote.skills.SkGetRemoteDiscovery;
import io.inion.os.common.discovery.remote.skills.SkProcessRemoteDiscovery;
import io.inion.os.common.discovery.remote.skills.SkRegisterRemoteDiscovery;
import io.inion.os.common.discovery.remote.skills.SkStartRemoteDiscovery;
import io.inion.os.common.exception.CellRunException;
import io.inion.os.common.types.SiList;
import io.inion.os.common.types.SiString;

@CellType(
    objectClass = SiRemoteDiscoveryManager.SiRemoteDiscoveryManagerObject.class,
    type = SiRemoteDiscoveryManager.CELL_TYPE,
    uuid = SiRemoteDiscoveryManager.CELL_UUID
)
public interface SiRemoteDiscoveryManager extends
    SiCell<SiRemoteDiscoveryManager, SiList<SiRemoteDiscovery>> {

  String CELL_TYPE = "remote-discovery-manager";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";

  SiRemoteDiscovery getRemoteDiscovery(SiString discoveryURI);

  void processRemoteDiscovery(SiString discoveryURI, SiRemoteDiscovery remoteDiscovery);

  SiRemoteDiscovery registerRemoteDiscovery(SiString discoveryURI);

  void start();

  class SiRemoteDiscoveryManagerObject extends
      SiCellObject<SiRemoteDiscoveryManager, SiList<SiRemoteDiscovery>> implements
      SiRemoteDiscoveryManager {

    @Cell
    private SiErrorInterpreter discoveryCommunicationErrorInterpreter;
    @Cell
    private SiDiscoveryExchangeInterpreter discoveryOrganismExchangeInterpreter;
    @Cell
    private SiDiscoveryPingInterpreter discoveryPingInterpreter;
    @Cell
    private SiExecuteCellSkillInterpreter executeCellMethodInterpreter;
    @Cell
    private SiRemoteDiscoveryNetworkScanner remoteDiscoveryNetworkScanner;
    @Cell
    private SiRemoteDiscoveryExchanger remoteDiscoveryOrganismExchanger;
    @Cell
    private SkGetRemoteDiscovery skGetRemoteDiscovery;
    @Cell
    private SkProcessRemoteDiscovery skProcessRemoteDiscovery;
    @Cell
    private SkRegisterRemoteDiscovery skRegisterRemoteDiscovery;
    @Cell
    private SkStartRemoteDiscovery skStartRemoteDiscovery;

    @Override
    public void afterCreate() {
      SiCommunicationManager<?> communicationManager = (SiCommunicationManager<?>) rootSkill(
          SiCommunicationManager.CELL_TYPE);

      if (communicationManager == null) {
        log().warn("Communication Manager not found. Remote interaction disabled.");

        return;
      }

      communicationManager.registerInterpreter(discoveryPingInterpreter);
      communicationManager.registerInterpreter(discoveryCommunicationErrorInterpreter);
      communicationManager.registerInterpreter(discoveryOrganismExchangeInterpreter);
      communicationManager.registerInterpreter(executeCellMethodInterpreter);
    }

    @Override
    public SiRemoteDiscovery getRemoteDiscovery(SiString discoveryURI) {
      try {
        return skGetRemoteDiscovery
            .createTransientInstance()
            .setCellURI(discoveryURI)
            .returnAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }

      return null;
    }

    @Override
    public void processRemoteDiscovery(SiString discoveryURI, SiRemoteDiscovery remoteDiscovery) {
      try {
        skProcessRemoteDiscovery
            .createTransientInstance()
            .setRemoteDiscovery(remoteDiscovery)
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }

    @Override
    public SiRemoteDiscovery registerRemoteDiscovery(SiString discoveryURI) {
      try {
        return skRegisterRemoteDiscovery
            .createTransientInstance()
            .setCellURI(discoveryURI)
            .returnAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }

      return null;
    }

    @Override
    public void start() {
      try {
        skStartRemoteDiscovery
            .createTransientInstance()
            .runAndDestroy();
      } catch (CellRunException cellRunException) {
        log().error("TODO: Exception Handling", cellRunException);
      }
    }
  }
}
