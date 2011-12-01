package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.tutorials.client.widget.MapContainer;

/**
 * This is the template of our page. It contain a header, footer and a body
 * containing a Logging part, a map and a map info component
 * 
 * @author Oussama Zoghlami
 * @date 24/11/2011
 * 
 */
public class MapPage extends Composite implements Page {

	private static MapPageUiBinder uiBinder = GWT.create(MapPageUiBinder.class);

	interface MapPageUiBinder extends UiBinder<Widget, MapPage> {
	}
	
	private HandlerManager eventBus;

	@UiField
	Login login;

	@UiField
	MapContainer map;

	@UiField
	MapPointInfo mapPointInfo;

	public MapPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	/**
	 * 
	 * @param eventBus
	 */
	public MapPage(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
		login.setEventBus(eventBus);
		map.setEventBus(eventBus);
		mapPointInfo.setEventBus(eventBus);
	}

	/**
	 * Method allowing to set the point associated to the authenticated user on
	 * the map
	 * 
	 * @param login
	 * @param password
	 */
	public void updateUserMapData(String login, String password) {
		if (null != map) {
			map.updateUserPoints(login, password);
		}
	}

	/**
	 * Method allowing to update the info of the selected point
	 * 
	 * @param latLng
	 */
	public void updateMapPointInfo(LatLng latLng) {
		if (null != map) {
			mapPointInfo.updateInfo(latLng);
		}
	}


	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}


	public HandlerManager getEventBus() {
		return eventBus;
	}


	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this);
	}

}
