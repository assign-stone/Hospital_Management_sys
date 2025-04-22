package com.cg.hms.phy.ctrl;

import com.cg.hms.phy.entity.Shift;
import com.cg.hms.phy.entity.Status;
import com.cg.hms.phy.exception.BadRequestException;
import com.cg.hms.phy.model.PhysicianModel;
import com.cg.hms.phy.repo.PhysicianRepo;
import com.cg.hms.phy.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/physician")
public class PhysicianController {

    @Autowired
    private PhysicianService physicianService;

    @Autowired
    private PhysicianRepo physicianRepository;

    @PostMapping("/")
    public ResponseEntity<PhysicianModel> createPhysician(@RequestBody PhysicianModel physicianModel) throws BadRequestException {
        if (physicianRepository.existsById(physicianModel.getId())) {
            throw new BadRequestException("Physician with this ID already exists.");
        }
        PhysicianModel createdPhysician = physicianService.createPhysician(physicianModel);
        return new ResponseEntity<>(createdPhysician, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PhysicianModel>> getAllPhysicians() throws BadRequestException {
        List<PhysicianModel> physicians = physicianService.getAllPhysicians();
        if (physicians.isEmpty()) {
            throw new BadRequestException("No physicians found.");
        }
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicianModel> getPhysicianById(@PathVariable int id) throws BadRequestException {
        if (!physicianRepository.existsById(id)) {
            throw new BadRequestException("Physician not found.");
        }
        PhysicianModel physician = physicianService.getPhysicianById(id);
        return new ResponseEntity<>(physician, HttpStatus.OK);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<PhysicianModel>> getPhysiciansByName(@PathVariable String name) throws BadRequestException {
        List<PhysicianModel> physicians = physicianService.getPhysiciansByName(name);
        if (physicians.isEmpty()) {
            throw new BadRequestException("No physicians found with name " + name);
        }
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @GetMapping("/byDepartment/{departmentIds}")
    public ResponseEntity<List<PhysicianModel>> getPhysiciansByDepartment(@PathVariable int departmentIds) throws BadRequestException {
        List<PhysicianModel> physicians = physicianService.getPhysiciansByDepartment(departmentIds);
        if (physicians.isEmpty()) {
            throw new BadRequestException("No physicians found for department ID " + departmentIds);
        }
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @GetMapping("/byStatus/{status}")
    public ResponseEntity<List<PhysicianModel>> getPhysiciansByStatus(@PathVariable Status status) throws BadRequestException {
        List<PhysicianModel> physicians = physicianService.getPhysiciansByStatus(status);
        if (physicians.isEmpty()) {
            throw new BadRequestException("No physicians found with status " + status);
        }
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @GetMapping("/byShift/{shift}")
    public ResponseEntity<List<PhysicianModel>> getPhysiciansByShift(@PathVariable Shift shift) throws BadRequestException {
        List<PhysicianModel> physicians = physicianService.getPhysiciansByShift(shift);
        if (physicians.isEmpty()) {
            throw new BadRequestException("No physicians found for shift " + shift);
        }
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhysicianModel> updatePhysician(@PathVariable int id, @RequestBody PhysicianModel physicianModel) throws BadRequestException {
        if (!physicianRepository.existsById(id)) {
            throw new BadRequestException("Physician not found.");
        }
        PhysicianModel updatedPhysician = physicianService.updatePhysician(id, physicianModel);
        return ResponseEntity.ok(updatedPhysician);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhysician(@PathVariable int id) throws BadRequestException {
        if (!physicianRepository.existsById(id)) {
            throw new BadRequestException("Physician not found.");
        }
        physicianService.deletePhysician(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}