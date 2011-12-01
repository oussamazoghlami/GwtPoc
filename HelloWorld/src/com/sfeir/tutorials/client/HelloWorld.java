package com.sfeir.tutorials.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Our Application entry point
 * 
 * @author Oussama Zoghlami
 * 
 */
public class HelloWorld implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(null);
		AppController appController = new AppController(eventBus);
		appController.go(RootPanel.get("helloWorld"));
		// RootPanel.get("helloWorld").add(new UserPointPage(eventBus));
	}

}
