package com.cg.hms.ui.controller;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cg.hms.ui.model.AppointmentModel;
import com.cg.hms.ui.model.DepartmentModel;
import com.cg.hms.ui.model.LoginModel;
import com.cg.hms.ui.model.NurseModel;
import com.cg.hms.ui.model.PatientModel;
import com.cg.hms.ui.model.PhysicianModel;
import com.cg.hms.ui.model.Role;
import com.cg.hms.ui.model.Shift;
import com.cg.hms.ui.model.Status;
import com.cg.hms.ui.model.UserModel;
import com.cg.hms.ui.service.UIService;

@Controller
@RequestMapping("/adminUI")
public class UIController {
	@Autowired
	private UIService uiService;

	@Autowired
	RestTemplate restTemplate;

	// private final String adminServiceUrl = "http://HMSADMINSERVICE/admin";

	@GetMapping("/index")
    public String index() {
        return "index";
    }
	
	@GetMapping("/register")
	public ModelAndView showRegistrationForm() {
		ModelAndView modelAndView = new ModelAndView("register");
		modelAndView.addObject("user", new UserModel());
		return modelAndView;
	}

	@PostMapping("/register")
	public ModelAndView registerUser(@ModelAttribute("user") UserModel userModel) {
		String response = uiService.registerUser(userModel);
		ModelAndView modelAndView = new ModelAndView("registerSuccess");
		modelAndView.addObject("message", response);
		return modelAndView;
	}

	@GetMapping("/login")
	public ModelAndView showLoginForm(@ModelAttribute("login") LoginModel loginModel) {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("login", new LoginModel());
		return modelAndView;
	}

	@PostMapping("/login")
	public ModelAndView loginUsers(@ModelAttribute("login") LoginModel loginModel) {
		ModelAndView modelAndView = new ModelAndView();

		String email = loginModel.getEmail();
		String pass = loginModel.getPassword();
		UserModel userDB = null;

		try {
			LoginModel response = uiService.loginUser(loginModel);

			userDB = uiService.getUserByEmail(email);
			if (email.equals(userDB.getEmail()) && (pass.equals(userDB.getPassword()))) {
				modelAndView.setViewName("loginSuccess");
				modelAndView.addObject("user", response);
			} else {
				modelAndView.setViewName("login");
				modelAndView.addObject("error", "Error logging in user: incoreect password");
			}

		} catch (Exception e) {
			 modelAndView.setViewName("login");
			modelAndView.addObject("login", loginModel);
			modelAndView.addObject("error", "Error logging in user: " + e.getMessage());			
		}
		return modelAndView;
	}

	// Update a user
	@PutMapping("/update/{id}")
	public ResponseEntity<UserModel> updateUser(@PathVariable int id, @RequestBody UserModel userModel) {
		UserModel response = uiService.updateUser(id, userModel);
		return ResponseEntity.ok(response);
	}

	// Delete a user
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		uiService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	// Get all users
	@GetMapping("/")
	public ResponseEntity<List<UserModel>> getAllUsers() {
		List<UserModel> response = uiService.getAllUsers();
		return ResponseEntity.ok(response);
	}

	// Get a user by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUserById(@PathVariable int id) {
		UserModel response = uiService.getUserById(id);
		return ResponseEntity.ok(response);
	}

	// Get a user by username
	@GetMapping("/username/{username}")
	public ResponseEntity<UserModel> getUserByUsername(@PathVariable String username) {
		UserModel response = uiService.getUserByUsername(username);
		return ResponseEntity.ok(response);
	}

	// Get a user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserModel> getUserByEmail(@PathVariable String email) {
		UserModel response = uiService.getUserByEmail(email);
		return ResponseEntity.ok(response);
	}
//--------------------------------------------------------------------------------------------------

	
	 @GetMapping("/departments")
	    public String getAllDepartments(Model model) {
	        List<DepartmentModel> departments = uiService.getAllDepartments();
	        model.addAttribute("departments", departments);
	        return "departments"; 
	    }

	    // Show a form to create a new department
	    @GetMapping("/departments/new")
	    public String showCreateDepartmentForm(Model model) {
	        model.addAttribute("department", new DepartmentModel()); // Empty object for binding
	        return "createdept"; 
	    }

	    // Handle the creation of a new department (uses the existing POST API)
	    @PostMapping("/departments")
	    public String createDepartment(@ModelAttribute DepartmentModel departmentModel) {
	        uiService.createDepartment(departmentModel);
	        return "redirect:/adminUI/departments"; // Redirect to the list of departments
	    }


	    // Search for department by either ID or Name
	    @GetMapping("/search")
	    public String searchDepartment(@RequestParam(required = false) Integer id, 
	                                   @RequestParam(required = false) String name, 
	                                   Model model) {
	        DepartmentModel department = null;

	        // If the id is provided, search by id
	        if (id != null) {
	            department = uiService.getDepartmentById(id);
	        }
	        // If the name is provided, search by name
	        else if (name != null && !name.trim().isEmpty()) {
	            department = uiService.getDepartmentByName(name);
	        }

	        // If no department is found, display a message
	        if (department == null) {
	            model.addAttribute("message", "No department found with the given criteria.");
	        }

	        model.addAttribute("department", department);
	        return "searchtemplate"; // This will map to the search results page
	    }



	    
