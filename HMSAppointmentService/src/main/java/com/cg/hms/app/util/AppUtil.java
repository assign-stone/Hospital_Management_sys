package com.cg.hms.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import com.cg.hms.app.entity.AppointmentEntity;
import com.cg.hms.app.entity.NurseEntity;
import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.model.AppointmentModel;
import com.cg.hms.app.model.NurseModel;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.NurseRepo;
import com.cg.hms.app.repo.PatientRepo;
 
@Component
public class AppUtil {
 
    @Autowired
    private NurseRepo nurseRepository;
 
    @Autowired
    private PatientRepo patientRepository;
 
    // Appointment conversion methods
    public AppointmentModel convertAppointmentEntityToModel(AppointmentEntity appointmentEntity) {
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setId(appointmentEntity.getId());
        appointmentModel.setDate(appointmentEntity.getDate());
        appointmentModel.setTime(appointmentEntity.getTime());
        appointmentModel.setDisease(appointmentEntity.getIssue()); // 'disease' mapped to 'issue'
        appointmentModel.setAppStatus(appointmentEntity.getAppStatus());
        appointmentModel.setPhysician_id(appointmentEntity.getPhysician_id());
 
        // Set nurse_id and patient_id
        appointmentModel.setNurse_id(appointmentEntity.getNurse() != null ? appointmentEntity.getNurse().getId() : 0);
        appointmentModel.setPatient_id(appointmentEntity.getPatient() != null ? appointmentEntity.getPatient().getId() : 0);
 
        return appointmentModel;
    }
 
    public AppointmentEntity convertAppointmentModelToEntity(AppointmentModel appointmentModel) {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setId(appointmentModel.getId());
        appointmentEntity.setDate(appointmentModel.getDate());
        appointmentEntity.setTime(appointmentModel.getTime());
        appointmentEntity.setIssue(appointmentModel.getDisease());  // 'disease' mapped to 'issue'
        appointmentEntity.setAppStatus(appointmentModel.getAppStatus());
        appointmentEntity.setPhysician_id(appointmentModel.getPhysician_id());
 
        // Fetch the NurseEntity and PatientEntity based on their IDs
        Optional<NurseEntity> nurse = nurseRepository.findById(appointmentModel.getNurse_id());
        Optional<PatientEntity> patient = patientRepository.findById(appointmentModel.getPatient_id());
 
        // Set NurseEntity and PatientEntity if present
        nurse.ifPresent(appointmentEntity::setNurse);
        patient.ifPresent(appointmentEntity::setPatient);
 
        return appointmentEntity;
    }
 
    public List<AppointmentModel> convertAppointmentEntityListToModelList(List<AppointmentEntity> appointmentEntities) {
        List<AppointmentModel> appointmentModels = new ArrayList<>();
 
        for (AppointmentEntity appointmentEntity : appointmentEntities) {
            AppointmentModel appointmentModel = convertAppointmentEntityToModel(appointmentEntity);
            appointmentModels.add(appointmentModel);
        }
 
        return appointmentModels;
    }
 
    // Patient conversion methods
    public PatientModel convertPatientEntityToModel(PatientEntity patientEntity) {
        PatientModel patientModel = new PatientModel();
        patientModel.setId(patientEntity.getId());
        patientModel.setName(patientEntity.getName());
        patientModel.setAge(patientEntity.getAge());
        patientModel.setIssue(patientEntity.getIssue());
        patientModel.setContact(patientEntity.getContact());
        patientModel.setAdmin_id(patientEntity.getAdmin_id());
 
        return patientModel;
    }
 
    public PatientEntity convertPatientModelToEntity(PatientModel patientModel) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientModel.getId());
        patientEntity.setName(patientModel.getName());
        patientEntity.setAge(patientModel.getAge());
        patientEntity.setIssue(patientModel.getIssue());
        patientEntity.setContact(patientModel.getContact());
        patientEntity.setAdmin_id(patientModel.getAdmin_id());
 
 
        return patientEntity;
    }
 
    public List<PatientModel> convertPatientEntityListToModelList(List<PatientEntity> patientEntities) {
        List<PatientModel> patientModels = new ArrayList<>();
 
        for (PatientEntity patientEntity : patientEntities) {
            PatientModel patientModel = convertPatientEntityToModel(patientEntity);
            patientModels.add(patientModel);
        }
 
        return patientModels;
    }
 
    // Nurse conversion methods
    public NurseModel convertNurseEntityToModel(NurseEntity nurseEntity) {
        NurseModel nurseModel = new NurseModel();
        nurseModel.setId(nurseEntity.getId());
        nurseModel.setName(nurseEntity.getName());
        nurseModel.setNurseShift(nurseEntity.getNurseShift());
        nurseModel.setNurseStatus(nurseEntity.getNurseStatus());
        nurseModel.setContact(nurseEntity.getContact());
        nurseModel.setAvailabilitySchedule(nurseEntity.getAvailabilitySchedule());
        nurseModel.setDepartment_id(nurseEntity.getDepartment_id());
        nurseModel.setAdmin_id(nurseEntity.getAdmin_id());
 

        return nurseModel;
    }
 
    public NurseEntity convertNurseModelToEntity(NurseModel nurseModel) {
        NurseEntity nurseEntity = new NurseEntity();
        nurseEntity.setId(nurseModel.getId());
        nurseEntity.setName(nurseModel.getName());
        nurseEntity.setNurseShift(nurseModel.getNurseShift());
        nurseEntity.setNurseStatus(nurseModel.getNurseStatus());
        nurseEntity.setContact(nurseModel.getContact());
        nurseEntity.setAvailabilitySchedule(nurseModel.getAvailabilitySchedule());
        nurseEntity.setDepartment_id(nurseModel.getDepartment_id());
        nurseEntity.setAdmin_id(nurseModel.getAdmin_id());
 
        return nurseEntity;
    }
 
    public List<NurseModel> convertNurseEntityListToModelList(List<NurseEntity> nurseEntities) {
        List<NurseModel> nurseModels = new ArrayList<>();
 
        for (NurseEntity nurseEntity : nurseEntities) {
            NurseModel nurseModel = convertNurseEntityToModel(nurseEntity);
            nurseModels.add(nurseModel);
        }
 
        return nurseModels;
    }
}
 