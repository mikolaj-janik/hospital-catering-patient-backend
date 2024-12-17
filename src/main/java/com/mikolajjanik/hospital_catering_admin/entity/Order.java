package com.mikolajjanik.hospital_catering_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "zamówienie")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pacjentid")
    private Patient patient;

    @Column(name = "cena_całkowita")
    private double totalPrice;

    @Column(name = "data")
    private LocalDateTime orderDate;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "zamówienie_posiłek",
            joinColumns = { @JoinColumn(name = "zamówienieid") },
            inverseJoinColumns = { @JoinColumn(name = "posiłekid") }
    )
    private List<Meal> meals;
}
