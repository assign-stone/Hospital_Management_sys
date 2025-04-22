package com.cg.hms.adm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.hms.adm.entity.DepartmentEntity;
import com.cg.hms.adm.entity.UserEntity;
import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.model.DepartmentModel;
import com.cg.hms.adm.repo.UserRepo;

@Component
public class DepartmentUtil {
	
	@Autowired
	private UserRepo userRepo;

	public DepartmentModel convertDepartmentEntityToModel(DepartmentEntity departmentEntity) {
		DepartmentModel departmentModel = new DepartmentModel();
		departmentModel.setId(departmentEntity.getId());
		departmentModel.setName(departmentEntity.getName());
		departmentModel.setDesc(departmentEntity.getDesc());
		departmentModel.setUserId(departmentEntity.getUser().getId());
		return departmentModel;
	}

	public DepartmentEntity convertDepartmentModelToEntity(DepartmentModel departmentModel) {
	    DepartmentEntity departmentEntity = new DepartmentEntity();
	    departmentEntity.setId(departmentModel.getId());
	    departmentEntity.setName(departmentModel.getName());
	    departmentEntity.setDesc(departmentModel.getDesc());

	    // Automatically retrieve the UserEntity based on userId
	    Optional<UserEntity> userEntityOpt = userRepo.findById(departmentModel.getUserId());
	    if (userEntityOpt.isPresent()) {
	        departmentEntity.setUser(userEntityOpt.get());  // Set the associated UserEntity
	    } else {
	        // Handle the case where no user was found
	        throw new BadRequestException("User with ID " + departmentModel.getUserId() + " not found.");
	    }

	    return departmentEntity;
	}

	public List<DepartmentModel> convertDepartmentEntityListToModelList(List<DepartmentEntity> departmentEntities) {
		List<DepartmentModel> departmentModels = new ArrayList<>();

		for (DepartmentEntity departmentEntity : departmentEntities) {
			DepartmentModel departmentModel = convertDepartmentEntityToModel(departmentEntity);
			departmentModels.add(departmentModel);
		}

		return departmentModels;
	}

	// Update department entity based on department model

	public void updateDepartmentEntityFromModel(DepartmentModel departmentModel, DepartmentEntity departmentEntity) {
		if (departmentModel.getName() != null) {
			departmentEntity.setName(departmentModel.getName());
		}
		if (departmentModel.getDesc() != null) {
			departmentEntity.setDesc(departmentModel.getDesc());
		}
	}

}
