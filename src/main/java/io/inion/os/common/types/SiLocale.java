package io.inion.os.common.types;

import io.inion.os.common.SiCell;
import io.inion.os.common.annotation.CellType;
import java.util.Locale;

@CellType(
    objectClass = SiLocale.SiLocaleObject.class,
    type = "locale",
    uuid = "4FAE8365-9642-48FF-9BE1-8AE16CF5D771"
)
public interface SiLocale extends SiCell<SiLocale, Locale> {

  class SiLocaleObject extends SiCellObject<SiLocale, Locale> implements SiLocale {

  }
}
