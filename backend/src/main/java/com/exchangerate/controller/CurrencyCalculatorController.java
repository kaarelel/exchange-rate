package com.exchangerate.controller;

import com.exchangerate.dto.CurrencyConversionResult;
import com.exchangerate.dto.ErrorResponse;
import com.exchangerate.service.ExchangeRateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path:/api}")
public class CurrencyCalculatorController {

    private final ExchangeRateService exchangeRateService;

    public CurrencyCalculatorController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "${api.calc-path:/calc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> convert(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            double result = exchangeRateService.convertCurrency(amount, from, to);
            double rate = exchangeRateService.getLatestRate(from, to);
            CurrencyConversionResult response = new CurrencyConversionResult(result, rate, from);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}