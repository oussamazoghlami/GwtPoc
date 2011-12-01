package com.sfeir.tutorials.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.sfeir.tutorials.client.uibinder.MapPage;
import com.sfeir.tutorials.client.uibinder.Page;
import com.sfeir.tutorials.client.uibinder.UserPointPage;

/**
 * This is our application controller, it contain all the navigation logic and
 * the history management
 * 
 * @author Oussama Zoghlami
 * 
 */
public class AppController implements ValueChangeHandler<String>, Page {

	private final HandlerManager eventBus;
	private HasWidgets container;

	/**
	 * 
	 * @param eventBus
	 */
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bind();
	}

	/**
	 * 
	 */
	private void bind() {
		History.addValueChangeHandler(this);
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("maps");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Page page = null;

			if (token.equals("maps")) {
				page = new MapPage(eventBus);
			} else if (token.equals("grid")) {
				page = new UserPointPage(eventBus);
			}

			if (page != null) {
				page.go(container);
			}

		}
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	public void setContainer(HasWidgets container) {
		this.container = container;
	}

	public HasWidgets getContainer() {
		return container;
	}

}
