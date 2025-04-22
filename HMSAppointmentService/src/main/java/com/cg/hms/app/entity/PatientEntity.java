package com.cg.hms.app.entity;

import java.util.List;
import jakarta.persistence.*;
 
@Entity
@Table(name = "Patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pat_id")
    private int id;
    @Column(name = "pat_name")
    private String name;
    @Column(name = "pat_age")
    private int age;
    @Column(name = "pat_issue")
    private String issue;
    @Column(name = "pat_contact")
    private long contact;
    private int admin_id;
    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;
    
  
    public PatientEntity() {
        super();
    }

 
 
	public PatientEntity(int id, String name, int age, String issue, long contact, int admin_id,
		List<AppointmentEntity> appointments) {
	super();
	this.id = id;
	this.name = name;
	this.age = age;
	this.issue = issue;
	this.contact = contact;
	this.admin_id = admin_id;
	this.appointments = appointments;
}



	// Getters and Setters

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
 
 
	public List<AppointmentEntity> getAppointments() {
		return appointments;
	}
 
 
	public void setAppointments(List<AppointmentEntity> appointments) {
		this.appointments = appointments;
	}
}


