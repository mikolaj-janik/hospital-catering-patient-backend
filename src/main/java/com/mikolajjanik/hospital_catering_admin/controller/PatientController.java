package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.EditPatientDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewPatientDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import com.mikolajjanik.hospital_catering_admin.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientId(@PathVariable("id") Long id) {
        Patient patient = patientService.findPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/get/{login}")
    public ResponseEntity<Patient> getPatientByLogin(@PathVariable("login") String login) {
        Patient patient = patientService.findPatientByLogin(login);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("/ward/{id}")
    public ResponseEntity<List<Patient>> getPatientsByWardId(@PathVariable("id") Long wardId, @RequestParam("orderBy") String orderBy) {
        List<Patient> patients = patientService.findPatientsByWardId(wardId, orderBy);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<List<Patient>> getPatientsByHospitalId(@PathVariable("id") Long hospitalId) {
        List<Patient> patients = patientService.findPatientsByHospitalId(hospitalId);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody NewPatientDTO patientDTO) {
        Patient patient = patientService.registerNewPatient(patientDTO);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<Patient> editPatient(@Valid @RequestBody EditPatientDTO patientDTO) {
        Patient patient = patientService.editPatient(patientDTO);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatientById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
