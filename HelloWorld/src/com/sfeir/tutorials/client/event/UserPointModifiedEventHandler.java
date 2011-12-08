package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserPointModifiedEventHandler extends EventHandler {

	void onUserPointsModified(UserPointsModifiedEvent userPointsModifiedEvent);

}
