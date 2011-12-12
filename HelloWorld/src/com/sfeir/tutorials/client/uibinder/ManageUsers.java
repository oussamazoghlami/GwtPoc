package com.sfeir.tutorials.client.uibinder;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.client.service.AuthenticationServiceAsync;
import com.sfeir.tutorials.client.widget.UserCell;
import com.sfeir.tutorials.client.widget.UserPanel;
import com.sfeir.tutorials.shared.User;

/**
 * Widget allowing to display all the user list and give the possibility to
 * update user informations
 * 
 * @author Oussama Zoghlami
 * @date 08/12/2011 TODO install the event bus to update the user cell when a
 *       new user is subscribed
 * 
 */
public class ManageUsers extends Composite {

	private static ManageUsersUiBinder uiBinder = GWT.create(ManageUsersUiBinder.class);

	private final AuthenticationServiceAsync authenticationService = GWT.create(AuthenticationService.class);

	interface ManageUsersUiBinder extends UiBinder<Widget, ManageUsers> {
	}

	@UiField
	UserPanel userPanel;

	@UiField
	UserInfo userInfo;

	private CellList<User> userCellList;

	public ManageUsers() {
		initWidget(uiBinder.createAndBindUi(this));
		initUserCellList();
		userPanel.setDisplay(userCellList);
	}

	/**
	 * Method allowing to initialize the user cell list
	 */
	private void initUserCellList() {
		UserCell userCell = new UserCell();
		userCellList = new CellList<User>(userCell);
		userCellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		userCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		initializeCellData();
		addCellClickHandler();
	}

	/**
	 * Method allowing to make an RPC call to get the subscribed user list and
	 * put the on the user cell list
	 */
	private void initializeCellData() {
		authenticationService.getUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onSuccess(List<User> users) {
				userCellList.setRowData(users);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Problem when trying to get the user list ! " + caught.toString());
			}
		});
	}

	/**
	 * Method allowing to add a click handler on the user Cell List component
	 */
	private void addCellClickHandler() {
		final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();
		userCellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				userInfo.updateUserInfo(selectionModel.getSelectedObject());
			}
		});

	}

}
