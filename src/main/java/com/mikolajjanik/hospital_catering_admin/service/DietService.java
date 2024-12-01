package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.entity.Diet;

import java.util.Set;

public interface DietService {
    Set<Diet> findAll();
    Set<Diet> findAllCurrentDiets();
    Diet findDietById(Long id, String diary);
    Set<Diet> findDietsByName(String name);
    Set<Diet> findDietsByWardId(Long id);
}