//	 // Show the form to update an existing department
//	    @GetMapping("/update/{id}")
//	    public ModelAndView showUpdateDepartmentForm(@PathVariable int id) {
//	        DepartmentModel department = uiService.getDepartmentById(id);
//	        ModelAndView mav = new ModelAndView();
//	        mav.setViewName("edit_dept");
//	        mav.addObject("department", department);
//	        return mav;
//	    }
//
//	    // Handle the update department request
//	    @PostMapping("/update/{id}")
//	    public ModelAndView updateDepartment(@PathVariable int id, @ModelAttribute DepartmentModel departmentModel) {
//	        departmentModel.setId(id); // Ensure the ID is set
//	        uiService.updateDepartment(id, departmentModel); // Update the department
//	        return new ModelAndView("redirect:/adminUI/departments"); // Redirect to department list after update
//	    }
	    
	    
	    
	    
	    @GetMapping("/departments/delete/{id}")
	    public String confirmDeleteDepartment(@PathVariable int id, Model model) {
	        DepartmentModel department = uiService.getDepartmentById(id);
	        model.addAttribute("department", department);
	        return "confirm_delete"; // Confirmation page for delete
	    }

	    // Handle deletion of department
	    @PostMapping("/departments/delete/{id}")
	    public String deleteDepartment(@PathVariable int id) {
	        uiService.deleteDepartment(id);
	        return "redirect:/adminUI/departments"; 
		
	    }
	
	

	// ---------------------------------------------------------------------------------------------------------------------


	
	  @GetMapping("/appointments/")
	    public ModelAndView getAllAppointments() {
	        List<AppointmentModel> appointments = uiService.getAllAppointments();
	        
	        ModelAndView mav = new ModelAndView("allappointments");
	        mav.addObject("appointments", appointments);
	        return mav;
	    }

	    @GetMapping("/appointments/byId/")
	    public ModelAndView getAppointmentById(@RequestParam("id") int id) {
	    	
	        AppointmentModel appointment = uiService.getAppointmentById(id);
	        ModelAndView mav = new ModelAndView("appointmentDetails");
	        mav.addObject("appointment", appointment);
	        return mav;
	    }


	    @GetMapping("/create")
	    public ModelAndView showCreateAppointmentForm() {
	        ModelAndView mav = new ModelAndView("createAppointment");
	        mav.addObject("appointment", new AppointmentModel());
	        return mav;
	    }
	    @PostMapping("/create")
	    public ModelAndView createAppointment(@ModelAttribute AppointmentModel appointmentModel) {
	        uiService.createAppointment(appointmentModel);
	        return new ModelAndView("redirect:/adminUI/appointments/");
	    }
	    @GetMapping("/update/{id}")
	    public ModelAndView showUpdateAppointmentForm(@PathVariable int id) {
	        AppointmentModel appointment = uiService.getAppointmentById(id);
	        ModelAndView mav = new ModelAndView("updateAppointment");
	        mav.addObject("appointment", appointment);
	        return mav;
	    }

	    @PostMapping("/appointments/{id}")
	    public ModelAndView updateAppointment(@PathVariable int id, @ModelAttribute AppointmentModel appointmentModel) {
	        appointmentModel.setId(id); // Ensure the ID is set
	        uiService.updateAppointment(id, appointmentModel);
	        return new ModelAndView("redirect:/adminUI/appointments/");
	    }
	    

	    @PostMapping("/delete/{id}")
	    public String deleteAppointment(@PathVariable("id") int id, Model model) {
	        uiService.deleteAppointment(id);
	        return "redirect:/adminUI/appointments/";
	    }
	    
	    @GetMapping("/appointments/byDate")
	    public ModelAndView searchByDate(@RequestParam("date") String date) {
	        LocalDate localDate = LocalDate.parse(date);
	        List<AppointmentModel> appointments = uiService.getAppointmentsByDate(localDate);
	        ModelAndView mav = new ModelAndView("appointmentsByDate");
	        mav.addObject("appointments", appointments);
	        mav.addObject("date", date);
	        return mav;
	    }
	    
