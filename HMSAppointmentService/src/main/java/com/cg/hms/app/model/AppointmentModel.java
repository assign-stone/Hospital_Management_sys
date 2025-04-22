package com.cg.hms.app.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cg.hms.app.entity.NurseEntity;
import com.cg.hms.app.entity.PatientEntity;

public class AppointmentModel {
	private int id;
	private LocalDate date;
	private LocalTime time;
	private String disease;
    private AppointmentStatus appStatus;
	private int Physician_id;
	private int nurse_id;
	private int patient_id;



	public AppointmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public AppointmentModel(int id, LocalDate date, LocalTime time, String disease, AppointmentStatus appStatus,
			int physician_id, int nurse_id, int patient_id) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.disease = disease;
		this.appStatus = appStatus;
		Physician_id = physician_id;
		this.nurse_id = nurse_id;
		this.patient_id = patient_id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	

	public AppointmentStatus getAppStatus() {
		return appStatus;
	}



	public void setAppStatus(AppointmentStatus appStatus) {
		this.appStatus = appStatus;
	}



	public int getPhysician_id() {
		return Physician_id;
	}

	public void setPhysician_id(int physician_id) {
		Physician_id = physician_id;
	}

	public int getNurse_id() {
		return nurse_id;
	}

	public void setNurse_id(int nurse_id) {
		this.nurse_id = nurse_id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	

}
