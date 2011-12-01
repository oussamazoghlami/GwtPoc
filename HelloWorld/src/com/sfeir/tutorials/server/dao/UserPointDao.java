package com.sfeir.tutorials.server.dao;

import com.googlecode.objectify.ObjectifyService;
import com.sfeir.tutorials.shared.UserPoint;

/**
 * User Point Dao Implementation
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserPointDao extends ObjectifyDao<UserPoint> {

	static {
		ObjectifyService.register(UserPoint.class);
	}

	public UserPointDao() {
		super(UserPoint.class);
	}

}
