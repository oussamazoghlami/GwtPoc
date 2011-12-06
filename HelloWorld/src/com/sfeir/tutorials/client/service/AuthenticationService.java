package com.sfeir.tutorials.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.tutorials.shared.User;

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
	 *         fail TODO add throw illegal argument exception
	 */
	public User authenticate(String login, String password);

	/**
	 * Method allowing to add a new user
	 * 
	 * @param user
	 */
	public void addUser(User user);

}
