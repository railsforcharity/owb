package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.CheckBox;

import java.util.List;
import java.util.ArrayList;

import com.owb.playhelp.client.presenter.ListReportPresenter;
import com.owb.playhelp.client.resources.Resources;;

public class ListReportView extends Composite implements ListReportPresenter.Display {

	private static NgoAdminListReportViewUiBinder uiBinder = GWT
			.create(NgoAdminListReportViewUiBinder.class);

	interface NgoAdminListReportViewUiBinder extends
			UiBinder<Widget, ListReportView> {
	}

	public ListReportView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label loadingLabel,titleReportField;
    @UiField
    FlexTable listTable;

    @UiField
    Hyperlink addNew;

    private List<String> deselectedNames = new ArrayList<String>();
    
	public ListReportView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	 
	@Override
	  public Label getTitleReportField(){
		  return titleReportField;
	  }
	@Override
	  public Label getLoadingLabel(){
		  return loadingLabel;
	  }
	
	@Override
	  public void setData(List<String> data) {
		
	}
	
	private void displayElments(List<String> elements) {

	    int i = 0;
	    getSelectedRows(); 
	    listTable.removeAllRows();

	    if (elements == null || elements.size() == 0) {
	      loadingLabel.setText("Empty.");
	      return;
	    }
	    
	    loadingLabel.setVisible(false);
	    List<String> seen = new ArrayList<String>();

	    for (final String readElement : elements) {
	      String name = truncateLongName(readElement);
	      CheckBox checkBoxName = new CheckBox(truncateLongName(name));
	      
	      if(deselectedNames.contains(truncateLongName(name))) { 
	      checkBoxName.setValue(false);
	      seen.add(name);
	      GWT.log("not selected: " + name);
	      } else {
	      checkBoxName.setValue(true);
	      }

	      final Image propertyButton = new Image(Resources.INSTANCE.propertyButton());
	      propertyButton.setStyleName("pointer");

	      listTable.setWidget(i, 0, checkBoxName);
	      listTable.setWidget(i, 1, propertyButton);
	      listTable.getCellFormatter().addStyleName(i, 0,
	          "elementInList");
	      i++;
	    }
	    
	    deselectedNames = seen;

	  }
	
	/**
	   * Shorten long displayName to something smaller if name is too long.
	   * 
	   * @param displayName
	   * @return a possibly truncated displayNames
	   */
	  private String truncateLongName(String displayName) {
	    final int MAX = 18; // truncate string if longer than MAX
	    final String SUFFIX = "...";

	    if (displayName.length() < MAX)
	      return displayName;

	    String shortened = displayName.substring(0, MAX - SUFFIX.length()) + SUFFIX;

	    return shortened;
	  }
	  
	@Override
	  public List<Integer> getSelectedRows() {
	    ArrayList<Integer> selectedRows = new ArrayList<Integer>();
	    ArrayList<String> deselected = new ArrayList<String>();

	    for (int i = 0; i < listTable.getRowCount(); ++i) {
	      CheckBox checkBox = (CheckBox) listTable.getWidget(i, 0);
	      if (checkBox.getValue()) {
	        selectedRows.add(i);
	      } else {
	        deselected.add(checkBox.getHTML());
	        GWT.log(checkBox.getHTML() + " not selected");
	      }
	    }
	    
	    deselectedNames = deselected;
	    
	    return selectedRows;
	  }
		public Widget asWidget(){
			return this;
		}

}
