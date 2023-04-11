package com.epam.xm.configuration;

import java.util.Set;

public class SupportedCurrenciesHolder {

    private final Set<String> currencies;

    public SupportedCurrenciesHolder(String[] currencies) {
        this.currencies = Set.of(currencies); //We need immutable set here
    }

    public boolean isCurrencySupported(String symbol) {
        return currencies.contains(symbol);
    }

    /**
     * @return unmodifiable set of supported currencies symbols
     */
    public Set<String> getCurrencies() {
        return currencies;
    }
}
