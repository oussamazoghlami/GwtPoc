package com.sfeir.tutorials.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.tutorials.client.widget.MapContainer;

/**
 * This is the Map Widget containing a map and a map point widgets
 * 
 * @author Oussama Zoghlami
 * 
 */
public class Map extends Composite {

	private static MapUiBinder uiBinder = GWT.create(MapUiBinder.class);

	interface MapUiBinder extends UiBinder<Widget, Map> {
	}

	public Map() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MapContainer map;

	@UiField
	MapPointInfo mapPointInfo;

	/**
	 * 
	 * @param eventBus
	 */
	public Map(HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		map.setEventBus(eventBus);
		map.setContainer(this);
		mapPointInfo.setEventBus(eventBus);
		mapPointInfo.setContainer(this);
	}

	public MapContainer getMap() {
		return map;
	}

	public void setMap(MapContainer map) {
		this.map = map;
	}

	public MapPointInfo getMapPointInfo() {
		return mapPointInfo;
	}

	public void setMapPointInfo(MapPointInfo mapPointInfo) {
		this.mapPointInfo = mapPointInfo;
	}

}
