package com.exchangerate.repository;

import com.exchangerate.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findAllByDate(LocalDate date);
    List<ExchangeRate> findAllByCurrencyCodeOrderByDateDesc(String currencyCode);
    Optional<ExchangeRate> findByCurrencyCodeAndDate(String currencyCode, LocalDate date);
    boolean existsByDate(LocalDate date);
}