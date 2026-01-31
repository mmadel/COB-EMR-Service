package com.cob.emr.repositories.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {

    Page<Patient> findByClinicsIn(Pageable pageable, List<Clinic> clinics);

    List<Patient> findByIdAndClinicsIn(Long patientId, List<Clinic> clinics);

    @Query("SELECT p FROM patient p JOIN p.clinics c WHERE :clinic IN c")
    List<Patient> findByClinic(@Param("clinic") Clinic clinic);

    @Query("SELECT pe FROM patient pe JOIN pe.clinics c  where " +
            "(LOWER(pe.firstName) LIKE CONCAT('%',:name,'%') OR LOWER(pe.middleName) LIKE CONCAT('%',:name,'%')  OR LOWER(pe.lastName) LIKE CONCAT('%',:name,'%')) " +
            "AND :clinic IN c" )
    List<Patient> findByName(@Param("name") String name , @Param("clinic") Clinic clinic);

    @Query(value = "SELECT DISTINCT p.* FROM patient p " +
            "LEFT JOIN patient_case pc ON pc.patient_id = p.id " +
            "JOIN patient_clinic pcl ON pcl.patient_id = p.id " +
            "JOIN clinic cl ON pcl.clinic_id = cl.id " +
            "WHERE (:clinicId IS NULL OR pcl.clinic_id = :clinicId) " +
            "AND (:organizationId IS NULL OR cl.organization_id = :organizationId) " +
            "AND (:firstName IS NULL OR LOWER(p.first_name) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
            "AND (:birthDate IS NULL OR p.birth_date = :birthDate) " +
            "AND (:insuranceCompanyId IS NULL OR JSON_CONTAINS(p.patient_insurances, CONCAT('{\"insuranceCompany\":{\"id\":', :insuranceCompanyId, '}}')) = 1) " +
            "AND (:therapistId IS NULL OR pc.therapist_id = :therapistId) " +
            "AND (:phoneNumber IS NULL OR REPLACE(REPLACE(REPLACE(REPLACE(CAST(p.patient_contacts AS CHAR), '(', ''), ')', ''), '-', ''), ' ', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(REPLACE(:phoneNumber, '(', ''), ')', ''), '-', ''), ' ', ''), '%')) " +
            "AND (:patientStatus IS NULL OR p.patient_status = :patientStatus) " +
            "AND (:emrId IS NULL OR p.emr_id = :emrId)", nativeQuery = true)
    List<Patient> searchPatients(@Param("clinicId") Long clinicId,
                                @Param("organizationId") Long organizationId,
                                @Param("firstName") String firstName,
                                @Param("lastName") String lastName,
                                @Param("birthDate") Long birthDate,
                                @Param("insuranceCompanyId") Long insuranceCompanyId,
                                @Param("therapistId") Long therapistId,
                                @Param("phoneNumber") String phoneNumber,
                                @Param("patientStatus") String patientStatus,
                                @Param("emrId") String emrId);

    @Query(value = "SELECT DISTINCT p.* FROM patient p " +
            "JOIN patient_clinic pcl ON pcl.patient_id = p.id " +
            "WHERE pcl.clinic_id = :clinicId " +
            "AND (LOWER(p.first_name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :name, '%')))", nativeQuery = true)
    List<Patient> searchByName(@Param("clinicId") Long clinicId,
                               @Param("name") String name);
}
