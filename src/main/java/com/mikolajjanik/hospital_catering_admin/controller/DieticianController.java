package com.mikolajjanik.hospital_catering_admin.controller;

import com.mikolajjanik.hospital_catering_admin.dto.DieticianDTO;
import com.mikolajjanik.hospital_catering_admin.dto.DieticianDetailsDTO;
import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.service.DieticianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/dieticians")
public class DieticianController {
    private final DieticianService dieticianService;

    @Autowired
    public DieticianController(DieticianService dieticianService) {
        this.dieticianService = dieticianService;
    }

    @GetMapping("")
    public ResponseEntity<List<Dietician>> findDieticians(@RequestParam("hospitalId") Long hospitalId) {
        List<Dietician> dieticians = dieticianService.findAllDieticians(hospitalId);
        return new ResponseEntity<>(dieticians, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DieticianDetailsDTO> findDieticianById(@PathVariable("id") Long id) {
        DieticianDetailsDTO dietician = dieticianService.findDieticianById(id);
        return new ResponseEntity<>(dietician, HttpStatus.OK);
    }
    @GetMapping("/email/{name}")
    public ResponseEntity<Dietician> findDieticianById(@PathVariable("name") String name) {
        Dietician dietician = dieticianService.findDieticianByEmail(name);
        return new ResponseEntity<>(dietician, HttpStatus.OK);
    }

    @PostMapping("/uploadPicture")
    @ResponseBody
    public ResponseEntity<DieticianDetailsDTO> uploadPicture(@RequestParam("dieticianId") Long id,
                                                                 @RequestPart("picture") MultipartFile picture) {
        DieticianDetailsDTO dietician = dieticianService.uploadProfilePicture(id, picture);
        return new ResponseEntity<>(dietician, HttpStatus.OK);
    }
}
