package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.SerializedName;

public class IngredientDTO {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String names;

    @SerializedName("count")
    private int counts;

    @SerializedName("unit")
    private String units;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
