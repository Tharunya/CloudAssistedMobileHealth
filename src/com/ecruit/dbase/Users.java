package com.ecruit.dbase;

public class Users {
	
	//private variables
	private String user_id;
	private String name;
	private String user_name;
	private String password;
	private String user_cat;
	
	//Empty Constructor
	public Users(){
		this.user_id = "null";
		this.name = "null";
		this.user_name = "null";
		this.password = "null";
		this.user_cat = "null";
	}

	/**
	 * @param user_id
	 */
	public Users(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @param name
	 * @param user_name
	 * @param password
	 * @param user_cat
	 */
	public Users(String name, String user_name, String password, String user_cat) {
		this.name = name;
		this.user_name = user_name;
		this.password = password;
		this.user_cat = user_cat;
	}

	/**
	 * @param user_id
	 * @param name
	 * @param user_name
	 * @param password
	 * @param user_cat
	 */
	public Users(String user_id, String name, String user_name, String password, String user_cat) {
		this.user_id = user_id;
		this.name = name;
		this.user_name = user_name;
		this.password = password;
		this.user_cat = user_cat;
	}

	//getting and setting user_id
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	//getting and setting name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//getting and setting user_name
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	//getting and setting password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//getting and setting user_category
	public String getUser_cat() {
		return user_cat;
	}

	public void setUser_cat(String user_cat) {
		this.user_cat = user_cat;
	}

}
