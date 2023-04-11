package com.epam.xm.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupportedCurrenciesHolderTest {

    public static final String[] SOURCE_CURRENCIES = new String[]{"BTC", "ETH", "LTC"};

    private SupportedCurrenciesHolder holder;

    @BeforeEach
    void setUp() {
        holder = new SupportedCurrenciesHolder(SOURCE_CURRENCIES);
    }

    @Test
    void testGetCurrencies() {
        assertEquals(SOURCE_CURRENCIES.length, holder.getCurrencies().size());

        for (String currency : SOURCE_CURRENCIES) {
            assertTrue(holder.getCurrencies().contains(currency));
        }
    }

    @Test
    void testIsCurrencySupported() {
        for (String currency : SOURCE_CURRENCIES) {
            assertTrue(holder.isCurrencySupported(currency));
        }
    }

    @Test
    void testCurrenciesHolderUnmodifiable() {
        assertThrows(Exception.class, () -> holder.getCurrencies().add("FAKE"));
    }
}
