package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event indicating that the authenticated user is disconnected
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserDisconnectedEvent extends GwtEvent<UserDisconnectedEventHandler> {

	public static Type<UserDisconnectedEventHandler> TYPE = new Type<UserDisconnectedEventHandler>();

	@Override
	public Type<UserDisconnectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserDisconnectedEventHandler handler) {
		handler.onUserDisconnected(this);
	}

}
