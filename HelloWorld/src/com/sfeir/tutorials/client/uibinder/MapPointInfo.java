package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
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

	private Map container;
	private HandlerManager eventBus;
	private Overlay overlayToDelete;
	private LatLng latLngTodDelete;

	@UiField
	Label latitude;

	@UiField
	Label longitude;

	@UiField
	Anchor deleteButton;
	
	@UiField
	Anchor validateButton;

	public MapPointInfo() {
		initWidget(uiBinder.createAndBindUi(this));
		initDeleteButton();
	}

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

	/**
	 * Method allowing to clear the widget informations
	 */
	public void clearInfo() {
		latitude.setText("0.0");
		longitude.setText("0.0");
	}
	
	@UiHandler("validateButton")
	void onClick(ClickEvent e) {
		container.getMap().validateUserPointUpdate();
	}

	/**
	 * Initialize the delete button click handler;
	 */
	private void initDeleteButton() {
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				container.getMap().getMap().removeOverlay(overlayToDelete);
				container.getMap().deleteUserPoint(latLngTodDelete.getLongitude(), latLngTodDelete.getLatitude());
				clearInfo();
				deleteButton.setVisible(false);
			}
		});

	}

	/**
	 * Method allowing to initialize the eventBus
	 */
	private void bindEventBus() {
		eventBus.addHandler(MapOverlayClickEvent.TYPE, new MapOverlayClickEventHandler() {

			@Override
			public void onMapOverlayClick(final MapOverlayClickEvent mapOverlayClickEvent) {
				updateInfo(mapOverlayClickEvent.getMapClickEvent().getOverlayLatLng());
				overlayToDelete = mapOverlayClickEvent.getMapClickEvent().getOverlay();
				latLngTodDelete = mapOverlayClickEvent.getMapClickEvent().getOverlayLatLng();
				deleteButton.setVisible(true);
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

	public void setContainer(Map container) {
		this.container = container;
	}

	public Map getContainer() {
		return container;
	}
	
	public Anchor getValidateButton() {
		return validateButton;
	}
	
}
