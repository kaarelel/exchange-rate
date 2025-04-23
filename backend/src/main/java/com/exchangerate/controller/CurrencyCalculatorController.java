package com.exchangerate.controller;

import com.exchangerate.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyCalculatorController {

    private final ExchangeRateService exchangeRateService;

    public CurrencyCalculatorController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/calc")
    public ResponseEntity<?> convert(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            double result = exchangeRateService.convertCurrency(amount, from, to);
            double rate = exchangeRateService.getLatestRate(from, to);
            return ResponseEntity.ok(Map.of(
                    "converted", result,
                    "rate", rate,
                    "from", from,
                    "to", to
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}