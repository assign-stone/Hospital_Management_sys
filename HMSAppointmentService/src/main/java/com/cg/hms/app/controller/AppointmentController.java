package com.cg.hms.app.controller;

import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.AppointmentModel;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/app/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    


    // Endpoint to get all appointments
    @GetMapping("/")
    public ResponseEntity<List<AppointmentModel>> getAllAppointments() throws BadRequestException {
        List<AppointmentModel> appointmentModels = appointmentService.getAllAppointments();
        if (appointmentModels.isEmpty()) {
            throw new BadRequestException("No appointments found.");
        }
        return ResponseEntity.ok(appointmentModels);
    }

    // Endpoint to get an appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentModel> getAppointmentById(@PathVariable int id) throws BadRequestException {
        AppointmentModel appointmentModel = appointmentService.getAppointmentById(id);
        if (appointmentModel != null) {
            return ResponseEntity.ok(appointmentModel);
        } else {
            throw new BadRequestException("Appointment with ID " + id + " does not exist.");
        }
    }

    // Endpoint to update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentModel> updateAppointment(@PathVariable int id, @RequestBody AppointmentModel appointmentModel) throws BadRequestException {
        AppointmentModel updatedAppointment = appointmentService.updateAppointment(id, appointmentModel);
        if (updatedAppointment != null) {
            return ResponseEntity.ok(updatedAppointment);
        } else {
            throw new BadRequestException("Appointment with ID " + appointmentModel.getId() + " does not exist.");
        }
    }


    // Endpoint to delete an appointment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) throws BadRequestException {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get appointments within a date range
    @GetMapping("/date-range")
    public ResponseEntity<List<AppointmentModel>> getAppointmentsByDateRange(@RequestParam LocalDate startDate,
                                                                             @RequestParam LocalDate endDate) throws BadRequestException {
        List<AppointmentModel> appointmentModels = appointmentService.getAppointmentsByDateRange(startDate, endDate);
        if (appointmentModels.isEmpty()) {
            throw new BadRequestException("No appointments found within the specified date range.");
        }
        return ResponseEntity.ok(appointmentModels);
    }

    // Endpoint to get appointments by Nurse ID
    @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<AppointmentModel>> getAppointmentsByNurse(@PathVariable int nurseId) throws BadRequestException {
        List<AppointmentModel> appointmentModels = appointmentService.getAppointmentsByNurse(nurseId);
        if (appointmentModels.isEmpty()) {
            throw new BadRequestException("No appointments found for Nurse ID " + nurseId);
        }
        return ResponseEntity.ok(appointmentModels);
    }

    // Endpoint to get appointments by Patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentModel>> getAppointmentsByPatient(@PathVariable int patientId) throws BadRequestException {
        List<AppointmentModel> appointmentModels = appointmentService.getAppointmentsByPatient(patientId);
        if (appointmentModels.isEmpty()) {
            throw new BadRequestException("No appointments found for Patient ID " + patientId);
        }
        return ResponseEntity.ok(appointmentModels);
    }

    // Endpoint to get a list of appointment dates by patient ID
    @GetMapping("/patient/{patientId}/appointments-dates")
    public ResponseEntity<List<LocalDateTime>> getAppointmentDatesByPatientId(@PathVariable int patientId) throws BadRequestException {
        List<LocalDateTime> appointmentDates = appointmentService.getAppointmentDatesByPatientId(patientId);
        if (appointmentDates != null && !appointmentDates.isEmpty()) {
            return ResponseEntity.ok(appointmentDates);
        } else {
            throw new BadRequestException("No appointment dates found for Patient ID " + patientId);
        }
    }

    // Endpoint to get all appointments for a specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentModel>> getAppointmentsByDate(@PathVariable LocalDate date) throws BadRequestException {
        List<AppointmentModel> appointmentModels = appointmentService.getAppointmentsByDate(date);
        if (appointmentModels != null && !appointmentModels.isEmpty()) {
            return ResponseEntity.ok(appointmentModels);
        } else {
            throw new BadRequestException("No appointments found for date " + date);
        }
    }

    // Endpoint to get patients assigned to a specific nurse
    @GetMapping("/nurse/{nurseId}/patients")
    public ResponseEntity<List<PatientModel>> getPatientsAssignedToNurse(@PathVariable int nurseId) throws BadRequestException {
        if (nurseId <= 0) {
            throw new BadRequestException("Invalid nurse ID provided.");
        }

        List<PatientModel> patients = appointmentService.getPatientsAssignedToNurse(nurseId);

        if (patients.isEmpty()) {
            throw new BadRequestException("No patients found for Nurse ID " + nurseId);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
    
    @GetMapping("/today")
    public ResponseEntity<Long> getTotalAppointmentsForToday() {
        long totalAppointments = appointmentService.getTotalAppointmentsForToday();
        return new ResponseEntity<>(totalAppointments, HttpStatus.OK);
    }
    
    // Endpoint to create a new appointment
    @PostMapping("/")
    public ResponseEntity<AppointmentModel> createAppointment(@RequestBody AppointmentModel appointmentModel) throws BadRequestException {
        if (appointmentModel.getId() != 0) {
            throw new BadRequestException("ID should not be provided. It is autogenerated.");
        }
        AppointmentModel createdAppointment = appointmentService.createAppointment(appointmentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }
}

