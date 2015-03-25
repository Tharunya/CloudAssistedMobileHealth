package com.ecruit.dbase;

public class Patient{

	// private variables
	private String id;
	private String name;
	private String gender;
	private String age;	
	private String ph_no;
	private String address;
	private String branch;
	private String doc;

	private String tests;
	private String medicines;
	private String injections;
	private String docfee;
	private String testfee;
	private String medfee;
	private String paidfee;
	private String testreport;
	private String teststats;

	/**
	 * NULL
	 * @param id
	 * @param name
	 * @param gender
	 * @param age 
	 * @param ph_no
	 * @param address
	 * @param doc
	 */
	public Patient() {
		this.id = "";
		this.name = "";
		this.gender = "";
		this.age = "";
		this.ph_no = "";
		this.address = "";
		this.doc = "";
	}

	/**
	 * @param name
	 * @param gender
	 * @param age
	 */
	public Patient(String name, String gender, String age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	/**
	 * @param name
	 * @param gender
	 * @param age 
	 * @param address
	 * @param branch
	 * @param doc
	 * @param ph_no
	 */
	public Patient(String name, String gender, String age,
			String address, String branch, String doc, String ph_no) {
		this.name = name;
		this.gender = gender;
		this.age = age;		
		this.address = address;
		this.branch = branch;
		this.doc = doc;
		this.ph_no = ph_no;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param gender
	 * @param age 
	 * @param address
	 * @param branch
	 * @param doc
	 * @param ph_no
	 */
	public Patient(String id, String name, String gender, String age,
			String address, String branch, String doc, String ph_no) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;		
		this.address = address;
		this.branch = branch;
		this.doc = doc;
		this.ph_no = ph_no;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTests() {
		return tests;
	}

	public void setTests(String tests) {
		this.tests = tests;
	}

	public String getMedicines() {
		return medicines;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	public String getInjections() {
		return injections;
	}

	public void setInjections(String injections) {
		this.injections = injections;
	}

	public String getDocfee() {
		return docfee;
	}

	public void setDocfee(String docfee) {
		this.docfee = docfee;
	}

	public String getTestfee() {
		return testfee;
	}

	public void setTestfee(String testfee) {
		this.testfee = testfee;
	}

	public String getMedfee() {
		return medfee;
	}

	public void setMedfee(String medfee) {
		this.medfee = medfee;
	}

	public String getPaidfee() {
		return paidfee;
	}

	public void setPaidfee(String paidfee) {
		this.paidfee = paidfee;
	}

	public String getTestreport() {
		return testreport;
	}

	public void setTestreport(String testreport) {
		this.testreport = testreport;
	}

	public String getTeststats() {
		return teststats;
	}

	public void setTeststats(String teststats) {
		this.teststats = teststats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPh_no() {
		return ph_no;
	}

	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

}
