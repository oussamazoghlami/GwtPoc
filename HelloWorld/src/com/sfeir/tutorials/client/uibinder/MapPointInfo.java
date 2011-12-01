package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.tutorials.client.event.MapOverlayClickEvent;
import com.sfeir.tutorials.client.event.MapOverlayClickEventHandler;

/**
 * This is the UIBinder Controller associated to Map Information component.
 * 
 * @author Oussama Zoghlami
 * @date 24/11/2011
 * 
 */
public class MapPointInfo extends Composite {

	private static MapPointInfoUiBinder uiBinder = GWT.create(MapPointInfoUiBinder.class);

	interface MapPointInfoUiBinder extends UiBinder<Widget, MapPointInfo> {
	}

	private HandlerManager eventBus;

	@UiField
	Label latitude;

	@UiField
	Label longitude;

	/**
	 * This method will accept a LatLng object and update the MapPointInfo
	 * component with the data of the passed component
	 * 
	 * @param latLng
	 */
	public void updateInfo(LatLng latLng) {
		latitude.setText(latLng.getLatitude() + "");
		longitude.setText(latLng.getLongitude() + "");
	}

	public MapPointInfo() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * Method allowing to initialize the eventBus
	 */
	private void bindEventBus() {
		eventBus.addHandler(MapOverlayClickEvent.TYPE, new MapOverlayClickEventHandler() {
			
			@Override
			public void onMapOverlayClick(MapOverlayClickEvent mapOverlayClickEvent) {
				updateInfo(mapOverlayClickEvent.getLatLng());
			}
		});
		
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindEventBus();
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}
	
}
