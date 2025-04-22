package com.cg.hms.app.entity;

import java.time.LocalDate;

import java.time.LocalTime;


import com.cg.hms.app.model.AppointmentStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "Appointment")
public class AppointmentEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private int id;

    @Column(name = "app_date")
    private LocalDate date;

    @Column(name = "app_time")
    private LocalTime time;

    @Column(name = "app_issue")
    private String issue;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_status")
    private AppointmentStatus appStatus;

    @Column(name = "physician_id")
    private int physician_id;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private NurseEntity nurse;

    @ManyToOne
    @JoinColumn(name = "patient_id")  // Foreign key column to PatientEntity
    private PatientEntity patient;  // No need for patient_id field here

    public AppointmentEntity() {
        super();
    }

    public AppointmentEntity(int id, LocalDate date, LocalTime time, String issue, AppointmentStatus appStatus,
                             int physician_id, NurseEntity nurse, PatientEntity patient) {
        super();
        this.id = id;
        this.date = date;
        this.time = time;
        this.issue = issue;
        this.appStatus = appStatus;
        this.physician_id = physician_id;
        this.nurse = nurse;
        this.patient = patient;
    }

    // Getters and Setters

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

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	
	public AppointmentStatus getAppStatus() {
		return appStatus;
	}



	public void setAppStatus(AppointmentStatus appStatus) {
		this.appStatus = appStatus;
	}


	public NurseEntity getNurse() {
		return nurse;
	}

	public void setNurse(NurseEntity nurse) {
		this.nurse = nurse;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public int getPhysician_id() {
		return physician_id;
	}

	public void setPhysician_id(int physician_id) {
		this.physician_id = physician_id;
	}

}
