package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.DietRepository;
import com.mikolajjanik.hospital_catering_admin.dao.HospitalRepository;
import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.dao.WardRepository;
import com.mikolajjanik.hospital_catering_admin.dto.EditPatientDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewPatientDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.exception.DietNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.HospitalNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.PatientNotFoundException;
import com.mikolajjanik.hospital_catering_admin.exception.WardNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final WardRepository wardRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final DietRepository dietRepository;

    @Autowired
    public PatientServiceImpl(WardRepository wardRepository,
                              PatientRepository patientRepository,
                              HospitalRepository hospitalRepository,
                              DietRepository dietRepository) {
        this.wardRepository = wardRepository;
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.dietRepository = dietRepository;
    }
    @Override
    @SneakyThrows
    public List<Patient> findPatientsByWardId(Long wardId, String orderBy) {
        Ward ward = wardRepository.findWardById(wardId);

        if (ward == null) {
            throw new WardNotFoundException(wardId);
        }

        return switch (orderBy) {
            case "date" -> patientRepository.getPatientsByWardId(wardId);
            case "diet" -> patientRepository.getPatientsByWardIdOrderByDiet(wardId);
            case "name" -> patientRepository.getPatientsByWardIdOrderByName(wardId);
            default -> patientRepository.getPatientsByHospitalId(wardId);
        };
    }

    @Override
    @SneakyThrows
    public List<Patient> findPatientsByHospitalId(Long id) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null) {
            throw new HospitalNotFoundException(id);
        }
        return patientRepository.getPatientsByHospitalId(id);
    }

    @Override
    @SneakyThrows
    public Patient registerNewPatient(NewPatientDTO patientDTO) {
        Long wardId = patientDTO.getWardId();
        Long dietId = patientDTO.getDietId();

        Ward ward = wardRepository.findWardById(wardId);

        if (ward == null) {
            throw new WardNotFoundException(wardId);
        }

        Diet diet = dietRepository.findDietById(dietId);

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Patient patient = new Patient();

        if (!patientDTO.getEmail().isEmpty()) {
            patient.setEmail(patientDTO.getEmail());
        }

        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setLogin(patientDTO.getPesel());
        patient.setPassword(encoder.encode(patientDTO.getDefaultPassword()));
        patient.setWard(ward);
        patient.setDiet(diet);
        patient.setAdmissionDate(LocalDate.now());

        return patientRepository.save(patient);
    }

    @Override
    @SneakyThrows
    public Patient findPatientById(Long id) {
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null) {
            throw new PatientNotFoundException(id);
        }
        return patient;
    }

    @Override
    @SneakyThrows
    public Patient editPatient(EditPatientDTO patientDTO) {
        Long id = patientDTO.getId();
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null) {
            throw new PatientNotFoundException(id);
        }

        Long wardId = patientDTO.getWardId();
        Long dietId = patientDTO.getDietId();

        Ward ward = wardRepository.findWardById(wardId);
        Diet diet = dietRepository.findDietById(dietId);

        if (ward == null) {
            throw new WardNotFoundException(wardId);
        }

        if (diet == null) {
            throw new DietNotFoundException(dietId);
        }

        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setLogin(patientDTO.getLogin());
        patient.setDiet(diet);
        patient.setWard(ward);

        return patientRepository.save(patient);
    }

    @Override
    @SneakyThrows
    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null) {
            throw new PatientNotFoundException(id);
        }

        patientRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public Patient findPatientByLogin(String login) {
        Patient patient = patientRepository.findPatientByLogin(login);

        if (patient == null) {
            throw new PatientNotFoundException(login);
        }
        return patientRepository.findPatientByLogin(login); 
    }
}
