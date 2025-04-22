package com.cg.hms.app.Test;

	import com.cg.hms.app.entity.AppointmentEntity;
	import com.cg.hms.app.entity.PatientEntity;
	import com.cg.hms.app.exception.BadRequestException;
	import com.cg.hms.app.model.AppointmentModel;
import com.cg.hms.app.model.PatientModel;
import com.cg.hms.app.repo.AppointmentRepo;
	import com.cg.hms.app.repo.PatientRepo;
import com.cg.hms.app.service.AppointmentServiceImpl;
import com.cg.hms.app.util.AppUtil;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.junit.jupiter.MockitoExtension;

	import java.time.LocalDate;
	import java.time.LocalDateTime;
	import java.time.LocalTime;
	import java.util.Arrays;
	import java.util.List;
	import java.util.Optional;

	import static org.junit.jupiter.api.Assertions.*;
	import static org.mockito.Mockito.*;

	@ExtendWith(MockitoExtension.class)
	public class AppointmentServiceImplTest {

	    @Mock
	    private AppointmentRepo appointmentRepo;

	    @Mock
	    private PatientRepo patientRepo;

	    @Mock
	    private AppUtil appUtil;

	    @InjectMocks
	    private AppointmentServiceImpl appointmentService;

	    private AppointmentEntity appointmentEntity;
	    private AppointmentModel appointmentModel;
	    private PatientEntity patientEntity;

	    @BeforeEach
	    void setUp() {
	        patientEntity = new PatientEntity();
	        patientEntity.setId(1);
	        patientEntity.setName("nikita landge");

	        appointmentEntity = new AppointmentEntity();
	        appointmentEntity.setId(1);
	        appointmentEntity.setDate(LocalDate.now());
	        appointmentEntity.setTime(LocalTime.now());
	        appointmentEntity.setPatient(patientEntity);

	        appointmentModel = new AppointmentModel();
	        appointmentModel.setId(1);
	        appointmentModel.setDate(LocalDate.now());
	        appointmentModel.setTime(LocalTime.now());
	        appointmentModel.setPatient_id(1);
	    }

	    @Test
	    void testGetAllAppointments() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findAll()).thenReturn(appointmentEntities);
	        when(appUtil.convertAppointmentEntityListToModelList(appointmentEntities)).thenReturn(Arrays.asList(appointmentModel));

	        List<AppointmentModel> result = appointmentService.getAllAppointments();

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testGetAppointmentById() throws BadRequestException {
	        when(appointmentRepo.findById(1)).thenReturn(Optional.of(appointmentEntity));
	        when(appUtil.convertAppointmentEntityToModel(appointmentEntity)).thenReturn(appointmentModel);

	        AppointmentModel result = appointmentService.getAppointmentById(1);

	        assertNotNull(result);
	        assertEquals(appointmentModel.getId(), result.getId());
	    }

	    @Test
	    void testCreateAppointment() throws BadRequestException {
	        when(appointmentRepo.existsById(appointmentModel.getId())).thenReturn(false);
	        when(appUtil.convertAppointmentModelToEntity(appointmentModel)).thenReturn(appointmentEntity);
	        when(patientRepo.findById(1)).thenReturn(Optional.of(patientEntity));
	        when(appointmentRepo.save(appointmentEntity)).thenReturn(appointmentEntity);
	        when(appUtil.convertAppointmentEntityToModel(appointmentEntity)).thenReturn(appointmentModel);

	        AppointmentModel result = appointmentService.createAppointment(appointmentModel);

	        assertNotNull(result);
	        assertEquals(appointmentModel.getId(), result.getId());
	    }

	    @Test
	    void testGetAppointmentsByDateRange() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findAppointmentsByDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(appointmentEntities);
	        when(appUtil.convertAppointmentEntityListToModelList(appointmentEntities)).thenReturn(Arrays.asList(appointmentModel));

	        List<AppointmentModel> result = appointmentService.getAppointmentsByDateRange(LocalDate.now(), LocalDate.now().plusDays(1));

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testGetAppointmentsByNurse() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findByNurseId(1)).thenReturn(appointmentEntities);
	        when(appUtil.convertAppointmentEntityListToModelList(appointmentEntities)).thenReturn(Arrays.asList(appointmentModel));

	        List<AppointmentModel> result = appointmentService.getAppointmentsByNurse(1);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testGetAppointmentsByPatient() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findByPatientId(1)).thenReturn(appointmentEntities);
	        when(appUtil.convertAppointmentEntityListToModelList(appointmentEntities)).thenReturn(Arrays.asList(appointmentModel));

	        List<AppointmentModel> result = appointmentService.getAppointmentsByPatient(1);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }


	    @Test
	    void testDeleteAppointment() throws BadRequestException {
	        when(appointmentRepo.findById(1)).thenReturn(Optional.of(appointmentEntity));

	        appointmentService.deleteAppointment(1);

	        verify(appointmentRepo, times(1)).findById(1);
	        verify(appointmentRepo, times(1)).delete(appointmentEntity);
	    }

	    @Test
	    void testGetAppointmentsByDate() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findByDate(any(LocalDate.class))).thenReturn(appointmentEntities);
	        when(appUtil.convertAppointmentEntityListToModelList(appointmentEntities)).thenReturn(Arrays.asList(appointmentModel));

	        List<AppointmentModel> result = appointmentService.getAppointmentsByDate(LocalDate.now());

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testGetAppointmentDatesByPatientId() throws BadRequestException {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findByPatientId(1)).thenReturn(appointmentEntities);

	        List<LocalDateTime> result = appointmentService.getAppointmentDatesByPatientId(1);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testGetPatientsAssignedToNurse() {
	        List<PatientEntity> patientEntities = Arrays.asList(patientEntity);
	        when(appointmentRepo.findPatientsAssignedToNurse(1)).thenReturn(patientEntities);
	        when(appUtil.convertPatientEntityListToModelList(patientEntities)).thenReturn(Arrays.asList(new PatientModel()));

	        List<PatientModel> result = appointmentService.getPatientsAssignedToNurse(1);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        verify(appointmentRepo, times(1)).findPatientsAssignedToNurse(1);
	    }

	    @Test
	    void testGetTotalAppointmentsForToday() {
	        List<AppointmentEntity> appointmentEntities = Arrays.asList(appointmentEntity);
	        when(appointmentRepo.findAppointmentsByStatusAndDate(anyList(), any(LocalDate.class))).thenReturn(appointmentEntities);

	        long result = appointmentService.getTotalAppointmentsForToday();

	        assertEquals(1, result);
	    }
	}

