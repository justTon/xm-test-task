package com.epam.xm.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "crypto_prices")
@IdClass(CryptoPriceId.class)
public class CryptoPrice {

    @Nonnull
    @Id
    @Column(name = "timestamp", nullable = false)
    private OffsetDateTime timestamp;

    @Nonnull
    @Id
    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Nonnull
    @Column(name = "price", nullable = false)
    private double price;


    public CryptoPrice() {
    }

    public CryptoPrice(OffsetDateTime timestamp, String symbol, double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoPrice that = (CryptoPrice) o;
        return Double.compare(that.price, price) == 0 && timestamp.equals(that.timestamp) && symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, symbol, price);
    }

    @Override
    public String toString() {
        return "CryptoPrice{" +
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
