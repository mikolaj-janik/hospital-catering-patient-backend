package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Admin;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface DieticianRepository extends JpaRepository<Dietician, Long> {
    Dietician findDieticianById(Long id);
    Set<Dietician> findDieticiansByHospitalId(Long id);
    @Query(value = "SELECT * FROM dietetyk ORDER BY dietetyk.szpitalid", nativeQuery = true)
    List<Dietician> findAllDieticians();
    @Query(value = "SELECT * FROM dietetyk WHERE dietetyk.szpitalid = :id ORDER BY dietetyk.szpitalid", nativeQuery = true)
    List<Dietician> findDieticians(Long id);
    @Query(value = "SELECT zdjęcie FROM dietetyk WHERE id = :id", nativeQuery = true)
    byte[] findPictureById(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE dietetyk SET zdjęcie = :picture WHERE id = :id", nativeQuery = true)
    void uploadPictureById(@Param("id") Long id, @Param("picture") byte[] picture);



    Dietician findDieticianByEmail(String email);
    @Query(value = "SELECT zdjęcie FROM dietetyk WHERE email = :email", nativeQuery = true)
    byte[] findProfilePictureByEmail(@Param("email") String email);
}
