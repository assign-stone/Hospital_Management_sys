package com.cg.hms.app.entity;

import java.time.LocalDateTime;
 
import java.util.List;
 
import com.cg.hms.app.model.NurseShift;
import com.cg.hms.app.model.NurseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
 
@Entity
@Table(name = "Nurse")
public class NurseEntity {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nurse_id")
	private int id;
 
	@Column(name = "nurse_name")
	private String name;
 
	@Enumerated(EnumType.STRING)
	@Column(name = "nurse_shift")
	private NurseShift nurseShift;
 
	@Enumerated(EnumType.STRING)
	@Column(name = "nurse_status")
	private NurseStatus nurseStatus;
 
	@Column(name = "nurse_contact")
	private long contact;
 
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name ="availability_Schedule")
	private LocalDateTime availabilitySchedule;
 

 
	private int department_id;
 
	private int admin_id;
 
	@OneToMany(mappedBy = "nurse")
	private List<AppointmentEntity> appointments;
 
	public NurseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	public NurseEntity(int id, String name, NurseShift nurseShift, NurseStatus nurseStatus, long contact,
			LocalDateTime availabilitySchedule, int department_id, int admin_id,
			List<AppointmentEntity> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.nurseShift = nurseShift;
		this.nurseStatus = nurseStatus;
		this.contact = contact;
		this.availabilitySchedule = availabilitySchedule;
		this.department_id = department_id;
		this.admin_id = admin_id;
		this.appointments = appointments;
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
 
 
 
	public List<AppointmentEntity> getAppointments() {
		return appointments;
	}
 
	public void setAppointments(List<AppointmentEntity> appointments) {
		this.appointments = appointments;
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

