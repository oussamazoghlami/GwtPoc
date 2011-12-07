package com.sfeir.tutorials.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * User Model
 * 
 * @author Oussama Zoghlami
 * 
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private Date birthday;
	@Transient
	private List<UserPoint> userPoints = new ArrayList<UserPoint>();

	public User() {
	}

	/**
	 * 
	 * @param login
	 * @param password
	 * @param name
	 * @param surname
	 * @param email
	 * @param birthday
	 */
	public User(String login, String password, String name, String surname, String email, Date birthday) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String prenom) {
		this.surname = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserPoints(List<UserPoint> userPoints) {
		this.userPoints = userPoints;
	}

	public List<UserPoint> getUserPoints() {
		return userPoints;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}

}
