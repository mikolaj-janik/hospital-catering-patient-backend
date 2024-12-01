package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DiaryRepository;
import com.mikolajjanik.hospital_catering_admin.dao.DietRepository;
import com.mikolajjanik.hospital_catering_admin.dao.MealRepository;
import com.mikolajjanik.hospital_catering_admin.dto.DiaryDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewDiaryDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diary;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import com.mikolajjanik.hospital_catering_admin.exception.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final DietRepository dietRepository;
    private final MealRepository mealRepository;

    private static final String BREAKFAST_NAME = "śniadanie";
    private static final String LUNCH_NAME = "obiad";
    private static final String SUPPER_NAME = "kolacja";
    private static final String NEVER = "never";
    private static final String EVERY_WEEK = "every week";
    private static final String EVERY_2_WEEKS = "every 2 weeks";

    @Autowired
    public DiaryServiceImpl(
            DiaryRepository diaryRepository,
            DietRepository dietRepository,
            MealRepository mealRepository
    ) {
        this.diaryRepository = diaryRepository;
        this.dietRepository = dietRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    @SneakyThrows
    public Diary getDiaryByDetails(String date, Long dietId) {
        Diary diary = diaryRepository.findDiaryByDietIdAndDate(dietId, LocalDate.parse(date));

        if (diary == null) {
            throw new DiaryNotFoundException();
        }
        return diary;
    }

    @Override
    @SneakyThrows
    public Diary getDiaryById(Long id) {
        checkIfDiaryExists(id);
        return this.diaryRepository.findDiaryById(id);
    }

    @Override
    @SneakyThrows
    public Diary createDiary(NewDiaryDTO newDiaryDTO) {
        Long dietId = newDiaryDTO.getDietId();
        Long breakfastId = newDiaryDTO.getBreakfastId();
        Long lunchId = newDiaryDTO.getLunchId();
        Long supperId = newDiaryDTO.getSupperId();
        String stringDate = newDiaryDTO.getDate();
        String repeatFor = newDiaryDTO.getRepeatFor();
        int repeatUntil = Integer.parseInt(newDiaryDTO.getRepeatUntil());
        int repeatForDays = 0;


        Diet diet = dietRepository.findDietById(dietId);
        Meal breakfast = mealRepository.findMealById(breakfastId);
        Meal lunch = mealRepository.findMealById(lunchId);
        Meal supper = mealRepository.findMealById(supperId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(stringDate, formatter);

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        if (breakfast == null) {
            throw new MealNotFoundException(breakfastId);
        }

        if (lunch == null) {
            throw new MealNotFoundException(lunchId);
        }

        if (supper == null) {
            throw new MealNotFoundException(supperId);
        }

        if (!dietId.equals(breakfast.getDiet().getId())) {
            throw new DietMismatchException(dietId, breakfastId);
        }

        if (!dietId.equals(lunch.getDiet().getId())) {
            throw new DietMismatchException(dietId, breakfastId);
        }

        if (!dietId.equals(supper.getDiet().getId())) {
            throw new DietMismatchException(dietId, breakfastId);
        }

        if (!breakfast.getType().equals(BREAKFAST_NAME)) {
            throw new IncorrectMealException(breakfastId, BREAKFAST_NAME);
        }

        if (!lunch.getType().equals(LUNCH_NAME)) {
            throw new IncorrectMealException(lunchId, LUNCH_NAME);
        }

        if (!supper.getType().equals(SUPPER_NAME)) {
            throw new IncorrectMealException(supperId, SUPPER_NAME);
        }

        if (breakfast.getPrice() > 0 || lunch.getPrice() > 0 || supper.getPrice() > 0) {
            throw new PremiumMealDiaryException();
        }

        Diary existingDiary = this.diaryRepository.findDiaryByDietIdAndDate(dietId, date);

        if (existingDiary != null) {
            throw new DiaryAlreadyExistsException(diet.getName(), date);
        }

        if (repeatFor.equals(EVERY_WEEK)) {
            repeatForDays = 7;
        } else if (repeatFor.equals(EVERY_2_WEEKS)) {
            repeatForDays = 14;
        }

        if (repeatFor.equals(NEVER)) {
            Diary diary = new Diary();
            diary.setDate(date);
            diary.setDiet(diet);
            diary.setBreakfast(breakfast);
            diary.setLunch(lunch);
            diary.setSupper(supper);
            diaryRepository.save(diary);

        } else if (repeatFor.equals(EVERY_WEEK) || repeatFor.equals(EVERY_2_WEEKS)) {
            LocalDate finalDate = date.plusMonths(repeatUntil);

            while (date.isBefore(finalDate)) {
                Diary diary = new Diary();
                diary.setDate(date);
                diary.setDiet(diet);
                diary.setBreakfast(breakfast);
                diary.setLunch(lunch);
                diary.setSupper(supper);
                diaryRepository.save(diary);
                date = date.plusDays(repeatForDays);
            }
        }
        Diary diary = new Diary();
        diary.setDate(date);
        diary.setDiet(diet);
        diary.setBreakfast(breakfast);
        diary.setLunch(lunch);
        diary.setSupper(supper);

        return diary;
    }

    @Override
    @SneakyThrows
    public Diary updateDiary(DiaryDTO diaryDTO) {
        Long id = diaryDTO.getId();
        Diary diary = diaryRepository.findDiaryById(id);

        if (diary == null) {
            throw new DiaryNotFoundException(id);
        }

        Long breakfastId = diaryDTO.getBreakfastId();
        Long lunchId = diaryDTO.getLunchId();
        Long supperId = diaryDTO.getSupperId();

        Meal breakfast = mealRepository.findMealById(breakfastId);
        Meal lunch = mealRepository.findMealById(lunchId);
        Meal supper = mealRepository.findMealById(supperId);

        if (breakfast == null) {
            throw new MealNotFoundException(breakfastId);
        }

        if (lunch == null) {
            throw new MealNotFoundException(lunchId);
        }

        if (supper == null) {
            throw new MealNotFoundException(supperId);
        }

        diary.setBreakfast(breakfast);
        diary.setLunch(lunch);
        diary.setSupper(supper);

        return diaryRepository.save(diary);
    }

    @Override
    @SneakyThrows
    public Set<Diary> getDiaryByDietId(Long id) {
        Diet diet = dietRepository.findDietById(id);

        if (diet == null) {
            throw new DietNotFoundException(id);
        }
        return diaryRepository.findDiariesByDietId(id);
    }

    private void checkIfDiaryExists(Long id) throws IOException {
        Diary diary = this.diaryRepository.findDiaryById(id);
        if (diary == null) {
            throw new DiaryNotFoundException(id);
        }
    }

}
