package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientByLogin(String login);
    Patient findPatientById(Long id);
    @Query(value = "SELECT * FROM pacjent WHERE pacjent.oddziałid = :wardId ORDER BY data_przyjęcia DESC", nativeQuery = true)
    List<Patient> getPatientsByWardId(Long wardId);
    @Query(value = "SELECT pacjent.* FROM pacjent JOIN dieta ON pacjent.dietaid = dieta.id WHERE pacjent.oddziałid = :wardId ORDER BY dieta.nazwa", nativeQuery = true)
    List<Patient> getPatientsByWardIdOrderByDiet(Long wardId);
    @Query(value = "SELECT * FROM pacjent WHERE pacjent.oddziałid = :wardId ORDER BY nazwisko, imię", nativeQuery = true)
    List<Patient> getPatientsByWardIdOrderByName(Long wardId);
    @Query(value = "SELECT Pacjent.*\n" +
            "FROM Pacjent\n" +
            "JOIN Oddział ON Pacjent.OddziałId = Oddział.Id\n" +
            "JOIN Szpital ON Oddział.SzpitalId = Szpital.Id\n" +
            "WHERE Szpital.Id = :id", nativeQuery = true)
    List<Patient> getPatientsByHospitalId(Long id);
}
