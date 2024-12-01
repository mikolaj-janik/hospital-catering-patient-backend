package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.dto.UserDTO;
import com.mikolajjanik.hospital_catering_admin.dto.LoginUserDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.exception.BadLoginCredentialsException;
import com.mikolajjanik.hospital_catering_admin.exception.PatientNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PatientRepository patientRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(PatientRepository patientRepository,
                           AuthenticationManager authenticationManager,
                           JWTService jwtService) {
        this.patientRepository = patientRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @SneakyThrows
    public String verify(LoginUserDTO user) {
        Patient patient = patientRepository.findPatientByLogin(user.getLogin());

        if (patient == null || !encoder.matches(user.getPassword(), patient.getPassword())) {
            throw new BadLoginCredentialsException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));

        return jwtService.generateToken(user.getLogin());
    }

    @Override
    @SneakyThrows
    public UserDTO getUserByLogin(String login) {
        Patient patient = patientRepository.findPatientByLogin(login);

        if (patient == null) {
            throw new PatientNotFoundException(login);
        }

        return new UserDTO(
                patient.getId(),
                patient.getEmail(),
                patient.getLogin(),
                patient.getName(),
                patient.getSurname(),
                patient.getAdmissionDate());
    }
}
