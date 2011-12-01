package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Event handler handling the event indicating that a new User is authenticated
 * 
 * @author Oussama Zoghlami
 * 
 */
public interface NewUserAuthenticatedEventHandler extends EventHandler {

	void onNewUserAuthenticated(NewUserAuthenticatedEvent newUserAuthenticatedEvent);

}
