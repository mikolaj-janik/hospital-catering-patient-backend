package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.*;
import com.mikolajjanik.hospital_catering_admin.dto.NewWardDTO;
import com.mikolajjanik.hospital_catering_admin.dto.UpdateWardDTO;
import com.mikolajjanik.hospital_catering_admin.entity.*;
import com.mikolajjanik.hospital_catering_admin.exception.CannotDeleteWardException;
import com.mikolajjanik.hospital_catering_admin.exception.DieticianNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.HospitalNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.WardNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;
    private final HospitalRepository hospitalRepository;
    private final DieticianRepository dieticianRepository;
    private final DieticianWardRepository dieticianWardRepository;
    private static final String POLISH_PHONE_EXTENSION = "+48 ";

    @Autowired
    public WardServiceImpl(WardRepository wardRepository,
                           HospitalRepository hospitalRepository,
                           DieticianRepository dieticianRepository,
                           DieticianWardRepository dieticianWardRepository) {
        this.wardRepository = wardRepository;
        this.hospitalRepository = hospitalRepository;
        this.dieticianRepository = dieticianRepository;
        this.dieticianWardRepository = dieticianWardRepository;
    }

    @Override
    @SneakyThrows
    public Ward findWardById(Long id) {
        Ward ward = wardRepository.findWardById(id);

        if (ward == null) {
            throw new WardNotFoundException(id);
        }

        return ward;
    }

    @Override
    @SneakyThrows
    public Set<Ward> findWardsByHospitalId(Long id) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null) {
            throw new HospitalNotFoundException(id);
        }

        return wardRepository.findWardsByHospitalIdOrderByName(id);
    }

    @Override
    @SneakyThrows
    public Set<Ward> findByHospitalIdAndNameContaining(Long id, String name) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null) {
            throw new HospitalNotFoundException(id);
        }

        return wardRepository.findWardsByHospitalIdAndNameContainingIgnoreCase(id, name);
    }

    @Override
    @SneakyThrows
    public Ward createWard(NewWardDTO wardDTO) {
        Long hospitalId = wardDTO.getHospital().getId();
        Hospital hospital = hospitalRepository.findHospitalById(hospitalId);

        if (hospital == null) {
            throw new HospitalNotFoundException(hospitalId);
        }

        Ward ward = new Ward();
        ward.setName(wardDTO.getName());
        ward.setPhoneNumber(POLISH_PHONE_EXTENSION + wardDTO.getPhoneNumber());
        ward.setHospital(hospital);

        wardRepository.save(ward);

        List<Dietician> dieticians = wardDTO.getDieticians();

        if (!dieticians.isEmpty()) {
            for(Dietician dietician: dieticians) {
                DieticianWard dieticianWard = new DieticianWard();
                dieticianWard.setDietician(dietician);
                dieticianWard.setWard(ward);

                dieticianWardRepository.save(dieticianWard);
            }
        }

        return ward;
    }

    @Override
    @SneakyThrows
    public Ward updateWard(UpdateWardDTO wardDTO) {
        Long id = wardDTO.getId();
        String name = wardDTO.getName();
        String phoneNumber = wardDTO.getPhoneNumber();
        List<Dietician> newDieticians = wardDTO.getDieticians();

        Ward ward = wardRepository.findWardById(id);

        if (ward == null) {
            throw new WardNotFoundException(id);
        }

        List<DieticianWard> dieticianWards = dieticianWardRepository.findDieticianWardsByWardId(id);
        List<Dietician> oldDieticians = new ArrayList<>();

        for (DieticianWard oldDietician : dieticianWards) {
            boolean dieticianToBeDeleted = true;

            for (Dietician newDietician : newDieticians) {
                if (oldDietician.getDietician().getId().equals(newDietician.getId())) {
                    dieticianToBeDeleted = false;
                    break;
                }
            }
            if (dieticianToBeDeleted) {
                dieticianWardRepository.deleteById(oldDietician.getId());
            } else {
                oldDieticians.add(oldDietician.getDietician());
            }
        }

        for (Dietician newDietician : newDieticians) {
            boolean toBeInserted = true;

            for (Dietician oldDietician : oldDieticians) {
                if (oldDietician.getId().equals(newDietician.getId())) {
                    toBeInserted = false;
                    break;
                }
            }
            if (toBeInserted) {
                DieticianWard dieticianWard = new DieticianWard();
                dieticianWard.setDietician(newDietician);
                dieticianWard.setWard(ward);

                dieticianWardRepository.save(dieticianWard);
            }
        }

        ward.setName(name);
        ward.setPhoneNumber(phoneNumber);

        return wardRepository.save(ward);
    }

    @Override
    @SneakyThrows
    public List<Ward> findWardsByDieticianId(Long id, String name) {
        Dietician dietician = dieticianRepository.findDieticianById(id);

        if (dietician == null) {
            throw new DieticianNotFoundException(id);
        }

        if (name.equals("null")) {
            return wardRepository.findWardsByDieticianId(id);
        }
        return wardRepository.findWardsByDieticianIdAndName(id, name);
    }
}
