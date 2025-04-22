package com.cg.hms.app.service;

import com.cg.hms.app.entity.NurseEntity;
import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.AppointmentRepo;
import com.cg.hms.app.repo.NurseRepo;
import com.cg.hms.app.repo.PatientRepo;
import com.cg.hms.app.util.AppUtil; // Assuming you have AppUtil for conversion

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private NurseRepo nurseRepo;
    @Autowired
    private AppUtil appUtil;

    @Override
    public PatientModel createPatient(PatientModel patientModel) throws BadRequestException {
        try {
            PatientEntity patientEntity = appUtil.convertPatientModelToEntity(patientModel);
            PatientEntity savedPatientEntity = patientRepo.save(patientEntity);
            return appUtil.convertPatientEntityToModel(savedPatientEntity);
        } catch (Exception e) {
            throw new BadRequestException("Error creating patient: " + e.getMessage());
        }
    }

    @Override
    public List<PatientModel> getAllPatients() throws BadRequestException {
        try {
            List<PatientEntity> patientEntities = patientRepo.findAll();
            return appUtil.convertPatientEntityListToModelList(patientEntities); // Convert entities to models
        } catch (Exception e) {
            throw new BadRequestException("Error fetching patients: " + e.getMessage());
        }
    }

    @Override
    public PatientModel getPatientById(int id) throws BadRequestException {
        try {
            Optional<PatientEntity> patient = patientRepo.findById(id);
            return patient.map(appUtil::convertPatientEntityToModel).orElseThrow(() -> new BadRequestException("Patient with ID " + id + " not found."));
        } catch (Exception e) {
            throw new BadRequestException("Error fetching patient by ID: " + e.getMessage());
        }
    }

    @Override
    public PatientModel updatePatient(int id, PatientModel patientModel) throws BadRequestException {
        try {
            if (patientRepo.existsById(id)) {
                patientModel.setId(id);
                PatientEntity patientEntity = appUtil.convertPatientModelToEntity(patientModel); // Convert model to entity
                PatientEntity updatedEntity = patientRepo.save(patientEntity); // Save entity
                return appUtil.convertPatientEntityToModel(updatedEntity); // Convert updated entity to model
            } else {
                throw new BadRequestException("Patient with ID " + id + " not found.");
            }
        } catch (Exception e) {
            throw new BadRequestException("Error updating patient: " + e.getMessage());
        }
    }

    @Override
    public String deletePatient(int id) throws BadRequestException {
        try {
            if (patientRepo.existsById(id)) {
                patientRepo.deleteById(id);
                return "Patient deleted successfully.";
            } else {
                throw new BadRequestException("Patient with ID " + id + " not found.");
            }
        } catch (Exception e) {
            throw new BadRequestException("Error deleting patient: " + e.getMessage());
        }
    }
    @Override
    public List<PatientModel> searchPatientsByName(String name) throws BadRequestException {
        List<PatientEntity> patientEntities = patientRepo.findByNameContainingIgnoreCase(name);
        
        // Check if the list is empty and throw an exception if no patients are found
        if (patientEntities.isEmpty()) {
            throw new BadRequestException("No patients found with name containing: " + name);
        }

        // Convert the PatientEntity list to PatientModel list
        return appUtil.convertPatientEntityListToModelList(patientEntities);
    }
}

