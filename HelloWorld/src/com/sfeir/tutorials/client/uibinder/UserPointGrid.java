package com.sfeir.tutorials.client.uibinder;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEvent;
import com.sfeir.tutorials.client.event.NewUserAuthenticatedEventHandler;
import com.sfeir.tutorials.client.event.UserDisconnectedEvent;
import com.sfeir.tutorials.client.event.UserDisconnectedEventHandler;
import com.sfeir.tutorials.client.util.Session;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * This is UI Binder controller associated to the Datagrid widget that will list
 * and display the point list of the authenticated user
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserPointGrid extends Composite {

	interface UserPointGridUiBinder extends UiBinder<Widget, UserPointGrid> {
	}

	private HandlerManager eventBus;
	private ListDataProvider<UserPoint> dataProvider = new ListDataProvider<UserPoint>(new ArrayList<UserPoint>());

	@UiField(provided = true)
	DataGrid<UserPoint> dataGrid;

	@UiField(provided = true)
	SimplePager pager;

	public UserPointGrid() {
		dataGrid = new DataGrid<UserPoint>();
		dataGrid.setWidth("800px");
		dataGrid.setHeight("200px");

		// Set the message to display when the table is empty.
		dataGrid.setEmptyTableWidget(new Label("Aucun points"));

		// Attach a column sort handler
		ListHandler<UserPoint> sortHandler = new ListHandler<UserPoint>(dataProvider.getList());
		dataGrid.addColumnSortHandler(sortHandler);

		// Initialize the datagrid pager
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(dataGrid);
		pager.setWidth("200px");
		pager.setPageSize(5);

		// Initialize the columns.
		initTableColumns(sortHandler);

		// Initialize the datagrid Data
		dataProvider.addDataDisplay(dataGrid);

		if (Session.isAuthenticatedUser) {
			displayAuthenticatedUserPoints();
		}

		// Initialize the widget
		UserPointGridUiBinder uiBinder = GWT.create(UserPointGridUiBinder.class);
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Method allowing to initialize the datagrid columns
	 * 
	 * @param sortHandler
	 */
	private void initTableColumns(ListHandler<UserPoint> sortHandler) {
		// Latitude Column.
		Column<UserPoint, String> latitudeColumn = new Column<UserPoint, String>(new EditTextCell()) {
			@Override
			public String getValue(UserPoint userPoint) {
				return userPoint.getLatitude() + "";
			}
		};
		latitudeColumn.setSortable(true);
		sortHandler.setComparator(latitudeColumn, new Comparator<UserPoint>() {

			@Override
			public int compare(UserPoint userPoint0, UserPoint userPoint1) {
				return userPoint0.getLatitude().compareTo(userPoint1.getLatitude());
			}
		});
		dataGrid.addColumn(latitudeColumn, "Latitude");
		dataGrid.setColumnWidth(latitudeColumn, 130, Unit.PX);

		// Longitude Column
		Column<UserPoint, String> longitudeColumn = new Column<UserPoint, String>(new EditTextCell()) {
			@Override
			public String getValue(UserPoint userPoint) {
				return userPoint.getLongitude() + "";
			}
		};
		longitudeColumn.setSortable(true);
		sortHandler.setComparator(longitudeColumn, new Comparator<UserPoint>() {

			@Override
			public int compare(UserPoint userPoint0, UserPoint userPoint1) {
				return userPoint0.getLongitude().compareTo(userPoint1.getLongitude());
			}
		});
		dataGrid.addColumn(longitudeColumn, "Longitude");
		dataGrid.setColumnWidth(longitudeColumn, 130, Unit.PX);
	}

	/**
	 * 
	 */
	private void bindEventBus() {
		// listen to NewUserAuthenticatedEvent
		eventBus.addHandler(NewUserAuthenticatedEvent.TYPE, new NewUserAuthenticatedEventHandler() {

			@Override
			public void onNewUserAuthenticated(NewUserAuthenticatedEvent newUserAuthenticatedEvent) {
				displayAuthenticatedUserPoints();
			}

		});

		// listen to UserDisconnectedEvent
		eventBus.addHandler(UserDisconnectedEvent.TYPE, new UserDisconnectedEventHandler() {

			@Override
			public void onUserDisconnected(UserDisconnectedEvent userDisconnectedEvent) {
				clearPointGrid();
			}

		});
	}

	/**
	 * Method allowing to clear the grid points
	 */
	private void clearPointGrid() {
		if (null != dataGrid) {
			dataProvider.getList().clear();
		}
	}

	/**
	 * Method allowing to display the points associated to the authenticated
	 * user
	 */
	private void displayAuthenticatedUserPoints() {
		User user = Session.authenticatedUser;
		if (null != user) {
			dataProvider.getList().clear();
			dataProvider.getList().addAll(user.getUserPoints());
		}
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bindEventBus();
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

}
