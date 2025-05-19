package com.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyConversionResult {
    @JsonProperty
    private final BigDecimal converted;
    @JsonProperty
    private final BigDecimal rate;
    @JsonProperty
    private final String from;

    public CurrencyConversionResult(BigDecimal converted, BigDecimal rate, String from) {
        this.converted = converted;
        this.rate = rate;
        this.from = from;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getFrom() {
        return from;
    }

    public BigDecimal getConverted() {
        return converted;
    }
}