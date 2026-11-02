package project.model.invplatform.assets;

import java.math.BigDecimal;

/**
 * RealAsset is the class rappresanting the asset that price
 * is based on real market data. It is possible using the 
 * Alpha Vantage API.
 * 
 * Url: https://www.alphavantage.co/
 */
public class RealAsset extends AbstractAsset {
    private final String API_KEY = "C3HKOUKP2G55WZAG";
    private final String URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";

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
     * Method to extract the current asset price
     * in the real market at the call moment. 
     * 
     * @return the current price.     * 
     */
    @Override
    protected BigDecimal getNewPrice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNewPrice'");
    }
    
}
