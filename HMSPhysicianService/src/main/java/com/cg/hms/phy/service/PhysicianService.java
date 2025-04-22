package com.cg.hms.phy.service;

import com.cg.hms.phy.entity.Shift;
import com.cg.hms.phy.entity.Status;
import com.cg.hms.phy.exception.BadRequestException;
import com.cg.hms.phy.model.PhysicianModel;
import java.util.List;

public interface PhysicianService {
	public List<PhysicianModel> getAllPhysicians()throws BadRequestException;

	public PhysicianModel getPhysicianById(int physicianId)throws BadRequestException;

	public List<PhysicianModel> getPhysiciansByDepartment(int departmentId)throws BadRequestException;

	public List<PhysicianModel> getPhysiciansByName(String name)throws BadRequestException;

	public List<PhysicianModel> getPhysiciansByStatus(Status status)throws BadRequestException;

	public List<PhysicianModel> getPhysiciansByShift(Shift shift)throws BadRequestException;

	public PhysicianModel createPhysician(PhysicianModel physicianModel)throws BadRequestException;

	public PhysicianModel updatePhysician(int physicianId, PhysicianModel physicianModel)throws BadRequestException;

	public void deletePhysician(int physicianId)throws BadRequestException;

}
