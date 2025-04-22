package com.cg.hms.adm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cg.hms.adm.entity.Role;
import com.cg.hms.adm.entity.UserEntity;
import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.model.AppointmentModel;
import com.cg.hms.adm.model.LoginModel;
import com.cg.hms.adm.model.PatientModel;
import com.cg.hms.adm.model.PhysicianModel;
import com.cg.hms.adm.model.UserModel;
import com.cg.hms.adm.repo.UserRepo;
import com.cg.hms.adm.util.UserUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private UserUtil userUtil;
	private final AuthenticationManager authenticationManager;

	public UserServiceImpl(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final String physicianServiceUrl = "http://HMSPHYSICIANSERVICE/physician";
	private final String appointmentServiceUrl = "http://HMSAPPOINTMENTSERVICE/app";

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserModel createUser(UserModel userModel) throws BadRequestException {
		try {
			UserEntity existingUser = userRepository.findByEmail(userModel.getEmail());
			if (existingUser != null) {
				throw new BadRequestException("User with this email already exists.");
			}

			if (userModel.getRole() == null) {
				userModel.setRole(Role.ADMIN); // Default role is ADMIN
			}

			// Encode the password before saving the user
			String encodedPassword = passwordEncoder.encode(userModel.getPassword());
			userModel.setPassword(encodedPassword); // Set the encoded password

			// userModel.setPassword(userModel.getPassword());

			UserEntity userEntity = userUtil.convertUserModelToEntity(userModel);
			userEntity = userRepository.save(userEntity);

			return userUtil.convertUserEntityToModel(userEntity);
		} catch (Exception e) {
			throw new BadRequestException("Error creating user: " + e.getMessage());
		}
	}

	@Override
	public LoginModel loginUser(LoginModel loginModel) throws BadRequestException {
		try {
			UserEntity user = userRepository.findByEmail(loginModel.getEmail());

			if (user != null) {
				// Compare the passwords using the BCrypt encoder
				if (passwordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					return new LoginModel(user.getEmail(), loginModel.getPassword()); // Return LoginModel with email
																						// and password
				} else {
					throw new BadRequestException("Password doesn't match.");
				}
			} else {
				throw new BadRequestException("User with given email not found!");
			}
		} catch (Exception e) {
			throw new BadRequestException("Error logging in user: " + e.getMessage());
		}
	}

	@Override
	public UserModel updateUser(int id, UserModel userModel) throws BadRequestException {
		Optional<UserEntity> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			UserEntity userEntity = existingUser.get();

			// Ensure role is not null during update
			if (userModel.getRole() == null) {
				userModel.setRole(Role.ADMIN); // Default to 'admin' if no role is passed
			}

			userUtil.updateUserEntityFromModel(userModel, userEntity);
			userEntity = userRepository.save(userEntity);
			return userUtil.convertUserEntityToModel(userEntity);
		} else {
			throw new BadRequestException("User with ID " + id + " not found.");
		}
	}

	@Override
	public void deleteUser(int id) throws BadRequestException {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isPresent()) {
			userRepository.delete(userEntity.get());
		} else {
			throw new BadRequestException("User with ID " + id + " not found.");
		}
	}

	@Override
	public List<UserModel> getAllUsers() {
		List<UserEntity> userEntities = userRepository.findAll();
		return userUtil.convertUserEntityListToModelList(userEntities);
	}

	@Override
	public UserModel getUserById(int id) throws BadRequestException {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isPresent()) {
			return userUtil.convertUserEntityToModel(userEntity.get());
		} else {
			throw new BadRequestException("User with ID " + id + " not found.");
		}
	}

	@Override
	public List<UserModel> getUserByUsername(String username) throws BadRequestException {
		List<UserEntity> userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			return userUtil.convertUserEntityListToModelList(userEntity);
		} else {
			throw new BadRequestException("User with username " + username + " not found.");
		}
	}

	@Override
	public UserModel getUserByEmail(String email) throws BadRequestException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity != null) {
			return userUtil.convertUserEntityToModel(userEntity);
		} else {
			throw new BadRequestException("User with email " + email + " not found.");
		}
	}
	
	public PhysicianModel getPhysicianForAppointment(int appointmentId)throws BadRequestException {
		String appointmentUrl = appointmentServiceUrl + "/appointments/" + appointmentId;
		AppointmentModel appointment = restTemplate.getForObject(appointmentUrl, AppointmentModel.class);
 
		if (appointment == null) {
			throw new BadRequestException("Appointment with ID " + appointmentId + " not found.");
		}
 
		// Get physicianId from appointment and fetch the physician details
		int physicianId = appointment.getPhysician_id();
		String physicianUrl = physicianServiceUrl + "/" + physicianId;
 
		PhysicianModel physician = restTemplate.getForObject(physicianUrl, PhysicianModel.class);
 
		if (physician == null) {
			throw new BadRequestException("Physician with ID " + physicianId + " not found.");
		}
 
		return physician;
	}
 
	
	public AppointmentModel createAppointment(AppointmentModel appointmentModel) throws BadRequestException {
		// Get physician details for validation
		String physicianUrl = physicianServiceUrl + "/" + appointmentModel.getPhysician_id();
		ResponseEntity<PhysicianModel> physicianResponse = restTemplate.exchange(physicianUrl, HttpMethod.GET, null,
				PhysicianModel.class);
 
		if (physicianResponse.getStatusCode() != HttpStatus.OK) {
			throw new BadRequestException("Physician not found");
		}
 
		// Physician details found, proceed to create appointment
		PhysicianModel physician = physicianResponse.getBody();
		appointmentModel.setAppStatus("Schedulled");
 
		String appointmentUrl = appointmentServiceUrl + "/appointments/";
		// Create appointment by making a POST request
		ResponseEntity<AppointmentModel> appointmentResponse = restTemplate.postForEntity(appointmentUrl,
				appointmentModel, AppointmentModel.class);
		return appointmentResponse.getBody();
	}
	
 
