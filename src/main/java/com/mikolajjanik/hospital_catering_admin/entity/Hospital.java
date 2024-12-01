package com.mikolajjanik.hospital_catering_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "szpital")
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "nr_telefonu")
    private String phoneNumber;

    @Column(name = "ulica")
    private String street;

    @Column(name = "nr_budynku")
    private int buildingNo;

    @Column(name = "kod_pocztowy")
    private String zipCode;

    @Column(name = "miasto")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    @JsonIgnore
    private Set<Ward> wards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    @JsonIgnore
    private Set<Dietician> dieticians = new HashSet<>();

}
