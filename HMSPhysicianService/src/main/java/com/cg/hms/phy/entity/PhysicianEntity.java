package com.cg.hms.phy.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Physician")
public class PhysicianEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "physician_id")
	private int id;


	@Column(name = "physician_name")
	private String name;

	@Column(name = "physician_speciality")
	private String speciality;

	@Enumerated(EnumType.STRING)
	@Column(name = "physician_shift")
	private Shift shift;

	@Enumerated(EnumType.STRING)
	@Column(name = "physician_status")
	private Status status;

	@Column(name = "physician_contact")
	private long contact;

	@Column(name = "department_ids")
	private int departmentIds;

	public PhysicianEntity(int id, String name, String speciality, Shift shift, Status status, long contact,
			int departmentIds) {
		this.id = id;
		this.name = name;
		this.speciality = speciality;
		this.shift = shift;
		this.status = status;
		this.contact = contact;
		this.departmentIds = departmentIds;
	}

	public PhysicianEntity() {
		super();
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

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public int getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(int departmentIds) {
		this.departmentIds = departmentIds;
	}

	@Override
	public String toString() {
		return "PhysicianEntity [id=" + id + ", name=" + name + ", speciality=" + speciality + ", shift=" + shift
				+ ", status=" + status + ", contact=" + contact + ", departmentIds=" + departmentIds + "]";
	}

}
