package com.mikolajjanik.hospital_catering_admin.service;

import com.mikolajjanik.hospital_catering_admin.dao.PatientRepository;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.entity.UserPrincipal;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final PatientRepository patientRepository;

    @Autowired
    public MyUserDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Patient patient = patientRepository.findPatientByLogin(login);
        return new UserPrincipal(patient);
    }
}
