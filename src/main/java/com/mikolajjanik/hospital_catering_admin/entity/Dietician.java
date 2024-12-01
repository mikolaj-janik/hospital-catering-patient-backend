package com.mikolajjanik.hospital_catering_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dietetyk")
@Getter
@Setter
public class Dietician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "hasło")
    @JsonIgnore
    private String password;

    @Column(name = "imię")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "szpitalid")
    private Hospital hospital;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "dietetyk_oddział",
            joinColumns = { @JoinColumn(name = "dietetykid") },
            inverseJoinColumns = { @JoinColumn(name = "oddziałid") }
    )
    private Set<Ward> wards = new HashSet<>();
}
