package com.sfeir.tutorials.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

public interface AuthenticationServiceAsync {

	void authenticate(String login, String password, AsyncCallback<User> callback);

	void addUser(User user, AsyncCallback<Void> callback);

	void updateUserPoints(String userLogin, List<UserPoint> userPoints, AsyncCallback<Void> callback);

	void getUsers(AsyncCallback<List<User>> callback);

	void updateUser(User user, AsyncCallback<Void> callback);

	void getUserPoints(AsyncCallback<Map<String, Integer>> callback);

}
