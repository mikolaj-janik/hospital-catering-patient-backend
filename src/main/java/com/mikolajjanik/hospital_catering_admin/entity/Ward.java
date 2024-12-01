package com.mikolajjanik.hospital_catering_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "oddział")
@Getter
@Setter
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "nr_telefonu")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "szpitalid")
    private Hospital hospital;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ward")
    @JsonIgnore
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany(mappedBy = "wards")
    @JsonIgnore
    private Set<Dietician> dieticians = new HashSet<>();

}
