package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event indicating that a new User was authenticated
 * 
 * @author Oussama Zoghlami
 * 
 */
public class NewUserAuthenticatedEvent extends GwtEvent<NewUserAuthenticatedEventHandler> {

	public static Type<NewUserAuthenticatedEventHandler> TYPE = new Type<NewUserAuthenticatedEventHandler>();

	@Override
	public Type<NewUserAuthenticatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NewUserAuthenticatedEventHandler handler) {
		handler.onNewUserAuthenticated(this);
	}

}
