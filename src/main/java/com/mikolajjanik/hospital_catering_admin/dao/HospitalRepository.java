package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Page<Hospital> findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrStreetContainingIgnoreCase(String name, String city, String street, Pageable pageable);
    Hospital findHospitalById(Long id);

    @Query(value = "SELECT DISTINCT szpital.* FROM szpital JOIN dietetyk ON szpital.id=dietetyk.szpitalid WHERE dietetyk.szpitalid > 0", nativeQuery = true)
    List<Hospital> findAllHospitalsWithDieticians();
    @Query(value = "SELECT zdjęcie FROM szpital WHERE id = :id", nativeQuery = true)
    byte[] findPictureById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE szpital SET zdjęcie = :picture WHERE id = :id", nativeQuery = true)
    void updatePictureByHospitalId(@Param("id") Long id, @Param("picture") byte[] picture);
}
