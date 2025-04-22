package com.cg.hms.phy.service;

import com.cg.hms.phy.entity.PhysicianEntity;
import com.cg.hms.phy.entity.Shift;
import com.cg.hms.phy.entity.Status;
import com.cg.hms.phy.exception.BadRequestException;
import com.cg.hms.phy.model.PhysicianModel;
import com.cg.hms.phy.repo.PhysicianRepo;
import com.cg.hms.phy.util.PhysicianUtil;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicianServiceImpl implements PhysicianService {

	@Autowired
	private PhysicianRepo physicianRepository;

	@Autowired
	private PhysicianUtil physicianUtil;

	 @Override
	    public List<PhysicianModel> getAllPhysicians() throws BadRequestException {
	        List<PhysicianEntity> physicianEntities = physicianRepository.findAll();
	        if (physicianEntities.isEmpty()) {
	            throw new BadRequestException("No physicians found.");
	        }
	        return physicianUtil.convertPhysicianEntityListToModelList(physicianEntities);
	    }

	    @Override
	    public PhysicianModel getPhysicianById(int id) throws BadRequestException {
	        Optional<PhysicianEntity> physicianEntityOpt = physicianRepository.findById(id);
	        if (physicianEntityOpt.isPresent()) {
	            return physicianUtil.convertPhysicianEntityToModel(physicianEntityOpt.get());
	        } else {
	            throw new BadRequestException("Physician with ID " + id + " does not exist.");
	        }
	    }

	    @Override
	    public List<PhysicianModel> getPhysiciansByName(String name) throws BadRequestException {
	        List<PhysicianEntity> physicianEntities = physicianRepository.findByName(name);
	        if (physicianEntities.isEmpty()) {
	            throw new BadRequestException("No physicians found with name " + name);
	        }
	        return physicianUtil.convertPhysicianEntityListToModelList(physicianEntities);
	    }

	    @Override
	    public List<PhysicianModel> getPhysiciansByDepartment(int departmentIds) throws BadRequestException {
	        List<PhysicianEntity> physicianEntities = physicianRepository.findByDepartmentId(departmentIds);
	        if (physicianEntities.isEmpty()) {
	            throw new BadRequestException("No physicians found for department ID " + departmentIds);
	        }
	        return physicianUtil.convertPhysicianEntityListToModelList(physicianEntities);
	    }

	    @Override
	    public List<PhysicianModel> getPhysiciansByStatus(Status status) throws BadRequestException {
	        List<PhysicianEntity> physicianEntities = physicianRepository.findByStatus(status);
	        if (physicianEntities.isEmpty()) {
	            throw new BadRequestException("No physicians found with status " + status);
	        }
	        return physicianUtil.convertPhysicianEntityListToModelList(physicianEntities);
	    }

	    @Override
	    public List<PhysicianModel> getPhysiciansByShift(Shift shift) throws BadRequestException {
	        List<PhysicianEntity> physicianEntities = physicianRepository.findByShift(shift);
	        if (physicianEntities.isEmpty()) {
	            throw new BadRequestException("No physicians found for shift " + shift);
	        }
	        return physicianUtil.convertPhysicianEntityListToModelList(physicianEntities);
	    }

	    @Transactional
	    @Override
	    public PhysicianModel createPhysician(PhysicianModel physicianModel) throws BadRequestException {
	        if (physicianModel.getId() != 0) {
	            throw new BadRequestException("Cannot create a physician with an existing ID");
	        }
	        PhysicianEntity physicianEntity = physicianUtil.convertPhysicianModelToEntity(physicianModel);
	        PhysicianEntity savedPhysicianEntity = physicianRepository.save(physicianEntity);
	        return physicianUtil.convertPhysicianEntityToModel(savedPhysicianEntity);
	    }

	    @Override
	    public PhysicianModel updatePhysician(int id, PhysicianModel physicianModel) throws BadRequestException {
	        Optional<PhysicianEntity> physicianEntityOpt = physicianRepository.findById(id);
	        if (physicianEntityOpt.isPresent()) {
	            PhysicianEntity physicianEntity = physicianEntityOpt.get();
	            physicianEntity.setName(physicianModel.getName());
	            physicianEntity.setSpeciality(physicianModel.getSpeciality());
	            physicianEntity.setShift(physicianModel.getShift());
	            physicianEntity.setStatus(physicianModel.getStatus());
	            physicianEntity.setContact(physicianModel.getContact());
	            physicianEntity.setDepartmentIds(physicianModel.getDepartmentIds());

	            PhysicianEntity updatedPhysicianEntity = physicianRepository.save(physicianEntity);
	            return physicianUtil.convertPhysicianEntityToModel(updatedPhysicianEntity);
	        } else {
	            throw new BadRequestException("Physician with ID " + id + " does not exist.");
	        }
	    }

	    @Override
	    public void deletePhysician(int id) throws BadRequestException {
	        Optional<PhysicianEntity> physicianEntityOpt = physicianRepository.findById(id);
	        if (physicianEntityOpt.isPresent()) {
	            physicianRepository.delete(physicianEntityOpt.get());
	        } else {
	            throw new BadRequestException("Physician with ID " + id + " does not exist.");
	        }
	    }

}
