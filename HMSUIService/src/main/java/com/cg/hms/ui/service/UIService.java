package com.cg.hms.ui.service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.cg.hms.ui.model.AppointmentModel;
import com.cg.hms.ui.model.DepartmentModel;
import com.cg.hms.ui.model.LoginModel;
import com.cg.hms.ui.model.NurseModel;
import com.cg.hms.ui.model.PatientModel;
import com.cg.hms.ui.model.PhysicianModel;
import com.cg.hms.ui.model.Shift;
import com.cg.hms.ui.model.Status;
import com.cg.hms.ui.model.UserModel;

@Service
public class UIService {

	@Autowired
	RestTemplate restTemplate;
	
	 String physicianServiceUrl = "http://HMSPHYSICIANSERVICE/physician";
	 String appointmentServiceUrl = "http://HMSAPPOINTMENTSERVICE/app";
	 String adminServiceUrl = "http://HMSADMINSERVICE/admin";
	
//	private final String physicianServiceUrl = "http://localhost:8080/physician";
//	private final String appointmentServiceUrl = "http://localhost:8082/app";
//	private final String adminServiceUrl = "http://localhost:8087/admin";

	

	public String registerUser(UserModel userModel) {
	    String url = adminServiceUrl + "/register";
	    try {
	        ResponseEntity<String> response = restTemplate.postForEntity(url, userModel, String.class);
	        return response.getBody();
	    } catch (HttpClientErrorException e) {
	        // Handle 4xx errors
	        System.err.println("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	        return "Client error: " + e.getStatusCode();
	    } catch (HttpServerErrorException e) {
	        // Handle 5xx errors
	        System.err.println("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	        return "Server error: " + e.getStatusCode();
	    } catch (Exception e) {
	        // Handle other errors
	        System.err.println("Error: " + e.getMessage());
	        return "Error: " + e.getMessage();
	    }
	}

//	public LoginModel loginUser(LoginModel loginModel) {
//		String url = adminServiceUrl + "/login";
//		ResponseEntity<LoginModel> response = restTemplate.postForEntity(url, loginModel, LoginModel.class);
//		return response.getBody();
//	}
	
	public LoginModel loginUser(LoginModel loginModel) {
	    String url = adminServiceUrl + "/login";
	    try {
	        ResponseEntity<LoginModel> response = restTemplate.postForEntity(url, loginModel, LoginModel.class);
	        System.out.println("Response: " + response.getBody());
	        return response.getBody();
	    } catch (HttpClientErrorException e) {
	        // Handle 4xx errors
	        System.err.println("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	        throw new RuntimeException("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	    } catch (HttpServerErrorException e) {
	        // Handle 5xx errors
	        System.err.println("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	        throw new RuntimeException("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
	    } catch (Exception e) {
	        // Handle other errors
	        System.err.println("Error: " + e.getMessage());
	        throw new RuntimeException("Error: " + e.getMessage());
	    }
	}

	public UserModel updateUser(int id, UserModel userModel) {
		String url = adminServiceUrl + "/update/" + id;
	    HttpEntity<UserModel> requestEntity = new HttpEntity<>(userModel);

		ResponseEntity<UserModel> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, UserModel.class);
		return response.getBody();
	}

	public void deleteUser(int id) {
		String url = adminServiceUrl + "/" + id;
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
	}

	public List<UserModel> getAllUsers() {
		String url = adminServiceUrl + "/";
		ResponseEntity<List<UserModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserModel>>() {
				});
		return response.getBody();
	}

	public UserModel getUserById(int id) {
		String url = adminServiceUrl + "/";
		ResponseEntity<UserModel> response = restTemplate.exchange(url + id, HttpMethod.GET, null, UserModel.class);
		return response.getBody();
	}

	public UserModel getUserByUsername(String username) {
		String url = adminServiceUrl + "/username/" + username;
		ResponseEntity<UserModel> response = restTemplate.exchange(url, HttpMethod.GET, null, UserModel.class);
		return response.getBody();
	}

	public UserModel getUserByEmail(String email) {
		String url = adminServiceUrl + "/email/" + email;
		ResponseEntity<UserModel> response = restTemplate.exchange(url, HttpMethod.GET, null, UserModel.class);
		return response.getBody();
	}
//------------------------------------------------------------------------------------------------------------

	// Create a new department
	public DepartmentModel createDepartment(DepartmentModel departmentModel) {
		String url = adminServiceUrl + "/departments/";
		ResponseEntity<DepartmentModel> response = restTemplate.postForEntity(url, departmentModel,
				DepartmentModel.class);
		return response.getBody();
	}

	// Update an existing department
	public DepartmentModel updateDepartment(int id, DepartmentModel departmentModel) {
		String url = adminServiceUrl + "/departments/" + id;
		ResponseEntity<DepartmentModel> response = restTemplate.exchange(url, HttpMethod.PUT, null,
				DepartmentModel.class);
		return response.getBody();
	}

	// Delete a department
	public void deleteDepartment(int id) {
		String url = adminServiceUrl + "/departments/" + id;
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
	}

	// Get all departments
	public List<DepartmentModel> getAllDepartments() {
		String url = adminServiceUrl + "/departments/getall";
		ResponseEntity<List<DepartmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DepartmentModel>>() {
				});
		return response.getBody();
	}

	// Get a department by ID
	public DepartmentModel getDepartmentById(int id) {
		String url = adminServiceUrl + "/departments/" + id;
		ResponseEntity<DepartmentModel> response = restTemplate.exchange(url, HttpMethod.GET, null,
				DepartmentModel.class);
		return response.getBody();
	}

	// Get a department by name
	public DepartmentModel getDepartmentByName(String name) {
		String url = adminServiceUrl + "/departments/name/" + name;
		ResponseEntity<DepartmentModel> response = restTemplate.exchange(url, HttpMethod.GET, null,
				DepartmentModel.class);
		return response.getBody();
	}

	// Example methods for interacting with PhysicianService
	public List<PhysicianModel> getAllPhysicians() {
		String url = physicianServiceUrl + "/";
		ResponseEntity<List<PhysicianModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PhysicianModel>>() {
				});
		return response.getBody();
	}

