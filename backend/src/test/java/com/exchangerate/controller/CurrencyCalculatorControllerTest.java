package com.exchangerate.controller;

import com.exchangerate.service.ExchangeRateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CurrencyCalculatorController.class)
class CurrencyCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    @DisplayName("Should return converted amount and rate when valid params are given")
    void shouldReturnConversion() throws Exception {
        Mockito.when(exchangeRateService.convertCurrency(new BigDecimal("100"), "EUR", "USD"))
                .thenReturn(new BigDecimal("114.5"));
        Mockito.when(exchangeRateService.getLatestRate("EUR", "USD"))
                .thenReturn(new BigDecimal("1.145"));

        mockMvc.perform(get("/api/calc")
                        .param("amount", "100")
                        .param("from", "EUR")
                        .param("to", "USD")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.converted").value(114.5))
                .andExpect(jsonPath("$.rate").value(1.145))
                .andExpect(jsonPath("$.from").value("EUR"));
    }

    @Test
    @DisplayName("Should return bad request when illegal argument thrown")
    void shouldReturnBadRequest() throws Exception {
        Mockito.when(exchangeRateService.convertCurrency(new BigDecimal("100"), "AAA", "USD"))
                .thenThrow(new IllegalArgumentException("No data for currency: AAA"));

        mockMvc.perform(get("/api/calc")
                        .param("amount", "100")
                        .param("from", "AAA")
                        .param("to", "USD")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("No data for currency: AAA"));
    }
}