package com.example.cookingrecipe.Domain.Model;

public class Ingredient {
    String name;
    String count;
    String unit;

    public Ingredient() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Ingredient(String name, String count, String unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + count +
                ", unit='" + unit + '\'' +
                '}';
    }
}
