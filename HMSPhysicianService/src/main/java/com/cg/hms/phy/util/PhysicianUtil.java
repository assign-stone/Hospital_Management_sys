package com.cg.hms.phy.util;

import com.cg.hms.phy.entity.PhysicianEntity;
import com.cg.hms.phy.model.PhysicianModel;
import com.cg.hms.phy.entity.Shift;
import com.cg.hms.phy.entity.Status;
import com.cg.hms.phy.repo.PhysicianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PhysicianUtil {

	@Autowired
	private PhysicianRepo physicianRepo;

	// Convert from PhysicianEntity to PhysicianModel
	public PhysicianModel convertPhysicianEntityToModel(PhysicianEntity physicianEntity) {
		PhysicianModel physicianModel = new PhysicianModel();
		physicianModel.setId(physicianEntity.getId());
		physicianModel.setName(physicianEntity.getName());
		physicianModel.setSpeciality(physicianEntity.getSpeciality());
		physicianModel.setShift(physicianEntity.getShift());
		physicianModel.setStatus(physicianEntity.getStatus());
		physicianModel.setContact(physicianEntity.getContact());
		physicianModel.setDepartmentIds(physicianEntity.getDepartmentIds());

		return physicianModel;
	}

	// Convert from PhysicianModel to PhysicianEntity
	public PhysicianEntity convertPhysicianModelToEntity(PhysicianModel physicianModel) {
		PhysicianEntity physicianEntity = new PhysicianEntity(0, null, null, null, null, 0, 0);
		physicianEntity.setId(physicianModel.getId());
		physicianEntity.setName(physicianModel.getName());
		physicianEntity.setSpeciality(physicianModel.getSpeciality());
		physicianEntity.setShift(physicianModel.getShift());
		physicianEntity.setStatus(physicianModel.getStatus());
		physicianEntity.setContact(physicianModel.getContact());

		// Assuming a single department ID (you can expand this logic if needed)
		physicianEntity.setDepartmentIds(physicianModel.getDepartmentIds());

		return physicianEntity;
	}

	// Convert a list of PhysicianEntity to a list of PhysicianModel
	public List<PhysicianModel> convertPhysicianEntityListToModelList(List<PhysicianEntity> physicianEntities) {
		List<PhysicianModel> physicianModels = new ArrayList<>();
		for (PhysicianEntity physicianEntity : physicianEntities) {
			PhysicianModel physicianModel = convertPhysicianEntityToModel(physicianEntity);
			physicianModels.add(physicianModel);
		}
		return physicianModels;
	}

	// Convert a list of PhysicianModel to a list of PhysicianEntity
	public List<PhysicianEntity> convertPhysicianModelListToEntityList(List<PhysicianModel> physicianModels) {
		List<PhysicianEntity> physicianEntities = new ArrayList<>();
		for (PhysicianModel physicianModel : physicianModels) {
			PhysicianEntity physicianEntity = convertPhysicianModelToEntity(physicianModel);
			physicianEntities.add(physicianEntity);
		}
		return physicianEntities;
	}
}
