package project.model.assets;

import java.math.BigDecimal;
import java.util.List;

/**
 * This is the interface that will be used for the assets.
 * Since we decided that the assets will just be the rapresentation of the things in which you can invest,
 * and the investments will be the single instances bought and sold, the structure for the assets will only
 * have to include all the data regarding the assets (even the type of asset which will be useful for the
 * differentiation of the assets later), a method to produce the percentage of the variation of the price (Enrico will
 * look into this further when he finds out how to implement the APIs), and a method to extract a list of the price
 * variation useful to show them on a graph.
 */
public interface AssetInterface {

    /**
     * This method returns the name of the asset.
     * 
     * @return the name of the asset.
     */
    String getAssetName();

    /**
     * This method returns the current price for a single instance of the asset.
     * 
     * @return the current price for a single instance of the asset.
     */
    BigDecimal getSinglePrice();

    /**
     * This method returns the type of the asset.
     * 
     * @return the type of asset as a String.
     */
    String getAssetType();

    /**
     * This method is used to update the price variation.
     * 
     * @return the percentage of the price variation as a BigDecimal.
     */
    BigDecimal priceVariation();

    /**
     * This method is used to extract the price variation history to later show it on a graph.
     * 
     * @return the List of the recorded price variation.
     */
    List<BigDecimal> getVariationList();
}
