package ru.cs.eatright.knowledgebase;

public class  Product {
    private final String name;
    private final double protein;
    private final double fat;
    private final double carbohydrate;
    private final double calorie;
    private final double glycemicIndex;
    private final int type;

    public Product(String name, double protein, double fat, double carbohydrate, double calorie, double glycemicIndex, int type) {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
        this.glycemicIndex = glycemicIndex;
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
    public double getGlycemicIndex() {
        return glycemicIndex;
    }
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s: калорийность '%.1f' калл, белки '%.1f'г, жиры '%.1f'г, углеводы '%.1f'г, ГИ '%.1f'\n",
                name, calorie, protein, fat, carbohydrate, glycemicIndex);
    }
}
