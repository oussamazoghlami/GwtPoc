package com.sfeir.tutorials.client.service;

import java.util.List;
import java.util.Map;

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

	/**
	 * Method allowing to get all the subscribed users
	 * 
	 * @return
	 */
	public List<User> getUsers();

	/**
	 * Method allowing to update a given user
	 * 
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * Method allowing to return the number of points associated to each user.
	 * (This method is used in the statistic module)
	 * 
	 * @return Map<String, Integer>: Key: FullName of the user, Integer: The
	 *         user points number
	 */
	public Map<String, Integer> getUserPoints();

}
