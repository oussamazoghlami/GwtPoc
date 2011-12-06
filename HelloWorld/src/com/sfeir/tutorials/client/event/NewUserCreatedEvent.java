package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.sfeir.tutorials.shared.User;

/**
 * Event indicating that a new user was registred and created
 * 
 * @author Oussama Zoghlami
 * 
 */
public class NewUserCreatedEvent extends GwtEvent<NewUserCreatedEventHandler> {

	public static Type<NewUserCreatedEventHandler> TYPE = new Type<NewUserCreatedEventHandler>();

	private final User createdUser;

	public NewUserCreatedEvent(User createdUser) {
		this.createdUser = createdUser;
	}

	@Override
	public Type<NewUserCreatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NewUserCreatedEventHandler handler) {
		handler.onNewUserCreated(this);
	}

	public User getCreatedUser() {
		return createdUser;
	}

}
