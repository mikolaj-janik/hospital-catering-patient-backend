package com.mikolajjanik.hospital_catering_admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jadłospis")
@Getter
@Setter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "dietaid")
    private Diet diet;

    @ManyToOne
    @JoinColumn(name = "sniadanieid")
    private Meal breakfast;

    @ManyToOne
    @JoinColumn(name = "obiadid")
    private Meal lunch;

    @ManyToOne
    @JoinColumn(name = "kolacjaid")
    private Meal supper;
}