	public PhysicianModel getPhysicianById(int id) {
		String url = physicianServiceUrl + "/" + id;
		ResponseEntity<PhysicianModel> response = restTemplate.exchange(url, HttpMethod.GET, null,
				PhysicianModel.class);
		return response.getBody();
	}

	public List<PhysicianModel> getPhysiciansByName(String name) {
		String url = physicianServiceUrl + "/byName/" + name;
		ResponseEntity<List<PhysicianModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PhysicianModel>>() {
				});
		return response.getBody();
	}

	public List<PhysicianModel> getPhysiciansByDepartment(int departmentIds) {
		String url = physicianServiceUrl + "/byDepartment/" + departmentIds;
		ResponseEntity<List<PhysicianModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PhysicianModel>>() {
				});
		return response.getBody();
	}

	public List<PhysicianModel> getPhysiciansByStatus(Status status) {
		String url = physicianServiceUrl + "/byStatus/" + status;
		ResponseEntity<List<PhysicianModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PhysicianModel>>() {
				});
		return response.getBody();
	}

	public List<PhysicianModel> getPhysiciansByShift(Shift shift) {
		String url = physicianServiceUrl + "/byShift/" + shift;
		ResponseEntity<List<PhysicianModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PhysicianModel>>() {
				});
		return response.getBody();
	}

	public PhysicianModel createPhysician(PhysicianModel physicianModel) {
		String url = physicianServiceUrl;
		ResponseEntity<PhysicianModel> response = restTemplate.postForEntity(url, physicianModel, PhysicianModel.class);
		return response.getBody();
	}

	public PhysicianModel updatePhysician(int id, PhysicianModel physicianModel) {
		String url = physicianServiceUrl + "/" + id;
		ResponseEntity<PhysicianModel> response = restTemplate.exchange(url, HttpMethod.PUT,
				new HttpEntity<>(physicianModel), PhysicianModel.class);
		return response.getBody();
	}

	public void deletePhysician(int id) {
		String url = physicianServiceUrl + "/" + id;
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
	}

