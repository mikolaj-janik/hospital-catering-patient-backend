package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DiaryRepository;
import com.mikolajjanik.hospital_catering_admin.dao.DietRepository;
import com.mikolajjanik.hospital_catering_admin.dao.MealRepository;
import com.mikolajjanik.hospital_catering_admin.dto.MealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewMealDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateMealDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diary;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.exception.*;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jndi.TypeMismatchNamingException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final DietRepository dietRepository;
    private final DiaryRepository diaryRepository;
    private static final String DEFAULT_MEAL_TYPE = "wszystkie";

    public MealServiceImpl(MealRepository mealRepository, DietRepository dietRepository, DiaryRepository diaryRepository) {
        this.mealRepository = mealRepository;
        this.dietRepository = dietRepository;
        this.diaryRepository = diaryRepository;
    }

    @Override
    @SneakyThrows
    public List<Meal> findMealsByDietIdAndType(Long dietId, String type) {
        Diet diet = dietRepository.findDietById(dietId);

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        return switch (type) {
            case "śniadanie", "obiad", "kolacja" -> {
                yield mealRepository.findMealsByDietIdAndType(dietId, type);
            }
            default -> throw new IncorrectTypeException();
        };
    }

    @Override
    @SneakyThrows
    public Page<MealDTO> findAll(Long dietId, String type, Pageable pageable) {
        if (dietId == null || dietId < 0) {
            throw new DietNotFoundException(dietId);
        }

        if (type.isEmpty() || !(type.equalsIgnoreCase("wszystkie")
                || type.equalsIgnoreCase("śniadanie")
                || type.equalsIgnoreCase("obiad")
                || type.equalsIgnoreCase("kolacja"))) {
            throw new InvalidMealTypeException();
        }
        Page<Meal> meals;

        if (dietId == 0 && type.equalsIgnoreCase(DEFAULT_MEAL_TYPE)) {
            meals = mealRepository.findAll(pageable);

        } else if (dietId > 0 && type.equalsIgnoreCase(DEFAULT_MEAL_TYPE)) {
            meals = mealRepository.findMealsByDietId(dietId, pageable);

        } else if (dietId == 0 && !type.equalsIgnoreCase(DEFAULT_MEAL_TYPE)) {
            meals = mealRepository.findMealsByTypeIgnoreCase(type, pageable);

        } else {
            meals = mealRepository.findMealsByDietIdAndTypeIgnoreCase(dietId, type, pageable);

        }
        return handleFindMeals(meals, pageable);
    }

    @Override
    @SneakyThrows
    public Page<MealDTO> findMealsByDietId(Long id, Pageable pageable) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }

        Page<Meal> meals = mealRepository.findMealsByDietId(id, pageable);

        return handleFindMeals(meals, pageable);
    }

    @Override
    @SneakyThrows
    public Page<Meal> findMealsByDietIdAndName(Long id, String name, Pageable pageable) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }

        return mealRepository.findMealsByDietIdAndNameContainingIgnoreCase(id, name, pageable);
    }

    @Override
    @SneakyThrows
    public MealDTO addMeal(NewMealDTO newMealDTO, MultipartFile picture) {
        Meal meal = new Meal();

        HospitalServiceImpl.validateImageFormat(picture);

        Long dietId = Long.parseLong(newMealDTO.getDietId());
        Diet diet = dietRepository.findDietById(dietId);

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        meal.setDiet(diet);
        meal.setName(newMealDTO.getName());
        meal.setDescription(newMealDTO.getDescription());
        meal.setPrice(Double.parseDouble(newMealDTO.getPrice()));
        meal.setType(newMealDTO.getType());
        meal.setCalories(Double.parseDouble(newMealDTO.getCalories()));
        meal.setProtein(Double.parseDouble(newMealDTO.getProtein()));
        meal.setCarbohydrates(Double.parseDouble(newMealDTO.getCarbohydrates()));
        meal.setFats(Double.parseDouble(newMealDTO.getFats()));

        meal = mealRepository.save(meal);

        byte[] pictureByte = picture.getBytes();

        mealRepository.updatePictureByMealId(meal.getId(), pictureByte);

        return new MealDTO(
                meal.getId(),
                meal.getDiet(),
                meal.getName(),
                meal.getDescription(),
                meal.getPrice(),
                meal.getType().toLowerCase(),
                meal.getCalories(),
                meal.getProtein(),
                meal.getCarbohydrates(),
                meal.getFats(),
                Base64.getEncoder().encodeToString(pictureByte)
        );
    }

    @Override
    @SneakyThrows
    public MealDTO findMealById(Long id) {
        Meal meal = mealRepository.findMealById(id);

        if (meal == null) {
            throw new MealNotFoundException(id);
        }

        byte[] mealImage = mealRepository.findPictureById(id);

        return new MealDTO(
                meal.getId(),
                meal.getDiet(),
                meal.getName(),
                meal.getDescription(),
                meal.getPrice(),
                meal.getType(),
                meal.getCalories(),
                meal.getProtein(),
                meal.getCarbohydrates(),
                meal.getFats(),
                Base64.getEncoder().encodeToString(mealImage)
                );
    }

    @Override
    @SneakyThrows
    public MealDTO updateMeal(UpdateMealDTO mealDTO, MultipartFile picture) {
        Long id = Long.parseLong(mealDTO.getId());

        Long dietId = Long.parseLong(mealDTO.getDietId());
        Diet diet = dietRepository.findDietById(dietId);

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        Meal meal = mealRepository.findMealById(id);

        if (meal == null) {
            throw new MealNotFoundException(id);
        }

        byte[] pictureByte = picture.getBytes();

        if (picture.isEmpty()) {
            pictureByte = mealRepository.findPictureById(id);
        }

        meal.setDiet(diet);
        meal.setName(mealDTO.getName());
        meal.setDescription(mealDTO.getDescription());
        meal.setPrice(Double.parseDouble(mealDTO.getPrice()));
        meal.setType(mealDTO.getType());
        meal.setCalories(Double.parseDouble(mealDTO.getCalories()));
        meal.setProtein(Double.parseDouble(mealDTO.getProtein()));
        meal.setCarbohydrates(Double.parseDouble(mealDTO.getCarbohydrates()));
        meal.setFats(Double.parseDouble(mealDTO.getFats()));

        meal = mealRepository.save(meal);
        mealRepository.updatePictureByMealId(meal.getId(), pictureByte);

        return new MealDTO(
                meal.getId(),
                meal.getDiet(),
                meal.getName(),
                meal.getDescription(),
                meal.getPrice(),
                meal.getType().toLowerCase(),
                meal.getCalories(),
                meal.getProtein(),
                meal.getCarbohydrates(),
                meal.getFats(),
                Base64.getEncoder().encodeToString(pictureByte)
        );
    }

    @Override
    public Page<MealDTO> findByNameContaining(String name, Pageable pageable) {
        Page<Meal> meals = mealRepository.findMealsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTypeContainingIgnoreCase(name,
                name, name, pageable);
        return this.handleFindMeals(meals, pageable);
    }

    @Override
    @SneakyThrows
    public void deleteMealById(Long id) {
        Meal meal = mealRepository.findMealById(id);

        if (meal == null) {
            throw new MealNotFoundException(id);
        }

        Set<Diary> diaryBreakfast = diaryRepository.findDiariesByBreakfastId(id);
        Set<Diary> diaryLunch = diaryRepository.findDiariesByLunchId(id);
        Set<Diary> diarySupper = diaryRepository.findDiariesBySupperId(id);

        if (!diaryBreakfast.isEmpty() || !diaryLunch.isEmpty() || !diarySupper.isEmpty()) {
            throw new MealCannotBeDeletedException();
        }

        this.mealRepository.deleteById(id);
    }

    private Page<MealDTO> handleFindMeals(Page<Meal> meals, Pageable pageable) {
        List<MealDTO> mealsList = new ArrayList<>();

        for (Meal meal : meals.getContent()) {
            byte[] rawPicture = mealRepository.findPictureById(meal.getId());
            MealDTO mealDTO = new MealDTO(
                    meal.getId(),
                    meal.getDiet(),
                    meal.getName(),
                    meal.getDescription(),
                    meal.getPrice(),
                    meal.getType(),
                    meal.getCalories(),
                    meal.getProtein(),
                    meal.getCarbohydrates(),
                    meal.getFats(),
                    Base64.getEncoder().encodeToString(rawPicture)
            );

            mealsList.add(mealDTO);
        }
        return new PageImpl<>(mealsList, pageable, meals.getTotalElements());
    }
}
