package com.sfeir.tutorials.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.sfeir.tutorials.client.event.MapOverlayClickEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEventHandler;
import com.sfeir.tutorials.client.event.UserDisconnectedEvent;
import com.sfeir.tutorials.client.event.UserDisconnectedEventHandler;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;
import com.sfeir.tutorials.client.uibinder.Map;
import com.sfeir.tutorials.client.util.Session;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * This is the widget that will contain a google maps. This widget could be
 * referenced from any uibinder. In our case it is referenced from the Base
 * Template binder. TODO thinking about the modification of a point
 * 
 * @author Oussama Zoghlami
 * 
 */
public class MapContainer extends Composite {

	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);
	
	private Map container;
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
	 * Method allowing to update the user point list when adding a new map
	 * overlay
	 * 
	 * @param longitude
	 * @param latitude
	 * @param userLogin
	 */
	public void addUserPoint(double longitude, double latitude) {
		if (Session.isAuthenticatedUser) {
			UserPoint userPoint = new UserPoint(latitude, longitude);
			authUserPoints.add(userPoint);
		}
	}

	/**
	 * Method allowing to update the user point list when deleting an overlay
	 * from the map
	 * 
	 * @param longitude
	 * @param latitude
	 */
	public void deleteUserPoint(double longitude, double latitude) {
		if (Session.isAuthenticatedUser) {
			for (UserPoint userPoint : authUserPoints) {
				if ((userPoint.getLongitude().doubleValue() == longitude)
						&& (userPoint.getLatitude().doubleValue() == latitude)) {
					authUserPoints.remove(userPoint);
					break;
				}
			}
		}
	}

	/**
	 * This method allow to validate the user point modification. it will make a
	 * call to the server to update the new user points
	 */
	public void validateUserPointUpdate() {
		if (Session.isAuthenticatedUser) {
			String userLogin = Session.authenticatedUser.getLogin();
			authenticationService.updateUserPoints(userLogin, authUserPoints, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					Window.alert("points updated");
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Problem : " + caught.toString());
				}
			});
		}

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
					// TODO possible delete of the associated event
					eventBus.fireEvent(new MapOverlayClickEvent(event));
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
				addUserPoint(event.getLatLng().getLongitude(), event.getLatLng().getLatitude());
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

	public void setContainer(Map container) {
		this.container = container;
	}

	public Map getContainer() {
		return container;
	}

}