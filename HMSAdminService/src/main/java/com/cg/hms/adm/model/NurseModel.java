package com.cg.hms.adm.model;

import java.time.LocalDateTime;


//import java.time.LocalTime;
//import com.cg.hms.app.entity.NurseEntity;
//import com.cg.hms.app.entity.PatientEntity;

public class NurseModel {
	private int id;
	private String name;
	private NurseShift nurseShift;
	private NurseStatus nurseStatus;
	private long contact;
	private LocalDateTime availabilitySchedule;

	private int department_id;
	
	private int admin_id;
	public NurseModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NurseModel(int id, String name, NurseShift nurseShift, NurseStatus nurseStatus, long contact,
			LocalDateTime availabilitySchedule, int department_id, int admin_id) {
		super();
		this.id = id;
		this.name = name;
		this.nurseShift = nurseShift;
		this.nurseStatus = nurseStatus;
		this.contact = contact;
		this.availabilitySchedule = availabilitySchedule;
		this.department_id = department_id;
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

	public NurseShift getNurseShift() {
		return nurseShift;
	}

	public void setNurseShift(NurseShift nurseShift) {
		this.nurseShift = nurseShift;
	}

	public NurseStatus getNurseStatus() {
		return nurseStatus;
	}

	public void setNurseStatus(NurseStatus nurseStatus) {
		this.nurseStatus = nurseStatus;
	}

	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public LocalDateTime getAvailabilitySchedule() {
		return availabilitySchedule;
	}
	public void setAvailabilitySchedule(LocalDateTime availabilitySchedule) {
		this.availabilitySchedule = availabilitySchedule;
	}

 

	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
}