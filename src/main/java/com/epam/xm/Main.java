package com.epam.xm;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
//        Path root = Paths.get("prices");
//        Set<String> cryptos = new HashSet<>();
//
//        List<String> cryptoFiles = Files.list(root)
//                .map(Path::getFileName)
//                .map(Path::toString)
//                .filter(x -> x.endsWith("_values.csv"))
//                .toList();
//
//        cryptoFiles.forEach(System.out::println);

        File f = new File("prices/ETH_values.csv");
        Reader in = new FileReader(f);

        CSVParser parser = new CSVParser(in,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withSkipHeaderRecord());
        List<CSVRecord> list = parser.getRecords();

        for (CSVRecord record : list) {
            String timestamp = record.get(0);


            String dayStart = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                    .with(LocalDateTime.MIN)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE);

            String dayStart2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneOffset.UTC)
                    .truncatedTo(ChronoUnit.DAYS)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE);

            System.out.println(timestamp + " - " + convertEpochToOffsetDateTime(timestamp) + " - " + (new Date(Long.parseLong(timestamp))) +
                               " - " + dayStart + " - " + dayStart2);
        }
    }

    private static OffsetDateTime convertEpochToOffsetDateTime(String epochString) {
        long epochValue = Long.parseLong(epochString);
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochValue), ZoneOffset.UTC);
    }
}
