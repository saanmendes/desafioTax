package com.tax.controllers.dtos;

public class TaxCalculationRequest {
    private int taxTypeId;
    private double baseValue;

    public TaxCalculationRequest() {
    }

    public int getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(int taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }
}
