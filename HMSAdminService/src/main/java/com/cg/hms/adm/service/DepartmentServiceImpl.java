package com.cg.hms.adm.service;

import com.cg.hms.adm.entity.DepartmentEntity;

import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.model.DepartmentModel;
import com.cg.hms.adm.repo.DepartmentRepo;
import com.cg.hms.adm.util.DepartmentUtil;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepository;

	@Autowired
	private DepartmentUtil departmentUtil;

	@Override
	public DepartmentModel createDepartment(DepartmentModel departmentModel) throws BadRequestException {
		// Check if ID is provided during creation (ID should not be passed during
		// creation)
		if (departmentModel.getId() != 0) {
			throw new BadRequestException("ID should not be provided while creating a new department.");
		}

		// Check if department with the same name already exists
		if (departmentRepository.findByName(departmentModel.getName()) != null) {
			throw new BadRequestException("Department with name '" + departmentModel.getName() + "' already exists.");
		}

		// Convert and save the department
		DepartmentEntity departmentEntity = departmentUtil.convertDepartmentModelToEntity(departmentModel);
		departmentEntity = departmentRepository.save(departmentEntity);
		return departmentUtil.convertDepartmentEntityToModel(departmentEntity);
	}

	@Override
	public DepartmentModel updateDepartment(int id, DepartmentModel departmentModel) throws BadRequestException {
		// Check if the department exists
		Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(id);
		if (!existingDepartment.isPresent()) {
			throw new BadRequestException("Department with ID " + id + " not found.");
		}

		// Update the department entity from the model
		DepartmentEntity departmentEntity = existingDepartment.get();
		departmentUtil.updateDepartmentEntityFromModel(departmentModel, departmentEntity);

		// Save the updated department
		departmentEntity = departmentRepository.save(departmentEntity);
		return departmentUtil.convertDepartmentEntityToModel(departmentEntity);
	}

	@Override
	public void deleteDepartment(int id) throws BadRequestException {
		// Check if the department exists
		Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(id);
		if (!departmentEntity.isPresent()) {
			throw new BadRequestException("Department with ID " + id + " not found.");
		}

		// Delete the department
		departmentRepository.delete(departmentEntity.get());
	}

	@Override
	public List<DepartmentModel> getAllDepartments() {
		List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
		return departmentUtil.convertDepartmentEntityListToModelList(departmentEntities);
	}

	@Override
	public DepartmentModel getDepartmentById(int id) throws BadRequestException {
		// Check if the department exists
		Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(id);
		if (!departmentEntity.isPresent()) {
			throw new BadRequestException("Department with ID " + id + " not found.");
		}
		return departmentUtil.convertDepartmentEntityToModel(departmentEntity.get());
	}

	@Override
	public DepartmentModel getDepartmentByName(String name) throws BadRequestException {
		// Check if the department exists
		DepartmentEntity departmentEntity = departmentRepository.findByName(name);
		if (departmentEntity == null) {
			throw new BadRequestException("Department with name '" + name + "' not found.");
		}
		return departmentUtil.convertDepartmentEntityToModel(departmentEntity);
	}

}
