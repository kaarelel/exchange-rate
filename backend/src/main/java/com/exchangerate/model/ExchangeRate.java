package com.exchangerate.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String currencyCode;

    private double rate;

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public double getRate() { return rate; }

    public void setRate(double rate) { this.rate = rate; }

}