//----------------------------------------------------------------------------------------------------------

	// Example methods for interacting with AppointmentService
	public List<AppointmentModel> getAllAppointments() {
		String url = appointmentServiceUrl + "/appointments/";
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	public AppointmentModel getAppointmentById(int id) {
		String url = appointmentServiceUrl + "/appointments/" + id;
		ResponseEntity<AppointmentModel> response = restTemplate.exchange(url, HttpMethod.GET, null,
				AppointmentModel.class);
		return response.getBody();
	}



	public AppointmentModel updateAppointment(int id, AppointmentModel appointmentModel) {
		String url = appointmentServiceUrl + "/appointments/"+id;
		ResponseEntity<AppointmentModel> response = restTemplate.exchange(url, HttpMethod.PUT,
				new HttpEntity<>(appointmentModel), AppointmentModel.class);
		return response.getBody();
	}

	public void deleteAppointment(int id) {
		String url = appointmentServiceUrl + "/appointments/" + id;
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
	}



	public List<AppointmentModel> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
		String url = appointmentServiceUrl + "/appointments/date-range?startDate=" + startDate + "&endDate=" + endDate;
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	public List<AppointmentModel> getAppointmentsByNurse(int nurseId) {
		String url = appointmentServiceUrl + "/appointments/nurse/" + nurseId;
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	public List<AppointmentModel> getAppointmentsByPatient(int patientId) {
		String url = appointmentServiceUrl + "/appointments/patient/" + patientId;
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	public List<LocalDateTime> getAppointmentDatesByPatientId(int patientId) {
		String url = appointmentServiceUrl + "/appointments/patient/" + patientId + "/appointments-dates";
		ResponseEntity<List<LocalDateTime>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LocalDateTime>>() {
				});
		return response.getBody();
	}

	public List<AppointmentModel> getAppointmentsByDate(LocalDate date) {
		String url = appointmentServiceUrl + "/appointments/date/" + date;
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	// Get patients assigned to a specific nurse//from appointment
	public List<PatientModel> getPatientsAssignedToNurse(int nurseId) {
		String url = appointmentServiceUrl + "/nurse/" + nurseId + "/patients";
		ResponseEntity<List<PatientModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PatientModel>>() {
				});
		return response.getBody();
	}

//-------------------------------------------------------------------------------------------------------------------------------

	// Example methods for interacting with NurseService
	public List<NurseModel> getAllNurses() {
		String url = appointmentServiceUrl + "/nurses/";
		ResponseEntity<List<NurseModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<NurseModel>>() {
				});
		return response.getBody();
	}

	public NurseModel getNurseById(int id) {
		String url = appointmentServiceUrl + "/nurses/" + id;
		ResponseEntity<NurseModel> response = restTemplate.exchange(url, HttpMethod.GET, null, NurseModel.class);
		return response.getBody();
	}

	public NurseModel createNurse(NurseModel nurseModel) {
		String url = appointmentServiceUrl + "/nurses/";
		ResponseEntity<NurseModel> response = restTemplate.postForEntity(url, nurseModel, NurseModel.class);
		return response.getBody();
	}

	public NurseModel updateNurse(int id, NurseModel nurseModel) {
		String url = appointmentServiceUrl + "/nurses/" + id;
		ResponseEntity<NurseModel> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(nurseModel),
				NurseModel.class);
		return response.getBody();
	}

	public void deleteNurse(int id) {
		String url = appointmentServiceUrl + "/nurses/" + id;
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
	}

	public NurseModel getNurseByName(String name) {
		String url = appointmentServiceUrl + "/nurses/name/" + name;
		ResponseEntity<NurseModel> response = restTemplate.exchange(url, HttpMethod.GET, null, NurseModel.class);
		return response.getBody();
	}

	public List<NurseModel> getNursesByShift(String shiftType) {
		String url = appointmentServiceUrl + "/nurses/by-shift?shiftType=" + shiftType;
		ResponseEntity<List<NurseModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<NurseModel>>() {
				});
		return response.getBody();
	}

	public List<NurseModel> getAvailableNurses() {
		String url = appointmentServiceUrl + "/nurses/available";
		ResponseEntity<List<NurseModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<NurseModel>>() {
				});
		return response.getBody();
	}
