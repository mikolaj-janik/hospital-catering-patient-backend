package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DietRepository;
import com.mikolajjanik.hospital_catering_admin.dao.MealRepository;
import com.mikolajjanik.hospital_catering_admin.dao.WardRepository;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.exception.DietNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.WardNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DietServiceImpl implements DietService {
    private final DietRepository dietRepository;
    private final MealRepository mealRepository;
    private final WardRepository wardRepository;

    @Autowired
    public DietServiceImpl(DietRepository dietRepository, MealRepository mealRepository, WardRepository wardRepository) {
        this.dietRepository = dietRepository;
        this.mealRepository = mealRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public Set<Diet> findAll() {
        return dietRepository.findAllByOrderByName();
    }

    @Override
    public Set<Diet> findAllCurrentDiets() {
        Set<Diet> diets = dietRepository.findAllByOrderByName();
        Set<Diet> dietsToReturn = new HashSet<>();

        for (Diet diet : diets) {
            Set<Meal> meals = mealRepository.findMealsByDietId(diet.getId());
            if (!meals.isEmpty()) {
                dietsToReturn.add(diet);
            }
        }
        return dietsToReturn;
    }

    @Override
    @SneakyThrows
    public Diet findDietById(Long id, String diary) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }

        if (diary.equals("true")) {
            Set<Meal> meals = mealRepository.findMealsByDietId(id);
            if (meals.isEmpty()) {
                throw new DietNotFoundException(id);
            }
        }
        return diet;
    }

    @Override
    public Set<Diet> findDietsByName(String name) {
        return dietRepository.findDietsByNameContainingIgnoreCase(name);
    }

    @Override
    @SneakyThrows
    public Set<Diet> findDietsByWardId(Long id) {
        Ward ward = wardRepository.findWardById(id);

        if (ward == null) {
            throw new WardNotFoundException(id);
        }
        return dietRepository.findDietsByWardId(id);
    }
}
