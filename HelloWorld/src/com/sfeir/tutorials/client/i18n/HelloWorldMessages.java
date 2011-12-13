package com.sfeir.tutorials.client.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * Parameterized messages interface
 * 
 * @author Oussama Zoghlami
 * @date 13/12/2011
 * 
 */
public interface HelloWorldMessages extends Messages {

	@DefaultMessage("The field ''{0}'' is not valid")
	String invalidSymbol(String symbol);
}
