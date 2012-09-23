package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectNeedInputPresenter;
import com.owb.playhelp.client.helper.MouseClick;

public class ProjectNeedInputView extends PopupPanel implements ProjectNeedInputPresenter.Display {

	private static ProjectNeedInputViewUiBinder uiBinder = GWT
			.create(ProjectNeedInputViewUiBinder.class);

	interface ProjectNeedInputViewUiBinder extends
			UiBinder<Widget, ProjectNeedInputView> {
	}

	public ProjectNeedInputView() {
		super(true);
		add(uiBinder.createAndBindUi(this));
		center();
		show();
	}

	public ProjectNeedInputView(MouseClick click) {
		super(true);
		add(uiBinder.createAndBindUi(this));
		if (click != null) {
			setPopupPosition(click.getX1(),click.getY1());
		} else {
			center();
		}
		show();
	}

	@UiField ListBox resourceList;
	@UiField
	Anchor addLink,cancelLink;
	@UiField 
	VerticalPanel contribPanel;


	  @Override 
	  public HasClickHandlers getAddLink(){
		  return addLink;
	  }
	  @Override 
	  public HasClickHandlers getCancelLink(){
		  return cancelLink;
	  }
	  @Override
	  public VerticalPanel getContribPanel() {
	    return contribPanel;
	  }
	  @Override
	  public ListBox getResourceList(){
		  return resourceList;
	  }

	  @Override
	  public void hide() {
	    super.hide();
	  }
	  
	  @Override
	  public void update(){
		  hide();
		  show();
	  }
	

}
