package com.sfeir.tutorials.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.tutorials.client.service.AuthenticationService;
import com.sfeir.tutorials.server.dao.UserDao;
import com.sfeir.tutorials.shared.User;

/**
 * Server side implementation for the authentication service
 * 
 * @author Oussama Zoghlami
 * 
 */
public class AuthenticationServiceImpl extends RemoteServiceServlet implements AuthenticationService {

	private static final long serialVersionUID = 6704462038767121874L;

	// private static List<User> users = new ArrayList<User>();

	/**
	 * Fill the user list //
	 */
	// static {
	// // User 1
	// List<UserPoint> userPoints = new ArrayList<UserPoint>();
	// // latLngs.add(LatLng.newInstance(48.856614, 2.3522219));
	// userPoints.add(new UserPoint(45.71743073334637, 4.779052734375));
	// userPoints.add(new UserPoint(48.71743073334637, 4.779052734375));
	// userPoints.add(new UserPoint(48.11743073334637, 4.999052734375));
	// userPoints.add(new UserPoint(48.11743073334637, 0.999052734375));
	// userPoints.add(new UserPoint(47.81743073334637, 1.999052734375));
	// userPoints.add(new UserPoint(47.41743073334637, 2.999052734375));
	// userPoints.add(new UserPoint(45.11743073334637, 3.999052734375));
	// userPoints.add(new UserPoint(46.11743073334637, 5.999052734375));
	// User user = new User("oussama", "zoghlami", "Oussama", "Zoghlami");
	//
	// // User 2
	// List<UserPoint> userPoints2 = new ArrayList<UserPoint>();
	// userPoints2.add(new UserPoint(48.11743073334637, 4.307373046875));
	// userPoints2.add(new UserPoint(45.71743073334637, 4.779052734375));
	// User user2 = new User("test", "test", "Name", "Surname", userPoints2);
	//
	// users.add(user);
	// users.add(user2);
	//
	// }

	@Override
	public User authenticate(String login, String password) {
		return new UserDao().loadFullAuthenticatedUser(login, password);
	}

}