//----------------------Patient----------------------------------------------------------------------


	

	    // Get all patients
	    public List<PatientModel> getAllPatients() {
	        ResponseEntity<List<PatientModel>> response = restTemplate.exchange(appointmentServiceUrl + "/patients", HttpMethod.GET, null, new ParameterizedTypeReference<List<PatientModel>>() {});
	        return response.getBody();
	    }

	    // Get a patient by ID
	    public PatientModel getPatientById(int id) {
	    	ResponseEntity<PatientModel>  response = restTemplate.exchange(appointmentServiceUrl + "/patients/" + id, HttpMethod.GET, null, PatientModel.class);
	        return response.getBody();
	    }

	    // Update a patient
	    public PatientModel updatePatient(int id, PatientModel patientModel) {
	        ResponseEntity<PatientModel> response = restTemplate.exchange(appointmentServiceUrl + "/patients/" + id, HttpMethod.PUT, null, PatientModel.class);
	        return response.getBody();
	    }

	    // Delete a patient
	    public String deletePatient(int id) {
	        ResponseEntity<String> response = restTemplate.exchange(appointmentServiceUrl + "/patients/" + id, HttpMethod.DELETE, null, String.class);
	        return response.getBody();
	    }

	    // Create a patient
	    public PatientModel createPatient(PatientModel patientModel) {
	        ResponseEntity<PatientModel> response = restTemplate.postForEntity(appointmentServiceUrl + "/patients", patientModel, PatientModel.class);
	        return response.getBody();
	    }
	    
	    // Search patients by name
	    public List<PatientModel> searchPatientsByName(String name) {
	        ResponseEntity<List<PatientModel>> response = restTemplate.exchange(appointmentServiceUrl + "/patients/search?name=" + name, HttpMethod.GET, null, new ParameterizedTypeReference<List<PatientModel>>() {});
	        return response.getBody();
	    }
	    
	 // Get total count of physicians
	    public int getTotalPhysicians(){
	        ResponseEntity<Integer> response = restTemplate.getForEntity(physicianServiceUrl + "/count", Integer.class);
	      
	        return response.getBody();
	    }

	    // Get total count of nurses
	    public int getTotalNurses() {
	        ResponseEntity<Integer> response = restTemplate.getForEntity(appointmentServiceUrl + "/count", Integer.class);
	        return response.getBody();
	    }

	    // Get total count of appointments for today
	    public long getTotalAppointmentsForToday() {
	        ResponseEntity<Long> response = restTemplate.getForEntity(appointmentServiceUrl + "/today/count", Long.class);	       
	        return response.getBody();
	    }



	// Create Appointment
	public AppointmentModel createAppointment(AppointmentModel appointmentModel) {
		String url = adminServiceUrl + "/create";
		ResponseEntity<AppointmentModel> response = restTemplate.exchange(url, HttpMethod.POST,
				new HttpEntity<>(appointmentModel), AppointmentModel.class);
		return response.getBody();
	}

	// Get Patients for Physician
	public List<PatientModel> getPatientsForPhysician(int physicianId) {
		String url = appointmentServiceUrl + "/patients/" + physicianId;
		ResponseEntity<List<PatientModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PatientModel>>() {
				});
		return response.getBody();
	}

	// Get Appointments for Physician
	public List<AppointmentModel> getAppointmentsForPhysician(int physicianId) {
		String url = adminServiceUrl + "users" + "/appointments/" + physicianId + "physician";
		ResponseEntity<List<AppointmentModel>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AppointmentModel>>() {
				});
		return response.getBody();
	}

	// Get Physician for Appointment
	public PhysicianModel getPhysicianForAppointment(int appointmentId) {
		String url = appointmentServiceUrl + "/appointments/" + appointmentId + "/physician";
		ResponseEntity<PhysicianModel> response = restTemplate.exchange(url, HttpMethod.GET, null,
				PhysicianModel.class);
		return response.getBody();
	}

}
