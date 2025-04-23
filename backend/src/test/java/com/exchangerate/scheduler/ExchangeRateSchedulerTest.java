package com.exchangerate.scheduler;

import com.exchangerate.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExchangeRateSchedulerTest {

    @Test
    void fetchDailyRates_shouldCallService() throws Exception {
        ExchangeRateService mockService = Mockito.mock(ExchangeRateService.class);
        ExchangeRateScheduler scheduler = new ExchangeRateScheduler(mockService);

        scheduler.fetchDailyRates();
        Mockito.verify(mockService).loadHistoricalRates();
    }
}