package com.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyConversionResult {
    @JsonProperty
    private final double converted;
    @JsonProperty
    private final double rate;
    @JsonProperty
    private final String from;

    public CurrencyConversionResult(double converted, double rate, String from) {
        this.converted = converted;
        this.rate = rate;
        this.from = from;
    }

    public double getRate() {
        return rate;
    }

    public String getFrom() {
        return from;
    }

    public double getConverted() {
        return converted;
    }
}