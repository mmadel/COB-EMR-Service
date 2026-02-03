package com.cob.emr.enums;

public enum ChargeType {
    COPAY("Copay"),
    DEDUCTIBLE("Deductible"),
    COINSURANCE("Coinsurance"),
    SUPPLIES("Supplies"),
    WELLNESS("Wellness"),
    INTERNAL_PAYMENT("Internal Payment"),
    PAYMENT_ON_ACCOUNT("Payment on Account"),
    OTHER("Other");

    private final String value;

    ChargeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
