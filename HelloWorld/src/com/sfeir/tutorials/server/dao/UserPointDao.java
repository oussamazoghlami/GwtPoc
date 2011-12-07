package com.sfeir.tutorials.server.dao;

import com.sfeir.tutorials.shared.UserPoint;

/**
 * User Point Dao Implementation
 * 
 * @author Oussama Zoghlami
 * 
 */
public class UserPointDao extends ObjectifyDao<UserPoint> {

	public UserPointDao() {
		super(UserPoint.class);
	}

}
