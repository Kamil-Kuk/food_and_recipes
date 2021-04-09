package com.kamilkuk.food_and_recipes.model;

public enum WeightUnit {
    MILLIGRAM(1),
    POUND(453592.37),
    CENTIGRAMS(10),
    GRAM(1000),
    KILOGRAM(1000000),
    GRAIN(64.79891)
    ;

    private double weightInMilligrams;

    public double getWeightInMilligrams() {
        return weightInMilligrams;
    }

    private WeightUnit(double weightInMilligrams) {
        this.weightInMilligrams = weightInMilligrams;
    }
}
