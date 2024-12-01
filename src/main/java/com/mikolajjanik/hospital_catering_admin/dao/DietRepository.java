package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Set<Diet> findAllByOrderByName();
    Diet findDietByName(String name);
    Set<Diet> findDietsByNameContainingIgnoreCase(String name);
    @Query(value = "SELECT DISTINCT d.id, d.nazwa, d.opis FROM dieta d JOIN pacjent p ON d.id=p.dietaid WHERE p.oddziałid= :id", nativeQuery = true)
    Set<Diet> findDietsByWardId(@Param("id") Long wardId);
    Diet findDietById(Long id);
}
