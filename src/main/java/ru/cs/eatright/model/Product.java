package ru.cs.eatright.model;

import com.google.common.base.Objects;

public class Product {
    private final String name;
    private final int  protein;
    private final int fat;
    private final int carbohydrate;
    private final int calorie;
    private final int glycemicIndex;
    private final int type;

    public String getName() {
        return name;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getCalorie() {
        return calorie;
    }

    public int getGlycemicIndex() {
        return glycemicIndex;
    }

    public int getType() {
        return type;
    }

    public Product(String name, int protein, int fat, int carbohydrate, int calorie, int glycemicIndex, int type) {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
        this.glycemicIndex = glycemicIndex;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProtein() == product.getProtein() &&
                getFat() == product.getFat() &&
                getCarbohydrate() == product.getCarbohydrate() &&
                getCalorie() == product.getCalorie() &&
                getGlycemicIndex() == product.getGlycemicIndex() &&
                getType() == product.getType() &&
                Objects.equal(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getProtein(), getFat(), getCarbohydrate(), getCalorie(), getGlycemicIndex(), getType());
    }

    @Override
    public String toString() {
        return getName();
    }
}
