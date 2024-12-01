package com.mikolajjanik.hospital_catering_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pacjent")
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dietaid")
    private Diet diet;

    @ManyToOne
    @JoinColumn(name = "oddziałid")
    private Ward ward;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "hasło")
    @JsonIgnore
    private String password;

    @Column(name = "imię")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "data_przyjęcia")
    private LocalDate admissionDate;
}
