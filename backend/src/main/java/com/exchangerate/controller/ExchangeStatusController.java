package com.exchangerate.controller;

import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/status")
public class ExchangeStatusController {
    private final ExchangeRateRepository repo;

    public ExchangeStatusController(ExchangeRateRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Map<String, Object> status() {
        return Map.of(
                "latestDate", Objects.requireNonNull(repo.findMaxDate().orElse(null)),
                "totalRates", repo.count()
        );
    }
}
