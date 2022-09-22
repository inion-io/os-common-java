package io.inion.os.common.discovery.banner;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.Cell;
import io.inion.os.common.annotation.CellType;
import io.inion.os.common.discovery.banner.SiConsoleBanner.SiConsoleBannerImpl;
import io.inion.os.common.types.SiString;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@CellType(
    objectClass = SiConsoleBannerImpl.class,
    type = SiConsoleBanner.CELL_TYPE,
    uuid = SiConsoleBanner.CELL_UUID
)
public interface SiConsoleBanner extends SiCell<SiConsoleBanner, String> {

  String CELL_TYPE = "console-banner";

  String CELL_UUID = "D93364D5-C2AC-4DC4-B4CA-4BA22FDD59B9";

  void print();

  class SiConsoleBannerImpl extends SiCellObject<SiConsoleBanner, String> implements
      SiConsoleBanner {

    @Cell(prop = "discovery.banner.size", value = "large")
    private SiString size;

    @Override
    public void print() {
      try {
        try (InputStream in = this.getClass().getClassLoader()
            .getResourceAsStream("META-INF/resources/BANNER")) {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] b = new byte[65536];
          while (true) {
            int r = in.read(b);
            if (r == -1) {
              break;
            }
            out.write(b, 0, r);
          }

          if (size.getCellValue().equalsIgnoreCase("large")) {
            System.out.println(out);
            System.out.println("Name: " + root().getDiscoveryName().getCellValue());
            System.out.println("Version: ");
            System.out.println("Network: " + root().getCellURI());
            System.out.println();
          }
          if (size.getCellValue().equalsIgnoreCase("small")) {
            // TODO: Kleinen Banner bauen !!
            System.out.println("______________________________________");
            System.out.println("| Inion OS");
            System.out.println("Name: " + root().getDiscoveryName().getCellValue());
            System.out.println("Version: ");
            System.out.println("Network: " + root().getCellURI());
            System.out.println();
          }
        }
      } catch (Exception e) {
        log().error(e.getMessage(), e);
      }
    }
  }
}
