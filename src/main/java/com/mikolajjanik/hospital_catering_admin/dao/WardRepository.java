package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;


public interface WardRepository extends JpaRepository<Ward, Long> {
    Ward findWardById(Long id);
    Set<Ward> findWardsByHospitalIdOrderByName(Long id);
    Set<Ward> findWardsByHospitalIdAndNameContainingIgnoreCase(Long id, String name);

    @Query(value = "SELECT oddział.* FROM oddział JOIN dietetyk_oddział ON \n" +
            "\toddział.id=dietetyk_oddział.oddziałid \n" +
            "\tjoin dietetyk on dietetyk_oddział.dietetykid=dietetyk.id WHERE \n" +
            "\tdietetyk.id=:id AND oddział.nazwa ILIKE '%' || :name || '%'; ", nativeQuery = true)
    List<Ward> findWardsByDieticianIdAndName(Long id, String name);

    @Query(value = "SELECT oddział.* FROM " +
            "oddział JOIN dietetyk_oddział ON oddział.id=dietetyk_oddział.oddziałid " +
            "JOIN dietetyk on dietetyk_oddział.dietetykid=dietetyk.id " +
            "WHERE dietetyk.id=:id ORDER BY oddział.nazwa", nativeQuery = true)
    List<Ward> findWardsByDieticianId(Long id);
}
