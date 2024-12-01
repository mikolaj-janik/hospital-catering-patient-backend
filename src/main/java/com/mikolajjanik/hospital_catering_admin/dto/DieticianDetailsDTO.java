package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import lombok.Data;

@Data
public class DieticianDetailsDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Hospital hospital;
    private String picture;

    public DieticianDetailsDTO(Long id, String name, String surname, String email, Hospital hospital, String picture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.hospital = hospital;
        this.picture = picture;
    }

    public DieticianDetailsDTO(Long id, String name, String surname, String email, Hospital hospital) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.hospital = hospital;
        this.picture = "";
    }
}
