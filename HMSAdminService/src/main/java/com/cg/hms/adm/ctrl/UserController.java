package com.cg.hms.adm.ctrl;

import com.cg.hms.adm.entity.Role;

import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.exception.ErrorResponse;
import com.cg.hms.adm.model.AppointmentModel;
import com.cg.hms.adm.model.LoginModel;
import com.cg.hms.adm.model.PatientModel;
import com.cg.hms.adm.model.PhysicianModel;
import com.cg.hms.adm.model.UserModel;
import com.cg.hms.adm.repo.UserRepo;
import com.cg.hms.adm.service.UserService;
import com.cg.hms.adm.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	//register user
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserModel userModel) {
		try {

			userServiceImpl.createUser(userModel);
			return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occurred: " + ex.getMessage());
		}
	}

	// login user
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginModel loginModel) {
		try {
			LoginModel loggedInUser = userService.loginUser(loginModel);
			return ResponseEntity.status(HttpStatus.CREATED).body("Login Successfull");
		} catch (BadRequestException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()+"Email or Password is wrong.");
		}
	}

	//update user
	@PutMapping("/update/{id}")
	public ResponseEntity<UserModel> updateUser(@PathVariable int id, @RequestBody UserModel userModel)
			throws BadRequestException {
		UserModel updatedUser = userService.updateUser(id, userModel);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) throws BadRequestException {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/")
	public ResponseEntity<List<UserModel>> getAllUsers() {
		List<UserModel> userModels = userService.getAllUsers();
		return ResponseEntity.ok(userModels);
	}

	//get user by id
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUserById(@PathVariable int id) throws BadRequestException {
		UserModel userModel = userService.getUserById(id);
		if (userModel != null) {
			return ResponseEntity.ok(userModel);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//get user by username
	@GetMapping("/username/{username}")
	public ResponseEntity<List<UserModel>> getUserByUsername(@PathVariable String username) throws BadRequestException {
		List<UserModel> userModel = userService.getUserByUsername(username);
		return ResponseEntity.ok(userModel);
	}

	//get user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserModel> getUserByEmail(@PathVariable String email) throws BadRequestException {
		UserModel userModel = userService.getUserByEmail(email);
		return ResponseEntity.ok(userModel);
	}

	// Rest APIs
	// get Physician For Appointment
	@GetMapping("/appointments/{appointmentId}/physician")
	public PhysicianModel getPhysicianForAppointment(@PathVariable int appointmentId) {
		return userServiceImpl.getPhysicianForAppointment(appointmentId);
	}

	// get Appointments For Physician
	@GetMapping("appointments/{physicianId}")
	public ResponseEntity<List<AppointmentModel>> getAppointmentsForPhysician(@PathVariable int physicianId) {
		try {
			List<AppointmentModel> appointments = userServiceImpl.getAppointmentsForPhysician(physicianId);
			return ResponseEntity.status(200).body(appointments);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ResponseEntity.status(200).body(null);
	}

	// get Patients For Physician
	@GetMapping("patients/{physicianId}")
	public ResponseEntity<List<PatientModel>> getPatientsForPhysician(@PathVariable int physicianId) {
		try {
			List<PatientModel> patients = userServiceImpl.getPatientsForPhysician(physicianId);
			return ResponseEntity.status(HttpStatus.OK).body(patients);
		} catch (HttpClientErrorException.NotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// create appointment
	@PostMapping("/create")
	public ResponseEntity<AppointmentModel> createAppointment(@RequestBody AppointmentModel appointmentModel)
			throws BadRequestException {
		try {
			AppointmentModel createdAppointment = userServiceImpl.createAppointment(appointmentModel);
			return ResponseEntity.ok(createdAppointment);
		} catch (Exception e) {
			throw new BadRequestException("Error Creating appointment");
		}
	}

}
