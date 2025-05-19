package com.exchangerate.dto;

import java.util.List;

public class AvailableCurrenciesResponse {
    public final List<String> currencies;

    public AvailableCurrenciesResponse(List<String> currencies) {
        this.currencies = currencies;
    }
}