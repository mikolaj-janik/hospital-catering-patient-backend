package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.MealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewMealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateMealDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MealService {
    List<Meal> findMealsByDietIdAndType(Long dietId, String type);
    Page<MealDTO> findAll(Long dietId, String type, Pageable pageable);
    Page<MealDTO> findMealsByDietId(Long id, Pageable pageable);
    Page<Meal> findMealsByDietIdAndName(Long id, String name, Pageable pageable);
    MealDTO addMeal(NewMealDTO newMealDTO, MultipartFile picture);
    MealDTO findMealById(Long id);
    MealDTO updateMeal(UpdateMealDTO mealDTO, MultipartFile picture);
    Page<MealDTO> findByNameContaining(String name, Pageable pageable);
    void deleteMealById(Long id);
}
