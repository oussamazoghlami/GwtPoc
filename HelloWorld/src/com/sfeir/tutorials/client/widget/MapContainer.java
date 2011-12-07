package com.sfeir.tutorials.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapDoubleClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.sfeir.tutorials.client.event.MapOverlayClickEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEventHandler;
import com.sfeir.tutorials.client.event.UserDisconnectedEvent;
import com.sfeir.tutorials.client.event.UserDisconnectedEventHandler;
import com.sfeir.tutorials.client.util.Session;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * This is the widget that will contain a google maps. This widget could be
 * referenced from any uibinder. In our case it is referenced from the Base
 * Template binder.
 * 
 * @author Oussama Zoghlami
 * 
 */
public class MapContainer extends Composite {

	private MapWidget map;
	private DockLayoutPanel layoutPanel = new DockLayoutPanel(Unit.PX);
	private HandlerManager eventBus;
	private List<UserPoint> authUserPoints = new ArrayList<UserPoint>();

	/**
	 * 
	 */
	public MapContainer() {
		initWidget(layoutPanel);
		Maps.loadMapsApi("", "2", false, new Runnable() {

			@Override
			public void run() {
				initializeMap();
				displayUserMapPoints();
			}

		});

	}

	/**
	 * Method to initialize our map
	 */
	private void initializeMap() {
		// center the map around Paris
		LatLng parisCity = LatLng.newInstance(48.856614, 2.3522219);
		map = new MapWidget(parisCity, 5);
		map.setSize("100%", "100%");
		map.addControl(new LargeMapControl());
		map.setDoubleClickZoom(false);
		map.setCenter(parisCity);
		map.getInfoWindow().open(map.getCenter(), new InfoWindowContent("Paris, capitale de France"));
		addMapClickHandler();
		addMapDoubleClickHandler();
		layoutPanel.addNorth(map, 500);
	}

	/**
	 * Add a map click handler, to detect clicks on the map's overlays
	 */
	private void addMapClickHandler() {
		map.addMapClickHandler(new MapClickHandler() {

			@Override
			public void onClick(MapClickEvent event) {
				Overlay overlay = event.getOverlay();
				if (overlay != null && overlay instanceof Marker) {
					eventBus.fireEvent(new MapOverlayClickEvent(event.getOverlayLatLng()));
				}

			}

		});
	}

	/**
	 * Add a map double click handler, to add a new map overlay (associated to a
	 * new user point)
	 */
	private void addMapDoubleClickHandler() {
		map.addMapDoubleClickHandler(new MapDoubleClickHandler() {

			@Override
			public void onDoubleClick(MapDoubleClickEvent event) {
				map.addOverlay(new Marker(event.getLatLng()));
				UserPoint userPoint = new UserPoint(event.getLatLng().getLatitude(), event.getLatLng().getLongitude());
				authUserPoints.add(userPoint);
			}

		});
	}

	/**
	 * Method allowing to initialize the event bus
	 */
	private void bindEventBus() {
		// listen to NewUserAuthenticatedEvent
		eventBus.addHandler(NewUserAuthenticatedEvent.TYPE, new NewUserAuthenticatedEventHandler() {

			@Override
			public void onNewUserAuthenticated(NewUserAuthenticatedEvent newUserAuthenticatedEvent) {
				displayUserMapPoints();
				authUserPoints.clear();
				authUserPoints.addAll(Session.authenticatedUser.getUserPoints());
			}
		});

		// listen to UserDisconnectedEvent
		eventBus.addHandler(UserDisconnectedEvent.TYPE, new UserDisconnectedEventHandler() {

			@Override
			public void onUserDisconnected(UserDisconnectedEvent userDisconnectedEvent) {
				clearMapMarkers();
			}

		});
	}

	/**
	 * Method allowing to clear all the map markers
	 */
	private void clearMapMarkers() {
		if (null != map) {
			map.clearOverlays();
		}

	}

	/**
	 * Method allowing to display the map points associated to the authenticated
	 * user
	 */
	private void displayUserMapPoints() {
		clearMapMarkers();
		if (Session.isAuthenticatedUser) {
			User authenticatedUser = Session.authenticatedUser;
			if (null != authenticatedUser) {
				for (UserPoint userPoint : authenticatedUser.getUserPoints()) {
					LatLng latLng = LatLng.newInstance(userPoint.getLatitude(), userPoint.getLongitude());
					map.addOverlay(new Marker(latLng));
				}
			}
		}
	}

	public MapWidget getMap() {
		return map;
	}

	public void setMap(MapWidget map) {
		this.map = map;
	}

	public DockLayoutPanel getDock() {
		return layoutPanel;
	}

	public void setDock(DockLayoutPanel dock) {
		this.layoutPanel = dock;
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindEventBus();
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

}