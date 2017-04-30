package com.dekoraktiv.android.pa.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Stem {
    @SerializedName("aditivi")
    private ArrayList<Additive> additives;

    public ArrayList<Additive> getAdditives() {
        return additives;
    }
}
