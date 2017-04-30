package com.dekoraktiv.android.pa.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Additive implements Serializable {
    @SerializedName("oznaka")
    private String label;

    @SerializedName("naziv")
    private String name;

    @SerializedName("rizik")
    private int risk;

    @SerializedName("opis")
    private String description;

    @SerializedName("dopustenaUpotreba")
    private String permittedUse;

    @SerializedName("adi")
    private String adi;

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public int getRisk() {
        return risk;
    }

    public String getDescription() {
        return description;
    }

    public String getPermittedUse() {
        return permittedUse;
    }

    public String getAdi() {
        return adi;
    }

    @Override
    public String toString() {
        return String.format("%s %s", label, name);
    }
}
