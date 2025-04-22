package com.cg.hms.app.controller;

import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Get all patients
    @GetMapping
    public ResponseEntity<List<PatientModel>> getAllPatients() throws BadRequestException {
        List<PatientModel> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    // Get a patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getPatientById(@PathVariable int id) throws BadRequestException {
        PatientModel patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            throw new BadRequestException("Patient with ID " + id + " does not exist.");
        }
    }

    // Update a patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientModel> updatePatient(@PathVariable int id, @RequestBody PatientModel patientModel) throws BadRequestException {
        PatientModel updatedPatient = patientService.updatePatient(id, patientModel);
        if (updatedPatient != null) {
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } else {
            throw new BadRequestException("Patient with ID " + id + " does not exist.");
        }
    }

    // Delete a patient
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) throws BadRequestException {
        String response = patientService.deletePatient(id);
        if (response.equals("Patient deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new BadRequestException("Patient with ID " + id + " does not exist.");
        }
    }

    // Create a patient
    @PostMapping
    public ResponseEntity<PatientModel> createPatient(@RequestBody PatientModel patientModel) throws BadRequestException {
        PatientModel createdPatient = patientService.createPatient(patientModel);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PatientModel>> searchPatientsByName(@RequestParam String name) throws BadRequestException {
        List<PatientModel> patients = patientService.searchPatientsByName(name);
        if (patients.isEmpty()) {
            throw new BadRequestException("No patients found with name containing: " + name);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}


