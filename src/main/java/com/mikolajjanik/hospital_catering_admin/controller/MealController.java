package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.MealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewMealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateMealDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.service.MealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/meals")
public class MealController {
    private final MealService mealService;
    private static final String BREAKFAST = "śniadanie";
    private static final String LUNCH = "obiad";
    private static final String SUPPER = "kolacja";

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("")
    public Page<MealDTO> getAll(@RequestParam("dietId") Long dietId,
                                @RequestParam("type") String type,
                                Pageable pageable) {
        return mealService.findAll(dietId, type, pageable);
    }

    @GetMapping("/breakfasts")
    public List<Meal> getBreakfastsByDietId(@RequestParam("dietId") Long dietId) {
        return mealService.findMealsByDietIdAndType(dietId, BREAKFAST);
    }

    @GetMapping("/lunches")
    public List<Meal> getLunchesByDietId(@RequestParam("dietId") Long dietId) {
        return mealService.findMealsByDietIdAndType(dietId, LUNCH);
    }

    @GetMapping("/suppers")
    public List<Meal> getSuppersByDietId(@RequestParam("dietId") Long dietId) {
        return mealService.findMealsByDietIdAndType(dietId, SUPPER);
    }

    @GetMapping("/search")
    public Page<MealDTO> getMealsByName(@RequestParam("name") String name, Pageable pageable) {
        return mealService.findByNameContaining(name, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> getMealById(@PathVariable("id") Long id) {
        MealDTO meal = mealService.findMealById(id);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<MealDTO> newMeal(@Valid @ModelAttribute NewMealDTO newMealDTO,
                                           @RequestPart("picture") MultipartFile picture) {
        MealDTO meal = mealService.addMeal(newMealDTO, picture);
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<MealDTO> updateMeal(@Valid @ModelAttribute UpdateMealDTO mealDTO,
                                              @RequestPart("picture") MultipartFile picture) {
        MealDTO meal = mealService.updateMeal(mealDTO, picture);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMealById(@PathVariable ("id") Long id) {
        mealService.deleteMealById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
