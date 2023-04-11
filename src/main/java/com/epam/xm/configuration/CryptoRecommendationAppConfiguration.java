package com.epam.xm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.support.DatabaseStartupValidator;

import javax.sql.DataSource;

@Configuration
public class CryptoRecommendationAppConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CryptoRecommendationAppConfiguration.class);

    private static final String DEFAULT_SUPPORTED_CURRENCIES = "BTC";

    @Bean
    public SupportedCurrenciesHolder currenciesHolder(Environment environment) {
        String cryptos = environment.getProperty("SUPPORTED_CRYPTOS");

        if (cryptos == null || cryptos.isEmpty()) {
            logger.error("List of supported cryptos is not provided. Please fill SUPPORTED_CRYPTOS environment variable. " +
                         "Falling back to support only BTC");
            cryptos = DEFAULT_SUPPORTED_CURRENCIES;
        }

        String[] splitCryptos = cryptos.split(",");

        return new SupportedCurrenciesHolder(splitCryptos);
    }
//
//    @Bean
//    @DependsOn("databaseStartupValidator")
//    public DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(
//            DataSource dataSource,
//            DatabaseInitializationSettings settings) {
//        return new DataSourceScriptDatabaseInitializer(dataSource, settings);
//    }

    @Bean
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
        logger.info("creating databaseStartupValidator");
        var dsv = new DatabaseStartupValidator();
        dsv.setDataSource(dataSource);
        return dsv;
    }
}