//	    @PostMapping("/appointments/create")
//	    public ModelAndView createAppointment(@RequestBody AppointmentModel appointmentModel) {
//	        AppointmentModel createdAppointment = uiService.createAppointment(appointmentModel);
//	        ModelAndView mav = new ModelAndView("appointment");
//	        mav.addObject("appointment", createdAppointment);
//	        return mav;
//	    }
	    
//    @PutMapping("/appointments/{id}")
//    public ModelAndView updateAppointment(@PathVariable int id, @RequestBody AppointmentModel appointmentModel) {
//        AppointmentModel updatedAppointment = uiService.updateAppointment(appointmentModel);
//        ModelAndView mav = new ModelAndView("appointment");
//        mav.addObject("appointment", updatedAppointment);
//        return mav;
//    }

   

    @GetMapping("/appointments/date-range")
    public ModelAndView getAppointmentsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<AppointmentModel> appointments = uiService.getAppointmentsByDateRange(startDate, endDate);
        ModelAndView mav = new ModelAndView("appointments");
        mav.addObject("appointments", appointments);
        return mav;
    }

    @GetMapping("/appointments/nurse/{nurseId}")
    public ModelAndView getAppointmentsByNurse(@PathVariable int nurseId) {
        List<AppointmentModel> appointments = uiService.getAppointmentsByNurse(nurseId);
        ModelAndView mav = new ModelAndView("appointments");
        mav.addObject("appointments", appointments);
        return mav;
    }

    @GetMapping("/appointments/patient/{patientId}")
    public ModelAndView getAppointmentsByPatient(@PathVariable int patientId) {
        List<AppointmentModel> appointments = uiService.getAppointmentsByPatient(patientId);
        ModelAndView mav = new ModelAndView("appointments");
        mav.addObject("appointments", appointments);
        return mav;
    }

    @GetMapping("/appointments/patient/{patientId}/appointments-dates")
    public ModelAndView getAppointmentDatesByPatientId(@PathVariable int patientId) {
        List<LocalDateTime> appointmentDates = uiService.getAppointmentDatesByPatientId(patientId);
        ModelAndView mav = new ModelAndView("appointmentDates");
        mav.addObject("appointmentDates", appointmentDates);
        return mav;
    }

    @GetMapping("/nurse/{nurseId}/patients")
    public ModelAndView getPatientsAssignedToNurse(@PathVariable int nurseId) {
        List<PatientModel> patients = uiService.getPatientsAssignedToNurse(nurseId);
        ModelAndView mav = new ModelAndView("patients");
        mav.addObject("patients", patients);
        return mav;
    }

	    
//----------------------------------------------------------------------------------------
	// Get all nurses
	
	// -----------------rest controller Apis-----------------------------

	@GetMapping("/physicians/count")
	public ResponseEntity<?> getTotalPhysicians() {
		int count = uiService.getTotalPhysicians();
		return ResponseEntity.ok(count);

	}

	@GetMapping("/nurses/count")
	public ResponseEntity<?> getTotalNurses() {
		int count = uiService.getTotalNurses();
		return ResponseEntity.ok(count);
	}

	@GetMapping("/appointments/today/count")
	public ResponseEntity<?> getTotalAppointmentsForToday() {
		long count = uiService.getTotalAppointmentsForToday();
		return ResponseEntity.ok(count);
	}

	// Get Physician for Appointment
	@GetMapping("/appointments/{appointmentId}/physician")
	public ResponseEntity<PhysicianModel> getPhysicianForAppointment(@PathVariable int appointmentId) {
		try {
			PhysicianModel physician = uiService.getPhysicianForAppointment(appointmentId);
			if (physician == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.status(HttpStatus.OK).body(physician);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Get Appointments for Physician
	@GetMapping("/appointments/phy/{physicianId}")
	public ResponseEntity<List<AppointmentModel>> getAppointmentsForPhysician(@PathVariable int physicianId) {
		try {
			List<AppointmentModel> appointments = uiService.getAppointmentsForPhysician(physicianId);
			if (appointments.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No appointments found
			}
			return ResponseEntity.status(HttpStatus.OK).body(appointments);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Internal server error
		}
	}

	// Get Patients for Physician
	@GetMapping("/patients/{physicianId}")
	public ResponseEntity<List<PatientModel>> getPatientsForPhysician(@PathVariable int physicianId) {
		try {
			List<PatientModel> patients = uiService.getPatientsForPhysician(physicianId);
			if (patients == null || patients.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No patients found
			}
			return ResponseEntity.status(HttpStatus.OK).body(patients);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Internal server error
		}
	}

	

}
