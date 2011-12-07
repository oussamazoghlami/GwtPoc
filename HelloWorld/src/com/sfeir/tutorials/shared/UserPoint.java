package com.sfeir.tutorials.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

/**
 * User Map Point model
 * 
 * @author Oussama Zoghlami
 * 
 */
@Entity
public class UserPoint implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private Double latitude;
	private Double longitude;
	private Key<User> user;

	public UserPoint() {
	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public UserPoint(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @param user
	 */
	public UserPoint(Double latitude, Double longitude, Key<User> user) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.user = user;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUser(Key<User> user) {
		this.user = user;
	}

	public Key<User> getUser() {
		return user;
	}

}
