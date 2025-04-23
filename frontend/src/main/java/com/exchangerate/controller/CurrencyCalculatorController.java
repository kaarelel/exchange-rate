package com.exchangerate.controller;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/calc")
public class CurrencyCalculatorController {

    private final ExchangeRateRepository repository;

    public CurrencyCalculatorController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> convert(@RequestParam double amount, @RequestParam String currency) {
        Optional<ExchangeRate> rateOpt = repository.findByCurrencyCodeAndDate(currency, LocalDate.now().minusDays(1));

        return rateOpt.map(rate -> ResponseEntity.ok(Map.of(
                "currency", currency,
                "rate", rate.getRate(),
                "amount", amount,
                "converted", amount * rate.getRate()
        ))).orElseGet(() -> ResponseEntity.badRequest().body(Map.of(
                "error", "Rate not available for currency: " + currency
        )));
    }
}