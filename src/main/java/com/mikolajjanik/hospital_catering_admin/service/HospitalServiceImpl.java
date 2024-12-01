package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.HospitalRepository;
import com.mikolajjanik.hospital_catering_admin.dto.HospitalDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewHospitalDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import com.mikolajjanik.hospital_catering_admin.exception.HospitalNotFoundException;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Page<HospitalDTO> findAll(Pageable pageable) {
        Page<Hospital> hospitals = hospitalRepository.findAll(pageable);
        return handleFindHospitals(hospitals, pageable);
    }

    @Override
    public Page<HospitalDTO> findByNameContaining(String name, Pageable pageable) {
        Page<Hospital> hospitals = hospitalRepository.findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrStreetContainingIgnoreCase(name,
                name, name, pageable);
        return handleFindHospitals(hospitals, pageable);
    }

    @Override
    public List<Hospital> findAllHospitalsWithDieticians() {
        return hospitalRepository.findAllHospitalsWithDieticians();
    }

    @Override
    @SneakyThrows
    public HospitalDTO findHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null) {
            throw new HospitalNotFoundException(id);
        }

        byte[] rawPicture = hospitalRepository.findPictureById(id);

        HospitalDTO hospitalDTO = new HospitalDTO(
          hospital.getId(),
          hospital.getName(),
          hospital.getPhoneNumber(),
          hospital.getStreet(),
          hospital.getBuildingNo(),
          hospital.getZipCode(),
          hospital.getCity(),
          Base64.getEncoder().encodeToString(rawPicture)
        );

        return hospitalDTO;
    }

    @Override
    @SneakyThrows
    public HospitalDTO addHospital(NewHospitalDTO newHospitalDTO, MultipartFile picture) {
        Hospital hospital = new Hospital();
        validateImageFormat(picture);

        hospital.setName(newHospitalDTO.getName());
        hospital.setPhoneNumber(newHospitalDTO.getPhoneNumber());
        hospital.setStreet(newHospitalDTO.getStreet());
        hospital.setBuildingNo(Integer.parseInt(newHospitalDTO.getBuildingNo()));
        hospital.setZipCode(newHospitalDTO.getZipCode());
        hospital.setCity(newHospitalDTO.getCity());

        hospital = hospitalRepository.save(hospital);
        byte [] pictureByte = picture.getBytes();
        hospitalRepository.updatePictureByHospitalId(hospital.getId(), pictureByte);

        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getPhoneNumber(),
                hospital.getStreet(),
                hospital.getBuildingNo(),
                hospital.getZipCode(),
                hospital.getCity(),
                Base64.getEncoder().encodeToString(pictureByte)
        );
    }

    @Override
    public List<Hospital> findAllHospitalsList() {
        return hospitalRepository.findAll();
    }

    private Page<HospitalDTO> handleFindHospitals(Page<Hospital> hospitals, Pageable pageable) {
        List<HospitalDTO> hospitalsList = new ArrayList<>();

        for(Hospital hospital : hospitals.getContent()) {

            byte[] rawPicture = hospitalRepository.findPictureById(hospital.getId());
            HospitalDTO hospitalDTO = new HospitalDTO(
                    hospital.getId(),
                    hospital.getName(),
                    hospital.getPhoneNumber(),
                    hospital.getStreet(),
                    hospital.getBuildingNo(),
                    hospital.getZipCode(),
                    hospital.getCity(),
                    Base64.getEncoder().encodeToString(rawPicture)
            );

            hospitalsList.add(hospitalDTO);
        }

        return new PageImpl<>(hospitalsList, pageable, hospitals.getTotalElements());
    }

    public static void validateImageFormat(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null &&
                !originalFilename.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")) {
            throw new IllegalArgumentException("Invalid file format. Allowed formats: jpg, jpeg, png.");
        }

        String mimeType = file.getContentType();
        if (mimeType == null ||
                !(mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
            throw new IllegalArgumentException("Invalid MIME type. Allowed types: image/jpeg, image/png.");
        }
    }
}
