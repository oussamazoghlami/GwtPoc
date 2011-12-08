package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event indicating that the authenticated user's points was modified
 * 
 * @author Oussama Zoghlami
 * @date 07/12/2011
 * 
 */
public class UserPointsModifiedEvent extends GwtEvent<UserPointModifiedEventHandler> {

	public static Type<UserPointModifiedEventHandler> TYPE = new Type<UserPointModifiedEventHandler>();

	@Override
	public Type<UserPointModifiedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserPointModifiedEventHandler handler) {
		handler.onUserPointsModified(this);
	}

}
