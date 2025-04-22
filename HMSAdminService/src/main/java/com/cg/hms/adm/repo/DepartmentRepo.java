package com.cg.hms.adm.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.hms.adm.entity.DepartmentEntity;
import com.cg.hms.adm.exception.BadRequestException;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Integer> {
	
	@Query("SELECT d FROM DepartmentEntity d WHERE d.name = :name")
    DepartmentEntity findByName(String name) throws BadRequestException;

    
    @Query("SELECT d FROM DepartmentEntity d")
    List<DepartmentEntity> findAllDepartments() throws BadRequestException;
    
   
    @Query("SELECT d FROM DepartmentEntity d WHERE d.id = :id")
    Optional<DepartmentEntity> findById(int id) throws BadRequestException;

}
