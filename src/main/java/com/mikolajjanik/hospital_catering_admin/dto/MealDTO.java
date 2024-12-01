package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Diet;
import lombok.Data;

@Data
public class MealDTO {
    private Long id;
    private Diet diet;
    private String name;
    private String description;
    private Double price;
    private String type;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fats;
    private String image;

    public MealDTO(Long id, Diet diet, String name, String description, Double price, String type, Double calories, Double protein, Double carbohydrates, Double fats, String image) {
        this.id = id;
        this.diet = diet;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.image = image;
    }
}
