package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface NewUserCreatedEventHandler extends EventHandler {
	
	void onNewUserCreated(NewUserCreatedEvent newUserCreatedEvent);

}
