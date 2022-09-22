package io.inion.os.common.viewer;

import io.inion.os.common.SiCell;

public interface SiViewer<C extends SiCell<?, ?>> extends SiCell<C, String> {

  String CELL_TYPE = "viewer";

  String CELL_UUID = "4FAE8365-9642-48FF-9BE1-8AE16CF5D773";
}
