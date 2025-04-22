package com.cg.hms.app.service;
import com.cg.hms.app.entity.NurseEntity;

import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.NurseModel;
import com.cg.hms.app.model.NurseShift;
import com.cg.hms.app.model.NurseStatus;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.NurseRepo;
import com.cg.hms.app.repo.PatientRepo;
import com.cg.hms.app.util.AppUtil; // Assuming you have AppUtil for conversion
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class NurseServiceImpl implements NurseService {
 
	@Autowired
	private NurseRepo nurseRepository;
 
	@Autowired
	private AppUtil appUtil; // Utility class for converting between Model and Entity
 
	@Autowired
	private PatientRepo patientRepository;
 
	@Override
	public NurseModel createNurse(NurseModel nurseModel) {
		NurseEntity nurseEntity = appUtil.convertNurseModelToEntity(nurseModel);
		NurseEntity savedEntity = nurseRepository.save(nurseEntity); // Save entity
		return appUtil.convertNurseEntityToModel(savedEntity); // Convert saved entity to model
	}
 
	@Override
	public List<NurseModel> getAllNurses() {
		List<NurseEntity> nurseEntities = nurseRepository.findAll();
		return appUtil.convertNurseEntityListToModelList(nurseEntities); // Convert entities to models
	}
 
	@Override
	public NurseModel getNurseById(int id) {
		Optional<NurseEntity> nurse = nurseRepository.findById(id);
		return nurse.map(appUtil::convertNurseEntityToModel).orElse(null); // Convert entity to model if found
	}
 
	@Override
	public List<NurseModel> getNurseByName(String name) {
		List<NurseEntity> nurseEntity = nurseRepository.getNurseByName(name); // Fetch nurse by name
		
		return appUtil.convertNurseEntityListToModelList(nurseEntity) ;
	}
	   
 
	@Override
	public NurseModel updateNurse(int id, NurseModel nurseModel) {
		if (nurseRepository.existsById(id)) {
			nurseModel.setId(id);
			NurseEntity nurseEntity = appUtil.convertNurseModelToEntity(nurseModel); // Convert model to entity
			NurseEntity updatedEntity = nurseRepository.save(nurseEntity); // Save entity
			return appUtil.convertNurseEntityToModel(updatedEntity); // Convert updated entity to model
		}
		return null;
	}
 
	@Override
	public String deleteNurse(int id) {
		if (nurseRepository.existsById(id)) {
			nurseRepository.deleteById(id);
			return "Nurse deleted successfully.";
		}
		return "Nurse not found.";
	}
 
	
	@Override
	public List<NurseModel> getNursesByShift(String shiftType) {
		try {
			// Convert String to enum using valueOf
			NurseShift shift = NurseShift.valueOf(shiftType);
			List<NurseEntity> nurseEntities = nurseRepository.findByNurseShift(shift);
			return appUtil.convertNurseEntityListToModelList(nurseEntities); // Convert list of entities to models
		} catch (IllegalArgumentException e) {
			return null; // If invalid shift type is provided
		}
	}
 
	@Override
	public List<NurseModel> getAvailableNurses() {
		List<NurseEntity> nurseEntities = nurseRepository.findByNurseStatus(NurseStatus.Available); // Use enum for
																									// status
		return appUtil.convertNurseEntityListToModelList(nurseEntities); // Convert list of entities to models
	}
 
	   public int getTotalNurses() {
	        return nurseRepository.countAllNurses();
	    }
	
 
}
