package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;
import com.sfeir.tutorials.shared.User;

/**
 * Widget allowing to display and update a given user information
 * 
 * @author Oussama Zoghlami
 * @date 09/12/2011
 * 
 */
public class UserInfo extends Composite {
	
	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);

	@UiField
	TextBox loginTextBox;

	@UiField
	SpanElement loginLabel;

	@UiField
	DivElement loginDiv;

	@UiField
	TextBox nameTextBox;

	@UiField
	SpanElement nameLabel;

	@UiField
	DivElement nameDiv;

	@UiField
	TextBox surnameTextBox;

	@UiField
	SpanElement surnameLabel;

	@UiField
	DivElement surnameDiv;

	@UiField
	TextBox emailTextBox;

	@UiField
	SpanElement emailLabel;

	@UiField
	DivElement emailDiv;

	@UiField
	DateBox birthdayBox;

	@UiField
	SpanElement birthdayLabel;

	@UiField
	DivElement birthdayDiv;

	@UiField
	PasswordTextBox passwordTextBox;

	@UiField
	SpanElement passwordLabel;

	@UiField
	DivElement passwordDiv;

	@UiField
	Anchor inscriptionButton;
	
	private User selectedUser = null;

	private static UserInfoUiBinder uiBinder = GWT.create(UserInfoUiBinder.class);

	interface UserInfoUiBinder extends UiBinder<Widget, UserInfo> {
	}

	public UserInfo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * 
	 * @param user
	 */
	public void updateUserInfo(User user) {
		selectedUser = user;
		loginTextBox.setText(user.getLogin());
		nameTextBox.setText(user.getName());
		emailTextBox.setText(user.getEmail());
		passwordTextBox.setText(user.getPassword());
		birthdayBox.setValue(user.getBirthday());
		surnameTextBox.setText(user.getSurname());
	}
	
	@UiHandler("inscriptionButton")
	void onClick(ClickEvent e) {
		if (null != selectedUser) {
			selectedUser.setName(nameTextBox.getText());
			selectedUser.setSurname(surnameTextBox.getText());
			selectedUser.setBirthday(birthdayBox.getValue());
			selectedUser.setPassword(passwordTextBox.getText());
			selectedUser.setEmail(emailTextBox.getText());
			authenticationService.updateUser(selectedUser, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					Window.alert("User updated");
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("");
				}
			});
		}
	}

}
