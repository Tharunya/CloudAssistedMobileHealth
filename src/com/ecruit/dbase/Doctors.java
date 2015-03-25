package com.ecruit.dbase;

public class Doctors {

	private String doc_id;
	private String doc_name;
	private String doc_branch;
	private String doc_phone;
	private String doc_email;
	private String doc_address;

	/**
	 * 
	 */
	public Doctors() {
		// Setting empty values
		this.doc_id = "";
		this.doc_name = "";
		this.doc_branch = "";
		this.doc_phone = "";
		this.doc_email = "";
		this.doc_address = "";		
	}

	/**
	 * @param doc_name
	 * @param doc_branch
	 * @param doc_phone
	 * @param doc_email
	 * @param doc_address
	 */
	public Doctors(String doc_name, String doc_branch, String doc_phone,
			String doc_email, String doc_address) {
		this.doc_name = doc_name;
		this.doc_branch = doc_branch;
		this.doc_phone = doc_phone;
		this.doc_email = doc_email;
		this.doc_address = doc_address;
	}

	public String getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getDoc_branch() {
		return doc_branch;
	}

	public void setDoc_branch(String doc_branch) {
		this.doc_branch = doc_branch;
	}

	public String getDoc_phone() {
		return doc_phone;
	}

	public void setDoc_phone(String doc_phone) {
		this.doc_phone = doc_phone;
	}

	public String getDoc_email() {
		return doc_email;
	}

	public void setDoc_email(String doc_email) {
		this.doc_email = doc_email;
	}

	public String getDoc_address() {
		return doc_address;
	}

	public void setDoc_address(String doc_address) {
		this.doc_address = doc_address;
	}

}
