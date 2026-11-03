package project.model.investmentplatform.assets;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * RealAsset is the class rappresanting the asset that price
 * is based on real market data. It is possible using the 
 * Alpha Vantage API.
 * Url: https://www.alphavantage.co/
 * 
 */
public class RealAsset extends AbstractAsset {
    private static final String API_KEY = "C3HKOUKP2G55WZAG";
    private static final String URL_BASE = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    /**
     * Costructor for the real asset.
     * 
     * @param symbol from Alpha Vantage to use
     * @param type of the asset 
     */
    public RealAsset(final String symbol, 
                     final AssetType type) {
        super(symbol, type);
        super.addPriceManually(getNewPrice(), getLastUpdate());
    }

    /**
     * Private method to build the entyre URL
     * adding the symbol of the asset and the api key.
     * 
     * @return the builded URL.
     */
    private String buildURL() {
        return URL_BASE + super.getAssetName() + "&apikey=" + API_KEY;
    }

    /**
     * Metohd to build the request to the API
     * to ask data. 
     * 
     * @return the HttpRequest builded with the URL.
     */
    private HttpRequest buildRequest() {
        return HttpRequest.newBuilder()
                    .uri(URI.create(this.buildURL()))
                    .GET()
                    .build();
    }

    /**
     * Method to extract the price column from the json body
     * response. 
     * 
     * @param body the request body in json format. 
     * @return the price column.
     */
    private BigDecimal extractData(final String body) {
        final String priceKey = "\"05. price\": \"";
        final int endIndex;
        final String priceStr;
        int startIndex = body.indexOf(priceKey);

        if (startIndex != -1) {
            startIndex += priceKey.length();
            endIndex = body.indexOf('\"', startIndex);
            priceStr = body.substring(startIndex, endIndex);
            return new BigDecimal(priceStr);
        }
        return super.getSinglePrice();
    }

    /**
     * Method to extract the current asset price
     * in the real market at the call moment. 
     * 
     * @return the current price.     * 
     */
    @Override
    protected final BigDecimal getNewPrice() {
        final HttpRequest request;
        final HttpResponse<String> response;
        try {
            request = this.buildRequest();
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return this.extractData(response.body());
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            //System.err.println("Error while fetching data from API: " + e.getMessage()); 
            return this.getSinglePrice();
        }
    }
}
