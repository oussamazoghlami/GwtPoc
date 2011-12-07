package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;

/**
 * Event indicating that a map point (overlay) was clicked
 * 
 * @author Oussama Zoghlami
 * 
 */
public class MapOverlayClickEvent extends GwtEvent<MapOverlayClickEventHandler> {

	public static Type<MapOverlayClickEventHandler> TYPE = new Type<MapOverlayClickEventHandler>();

	private final MapClickEvent mapClickEvent;

	public MapOverlayClickEvent(MapClickEvent mapClickEvent) {
		this.mapClickEvent = mapClickEvent;
	}

	@Override
	public Type<MapOverlayClickEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MapOverlayClickEventHandler handler) {
		handler.onMapOverlayClick(this);
	}

	public MapClickEvent getMapClickEvent() {
		return mapClickEvent;
	}

}
