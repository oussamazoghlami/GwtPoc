package com.sfeir.tutorials.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.tutorials.shared.User;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * Client Authentication Service Interface
 * 
 * @author Oussama Zoghlami
 * 
 */
@RemoteServiceRelativePath("authenticationService")
public interface AuthenticationService extends RemoteService {

	/**
	 * Method allowing to check a user authentication
	 * 
	 * @param login
	 * @param password
	 * @return User object in case of success, else a null object in case of
	 *         fail
	 */
	public User authenticate(String login, String password);

	/**
	 * Method allowing to add a new user
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * Method allowing to update the user points
	 * 
	 * @param userLogin
	 * @param userPoints
	 */
	public void updateUserPoints(String userLogin, List<UserPoint> userPoints);

}
