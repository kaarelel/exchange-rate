package com.exchangerate.controller;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class ExchangeRateHistoryController {

    private final ExchangeRateRepository repository;

    public ExchangeRateHistoryController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{currency}")
    public ResponseEntity<List<ExchangeRate>> getCurrencyHistory(@PathVariable String currency) {
        List<ExchangeRate> history = repository.findAllByCurrencyCodeOrderByDateDesc(currency);
        if (history.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }
}
