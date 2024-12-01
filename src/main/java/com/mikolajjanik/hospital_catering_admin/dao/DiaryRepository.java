package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryById(Long id);
    Diary findDiaryByDietIdAndDate(Long dietId, LocalDate date);
    Set<Diary> findDiariesByDietId(Long id);
    Set<Diary> findDiariesByBreakfastId(Long id);
    Set<Diary> findDiariesByLunchId(Long id);
    Set<Diary> findDiariesBySupperId(Long id);
}
