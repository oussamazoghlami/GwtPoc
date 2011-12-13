package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEventHandler;
import com.sfeir.tutorials.client.event.UserDisconnectedEvent;
import com.sfeir.tutorials.client.event.UserDisconnectedEventHandler;

/**
 * This is the template of our application pages. It contain a header, footer
 * and a body containing a left part and a right part
 * 
 * @author Oussama Zoghlami
 * @date 24/11/2011
 * 
 */
public class PrincipalePage extends Composite implements ValueChangeHandler<String> {

	private static PrincipalePageUiBinder uiBinder = GWT.create(PrincipalePageUiBinder.class);

	interface PrincipalePageUiBinder extends UiBinder<Widget, PrincipalePage> {
	}

	private HandlerManager eventBus;
	private UserPointGrid userPointGrid;
	private Map map;
	private Login login;
	private Welcome welcome;
	private ManageUsers manageUsers;
	private Inscription inscription;

	@UiField
	SimplePanel leftPart;

	@UiField
	SimplePanel rightPart;

	public PrincipalePage() {
	}

	/**
	 * 
	 * @param eventBus
	 */
	public PrincipalePage(HandlerManager eventBus) {
		this.eventBus = eventBus;
		initWidget(uiBinder.createAndBindUi(this));
		initializePageComponents();
		initializeEventBusListener();
		leftPart.add(login);
		History.addValueChangeHandler(this);
		initHistoryToken();
	}

	/**
	 * This method allow to initialize the different page components
	 */
	private void initializePageComponents() {
		login = new Login(eventBus);
		userPointGrid = new UserPointGrid(eventBus);
		manageUsers = new ManageUsers();
		map = new Map(eventBus);
		welcome = new Welcome();
		inscription = new Inscription(eventBus);
	}

	/**
	 * Method allowing to initialize the history token
	 */
	private void initHistoryToken() {
		if ("".equals(History.getToken())) {
			History.newItem("!welcome");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {
			if (token.equals("!maps")) {
				rightPart.clear();
				rightPart.add(map);
			}

			else if (token.equals("!grid")) {
				rightPart.clear();
				rightPart.add(userPointGrid);
			}

			else if (token.equals("!welcome")) {
				rightPart.clear();
				rightPart.add(welcome);
			}

			else if (token.equals("!inscription")) {
				rightPart.clear();
				rightPart.add(inscription);
			}

			else if (token.equals("!manageUsers")) {
				rightPart.clear();
				rightPart.add(manageUsers);
			}
		}
	}

	/**
	 * Method allowing to initialize the events to listen by the principal
	 * Widget
	 */
	private void initializeEventBusListener() {
		// listen to NewUserAuthenticatedEvent
		eventBus.addHandler(NewUserAuthenticatedEvent.TYPE, new NewUserAuthenticatedEventHandler() {

			@Override
			public void onNewUserAuthenticated(NewUserAuthenticatedEvent newUserAuthenticatedEvent) {
				History.newItem("!maps");
			}

		});

		// listen to UserDisconnectedEvent
		eventBus.addHandler(UserDisconnectedEvent.TYPE, new UserDisconnectedEventHandler() {

			@Override
			public void onUserDisconnected(UserDisconnectedEvent userDisconnectedEvent) {
				History.newItem("!welcome");
			}

		});
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

}
