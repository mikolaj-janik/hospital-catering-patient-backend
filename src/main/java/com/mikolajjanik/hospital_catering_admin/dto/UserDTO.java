package com.mikolajjanik.hospital_catering_admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String login;
    private String name;
    private String surname;
    private LocalDate admissionDate;

    public UserDTO(Long id, String email, String login, String name, String surname, LocalDate admissionDate) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.admissionDate = admissionDate;
    }
}
