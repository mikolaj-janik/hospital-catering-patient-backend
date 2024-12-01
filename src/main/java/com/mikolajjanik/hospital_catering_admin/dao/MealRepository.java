package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findMealById(Long id);
    Page<Meal> findMealsByDietId(Long id, Pageable pageable);
    Page<Meal> findMealsByTypeIgnoreCase(String type, Pageable pageable);
    Page<Meal> findMealsByDietIdAndTypeIgnoreCase(Long dietId, String type, Pageable pageable);
    Set<Meal> findMealsByDietId(Long id);
    @Query(value = "SELECT * FROM posiłek WHERE dietaid = :id AND rodzaj = :type AND cena = 0.00", nativeQuery = true)
    List<Meal> findMealsByDietIdAndType(@Param("id") Long id, @Param("type") String type);
    Page<Meal> findMealsByDietIdAndNameContainingIgnoreCase(Long id, String name, Pageable pageable);
    Page<Meal> findMealsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTypeContainingIgnoreCase(String name, String description, String type, Pageable pageable);
    @Query(value = "SELECT obraz FROM posiłek WHERE id = :id", nativeQuery = true)
    byte[] findPictureById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posiłek SET obraz = :picture WHERE id = :id", nativeQuery = true)
    void updatePictureByMealId(@Param("id") Long id, @Param("picture") byte[] picture);

}
