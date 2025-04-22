package com.cg.hms.adm.service;

import com.cg.hms.adm.exception.BadRequestException;

import com.cg.hms.adm.model.DepartmentModel;
import java.util.List;

public interface DepartmentService {

	public DepartmentModel createDepartment(DepartmentModel departmentModel) throws BadRequestException;

	public DepartmentModel updateDepartment(int id, DepartmentModel departmentModel) throws BadRequestException;

	public void deleteDepartment(int id) throws BadRequestException;

	public List<DepartmentModel> getAllDepartments();

	public DepartmentModel getDepartmentById(int id) throws BadRequestException;

	public DepartmentModel getDepartmentByName(String name) throws BadRequestException;

}
