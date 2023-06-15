package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.SerializedName;

public class CookOrdersDTO {

    @SerializedName("id")
    private int id;

    @SerializedName("names")
    private String names;

    @SerializedName("counts")
    private int counts;

    @SerializedName("units")
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
