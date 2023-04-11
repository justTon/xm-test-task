# XM Crypto Recommendation Service

## How to start
Service is implemented as Docker container. To run it you'll need Docker running on you computer

`docker-compose -f docker-compose.yml up -d`

## REST Endpoints description
By default, application is available at `http://localhost:8080/api`. It exposes 3 endpoints:

### Return a descending sorted list of all the cryptos, comparing the normalized range
`http://localhost:8080/api/sortCryptos`
This endpoint doesn't require any input parameters. It returns a sorted list of JSON objects consisting of "symbol - normalized range" pairs

``[
    {"symbol":"ETH","normalizedRange":0.6383810110763016},
    {"symbol":"XRP","normalizedRange":0.5060541310541311},
    {"symbol":"DOGE","normalizedRange":0.5046511627906975},
    {"symbol":"LTC","normalizedRange":0.4651837524177949},
    {"symbol":"BTC","normalizedRange":0.43412110435594536}
]``

### Return the oldest/newest/min/max values for a requested crypto
`http://localhost:8080/api/cryptoAggregates?symbol=BTC&months=26`
Parameters:
* `symbol` - desired cryptocurrency code, e.g. 'BTC'
* `months` - length of time window to calculate stats over, in months. For example, if `months = 6`, values will be calculated for the last 6 months

Returns JSON object:
``{"min":33276.59,"max":47722.66,"symbol":"BTC","oldest":46813.21,"newest":38415.79}``

### Return the crypto with the highest normalized range for a specific day
`http://localhost:8080/api/getCryptoWithHighestNormalizedRange?dayTimestamp=1643403600000`
Parameters:
* `dayTimestamp` - UNIX epoch milliseconds, representing a day. Value will be truncated to the beginning of the day, normalized range will be calculated for 1 day

Returns JSON object:
``{"symbol":"ETH","normalizedRange":0.06069066555043942}``