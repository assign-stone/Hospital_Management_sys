package com.cg.hms.ui.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;


public class AppointmentModel {
	
	@NotEmpty (message ="id cannot be empty")
	private int id;
	
	@NotEmpty(message ="date cannot be empty")
	private LocalDate date;
	
	@NotEmpty(message ="time cannot be empty")
	private LocalTime time;
	
	@NotEmpty(message ="disease cannot be empty")
	private String disease;
	
	@NotEmpty(message ="appointment status  cannot be empty")
    private AppointmentStatus appStatus;
	
	@NotEmpty(message ="physician id cannot be empty")
	private int Physician_id;
	
	@NotEmpty(message ="nurse id cannot be empty")
	private int nurse_id;
	
	@NotEmpty(message ="patient id cannot be empty")
	private int patient_id;

	// private NurseEntity nurse;
	// private PatientEntity patient;

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
