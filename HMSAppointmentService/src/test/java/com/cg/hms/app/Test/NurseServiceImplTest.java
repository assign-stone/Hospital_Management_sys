package com.cg.hms.app.Test;

import com.cg.hms.app.entity.NurseEntity;

import com.cg.hms.app.model.NurseModel;
import com.cg.hms.app.model.NurseShift;
import com.cg.hms.app.model.NurseStatus;
import com.cg.hms.app.repo.NurseRepo;
import com.cg.hms.app.service.NurseServiceImpl;
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
public class NurseServiceImplTest {

	@Mock
	private NurseRepo nurseRepository;

	@Mock
	private AppUtil appUtil;

	@InjectMocks
	private NurseServiceImpl nurseService;

	private NurseEntity nurseEntity;
	private NurseModel nurseModel;

	@BeforeEach
	void setUp() {
		nurseEntity = new NurseEntity();
		nurseEntity.setId(1);
		nurseEntity.setName("nikita");
		nurseEntity.setNurseShift(NurseShift.Morning);
		nurseEntity.setNurseStatus(NurseStatus.Available);

		nurseModel = new NurseModel();
		nurseModel.setId(1);
		nurseModel.setName("nikita");
		nurseModel.setNurseShift(NurseShift.Morning);
		nurseModel.setNurseStatus(NurseStatus.Available);
	}

	@Test
	void testCreateNurse() {
		when(appUtil.convertNurseModelToEntity(nurseModel)).thenReturn(nurseEntity);
		when(nurseRepository.save(nurseEntity)).thenReturn(nurseEntity);
		when(appUtil.convertNurseEntityToModel(nurseEntity)).thenReturn(nurseModel);

		NurseModel result = nurseService.createNurse(nurseModel);

		assertNotNull(result);
		assertEquals(nurseModel.getName(), result.getName());
	}

	@Test
	void testGetAllNurses() {
		List<NurseEntity> nurseEntities = Arrays.asList(nurseEntity);
		when(nurseRepository.findAll()).thenReturn(nurseEntities);
		when(appUtil.convertNurseEntityListToModelList(nurseEntities)).thenReturn(Arrays.asList(nurseModel));

		List<NurseModel> result = nurseService.getAllNurses();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testGetNurseById() {
		when(nurseRepository.findById(1)).thenReturn(Optional.of(nurseEntity));
		when(appUtil.convertNurseEntityToModel(nurseEntity)).thenReturn(nurseModel);

		NurseModel result = nurseService.getNurseById(1);

		assertNotNull(result);
		assertEquals(nurseModel.getName(), result.getName());
	}



	@Test
	void testUpdateNurse() {
		when(nurseRepository.existsById(1)).thenReturn(true);
		when(appUtil.convertNurseModelToEntity(nurseModel)).thenReturn(nurseEntity);
		when(nurseRepository.save(nurseEntity)).thenReturn(nurseEntity);
		when(appUtil.convertNurseEntityToModel(nurseEntity)).thenReturn(nurseModel);

		NurseModel result = nurseService.updateNurse(1, nurseModel);

		assertNotNull(result);
		assertEquals(nurseModel.getName(), result.getName());
	}

	@Test
	void testDeleteNurse() {
		when(nurseRepository.existsById(1)).thenReturn(true);

		String result = nurseService.deleteNurse(1);

		assertEquals("Nurse deleted successfully.", result);
	}

	@Test
	void testGetAvailableNurses() {
		List<NurseEntity> nurseEntities = Arrays.asList(nurseEntity);
		when(nurseRepository.findByNurseStatus(NurseStatus.Available)).thenReturn(nurseEntities);
		when(appUtil.convertNurseEntityListToModelList(nurseEntities)).thenReturn(Arrays.asList(nurseModel));

		List<NurseModel> result = nurseService.getAvailableNurses();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void testGetTotalNurses() {
		when(nurseRepository.countAllNurses()).thenReturn(1);

		int result = nurseService.getTotalNurses();

		assertEquals(1, result);
	}
}
