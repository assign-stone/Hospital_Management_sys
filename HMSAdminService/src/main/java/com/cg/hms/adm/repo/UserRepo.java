package com.cg.hms.adm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.hms.adm.entity.UserEntity;
import com.cg.hms.adm.exception.BadRequestException;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>{
	
	@Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    List<UserEntity> findByUsername(String username)throws BadRequestException;

    
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(String email) throws BadRequestException;

}
