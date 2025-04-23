package com.exchangerate.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ECBClientTest {

    private final ECBClient ecbClient = new ECBClient();

    @Test
    void fetchRates_shouldReturnValidMap() throws Exception {
        Map<LocalDate, Map<String, Double>> rates = ecbClient.fetchRates();
        assertNotNull(rates);
        assertFalse(rates.isEmpty());

        Map.Entry<LocalDate, Map<String, Double>> latest = rates.entrySet().stream().findFirst().get();
        assertNotNull(latest.getKey());
        assertNotNull(latest.getValue());
        assertTrue(latest.getValue().containsKey("USD"));
    }
}
