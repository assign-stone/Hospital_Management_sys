package com.cg.hms.adm.model;


public class PatientModel {
 
	private int id;
	private String name;
	private int age;
	private String issue;
	private long contact;
	private int admin_id;
 
	public PatientModel() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	public PatientModel(int id, String name, int age, String issue, long contact, int admin_id) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.issue = issue;
		this.contact = contact;
		this.admin_id = admin_id;
	}
 
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public int getAge() {
		return age;
	}
 
	public void setAge(int age) {
		this.age = age;
	}
 
	public String getIssue() {
		return issue;
	}
 
	public void setIssue(String issue) {
		this.issue = issue;
	}
 
	public long getContact() {
		return contact;
	}
 
	public void setContact(long contact) {
		this.contact = contact;
	}
 
	public int getAdmin_id() {
		return admin_id;
	}
 
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
 
}