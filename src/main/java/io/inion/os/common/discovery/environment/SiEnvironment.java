package io.inion.os.common.discovery.environment;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiString;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@CellType(
    objectClass = SiEnvironment.SiEnvironmentObject.class,
    type = SiEnvironment.CELL_TYPE,
    uuid = SiEnvironment.CELL_UUID
)
public interface SiEnvironment extends SiCell<SiEnvironment, Void> {

  String CELL_TYPE = "environment";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  SiString getHost();

  SiInteger getPort();

  class SiEnvironmentObject extends SiCellObject<SiEnvironment, Void> implements
      SiEnvironment {


    private SiString host;
    private SiInteger port;

    @Override
    public SiEnvironment create() {
      host = createCell(SiString.class, "host");
      port = createCell(SiInteger.class, "port");

      try {
        //host.setCellValue(InetAddress.getByName(
        //InetAddress.getLocalHost().getHostAddress()).getHostAddress());
        host.setCellValue(InetAddress.getLocalHost().getHostAddress());
        //host.setCellValue("localhost");
      } catch (UnknownHostException unknownHostException) {
        log().error("{}", unknownHostException);
      }

      for (int a = 4000; a <= 4999; a++) {
        try {
          Socket socket = new Socket();
          socket.connect(new InetSocketAddress(host.getCellValue(), a), 1000);
          socket.close();
        } catch (IOException ioException) {
          port.setCellValue(a);
          break;
        }
      }

      setCreated(true);

      return getSelf();
    }

    @Override
    public SiString getHost() {
      return host;
    }

    @Override
    public SiInteger getPort() {
      return port;
    }
  }
}
