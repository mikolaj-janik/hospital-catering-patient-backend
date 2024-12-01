package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.DieticianDTO;
import com.mikolajjanik.hospital_catering_admin.dto.DieticianDetailsDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface DieticianService {
    DieticianDetailsDTO findDieticianById(Long id);
    Dietician findDieticianByEmail(String name);
    Set<Dietician> findDieticiansByHospitalId(Long id);
    Set<DieticianDetailsDTO> findDieticiansByWardId(Long id);
    List<Dietician> findAllDieticians(Long hospitalId);
    DieticianDetailsDTO uploadProfilePicture(Long id, MultipartFile picture);
}
