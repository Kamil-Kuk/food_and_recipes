package com.kamilkuk.food_and_recipes.model;

public enum VolumeUnit {
    MILLILITRE(1),
    TABLESPOON(15),
    TEASPOON(5),
    OUNCE(30),
    CUP(250),
    PINT(500),
    QUART(950),
    GALLON(3800),
    CENTILITRE(10),
    DECILITRE(100),
    LITRE(1000)
    ;

    private int volumeInMilliliters;

    public int getVolumeInMilliliters() {
        return volumeInMilliliters;
    }

    private VolumeUnit(int volumeInMilliliters) {
        this.volumeInMilliliters = volumeInMilliliters;
    }
}
