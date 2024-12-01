package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.HospitalDTO;
import com.mikolajjanik.hospital_catering_admin.dto.NewHospitalDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import com.mikolajjanik.hospital_catering_admin.service.DieticianService;
import com.mikolajjanik.hospital_catering_admin.service.WardService;
import jakarta.validation.Valid;
import com.mikolajjanik.hospital_catering_admin.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;
    private final WardService wardService;
    private final DieticianService dieticianService;

    @Autowired
    public HospitalController(HospitalService hospitalService, WardService wardService, DieticianService dieticianService) {
        this.hospitalService = hospitalService;
        this.wardService = wardService;
        this.dieticianService = dieticianService;
    }

    @GetMapping("")
    public Page<HospitalDTO> getAll(Pageable pageable) {
        return hospitalService.findAll(pageable);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hospital>> getAllHospitalsList() {
        List<Hospital> hospitals = hospitalService.findAllHospitalsList();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<HospitalDTO> getHospitalsByName(@RequestParam("name") String name, Pageable pageable) {
        return hospitalService.findByNameContaining(name, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable("id") Long id) {
        HospitalDTO hospital = hospitalService.findHospitalById(id);
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @GetMapping("/allWithDieticians")
    public ResponseEntity<List<Hospital>> getAllHospitalsWithDieticians() {
        List<Hospital> hospitals = hospitalService.findAllHospitalsWithDieticians();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @GetMapping("/{id}/wards")
    public ResponseEntity<Set<Ward>> getWardsByHospitalId(@PathVariable("id") Long id) {
        Set<Ward> wards = wardService.findWardsByHospitalId(id);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }

    @GetMapping("/{id}/wards/{name}")
    public ResponseEntity<Set<Ward>> getWardsByName(@PathVariable("id") Long id, @PathVariable("name") String name) {
        Set<Ward> wards = wardService.findByHospitalIdAndNameContaining(id, name);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }

    @GetMapping("/{id}/dieticians")
    public ResponseEntity<Set<Dietician>> getDieticiansByHospitalId(@PathVariable("id") Long id) {
        Set<Dietician> dieticians = dieticianService.findDieticiansByHospitalId(id);
        return new ResponseEntity<>(dieticians, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<HospitalDTO> newHospital(@Valid @ModelAttribute NewHospitalDTO newHospitalDTO,
                                                @RequestPart("picture") MultipartFile picture) {
        HospitalDTO hospital = hospitalService.addHospital(newHospitalDTO, picture);
        return new ResponseEntity<>(hospital, HttpStatus.CREATED);
    }

}
