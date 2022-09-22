package io.inion.os.common.discovery.remote;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.communication.SiCommunicationManager;
import io.inion.os.common.communication.SiCommunicationMessage;
import io.inion.os.common.discovery.environment.SiEnvironment;
import io.inion.os.common.discovery.remote.communication.SiDiscoveryPingInterpreter;
import io.inion.os.common.discovery.remote.communication.payload.SiPingPayload;
import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiString;
import java.net.InetAddress;
import java.util.Stack;

@CellType(
    objectClass = SiRemoteDiscoveryNetworkScanner.SiRemoteDiscoveryNetworkScannerObject.class,
    type = SiRemoteDiscoveryNetworkScanner.CELL_TYPE,
    uuid = SiRemoteDiscoveryNetworkScanner.CELL_UUID
)
public interface SiRemoteDiscoveryNetworkScanner extends
    SiCell<SiRemoteDiscoveryNetworkScanner, Void> {

  String CELL_TYPE = "remote-discovery-network-scanner";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  class SiRemoteDiscoveryNetworkScannerObject extends
      SiCellObject<SiRemoteDiscoveryNetworkScanner, Void> implements
      SiRemoteDiscoveryNetworkScanner {

    @Override
    public SiRemoteDiscoveryNetworkScanner run() {
      SiCommunicationManager<?> communicationManager = (SiCommunicationManager<?>) rootSkill(
          SiCommunicationManager.CELL_TYPE);

      SiEnvironment environment = rootSkill(SiEnvironment.class);

      String host = environment.getHost().getCellValue();
      String addMask =
          host.split("\\.")[0] + "." + host.split("\\.")[1] + "." + host.split("\\.")[2];

      /*Stack<String> stack = new Stack<>();
      AddressFinderLevel3 finder = new AddressFinderLevel3(addMask, stack, 10, 0, 255);
      finder.start();
      stack.forEach(System.out::println);*/

      Stack<String> stack = new Stack<>();
      stack.push(host);

      for (int a = 0; a <= 255; a++) {
        String traceHost = addMask + "." + a;

        if (traceHost.equals(host)) {
          continue;
        }

        try {
          if (InetAddress.getByName(traceHost).isReachable(10)) {
            stack.push(traceHost);
          }
        } catch (Exception ignored) {}
      }
      for (String pingHost : stack) {
        for (int a = 4000; a <= 4010; a++) {
          if (pingHost.equals(host) && a == environment.getPort().getCellValue()) {
            continue;
          }

          SiString siHost = createTransientCell(SiString.class, pingHost);
          SiInteger siPort = createTransientCell(SiInteger.class, a);
          SiString siCommand = createTransientCell(SiString.class, SiDiscoveryPingInterpreter.COMMAND);
          SiString siPayload = createTransientCell(SiPingPayload.class);
          SiCommunicationMessage request = createTransientCell(SiCommunicationMessage.class);
          request.setCommand(siCommand);
          request.setPayload(siPayload);

          communicationManager.requestResponse(siHost, siPort, request);
        }
      }


      return getSelf();
    }
  }

  class AddressFinderLevel4 extends Thread {

    private String addmask;
    private Stack<String> stack;
    private int start, end;

    public AddressFinderLevel4(String addmask, Stack stack, int start, int end) {
      this.addmask = addmask;
      this.stack = stack;
      this.start = start;
      this.end = end;
    }

    @Override
    public void run() {
      try {
        int timeout = 1000;
        for (int i = start; i <= end; i++) {
          String host = addmask + "." + i;
          System.out.println(host);
          if (InetAddress.getByName(host).isReachable(timeout)) {
            stack.push(host);
          }
        }
      } catch (Exception ex) {

      }
    }
  }

  class AddressFinderLevel3 extends Thread {

    private String addmask;
    private Stack<String> stack;
    private int start, end;
    private int packSize;

    public AddressFinderLevel3(String addmask, Stack stack, int packSize, int start, int end) {
      this.addmask = addmask;
      this.stack = stack;
      this.start = start;
      this.end = end;
      this.packSize = packSize;
    }

    @Override
    public void run() {
      try {
        for (int i = start; i <= end; i++) {
          int j = 1;
          String host = addmask + "." + i;
          while (j <= 255) {
            AddressFinderLevel4 addressFinderLevel4 = new AddressFinderLevel4(host, stack, j,
                j + packSize + 5);
            addressFinderLevel4.start();
            j = j + packSize;
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
