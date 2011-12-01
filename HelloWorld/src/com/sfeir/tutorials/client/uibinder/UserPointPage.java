package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the user point page allowing to display the user points on a datagrid
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserPointPage extends Composite implements Page {

	private static UserPointPageUiBinder uiBinder = GWT.create(UserPointPageUiBinder.class);

	interface UserPointPageUiBinder extends UiBinder<Widget, UserPointPage> {
	}

	private HandlerManager eventBus;

	@UiField
	Login login;

	@UiField
	UserPointGrid userPointGrid;

	public UserPointPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * 
	 * @param eventBus
	 */
	public UserPointPage(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
		login.setEventBus(eventBus);
		userPointGrid.setEventBus(eventBus);
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this);
	}

}
