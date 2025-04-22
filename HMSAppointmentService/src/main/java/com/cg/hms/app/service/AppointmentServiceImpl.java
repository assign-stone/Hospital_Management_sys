package com.cg.hms.app.service;

import com.cg.hms.app.entity.AppointmentEntity;
import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.model.AppointmentModel;
import com.cg.hms.app.model.AppointmentStatus;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.AppointmentRepo;
import com.cg.hms.app.repo.PatientRepo;
import com.cg.hms.app.util.AppUtil;
import com.cg.hms.app.exception.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private AppUtil appUtil;  // Utility class for converting entity to model
    // Method to get all appointments
    @Override
    public List<AppointmentModel> getAllAppointments() throws BadRequestException {
        List<AppointmentEntity> appointmentEntities = appointmentRepo.findAll();
        if (appointmentEntities.isEmpty()) {
            throw new BadRequestException("No appointments found.");
        }
        return appUtil.convertAppointmentEntityListToModelList(appointmentEntities);
    }

    // Method to get appointment by ID
    @Override
    public AppointmentModel getAppointmentById(int id) throws BadRequestException {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepo.findById(id);
        if (appointmentEntity.isEmpty()) {
            throw new BadRequestException("Appointment with ID " + id + " does not exist.");
        }
        return appUtil.convertAppointmentEntityToModel(appointmentEntity.get());
    }

    // Method to create an appointment
    @Override
    public AppointmentModel createAppointment(AppointmentModel appointmentModel) throws BadRequestException {
        // Check if an appointment with the same ID already exists
        if (appointmentRepo.existsById(appointmentModel.getId())) {
            throw new BadRequestException("Appointment with ID " + appointmentModel.getId() + " already exists.");
        }

        // Convert model to entity
        AppointmentEntity appointmentEntity = appUtil.convertAppointmentModelToEntity(appointmentModel);

        // Check if patient exists for the appointment
        Optional<PatientEntity> patientEntity = patientRepo.findById(appointmentEntity.getPatient().getId());
        if (patientEntity.isEmpty()) {
            throw new BadRequestException("Patient with ID " + appointmentEntity.getPatient().getId() + " does not exist.");
        }

        // Set default appointment status if not provided
        if (appointmentEntity.getAppStatus() == null) {
            appointmentEntity.setAppStatus(AppointmentStatus.Schedulled);  // Default status is Scheduled
        }

        // Save the appointment entity and convert it back to the model
        appointmentEntity = appointmentRepo.save(appointmentEntity);
        return appUtil.convertAppointmentEntityToModel(appointmentEntity);
    }


    // Method to get appointments within a date range
    @Override
    public List<AppointmentModel> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) throws BadRequestException {
        List<AppointmentEntity> appointmentEntities = appointmentRepo.findAppointmentsByDateRange(startDate, endDate);
        if (appointmentEntities.isEmpty()) {
            throw new BadRequestException("No appointments found in the specified date range.");
        }
        return appUtil.convertAppointmentEntityListToModelList(appointmentEntities);
    }

    // Method to get appointments by Nurse ID
    @Override
    public List<AppointmentModel> getAppointmentsByNurse(int nurseId) throws BadRequestException {
        List<AppointmentEntity> appointmentEntities = appointmentRepo.findByNurseId(nurseId);
        if (appointmentEntities.isEmpty()) {
            throw new BadRequestException("No appointments found for Nurse with ID " + nurseId);
        }
        return appUtil.convertAppointmentEntityListToModelList(appointmentEntities);
    }

    // Method to get appointments by Patient ID
    @Override
    public List<AppointmentModel> getAppointmentsByPatient(int patientId) throws BadRequestException {
        List<AppointmentEntity> appointmentEntities = appointmentRepo.findByPatientId(patientId);
        if (appointmentEntities.isEmpty()) {
            throw new BadRequestException("No appointments found for Patient with ID " + patientId);
        }
        return appUtil.convertAppointmentEntityListToModelList(appointmentEntities);
    }

    // Method to update an appointment
    @Override
    public AppointmentModel updateAppointment(int id, AppointmentModel appointmentModel) throws BadRequestException {
        Optional<AppointmentEntity> existingAppointment = appointmentRepo.findById(appointmentModel.getId());
        if (existingAppointment.isEmpty()) {
            throw new BadRequestException("Appointment with ID " + appointmentModel.getId() + " does not exist.");
        }
        AppointmentEntity appointmentEntity = existingAppointment.get();

        // Update appointment fields
        appointmentEntity.setDate(appointmentModel.getDate());
        appointmentEntity.setTime(appointmentModel.getTime());
        appointmentEntity.setIssue(appointmentModel.getDisease());
        if (appointmentModel.getAppStatus() != null) {
            appointmentEntity.setAppStatus(appointmentModel.getAppStatus());
        }
        appointmentEntity = appointmentRepo.save(appointmentEntity);
        return appUtil.convertAppointmentEntityToModel(appointmentEntity);
    }

    // Method to delete an appointment by ID
    @Override
    public void deleteAppointment(int id) throws BadRequestException {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepo.findById(id);
        if (appointmentEntity.isEmpty()) {
            throw new BadRequestException("Appointment with ID " + id + " does not exist.");
        }
        appointmentRepo.delete(appointmentEntity.get());
    }

    @Override
    public List<AppointmentModel> getAppointmentsByDate(LocalDate date) throws BadRequestException {
        List<AppointmentEntity> appointments = appointmentRepo.findByDate(date);
        if (appointments.isEmpty()) {
            throw new BadRequestException("No appointments found on the specified date.");
        }
        return appUtil.convertAppointmentEntityListToModelList(appointments);
    }

  @Override
  public List<LocalDateTime> getAppointmentDatesByPatientId(int patientId) throws BadRequestException {
      List<AppointmentEntity> appointments = appointmentRepo.findByPatientId(patientId);
      if (appointments.isEmpty()) {
          throw new BadRequestException("No appointments found for pateint id "+patientId+".");
      }
      List<LocalDateTime> appointmentDates = new ArrayList<>();

      for (AppointmentEntity appointment : appointments) {
          appointmentDates.add(appointment.getDate().atTime(appointment.getTime()));
      }

      return appointmentDates;
  }
  
  @Override
  public List<PatientModel> getPatientsAssignedToNurse(int nurseId) {
      List<PatientEntity> patientEntities = appointmentRepo.findPatientsAssignedToNurse(nurseId);
      
      return appUtil.convertPatientEntityListToModelList(patientEntities);
  }
  
  public long getTotalAppointmentsForToday() {
      List<AppointmentStatus> statuses = Arrays.asList(AppointmentStatus.Schedulled, AppointmentStatus.Rescheduled);
      LocalDate currentDate = LocalDate.now();
      List<AppointmentEntity> appointments = appointmentRepo.findAppointmentsByStatusAndDate(statuses, currentDate);
      return appointments.size();
  }
  

}








