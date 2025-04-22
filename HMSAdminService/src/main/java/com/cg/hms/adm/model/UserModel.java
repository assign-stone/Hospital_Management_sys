package com.cg.hms.adm.model;

import com.cg.hms.adm.entity.Role;

public class UserModel {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String password;
	private Role role;

	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserModel(int id, String name, String email, String phone, String password, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
