package com.mikolajjanik.hospital_catering_admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dietetyk_oddział")
@Getter
@Setter
public class DieticianWard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dietetykid")
    private Dietician dietician;

    @ManyToOne
    @JoinColumn(name = "oddziałid")
    private Ward ward;
}
