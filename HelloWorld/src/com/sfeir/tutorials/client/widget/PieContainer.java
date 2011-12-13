package com.sfeir.tutorials.client.widget;

import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;

/**
 * Pie Chart allowing to present statistics associated to the user points
 * 
 * @author Oussama Zoghlami
 * @date 13/12/2011
 */
public class PieContainer extends Composite {

	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);

	private PieChart pieChart;
	private VerticalPanel verticalPanel = new VerticalPanel();

	public PieContainer() {
		initWidget(verticalPanel);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				authenticationService.getUserPoints(new AsyncCallback<Map<String, Integer>>() {

					@Override
					public void onSuccess(Map<String, Integer> userPointsMap) {
						// Create a pie chart visualization.
						pieChart = new PieChart(createTable(userPointsMap), createOptions());
						pieChart.addSelectHandler(createSelectHandler(pieChart));
						verticalPanel.add(pieChart);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Could not retrieve the user points map " + caught.toString());
					}
				});

			}
		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
	}

	/**
	 * 
	 * @param chart
	 * @return
	 */
	private SelectHandler createSelectHandler(final PieChart chart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String message = "";

				// May be multiple selections.
				JsArray<Selection> selections = chart.getSelections();

				for (int i = 0; i < selections.length(); i++) {
					// add a new line for each selection
					message += i == 0 ? "" : "\n";

					Selection selection = selections.get(i);

					if (selection.isCell()) {
						// isCell() returns true if a cell has been selected.

						// getRow() returns the row number of the selected cell.
						int row = selection.getRow();
						// getColumn() returns the column number of the selected
						// cell.
						int column = selection.getColumn();
						message += "cell " + row + ":" + column + " selected";
					} else if (selection.isRow()) {
						// isRow() returns true if an entire row has been
						// selected.

						// getRow() returns the row number of the selected row.
						int row = selection.getRow();
						message += "row " + row + " selected";
					} else {
						// unreachable
						message += "Pie chart selections should be either row selections or cell selections.";
						message += "  Other visualizations support column selections as well.";
					}
				}

				// Window.alert(message);
			}
		};
	}

	/**
	 * Initialize the pie options
	 * 
	 * @return
	 */
	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(550);
		options.setHeight(380);
		options.set3D(true);
		// TODO i18n
		options.setTitle("User Points Statistics");
		return options;
	}

	/**
	 * Initialize the pie data
	 * 
	 * @param userPointsMap
	 * 
	 * @return
	 */
	private AbstractDataTable createTable(Map<String, Integer> userPointsMap) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Username");
		data.addColumn(ColumnType.NUMBER, "Number of points");
		data.addRows(userPointsMap.size());
		Set<String> usernames = userPointsMap.keySet();
		int rowIndex = 0;
		for (String username : usernames) {
			data.setValue(rowIndex, 0, username);
			data.setValue(rowIndex, 1, userPointsMap.get(username));
			rowIndex++;
		}
		return data;
	}

}
