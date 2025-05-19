package com.exchangerate.controller;

import com.exchangerate.repository.ExchangeRateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ExchangeStatusController.class)
class ExchangeStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateRepository repository;

    @Test
    @DisplayName("Should return latest date and total rate count")
    void shouldReturnStatus() throws Exception {
        LocalDate latest = LocalDate.of(2024, 5, 15);
        Mockito.when(repository.findMaxDate()).thenReturn(Optional.of(latest));
        Mockito.when(repository.count()).thenReturn(123L);

        mockMvc.perform(get("/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latestDate").value("2024-05-15"))
                .andExpect(jsonPath("$.totalRates").value(123));
    }

    @Test
    @DisplayName("Should return 404 when no data available")
    void shouldReturnNotFound() throws Exception {
        Mockito.when(repository.findMaxDate()).thenReturn(Optional.empty());

        mockMvc.perform(get("/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
