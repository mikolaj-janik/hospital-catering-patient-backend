package com.mikolajjanik.hospital_catering_admin.dao;

import com.mikolajjanik.hospital_catering_admin.entity.DieticianWard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DieticianWardRepository extends JpaRepository<DieticianWard, Long> {
    List<DieticianWard> findDieticianWardsByWardId(Long id);
}
