package com.mikolajjanik.hospital_catering_admin.dto;

import lombok.Data;

@Data
public class HospitalDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String street;
    private int buildingNo;
    private String zipCode;
    private String city;
    private String picture;

    public HospitalDTO(Long id, String name, String phoneNumber, String street, int buildingNo, String zipCode, String city, String picture) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.buildingNo = buildingNo;
        this.zipCode = zipCode;
        this.city = city;
        this.picture = picture;
    }
}
