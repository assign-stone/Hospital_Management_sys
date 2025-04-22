package com.cg.hms.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.hms.app.entity.PatientEntity;

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, Integer>{
    
	@Query("SELECT p FROM PatientEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<PatientEntity> findByNameContainingIgnoreCase(String name);
	
	
	
   
}
