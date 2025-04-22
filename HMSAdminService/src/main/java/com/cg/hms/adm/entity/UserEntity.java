package com.cg.hms.adm.entity;

import jakarta.persistence.Column;

import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
	
	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    private int id;

	    @Column(name = "user_name")  
	    private String username;

	    @Column(name = "user_email", nullable = false, unique = true)  
	    private String email;

	    @Column(name = "user_phone")
	    private String phone;

	    @Column(name = "user_password", nullable = false)  
	    private String password;

	    @Enumerated(EnumType.STRING) 
	    @Column(name = "user_role", nullable = false) 
	    private Role role;
	    
	    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    private List<DepartmentEntity> departments;

	   

		public UserEntity() {
			super();
			
		}

		public UserEntity(int id, String username, String email, String phone, String password,
				List<DepartmentEntity> departments, Role role) {
			this.id = id;
			this.username = username;
			this.email = email;
			this.phone = phone;
			this.password = password;
			this.departments = departments;
			this.role = role;
		}

		public int getId() {
			return id;
		}

		public String getUsername() {
			return username;
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

		public List<DepartmentEntity> getDepartments() {
			return departments;
		}

		public Role getRole() {
			return role;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setUsername(String username) {
			this.username = username;
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

		public void setDepartments(List<DepartmentEntity> departments) {
			this.departments = departments;
		}

		public void setRole(Role role) {
			this.role = role;
		}

	
}
