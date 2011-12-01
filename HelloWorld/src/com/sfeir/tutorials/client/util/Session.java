package com.sfeir.tutorials.client.util;

import com.sfeir.tutorials.shared.User;

/**
 * This is our custom session class. it will indicates if a user is
 * authenticated or not, if it is then it will contain the authenticated user
 * ...
 * 
 * @author Oussama Zoghlami
 * 
 */
public class Session {

	public static boolean isAuthenticatedUser = false;
	public static User authenticatedUser = null;

}
