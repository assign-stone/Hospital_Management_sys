package com.cg.hms.adm.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hms.adm.exception.BadRequestException;
import com.cg.hms.adm.model.DepartmentModel;
import com.cg.hms.adm.service.DepartmentService;

@RestController
@RequestMapping("/admin/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// --http://localhost:8087/admin/departments/
	@PostMapping("/")
	public DepartmentModel createDepartment(@RequestBody DepartmentModel departmentModel) throws BadRequestException {
		return departmentService.createDepartment(departmentModel);
	}

	@PutMapping("/{id}")
	public DepartmentModel updateDepartment(@PathVariable int id, @RequestBody DepartmentModel departmentModel)
			throws BadRequestException {
		return departmentService.updateDepartment(id, departmentModel);
	}

	@DeleteMapping("/{id}")
	public void deleteDepartment(@PathVariable int id) throws BadRequestException {
		departmentService.deleteDepartment(id);
	}

	@GetMapping("/getall")
	public List<DepartmentModel> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@GetMapping("/{id}")
	public DepartmentModel getDepartmentById(@PathVariable int id) throws BadRequestException {
		return departmentService.getDepartmentById(id);
	}

	@GetMapping("/name/{name}")
	public DepartmentModel getDepartmentByName(@PathVariable String name) throws BadRequestException {
		return departmentService.getDepartmentByName(name);
	}

}
