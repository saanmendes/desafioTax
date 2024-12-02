package com.tax.controllers.dtos;

import jakarta.validation.constraints.NotNull;


public class TaxType {
    int id;
    @NotNull
    String name;
    @NotNull
    String description;
    double aliquot;

    public TaxType(int i, String ipi, String taxOnIndustrializedProducts, double v) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public double getAliquot() {
        return aliquot;
    }

    public void setAliquot(double aliquot) {
        this.aliquot = aliquot;
    }
}
