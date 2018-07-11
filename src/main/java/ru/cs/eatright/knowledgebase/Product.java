package ru.cs.eatright.knowledgebase;

public class Product {
    private final String name;
    private final double protein;
    private final double fat;
    private final double carbohydrate;
    private final double calorie;
    private final double Glycemic_index;
    private final int type;

    public Product(String name, double protein, double fat, double carbohydrate, double calorie, double Glycemic_index, int type) {
        this.name = name.toLowerCase();
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
        this.Glycemic_index = Glycemic_index;
        this.type = type;
    }

    public String getName() { return name.toLowerCase().trim(); }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getCalorie() {
        return calorie;
    }

    public double getGlycemicIndex() {
        return Glycemic_index;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        //return String.format("\n%s: калорийность '%.1f' калл, белки '%.1f'г, жиры '%.1f'г, углеводы '%.1f'г, ГИ '%.1f'\n",
        ///        name, calorie, protein, fat, carbohydrate, Glycemic_index);
        return String.format("\n%s", name);
    }
}