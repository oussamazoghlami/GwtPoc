package com.sfeir.tutorials.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.server.dao.UserDao;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * Server side implementation for the authentication service
 * 
 * @author Oussama Zoghlami
 * 
 */
public class AuthenticationServiceImpl extends RemoteServiceServlet implements AuthenticationService {

	private static final long serialVersionUID = 6704462038767121874L;

	@Override
	public User authenticate(String login, String password) {
		return new UserDao().loadFullAuthenticatedUser(login, password);
	}

	@Override
	public void addUser(User user) {
		new UserDao().add(user);
	}
	
	public void updateUserPoints(String userLogin, List<UserPoint> userPoints) {
		new UserDao().updateUserPoints(userLogin, userPoints);
	}

}
