package com.cg.hms.app.service;


import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.AppointmentModel;
import com.cg.hms.app.model.PatientModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

	List<AppointmentModel> getAllAppointments()throws BadRequestException;

	AppointmentModel getAppointmentById(int id)throws BadRequestException;

	AppointmentModel createAppointment(AppointmentModel appointmentModel)throws BadRequestException;

	List<AppointmentModel> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate)throws BadRequestException;

	List<AppointmentModel> getAppointmentsByNurse(int nurseId)throws BadRequestException;

	List<AppointmentModel> getAppointmentsByPatient(int patientId)throws BadRequestException;


	AppointmentModel updateAppointment(int id, AppointmentModel appointmentModel)throws BadRequestException;

	void deleteAppointment(int id)throws BadRequestException;

	List<LocalDateTime> getAppointmentDatesByPatientId(int patientId)throws BadRequestException;

	 List<AppointmentModel> getAppointmentsByDate(LocalDate date)throws BadRequestException;
	    List<PatientModel> getPatientsAssignedToNurse(int nurseId );
		long getTotalAppointmentsForToday();

}
