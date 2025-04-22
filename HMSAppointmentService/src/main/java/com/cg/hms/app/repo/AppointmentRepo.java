package com.cg.hms.app.repo;

import com.cg.hms.app.entity.AppointmentEntity;
import com.cg.hms.app.entity.PatientEntity;
import com.cg.hms.app.model.AppointmentStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity, Integer> {

	@Query("SELECT a FROM AppointmentEntity a WHERE a.nurse.id = :nurseId")
	List<AppointmentEntity> findByNurseId(int nurseId);

	@Query("SELECT a FROM AppointmentEntity a WHERE a.patient.id = :patientId")
	List<AppointmentEntity> findByPatientId(int patientId);

	@Query("SELECT a FROM AppointmentEntity a WHERE a.date BETWEEN :startDate AND :endDate")
	List<AppointmentEntity> findAppointmentsByDateRange(LocalDate startDate, LocalDate endDate);

	// Get a list of appointment dates by patient ID
	@Query("SELECT a.date FROM AppointmentEntity a WHERE a.patient.id = :patientId")
	List<LocalDateTime> findAppointmentDatesByPatientId(int patientId);

	// Get a set of patients checked by a specific physician
	@Query("SELECT DISTINCT a.patient FROM AppointmentEntity a WHERE a.physician_id = :physicianId")
	List<PatientEntity> findPatientsCheckedByPhysician(int physicianId);

	// Get a set of patients checked by a specific physician on a given date
	@Query("SELECT DISTINCT a.patient FROM AppointmentEntity a WHERE a.physician_id = :physicianId AND a.date = :date")
	List<PatientEntity> findPatientsCheckedByPhysicianOnDate(int physicianId, LocalDate date);

	// Get a specific patient by patient ID and physician ID
	@Query("SELECT a.patient FROM AppointmentEntity a WHERE a.physician_id = :physicianId AND a.patient.id = :patientId")
	PatientEntity findPatientByPatientIdAndPhysicianId(int physicianId, int patientId);

	// Get a set of patients attended by a specific nurse
	@Query("SELECT DISTINCT a.patient FROM AppointmentEntity a WHERE a.nurse.id = :nurseId")
	List<PatientEntity> findPatientsAttendedByNurse(int nurseId);

	// Get a specific patient by patient ID and nurse ID
	@Query("SELECT a.patient FROM AppointmentEntity a WHERE a.nurse.id = :nurseId AND a.patient.id = :patientId")
	PatientEntity findPatientByPatientIdAndNurseId(int nurseId, int patientId);

    List<AppointmentEntity> findByDate(LocalDate date);
    
    @Query("SELECT a.patient FROM AppointmentEntity a WHERE a.nurse.id = :nurseId")
    List<PatientEntity> findPatientsAssignedToNurse(int nurseId);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.appStatus IN (:statuses) AND a.date = :currentDate")
    List<AppointmentEntity> findAppointmentsByStatusAndDate(@Param("statuses") List<AppointmentStatus> statuses, @Param("currentDate") LocalDate currentDate);

}
