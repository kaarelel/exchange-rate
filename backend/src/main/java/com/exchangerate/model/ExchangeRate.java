package com.exchangerate.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String currencyCode;

    @Column(precision = 19, scale = 8, nullable = false)
    private BigDecimal rate;

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public String getCurrencyCode() { return currencyCode; }

    public BigDecimal getRate() { return rate; }

    public void setRate(BigDecimal rate) { this.rate = rate; }
}