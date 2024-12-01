package com.mikolajjanik.hospital_catering_admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posiłek")
@Getter
@Setter
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dietaid")
    private Diet diet;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "opis")
    private String description;

    @Column(name = "cena")
    private Double price;

    @Column(name = "rodzaj")
    private String type;

    @Column(name = "kalorie")
    private Double calories;

    @Column(name = "białko")
    private Double protein;

    @Column(name = "węglowodany")
    private Double carbohydrates;

    @Column(name = "tłuszcze")
    private Double fats;
}
