package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DieticianRepository;
import com.mikolajjanik.hospital_catering_admin.dao.DieticianWardRepository;
import com.mikolajjanik.hospital_catering_admin.dao.HospitalRepository;
import com.mikolajjanik.hospital_catering_admin.dao.WardRepository;
import com.mikolajjanik.hospital_catering_admin.dto.DieticianDTO;
import com.mikolajjanik.hospital_catering_admin.dto.DieticianDetailsDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.entity.DieticianWard;
import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.exception.CannotDeleteDieticianException;
import com.mikolajjanik.hospital_catering_admin.exception.DieticianNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.HospitalNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.WardNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DieticianServiceImpl implements DieticianService {

    private final DieticianRepository dieticianRepository;
    private final HospitalRepository hospitalRepository;
    private final WardRepository wardRepository;

    @Autowired
    public DieticianServiceImpl(DieticianRepository dieticianRepository,
                                HospitalRepository hospitalRepository,
                                WardRepository wardRepository,
                                DieticianWardRepository dieticianWardRepository) {
        this.dieticianRepository = dieticianRepository;
        this.hospitalRepository = hospitalRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    @SneakyThrows
    public DieticianDetailsDTO findDieticianById(Long id) {
        Dietician dietician = dieticianRepository.findDieticianById(id);

        if (dietician == null) {
            throw new DieticianNotFoundException(id);
        }
        byte[] rawPicture = dieticianRepository.findPictureById(id);

        if (rawPicture == null) {
            return new DieticianDetailsDTO(
                    dietician.getId(),
                    dietician.getName(),
                    dietician.getSurname(),
                    dietician.getEmail(),
                    dietician.getHospital()
            );
        }

        return new DieticianDetailsDTO(
                dietician.getId(),
                dietician.getName(),
                dietician.getSurname(),
                dietician.getEmail(),
                dietician.getHospital(),
                Base64.getEncoder().encodeToString(rawPicture)
        );
    }

    @Override
    @SneakyThrows
    public Dietician findDieticianByEmail(String name) {
        Dietician dietician = dieticianRepository.findDieticianByEmail(name);

        if (dietician == null) {
            throw new DieticianNotFoundException(name);
        }
        return dietician;
    }

    @Override
    @SneakyThrows
    public Set<Dietician> findDieticiansByHospitalId(Long id) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null) {
            throw new HospitalNotFoundException(id);
        }

        return dieticianRepository.findDieticiansByHospitalId(id);
    }

    @Override
    @SneakyThrows
    public Set<DieticianDetailsDTO> findDieticiansByWardId(Long id) {
        Ward ward = wardRepository.findWardById(id);

        if (ward == null) {
            throw new WardNotFoundException(id);
        }
        Set<Dietician> dieticians = ward.getDieticians();
        Set<DieticianDetailsDTO> dieticiansDetailsSet = new HashSet<>();

        dieticians.forEach((dietician) -> {
            byte[] rawPicture = dieticianRepository.findPictureById(dietician.getId());

            if (rawPicture == null) {
                dieticiansDetailsSet.add(new DieticianDetailsDTO(
                        dietician.getId(),
                        dietician.getName(),
                        dietician.getSurname(),
                        dietician.getEmail(),
                        dietician.getHospital()
                ));

            } else {
                dieticiansDetailsSet.add(new DieticianDetailsDTO(
                        dietician.getId(),
                        dietician.getName(),
                        dietician.getSurname(),
                        dietician.getEmail(),
                        dietician.getHospital(),
                        Base64.getEncoder().encodeToString(rawPicture)
                ));
            }
        });
        return dieticiansDetailsSet;
    }

    @Override
    @SneakyThrows
    public List<Dietician> findAllDieticians(Long hospitalId) {
        List<Dietician> dieticians;
        if (hospitalId == 0) {
            dieticians = dieticianRepository.findAllDieticians();

        } else {
            Hospital hospital = hospitalRepository.findHospitalById(hospitalId);

            if (hospital == null) {
                throw new HospitalNotFoundException(hospitalId);
            }
            dieticians = dieticianRepository.findDieticians(hospitalId);
        }

        return dieticians;
    }

    @Override
    @SneakyThrows
    public DieticianDetailsDTO uploadProfilePicture(Long id, MultipartFile picture) {
        Dietician dietician = dieticianRepository.findDieticianById(id);

        if (dietician == null) {
            throw new DieticianNotFoundException(id);
        }

        byte[] pictureByte = picture.getBytes();
        dieticianRepository.uploadPictureById(dietician.getId(), pictureByte);

        return new DieticianDetailsDTO(
                dietician.getId(),
                dietician.getName(),
                dietician.getSurname(),
                dietician.getEmail(),
                dietician.getHospital(),
                Base64.getEncoder().encodeToString(pictureByte)
        );
    }
}
