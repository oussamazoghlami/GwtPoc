package com.sfeir.tutorials.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
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
		User user = getByProperty("login", login);
		if (null != user) {
			// Check the user password
			if (user.getPassword().equals(password)) {
				// Load the user points
				user.setUserPoints(ofy().query(UserPoint.class).filter("user", user).list());
				return user;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param login
	 * @param userPoints
	 */
	public void updateUserPoints(String login, List<UserPoint> userPoints) {
		UserPointDao userPointDao = new UserPointDao();
		User user = getByProperty("login", login);
		if (null != user) {
			// delete all the old user points
			userPointDao.delete(ofy().query(UserPoint.class).filter("user", user).list());
			
			// insert the updated user points
			for (UserPoint userPoint : userPoints) {
				userPoint.setUser(new Key<User>(User.class, login));
				userPointDao.add(userPoint);
			}
		}

	}
	
	public Map<String, Integer> getUserPoints() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<User> users = getAll();
		for (User user: users) {
			int userPoint = ofy().query(UserPoint.class).filter("user", user).list().size();
			result.put(user.getFullName(), userPoint);
		}
		return result;
	}

}
