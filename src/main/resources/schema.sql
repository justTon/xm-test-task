CREATE TABLE IF NOT EXISTS crypto_prices (
  timestamp TIMESTAMPTZ NOT NULL,
  symbol TEXT NOT NULL,
  price DOUBLE PRECISION NULL,
  PRIMARY KEY(timestamp, symbol)
) ;

SELECT create_hypertable('crypto_prices','timestamp', if_not_exists => TRUE);