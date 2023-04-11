package com.epam.xm.service;

import com.epam.xm.configuration.SupportedCurrenciesHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SourceFilesHelper {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    private static final String DEFAULT_INPUT_PRICES_FOLDER = "/var/local/prices/";
    private static final String DEFAULT_PRICES_FILE_SUFFIX = "_values.csv";

    private final Environment environment;
    private final SupportedCurrenciesHolder supportedCurrencies;

    @Autowired
    public SourceFilesHelper(Environment environment, SupportedCurrenciesHolder supportedCurrencies) {
        this.environment = environment;
        this.supportedCurrencies = supportedCurrencies;
    }

    public List<String> resolveSourceFilesPaths() {
        String pricesFolder = environment.getProperty("INPUT_CRYPTO_PRICES_FOLDER", DEFAULT_INPUT_PRICES_FOLDER);
        logger.info("pricesFolder set to {}", pricesFolder);

        return supportedCurrencies.getCurrencies().stream()
                .map(currency -> {
                    String fileName = currency + DEFAULT_PRICES_FILE_SUFFIX;
                    return pricesFolder + fileName;
                }).collect(Collectors.toList());
    }
}
