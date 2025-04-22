package com.cg.hms.adm.service;

import java.util.List;

import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.model.LoginModel;
import com.cg.hms.adm.model.UserModel;

public interface UserService {

	public UserModel createUser(UserModel userModel) throws BadRequestException;

	public LoginModel loginUser(LoginModel loginModel) throws BadRequestException;

	public UserModel updateUser(int id, UserModel userModel) throws BadRequestException;

	public void deleteUser(int id) throws BadRequestException;

	public List<UserModel> getAllUsers();

	public UserModel getUserById(int id) throws BadRequestException;

	public List<UserModel> getUserByUsername(String username) throws BadRequestException;

	public UserModel getUserByEmail(String email) throws BadRequestException;

}
