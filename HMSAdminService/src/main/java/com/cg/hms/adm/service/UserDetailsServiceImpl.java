package com.cg.hms.adm.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.hms.adm.entity.UserEntity;
import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Fetching user details for: " + username); // Debug log
		UserEntity userEntity = userRepo.findByEmail(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
		String roleWithPrefix = "ROLE_" + userEntity.getRole().name();
		return new User(userEntity.getEmail(), userEntity.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(roleWithPrefix)) // ROLE_ADMIN
		);
	}
}