package project.model.investmentplatform.investiments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import project.model.investmentplatform.assets.AssetInterface;

/**
 * InvestimentInterface is the interface that will be used for the investments.
 * Since we decided that the assets will just be the rapresentation of the things in which you can invest,
 * and the investments will be the single instances bought and sold, the structure for the investments will only
 * contain the information about the specific instance of the asset that was bought.
 * 
 */
public interface InvestmentInterface {

    /**
     * This method is the getter for the asset.
     * Note that probably it should return a Asset and not a AssetInterface, but right now I will leave
     * it like this because we don't have the implementation for the Asset yet.
     * 
     * @return the asset
     */
    AssetInterface getAsset();

    /**
     * This method is a getter for the original value of a single instance of the asset.
     * 
     * @return the original value for a single instance of the asset, as it was when the investment started.
     */
    BigDecimal getOriginalSingleValue();

    /**
     * This method is a getter for the date of the purchase of the investment.
     * 
     * @return the date of the purchase of the investment.
     */
    LocalDate getpurchasedDate();

    /**
     * This method is the getter for the price variation list, which we will use to make graphs.
     * 
     * @return the price variation list.
     */
    List<BigDecimal> getPriceVariationList();

    /**
     * Method to get the value in gain or loss of the value of the investment,
     * regardless of interests or dividends that may have been obtained.
     * 
     * @return the value in gain or loss of the investiment in the current valute.
     */
    BigDecimal totalVariationValue();

    /**
     * This method is a getter for the number of instances of the assets we bought.
     * 
     * @return the number of assets we bought.
     */
    int nBought();

    /**
     * This method will be used to set the current value, and will be called by the updateCurrentValue
     * or by the asset when it updates, because it should always reflect the last update made.
     * 
     * @param newValue the new value.
     */
    void setCurrentValue(BigDecimal newValue);

    /**
     * This method is a getter for the original total value of the investment.
     * 
     * @return the total original value of the investment.
     */
    BigDecimal totalOriginalValue();

    /**
     * This method is a getter for the current total value of the investment.
     * 
     * @return the total current value of the investment.
     */
    BigDecimal totalCurrentValue();

    /**
     * This method is used to update the current value of the asset and assign the necessary modifications.
     */
    void updateCurrentValue();

}
