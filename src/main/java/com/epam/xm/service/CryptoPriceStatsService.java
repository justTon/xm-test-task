package com.epam.xm.service;

import com.epam.xm.entity.CryptoAggregates;
import com.epam.xm.entity.CryptoNormalizedRange;
import com.epam.xm.repository.CryptoPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class CryptoPriceStatsService {

    private static final Logger logger = LoggerFactory.getLogger(CryptoPriceStatsService.class);

    private final CryptoPriceRepository priceRepository;

    @Autowired
    public CryptoPriceStatsService(CryptoPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<CryptoNormalizedRange> sortCryptosByNormalizedRange() {
        return priceRepository.sortCryptosByNormalizedRange();
    }

    public CryptoAggregates getCryptoAggregates(String symbol, Integer months) {
        if (months == null || months <= 1)
            months = 1;
        return priceRepository.getCryptoAggregates(symbol, months);
    }

    public CryptoNormalizedRange getCryptoWithHighestNormalizedRange(long dayTimestamp) {
        String dayStart = ZonedDateTime.ofInstant(Instant.ofEpochMilli(dayTimestamp), ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        return priceRepository.getCryptoWithHighestNormalizedRange(dayStart);
    }
}
