/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderPanel extends Composite {
  @UiField
  VerticalPanel userPanel;

  private static HeaderPanelUiBinder uiBinder = GWT
      .create(HeaderPanelUiBinder.class);

  interface HeaderPanelUiBinder extends UiBinder<Widget, HeaderPanel> {
  }

  public HeaderPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public VerticalPanel getuserPanel() {
    return userPanel;
  }

}
