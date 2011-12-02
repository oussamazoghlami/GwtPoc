package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is our Welcome Widget
 * 
 * @author Oussama Zoghlami
 * 
 */
public class Welcome extends Composite {

	private static WelcomeUiBinder uiBinder = GWT.create(WelcomeUiBinder.class);

	interface WelcomeUiBinder extends UiBinder<Widget, Welcome> {
	}

	public Welcome() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
