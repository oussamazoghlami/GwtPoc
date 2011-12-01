package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Event handler handling the event indicating that the authenticated user is
 * disconnected
 * 
 * @author Oussama Zoghlami
 * 
 */
public interface UserDisconnectedEventHandler extends EventHandler {

	void onUserDisconnected(UserDisconnectedEvent userDisconnectedEvent);

}
