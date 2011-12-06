package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserCreatedEvent;
import com.sfeir.tutorials.client.event.NewUserCreatedEventHandler;
import com.sfeir.tutorials.client.event.UserDisconnectedEvent;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;
import com.sfeir.tutorials.client.util.Session;
import com.sfeir.tutorials.shared.User;

/**
 * This is the UIBinder controller associated to our login component.
 * 
 * @author Oussama Zoghlami
 * @date 24/11/2011
 * 
 */
public class Login extends Composite {

	private static HomePageMenuUiBinder uiBinder = GWT.create(HomePageMenuUiBinder.class);

	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);

	interface HomePageMenuUiBinder extends UiBinder<Widget, Login> {
	}

	private HandlerManager eventBus;

	@UiField
	InlineLabel userLabel;

	@UiField
	TextBox nameTextBox;

	@UiField
	PasswordTextBox passwordTextBox;

	@UiField
	HTMLPanel connected;

	@UiField
	HTMLPanel notConnected;

	@UiField
	Anchor loginButton;

	@UiField
	Label userName;

	@UiField
	Anchor logoutButton;

	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		if (Session.isAuthenticatedUser) {
			showConnectedPart();
		}
	}

	/**
	 * 
	 * @param eventBus
	 */
	public Login(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		if (Session.isAuthenticatedUser) {
			showConnectedPart();
		}
		this.eventBus = eventBus;
		bindEventBus();
	}
	
	public void setUserName(String userName) {
		userLabel.setText(userName);
	}

	@UiHandler("logoutButton")
	void onClick2(ClickEvent e) {
		Session.isAuthenticatedUser = false;
		Session.authenticatedUser = null;
		connected.setVisible(false);
		notConnected.setVisible(true);
		eventBus.fireEvent(new UserDisconnectedEvent());
	}

	@UiHandler("loginButton")
	void onClick(ClickEvent e) {
		/**
		 * Check the passed credentials through the userAuthenticationService
		 */
		String login = nameTextBox.getValue();
		String password = passwordTextBox.getValue();
		authenticationService.authenticate(login, password, new AsyncCallback<User>() {

			@Override
			public void onSuccess(User user) {
				authenticateUser(user);

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Problem when invoking the RPC Authentication Service !!!" + caught.toString());
			}
		});

	}

	/**
	 * 
	 */
	private void bindEventBus() {
		// listen to NewUserCreatedEvent
		eventBus.addHandler(NewUserCreatedEvent.TYPE, new NewUserCreatedEventHandler() {

			@Override
			public void onNewUserCreated(NewUserCreatedEvent newUserCreatedEvent) {
				authenticateUser(newUserCreatedEvent.getCreatedUser());
			}
		});
	}
	
	/**
	 * 
	 * @param user
	 */
	private void authenticateUser(User user) {
		if (null != user) {
			Session.isAuthenticatedUser = true;
			Session.authenticatedUser = user;
			showConnectedPart();
			eventBus.fireEvent(new NewUserAuthenticatedEvent());
		} else {
			Window.alert("Verifiez vos coordonnées !");
		}
	}

	/**
	 * 
	 */
	private void showConnectedPart() {
		if (Session.isAuthenticatedUser) {
			User user = Session.authenticatedUser;
			userName.setText(user.getName() + " " + user.getSurname());
			notConnected.setVisible(false);
			connected.setVisible(true);
		}
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindEventBus();
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

}