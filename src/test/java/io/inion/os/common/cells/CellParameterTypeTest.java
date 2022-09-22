package io.inion.os.common.cells;

import io.inion.os.common.types.SiInteger;
import io.inion.os.common.types.SiInteger.SiIntegerObject;
import io.inion.os.common.types.SiString;
import io.inion.os.common.types.SiString.SiStringObject;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class CellParameterTypeTest {

  @Test
  void createNewCell() throws Exception {
    SiString cell1 = new SiStringObject();
    cell1.setCellValue("HALLO");

    SiInteger cell2 = new SiIntegerObject();
    cell2.setCellValue(2);
    cell2.setController(cell1);

    UUID uuid = UUID.randomUUID();
    cell2.setCellUUID(uuid);

    Assertions.assertThat(cell1.getCellValue()).isInstanceOf(String.class);
    Assertions.assertThat(cell2.getCellValue()).isInstanceOf(Integer.class);
    Assertions.assertThat(cell2.getController().getCellValue()).isInstanceOf(String.class);
    Assertions.assertThat(cell2.getCellUUID()).isEqualTo(uuid);
  }
}
