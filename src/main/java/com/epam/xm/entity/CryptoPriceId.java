package com.epam.xm.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CryptoPriceId implements Serializable {

    private OffsetDateTime timestamp;
    private String symbol;

    public CryptoPriceId() {
    }

    public CryptoPriceId(OffsetDateTime timestamp, String symbol) {
        this.timestamp = timestamp;
        this.symbol = symbol;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoPriceId that = (CryptoPriceId) o;
        return timestamp.equals(that.timestamp) && symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, symbol);
    }
}
