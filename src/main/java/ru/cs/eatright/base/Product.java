package ru.cs.eatright.base;

public class  Product {
    private String name;
    private final double protein;
    private final double fat;


    private final double carbohydrate;
    private final double calorie;
    private final double glycemic_index;
    private final double type;

    public Product(String name, double protein, double fat, double carbohydrate, double calorie, double glycemic_index, double type) {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
        this.glycemic_index = glycemic_index;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public double getProtein() { return protein; }
    public double getFat() { return fat; }
    public double getCarbohydrate() {
        return carbohydrate;
    }
    public double getCalorie() {
        return calorie;
    }
    public double getglycemic_index() {
        return glycemic_index;
    }
    public double getType() {
        return type;
    }

}
