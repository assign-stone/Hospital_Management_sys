package com.cg.hms.app.service;
import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.PatientModel;
import java.util.List;
 
public interface PatientService {
    public PatientModel createPatient(PatientModel patientModel)throws BadRequestException;
    public List<PatientModel> getAllPatients()throws BadRequestException;
    public PatientModel getPatientById(int id)throws BadRequestException;
    public PatientModel updatePatient(int id, PatientModel patientModel)throws BadRequestException;
    public String deletePatient(int id)throws BadRequestException;
    public List<PatientModel> searchPatientsByName(String name)throws BadRequestException;

}