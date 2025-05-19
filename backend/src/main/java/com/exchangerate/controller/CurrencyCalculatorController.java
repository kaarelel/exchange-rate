package com.exchangerate.controller;

import com.exchangerate.dto.CurrencyConversionResult;
import com.exchangerate.dto.ErrorResponse;
import com.exchangerate.service.ExchangeRateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("${api.base-path:/api}")
public class CurrencyCalculatorController {

    private final ExchangeRateService exchangeRateService;

    public CurrencyCalculatorController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "${api.calc-path:/calc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> convert(
            @RequestParam BigDecimal amount,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            BigDecimal result = exchangeRateService.convertCurrency(amount, from, to);
            BigDecimal rate = exchangeRateService.getLatestRate(from, to);
            CurrencyConversionResult response = new CurrencyConversionResult(result, rate, from);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(e.getMessage()));
        }
    }
}
