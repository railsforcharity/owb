package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickEvent;

import java.util.List;

import com.owb.playhelp.client.helper.MouseClick;
import com.owb.playhelp.client.presenter.project.ProjectAddPresenter;

public class ProjectAddView extends Composite implements
		ProjectAddPresenter.Display {

	private static ProjectAddViewUiBinder uiBinder = GWT
			.create(ProjectAddViewUiBinder.class);

	interface ProjectAddViewUiBinder extends
			UiBinder<Widget, ProjectAddView> {
	}
	

	  @UiField
	  TextBox nameField;
	  @UiField
	  TextBox descField;
	  @UiField
	  TextBox ngoField, chapterField, orphanageField;
	  @UiField
	  Button cancelButton;
	  @UiField
	  Button saveButton;
	  @UiField
	  Button addNeed;
	  @UiField
	  FlexTable needTable;
	  @UiField
	  Label loadingLabel;

	public ProjectAddView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}

	  @Override
	  public HasClickHandlers getCancelButton() {
	    return cancelButton;
	  }
	  @Override
	  public HasClickHandlers getSaveButton() {
	    return saveButton;
	  }
	  @Override
	  public HasClickHandlers getAddNeedButton() {
	    return addNeed;
	  }
	  @Override
	  public HasValue<String> getNameField() {
	    return nameField;
	  }
	  @Override
	  public HasValue<String> getChapterField() {
	    return chapterField;
	  }
	  @Override
	  public HasValue<String> getNgoField() {
	    return ngoField;
	  }
	  @Override
	  public HasValue<String> getOrphanageField() {
	    return orphanageField;
	  }
	  @Override
	  public HasValue<String> getDescriptionField() {
	    return descField;
	  }
	  @Override
	  public HasClickHandlers getNeedTable(){
		  return needTable;
	  }
	  
	  @Override public void setData(List<String[]> needList){
		  displayNeed(needList);
	  }
	  
	  private void displayNeed(List<String[]> needList){
		  int i = 0;
		  
		  if (needList == null || needList.size() == 0){
			  loadingLabel.setText("No Needs associated to this project");
			  return;
		  }
		  
		  int numValTables = 0;
		  loadingLabel.setVisible(false);
		  for (final String[] need: needList){
			  needTable.setWidget(i,0,new Button("Remove"));
			  
			  numValTables = 0;
			  for (String valTable: need){
				  numValTables = numValTables + 1;
				  needTable.setText(i,numValTables,need[numValTables-1]);				  
			  }
			  i++;
		  }
	  }
	  
	  @Override public String getDeleteNeed(ClickEvent event){
		  String deletedNeed = null;
		  HTMLTable.Cell cell = needTable.getCellForEvent(event);
		  if (cell != null){
			  int index = cell.getCellIndex();
			  if (index == 0) {
				  deletedNeed = needTable.getText(cell.getCellIndex(), 1);
				  needTable.removeRow(cell.getCellIndex());
			  }
		  }
		  return deletedNeed;
	  }

	  @Override
	  public MouseClick getMouseClick(ClickEvent event){
		  MouseClick click = null;
		  int x = this.getAbsoluteLeft() + this.getOffsetWidth()/2;
		  int y = this.getAbsoluteTop() + this.getOffsetHeight()/2;
		  click = new MouseClick(x,y);
		  
		  return click;
	  }

}
