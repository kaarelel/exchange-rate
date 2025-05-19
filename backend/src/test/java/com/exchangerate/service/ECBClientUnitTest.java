package com.exchangerate.service;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ECBClientUnitTest {

    @Test
    void parseRates_shouldParseMockXmlCorrectly() throws Exception {
        ECBClient client = new ECBClient();
        InputStream mockStream = getClass().getResourceAsStream("/mock/ecb-sample.xml");

        Map<LocalDate, Map<String, Double>> result = client.parseRates(mockStream);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Map<String, Double> anyDay = result.values().iterator().next();
        assertTrue(anyDay.containsKey("USD"));
        assertTrue(anyDay.containsKey("EUR"));
    }
}
