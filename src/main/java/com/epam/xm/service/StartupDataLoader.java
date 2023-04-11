package com.epam.xm.service;

import com.epam.xm.entity.CryptoPrice;
import com.epam.xm.repository.CryptoPriceRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@DependsOn("databaseStartupValidator")
public class StartupDataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    private final CryptoPriceRepository priceRepository;
    private final SourceFilesHelper filesHelper;

    @Autowired
    public StartupDataLoader(CryptoPriceRepository priceRepository, SourceFilesHelper filesHelper) {
        this.priceRepository = priceRepository;
        this.filesHelper = filesHelper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String file : filesHelper.resolveSourceFilesPaths()) {
            logger.info("parsing file {}", file);

            Reader in = new FileReader(file);
            CSVParser parser = new CSVParser(in,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withSkipHeaderRecord());
            List<CSVRecord> list = parser.getRecords();

            logger.info("going to upload {} records", list.size());

            //TODO replace with batch upload
            list.stream()
                    .map(this::convertCsvToCryptoPrice)
                    .forEach(priceRepository::save);

            logger.info("upload completed");
        }
    }

    private CryptoPrice convertCsvToCryptoPrice(CSVRecord csvRecord) {
        String timestamp = csvRecord.get(0);
        OffsetDateTime dateTime = convertEpochToOffsetDateTime(timestamp);

        String symbol = csvRecord.get(1);
        double price = Double.parseDouble(csvRecord.get(2));

        return new CryptoPrice(dateTime, symbol, price);
    }

    private OffsetDateTime convertEpochToOffsetDateTime(String epochString) {
        long epochValue = Long.parseLong(epochString);
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochValue), ZoneOffset.UTC);
    }
}
