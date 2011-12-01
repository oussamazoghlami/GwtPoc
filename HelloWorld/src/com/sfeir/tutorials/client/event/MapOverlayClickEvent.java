package com.sfeir.tutorials.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.maps.client.geom.LatLng;

/**
 * Event indicating that a map point (overlay) was clicked
 * 
 * @author Oussama Zoghlami
 * 
 */
public class MapOverlayClickEvent extends GwtEvent<MapOverlayClickEventHandler> {

	public static Type<MapOverlayClickEventHandler> TYPE = new Type<MapOverlayClickEventHandler>();

	private final LatLng latLng;

	public MapOverlayClickEvent(LatLng latLng) {
		this.latLng = latLng;
	}

	@Override
	public Type<MapOverlayClickEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MapOverlayClickEventHandler handler) {
		handler.onMapOverlayClick(this);
	}

	public LatLng getLatLng() {
		return latLng;
	}

}
