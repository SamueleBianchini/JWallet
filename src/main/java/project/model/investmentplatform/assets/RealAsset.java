package project.model.investmentplatform.assets;

import java.math.BigDecimal;

/**
 * RealAsset is the class rappresanting the asset that price
 * is based on real market data. It is possible using the 
 * Alpha Vantage API.
 * Url: https://www.alphavantage.co/
 */
public final class RealAsset extends AbstractAsset {
    /*private final String API_KEY = "C3HKOUKP2G55WZAG";*/
    /**
     * Costructor for the real asset.
     * 
     * @param startingPrice the starting price
     * @param symbol from Alpha Vantage to use
     * @param type of the asset 
     */
    public RealAsset(final BigDecimal startingPrice, 
                     final String symbol, 
                     final AssetType type) {
        super(symbol, type);
    }

    @Override
    protected BigDecimal getNewPrice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNewPrice'");
    }

}
