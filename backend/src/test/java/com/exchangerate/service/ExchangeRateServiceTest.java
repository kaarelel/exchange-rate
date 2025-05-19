package com.exchangerate.service;

import com.exchangerate.model.ExchangeRate;
import com.exchangerate.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    private final ExchangeRateRepository repository = mock(ExchangeRateRepository.class);
    private final ECBClient client = mock(ECBClient.class);
    private final ExchangeRateService service = new ExchangeRateService(repository, client);

    @Test
    void testGetLatestRate() {
        LocalDate date = LocalDate.now();

        ExchangeRate eurRate = new ExchangeRate();
        eurRate.setCurrencyCode("EUR");
        eurRate.setRate(new BigDecimal("1.0"));
        eurRate.setDate(date);

        ExchangeRate usdRate = new ExchangeRate();
        usdRate.setCurrencyCode("USD");
        usdRate.setRate(new BigDecimal("1.2"));
        usdRate.setDate(date);

        when(repository.findFirstByCurrencyCodeOrderByDateDesc("EUR"))
                .thenReturn(Optional.of(eurRate));
        when(repository.findByCurrencyCodeAndDate("EUR", date))
                .thenReturn(Optional.of(eurRate));
        when(repository.findByCurrencyCodeAndDate("USD", date))
                .thenReturn(Optional.of(usdRate));

        BigDecimal rate = service.getLatestRate("EUR", "USD");
        assertEquals(new BigDecimal("1.2"), rate.setScale(1, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    void testConvertCurrency() {
        LocalDate date = LocalDate.now();

        ExchangeRate eur = new ExchangeRate();
        eur.setCurrencyCode("EUR");
        eur.setRate(new BigDecimal("1.0"));
        eur.setDate(date);

        ExchangeRate usd = new ExchangeRate();
        usd.setCurrencyCode("USD");
        usd.setRate(new BigDecimal("1.5"));
        usd.setDate(date);

        when(repository.findFirstByCurrencyCodeOrderByDateDesc("EUR")).thenReturn(Optional.of(eur));
        when(repository.findByCurrencyCodeAndDate("EUR", date)).thenReturn(Optional.of(eur));
        when(repository.findByCurrencyCodeAndDate("USD", date)).thenReturn(Optional.of(usd));

        BigDecimal result = service.convertCurrency(new BigDecimal("100"), "EUR", "USD");
        assertEquals(new BigDecimal("150.0"), result.setScale(1, BigDecimal.ROUND_HALF_UP));
    }
}