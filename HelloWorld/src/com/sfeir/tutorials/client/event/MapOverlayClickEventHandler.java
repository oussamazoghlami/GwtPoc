package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler of MapOverlayClickEvent
 * 
 * @author Oussama Zoghlami
 * 
 */
public interface MapOverlayClickEventHandler extends EventHandler {
 
	void onMapOverlayClick(MapOverlayClickEvent mapOverlayClickEvent);
	
}
