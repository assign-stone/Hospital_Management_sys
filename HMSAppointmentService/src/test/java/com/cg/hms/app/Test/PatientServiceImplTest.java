package com.cg.hms.app.Test;

import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.exception.BadRequestException;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.AppointmentRepo;
import com.cg.hms.app.repo.NurseRepo;
import com.cg.hms.app.repo.PatientRepo;
import com.cg.hms.app.service.PatientServiceImpl;
import com.cg.hms.app.util.AppUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @Mock
    private PatientRepo patientRepo;

    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private NurseRepo nurseRepo;

    @Mock
    private AppUtil appUtil;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientEntity patientEntity;
    private PatientModel patientModel;

    @BeforeEach
    void setUp() {
        patientEntity = new PatientEntity();
        patientEntity.setId(1);
        patientEntity.setName("nikita landge");

        patientModel = new PatientModel();
        patientModel.setId(1);
        patientModel.setName("nikita landge");
    }

    @Test
    void testCreatePatient() throws BadRequestException {
        when(appUtil.convertPatientModelToEntity(patientModel)).thenReturn(patientEntity);
        when(patientRepo.save(patientEntity)).thenReturn(patientEntity);
        when(appUtil.convertPatientEntityToModel(patientEntity)).thenReturn(patientModel);

        PatientModel result = patientService.createPatient(patientModel);

        assertNotNull(result);
        assertEquals(patientModel.getName(), result.getName());
    }

    @Test
    void testGetAllPatients() throws BadRequestException {
        List<PatientEntity> patientEntities = Arrays.asList(patientEntity);
        when(patientRepo.findAll()).thenReturn(patientEntities);
        when(appUtil.convertPatientEntityListToModelList(patientEntities)).thenReturn(Arrays.asList(patientModel));

        List<PatientModel> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetPatientById() throws BadRequestException {
        when(patientRepo.findById(1)).thenReturn(Optional.of(patientEntity));
        when(appUtil.convertPatientEntityToModel(patientEntity)).thenReturn(patientModel);

        PatientModel result = patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals(patientModel.getName(), result.getName());
    }

    @Test
    void testUpdatePatient() throws BadRequestException {
        when(patientRepo.existsById(1)).thenReturn(true);
        when(appUtil.convertPatientModelToEntity(patientModel)).thenReturn(patientEntity);
        when(patientRepo.save(patientEntity)).thenReturn(patientEntity);
        when(appUtil.convertPatientEntityToModel(patientEntity)).thenReturn(patientModel);

        PatientModel result = patientService.updatePatient(1, patientModel);

        assertNotNull(result);
        assertEquals(patientModel.getName(), result.getName());
    }

    @Test
    void testDeletePatient() throws BadRequestException {
        when(patientRepo.existsById(1)).thenReturn(true);

        String result = patientService.deletePatient(1);

        assertEquals("Patient deleted successfully.", result);
    }

    @Test
    void testSearchPatientsByName() throws BadRequestException {
        List<PatientEntity> patientEntities = Arrays.asList(patientEntity);
        when(patientRepo.findByNameContainingIgnoreCase("nikita")).thenReturn(patientEntities);
        when(appUtil.convertPatientEntityListToModelList(patientEntities)).thenReturn(Arrays.asList(patientModel));

        List<PatientModel> result = patientService.searchPatientsByName("nikita");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}