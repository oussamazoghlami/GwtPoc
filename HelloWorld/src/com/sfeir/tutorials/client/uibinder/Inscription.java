package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
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
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEventHandler;
import com.sfeir.tutorials.client.event.NewUserCreatedEvent;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;
import com.sfeir.tutorials.shared.User;

import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.validators.strings.EmailValidator;
import eu.maydu.gwt.validation.client.validators.strings.StringEqualsValidator;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

/**
 * This is the Inscription widget, allowing to subscribe a new user on the
 * system TODO thinking about adding the i18n
 * 
 * @author Oussama Zoghlami
 * 
 */
public class Inscription extends Composite {

	private static InscriptionUiBinder uiBinder = GWT.create(InscriptionUiBinder.class);

	/**
	 * Class constants
	 */
	private static final String LOGIN_VALIDATOR = "loginValidator";
	private static final String NAME_VALIDATOR = "nameValidator";
	private static final String SURNAME_VALIDATOR = "surnameValidator";
	private static final String EMAIL_VALIDATOR = "emailValidator";
	private static final String PASSWORD_VALIDATOR = "passwordValidator";
	private static final String EQUAL_PASSWORDS_VALIDATOR = "equalPasswordValidator";
	private static final String ERROR_CLASSNAME = "clearfix error";
	private static final String WELL_CLASSNAME = "clearfix";

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
	PasswordTextBox repeatPasswordTextBox;

	@UiField
	SpanElement repeatPasswordLabel;

	@UiField
	DivElement repeatPasswordDiv;

	@UiField
	Anchor inscriptionButton;

	private ValidationProcessor validator;
	private HandlerManager eventBus;

	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);

	interface InscriptionUiBinder extends UiBinder<Widget, Inscription> {
	}

	public Inscription() {
		initWidget(uiBinder.createAndBindUi(this));
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		birthdayBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		initValidator();
	}

	/**
	 * 
	 * @param eventBus
	 */
	public Inscription(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		birthdayBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		initValidator();
		bindEventBus();
	}

	/**
	 * Method allowing to initialize the form validator
	 */
	private void initValidator() {
		validator = new DefaultValidationProcessor();

		// Add the mail validation
		validator.addValidators(EMAIL_VALIDATOR, new EmailValidator(emailTextBox));
		initFieldValidation(emailTextBox, EMAIL_VALIDATOR, emailDiv, emailLabel, "Email invalide !");
		// TODO check if the specified login is available (make an RPC call)

		// Add the login validation
		validator.addValidators(LOGIN_VALIDATOR, new StringLengthValidator(loginTextBox, 1, 25));
		initFieldValidation(loginTextBox, LOGIN_VALIDATOR, loginDiv, loginLabel, "Login invalide !");

		// Add the name validation
		validator.addValidators(NAME_VALIDATOR, new StringLengthValidator(nameTextBox, 1, 25));
		initFieldValidation(nameTextBox, NAME_VALIDATOR, nameDiv, nameLabel, "Nom invalide !");

		// Add the surname validation
		validator.addValidators(SURNAME_VALIDATOR, new StringLengthValidator(surnameTextBox, 1, 25));
		initFieldValidation(surnameTextBox, SURNAME_VALIDATOR, surnameDiv, surnameLabel, "Prenom invalide !");

		// Add the password validation
		validator.addValidators(PASSWORD_VALIDATOR, new StringLengthValidator(passwordTextBox, 8, 30));
		initFieldValidation(passwordTextBox, PASSWORD_VALIDATOR, passwordDiv, passwordLabel, "Mot de passe invalide !");

		// Add the password equal validation
		validator.addValidators(EQUAL_PASSWORDS_VALIDATOR, new StringEqualsValidator(passwordTextBox,
				repeatPasswordTextBox));
		initFieldValidation(repeatPasswordTextBox, EQUAL_PASSWORDS_VALIDATOR, repeatPasswordDiv, repeatPasswordLabel,
				"Les mots de passes ne sont pas identiques !");

	}

	/**
	 * Initialize the event bus
	 */
	private void bindEventBus() {
		// listen to NewUserAuthenticatedEvent
		eventBus.addHandler(NewUserAuthenticatedEvent.TYPE, new NewUserAuthenticatedEventHandler() {

			@Override
			public void onNewUserAuthenticated(NewUserAuthenticatedEvent newUserAuthenticatedEvent) {
				clearFields();
			}
		});
	}

	/**
	 * Method allowing to initialize the validator associated to a given form
	 * field
	 * 
	 * @param textBox
	 * @param ValidatorName
	 * @param container
	 * @param errorLabel
	 * @param errorMsg
	 */
	private void initFieldValidation(TextBox textBox, final String ValidatorName, final DivElement container,
			final SpanElement errorLabel, final String errorMsg) {
		textBox.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (!validator.validate(ValidatorName)) {
					container.setClassName(ERROR_CLASSNAME);
					errorLabel.setInnerHTML(errorMsg);
				} else {
					container.setClassName(WELL_CLASSNAME);
					errorLabel.setInnerHTML("");
				}
			}
		});
	}

	@UiHandler("inscriptionButton")
	void onClick(ClickEvent e) {
		if (validator.validate()) {
			// Make an RPC call to register the new user
			final User newUser = new User(loginTextBox.getValue(), passwordTextBox.getValue(), nameTextBox.getValue(),
					surnameTextBox.getValue(), emailTextBox.getValue(), birthdayBox.getValue());
			authenticationService.addUser(newUser, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					clearFields();
					eventBus.fireEvent(new NewUserCreatedEvent(newUser));
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("problem when adding the user");
				}
			});
		}
		// TODO: thinking about validating all the fields
	}

	/**
	 * Method allowing to clear the inscription Form's different fields
	 */
	private void clearFields() {
		loginTextBox.setValue("");
		loginDiv.setClassName(WELL_CLASSNAME);
		loginLabel.setInnerHTML("");
		nameTextBox.setValue("");
		nameDiv.setClassName(WELL_CLASSNAME);
		nameLabel.setInnerHTML("");
		surnameTextBox.setValue("");
		surnameDiv.setClassName(WELL_CLASSNAME);
		surnameLabel.setInnerHTML("");
		emailTextBox.setValue("");
		emailDiv.setClassName(WELL_CLASSNAME);
		emailLabel.setInnerHTML("");
		passwordTextBox.setValue("");
		passwordDiv.setClassName(WELL_CLASSNAME);
		passwordLabel.setInnerHTML("");
		repeatPasswordTextBox.setValue("");
		repeatPasswordDiv.setClassName(WELL_CLASSNAME);
		repeatPasswordLabel.setInnerHTML("");
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}
}
