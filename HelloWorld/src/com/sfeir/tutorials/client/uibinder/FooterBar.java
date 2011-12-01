package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the footer that will be included in our pages.
 * 
 * @author Oussama Zoghlami
 * @date 24/11/2011
 * 
 */
public class FooterBar extends Composite {

	private static FooterBarUiBinder uiBinder = GWT.create(FooterBarUiBinder.class);

	interface FooterBarUiBinder extends UiBinder<Widget, FooterBar> {
	}

	public FooterBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FooterBar(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
