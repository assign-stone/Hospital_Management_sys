package com.cg.hms.app.controller;

import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.NurseModel;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/app/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    // Create a new nurse
    @PostMapping("/")
    public ResponseEntity<NurseModel> createNurse(@RequestBody NurseModel nurseModel) throws BadRequestException {
        if (nurseModel.getId() != 0) {
            throw new BadRequestException("ID should not be provided. It is autogenerated.");
        }
        NurseModel createdNurse = nurseService.createNurse(nurseModel);
        return new ResponseEntity<>(createdNurse, HttpStatus.CREATED);
    }

    // Get all nurses
    @GetMapping("/")
    public ResponseEntity<List<NurseModel>> getAllNurses() throws BadRequestException {
        List<NurseModel> nurses = nurseService.getAllNurses();
        if (nurses.isEmpty()) {
            throw new BadRequestException("No nurses found.");
        }
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    // Get a nurse by ID
    @GetMapping("/{id}")
    public ResponseEntity<NurseModel> getNurseById(@PathVariable int id) throws BadRequestException {
        NurseModel nurse = nurseService.getNurseById(id);
        if (nurse != null) {
            return new ResponseEntity<>(nurse, HttpStatus.OK);
        } else {
            throw new BadRequestException("Nurse with ID " + id + " not found.");
        }
    }

    // Get nurse by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<NurseModel>> getNurseByName(@PathVariable String name) throws BadRequestException {
        List<NurseModel> nurse = nurseService.getNurseByName(name);
        if (nurse == null) {
            throw new BadRequestException("Nurse with name " + name + " not found.");
        } else {
            return new ResponseEntity<List<NurseModel>>(nurse, HttpStatus.OK);

        }
    }
 

    // Update a nurse
    @PutMapping("/{id}")
    public ResponseEntity<NurseModel> updateNurse(@PathVariable int id, @RequestBody NurseModel nurseModel) throws BadRequestException {
        NurseModel updatedNurse = nurseService.updateNurse(id, nurseModel);
        if (updatedNurse != null) {
            return new ResponseEntity<>(updatedNurse, HttpStatus.OK);
        } else {
            throw new BadRequestException("Nurse with ID " + id + " not found.");
        }
    }

    // Delete a nurse
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNurse(@PathVariable int id) throws BadRequestException {
        String response = nurseService.deleteNurse(id);
        if (response.equals("Nurse deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new BadRequestException("Nurse with ID " + id + " not found.");
        }
    }

    // Get nurses by shift type
    @GetMapping("/by-shift")
    public ResponseEntity<List<NurseModel>> getNursesByShift(@RequestParam("shiftType") String shiftType) throws BadRequestException {
        List<NurseModel> nurses = nurseService.getNursesByShift(shiftType);
        if (nurses != null && !nurses.isEmpty()) {
            return ResponseEntity.ok(nurses);
        } else {
            throw new BadRequestException("No nurses found for shift type " + shiftType + ".");
        }
    }

    // Get available nurses
    @GetMapping("/available")
    public ResponseEntity<List<NurseModel>> getAvailableNurses() throws BadRequestException {
        List<NurseModel> availableNurses = nurseService.getAvailableNurses();
        if (availableNurses != null && !availableNurses.isEmpty()) {
            return ResponseEntity.ok(availableNurses);
        } else {
            throw new BadRequestException("No available nurses found.");
        }
    }
    
    //get Total Nurses count
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalNurses() throws BadRequestException {
        
            int count = nurseService.getTotalNurses();
            if(count ==0) {
            	throw new BadRequestException("No available nurses found.");	
            }
            return ResponseEntity.ok(count);
       
    }
}


