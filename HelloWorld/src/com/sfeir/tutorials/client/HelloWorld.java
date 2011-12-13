package com.sfeir.tutorials.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.sfeir.tutorials.client.uibinder.PrincipalePage;

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
		RootPanel.get("helloWorld").add(new PrincipalePage(eventBus));
	}

}
