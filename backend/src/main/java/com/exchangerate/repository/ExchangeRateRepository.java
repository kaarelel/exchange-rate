package com.exchangerate.repository;

import com.exchangerate.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findAllByCurrencyCodeOrderByDateDesc(String currencyCode);
    Optional<ExchangeRate> findByCurrencyCodeAndDate(String currencyCode, LocalDate date);
    boolean existsByDate(LocalDate date);

    @Query("SELECT DISTINCT e.currencyCode FROM ExchangeRate e")
    List<String> findDistinctCurrencyCodes();

    @Query("SELECT MAX(e.date) FROM ExchangeRate e")
    Optional<LocalDate> findMaxDate();

    Optional<ExchangeRate> findFirstByCurrencyCodeOrderByDateDesc(String currencyCode);
}