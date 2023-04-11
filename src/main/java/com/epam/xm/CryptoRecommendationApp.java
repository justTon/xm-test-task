package com.epam.xm;

import com.epam.xm.configuration.SupportedCurrenciesHolder;
import com.epam.xm.entity.CryptoAggregates;
import com.epam.xm.entity.CryptoNormalizedRange;
import com.epam.xm.service.CryptoPriceStatsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class CryptoRecommendationApp {

    private static final Logger logger = LoggerFactory.getLogger(CryptoRecommendationApp.class);

    private final CryptoPriceStatsService statsService;
    private final SupportedCurrenciesHolder supportedCurrencies;

    @Autowired
    public CryptoRecommendationApp(CryptoPriceStatsService statsService, SupportedCurrenciesHolder supportedCurrencies) {
        this.statsService = statsService;
        this.supportedCurrencies = supportedCurrencies;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoRecommendationApp.class, args);
    }

    @Operation( summary = "Find books", description = "Get registered books" )
    @GetMapping(path = "/sortCryptos")
    public List<CryptoNormalizedRange> sortCryptosByNormalizedRange() {
        return statsService.sortCryptosByNormalizedRange();
    }

    @GetMapping(path = "/cryptoAggregates")
    public CryptoAggregates getCryptoAggregates(@RequestParam @NotBlank String symbol,
                                                @RequestParam(required = false, defaultValue = "1") @Min(1) Integer months) {
        if (!supportedCurrencies.isCurrencySupported(symbol))
            return null;

        return statsService.getCryptoAggregates(symbol, months);
    }

    @GetMapping(path = "/getCryptoWithHighestNormalizedRange")
    public CryptoNormalizedRange getCryptoWithHighestNormalizedRange(@RequestParam long dayTimestamp) {
        return statsService.getCryptoWithHighestNormalizedRange(dayTimestamp);
    }
}