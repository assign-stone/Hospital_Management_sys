package com.cg.hms.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.hms.app.entity.NurseEntity;

import com.cg.hms.app.model.NurseShift;
import com.cg.hms.app.model.NurseStatus;

import java.util.List;
import java.util.Optional;



@Repository
public interface NurseRepo extends JpaRepository<NurseEntity, Integer> {
 
    List<NurseEntity> findByNurseShift(NurseShift shift);
 
    List<NurseEntity> findByNurseStatus(NurseStatus available);
 
    @Query("SELECT n FROM NurseEntity n WHERE LOWER(n.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<NurseEntity> getNurseByName(String name);
 
    @Query("SELECT COUNT(n) FROM NurseEntity n")
    int countAllNurses();
	  
	  
}