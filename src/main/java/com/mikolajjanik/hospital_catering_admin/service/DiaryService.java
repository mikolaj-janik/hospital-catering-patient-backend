package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.DiaryDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewDiaryDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diary;

import java.util.Set;

public interface DiaryService {
    Diary getDiaryByDetails(String date, Long dietId);
    Diary getDiaryById(Long id);
    Diary createDiary(NewDiaryDTO newDiaryDTO);
    Diary updateDiary(DiaryDTO diaryDTO);
    Set<Diary> getDiaryByDietId(Long id);
}
