package com.sfeir.tutorials.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.tutorials.shared.User;

public interface AuthenticationServiceAsync {

	void authenticate(String login, String password, AsyncCallback<User> callback);

}
