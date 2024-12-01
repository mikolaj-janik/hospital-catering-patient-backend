package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dto.NewWardDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateWardDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;

import java.util.List;
import java.util.Set;

public interface WardService {
    Ward findWardById(Long id);
    Set<Ward> findWardsByHospitalId(Long id);
    Set<Ward> findByHospitalIdAndNameContaining(Long id, String name);
    Ward createWard(NewWardDTO wardDTO);
    Ward updateWard(UpdateWardDTO wardDTO);
    List<Ward> findWardsByDieticianId(Long id, String name);
}
