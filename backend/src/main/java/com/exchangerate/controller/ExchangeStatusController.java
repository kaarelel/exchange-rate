package com.exchangerate.controller;

import com.exchangerate.repository.ExchangeRateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.status-path:/status}")
public class ExchangeStatusController {

    private final ExchangeRateRepository repo;

    public ExchangeStatusController(ExchangeRateRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> status() {
        var latestDate = repo.findMaxDate().orElse(null);
        if (latestDate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                Map.of(
                        "latestDate", latestDate,
                        "totalRates", repo.count()
                )
        );
    }
}
