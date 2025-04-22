package com.cg.hms.phy.repo;

import com.cg.hms.phy.entity.PhysicianEntity;
import com.cg.hms.phy.entity.Shift;
import com.cg.hms.phy.entity.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicianRepo extends JpaRepository<PhysicianEntity, Integer> {

	// Find physicians by department ID
	@Query("SELECT p FROM PhysicianEntity p WHERE p.departmentIds = :departmentId")
	List<PhysicianEntity> findByDepartmentId(@Param("departmentId") int departmentId);

	// Find physicians by status
	@Query("SELECT p FROM PhysicianEntity p WHERE p.status = :status")
	List<PhysicianEntity> findByStatus(@Param("status") Status status);

	// Find physicians by shift
	@Query("SELECT p FROM PhysicianEntity p WHERE p.shift = :shift")
	List<PhysicianEntity> findByShift(@Param("shift") Shift shift);

	// Custom query to find physicians by name
	@Query("SELECT p FROM PhysicianEntity p WHERE p.name LIKE %:name%")
	List<PhysicianEntity> findByName(@Param("name") String name);

	// Custom query to find physicians by name and status
	@Query("SELECT p FROM PhysicianEntity p WHERE p.name LIKE %:name% AND p.status = :status")
	List<PhysicianEntity> findByNameAndStatus(String name, String status);

}
