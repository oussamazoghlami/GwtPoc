package com.sfeir.tutorials.server.dao;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * User Dao implementation
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserDao extends ObjectifyDao<User> {

	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(UserPoint.class);
	}

	public UserDao() {
		super(User.class);
	}

	/**
	 * Method allowing to load and check the user associated to the passed
	 * credentials
	 * 
	 * @param login
	 * @return
	 */
	public User loadFullAuthenticatedUser(String login, String password) {
		List<User> users = listByProperty("login", login);
		if (users.size() == 1) {
			User user = users.get(0);
			// Check the user password
			if (user.getPassword().equals(password)) {
				// Load the user points
				user.setUserPoints(ofy().query(UserPoint.class).filter("user", user).list());
				return user;
			}
		}
		return null;
	}

}
