package com.epam.xm.repository;

import com.epam.xm.entity.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DependsOn("databaseStartupValidator")
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, CryptoPriceId> {

    //TODO limit range calculation by time window
    @Query(value = """
            select symbol, (max-min)/min normalizedRange
            from (
            SELECT
                symbol,
                toolkit_experimental.into_values(toolkit_experimental.max_n(price, 1)) max,
                toolkit_experimental.into_values(toolkit_experimental.min_n(price, 1)) min
            FROM crypto_prices
            GROUP BY symbol
            ) q
            order by normalizedRange desc
            """, nativeQuery = true)
    List<CryptoNormalizedRange> sortCryptosByNormalizedRange();

    @Query(value = """
            SELECT\s
            	symbol,\s
            	first(price, timestamp) oldest,\s
            	last(price, timestamp) newest,\s
            	toolkit_experimental.into_values(toolkit_experimental.max_n(price, 1)) max,
            	toolkit_experimental.into_values(toolkit_experimental.min_n(price, 1)) min
            FROM crypto_prices\s
            WHERE symbol = :symbol and now() - date(timestamp) < INTERVAL '1 month' * :months
            group by symbol""", nativeQuery = true)
    CryptoAggregates getCryptoAggregates(@Param("symbol") String symbol, @Param("months") int months);

    @Query(value = """
            select symbol, (max-min)/min normalizedRange
            from (
                SELECT\s
                    symbol,\s
                    toolkit_experimental.into_values(toolkit_experimental.max_n(price, 1)) max,
                    toolkit_experimental.into_values(toolkit_experimental.min_n(price, 1)) min
                FROM crypto_prices\s
                WHERE timestamp >= date(:dayStart) AND timestamp < date(:dayStart) + INTERVAL '1 day'
                GROUP BY symbol
            ) q
            order by normalizedRange desc
            limit 1""", nativeQuery = true)
    CryptoNormalizedRange getCryptoWithHighestNormalizedRange(@Param("dayStart") String dayStart);

}