//	public List<AppointmentModel> getAppointmentsForPhysician(int physicianId) {
//		// Ideally, this should be fetched from an API, not filtered in memory.
//		String url = appointmentServiceUrl + "/appointments/" + physicianId;
//		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<AppointmentModel>>() {
//				});
//		return response.getBody();
//	}
//	
	
	public List<AppointmentModel> getAppointmentsForPhysician(int physicianId) {
		List<AppointmentModel> appointments = getAllAppointments();
		List<AppointmentModel> forPhysician = new ArrayList<>();
		for (AppointmentModel model : appointments) {
			if (model.getPhysician_id() == physicianId) {
				forPhysician.add(model);
			}
		}
 
		return forPhysician;
	}
 
	public List<PatientModel> getPatientsForPhysician(int physicianId) {
		List<AppointmentModel> appointments = getAllAppointments();
		Set<Integer> uniquePatientIds = new HashSet<>();
		List<PatientModel> forPhysician = new ArrayList<>();
 
		for (AppointmentModel appointment : appointments) {
			if (appointment.getPhysician_id() == physicianId) {
				uniquePatientIds.add(appointment.getPatient_id());
			}
		}
		for (Integer patientId : uniquePatientIds) {
			try {
				PatientModel patient = getPatientById(patientId);
				if (patient != null) {
					forPhysician.add(patient);
				}
			} catch (HttpClientErrorException.NotFound e) {
				// Log the error or handle it as needed
				System.out.println("Patient with ID " + patientId + " does not exist.");
			}
		}
 
		return forPhysician;
	}
 
	// ---appointment calls
		public List<AppointmentModel> getAllAppointments() {
			String url = appointmentServiceUrl + "/appointments/";
			ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<AppointmentModel>>() {
					});
			return response.getBody();
		}
	 
		// Get a patient by ID
		public PatientModel getPatientById(int id) {
			String url = appointmentServiceUrl + "/patients/" + id;
			ResponseEntity<PatientModel> response = restTemplate.exchange(url, HttpMethod.GET, null, PatientModel.class);
			return response.getBody();
		}
}
