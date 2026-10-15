package project.model.assets;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * This class will be used as a template for all different types of assets.
 * For this reason, it will only have the fields necessary for every different type of investment.
 */
public abstract class AbstractInvestment {

    private final List<BigDecimal> priceVariationList;
    private final AssetInterface asset;
    private final LocalDate purchaseDate;
    private final BigDecimal originalSingleValue;
    private final int nBought;
    private BigDecimal currentSingleValue;

    /**
     * This method is the constructor for the basic information needed for the Investments.
     * 
     * @param asset the asset to which this investment refers.
     *      Note that probably it should be a Asset and not a AssetInterface, but right now I will leave
     *      it like this because we don't have the implementation for the Asset yet.
     * @param originalSingleValue the original value for a single instance.
     * @param nBought the number of instances of the asset bought.
     * @param purchaseDate the date on which we purchased the investment.
     */
    public AbstractInvestment(
        final AssetInterface asset,
        final BigDecimal originalSingleValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {
        this.priceVariationList = new ArrayList<>();
        this.asset = asset;
        this.originalSingleValue = originalSingleValue;
        this.nBought = nBought;
        this.currentSingleValue = originalSingleValue;
        this.purchaseDate = purchaseDate;
    }

    /**
     * This method is the getter for the asset.
     * Note that probably it should return a Asset and not a AssetInterface, but right now I will leave
     * it like this because we don't have the implementation for the Asset yet.
     * 
     * @return the asset
     */
    public AssetInterface getAsset() {
        return this.asset;
    }

    /**
     * This method is a getter for the original value of a single instance of the asset.
     * 
     * @return the original value for a single instance of the asset, as it was when the investment started.
     */
    public BigDecimal getOriginalSingleValue() {
        return this.originalSingleValue;
    }

    /**
     * This method is a getter for the number of instances of the assets we bought.
     * 
     * @return the number of assets we bought.
     */
    public int nBought() {
        return this.nBought;
    }

    /**
     * This method is a getter for the date of the purchase of the investment.
     * 
     * @return the date of the purchase of the investment.
     */
    public LocalDate getpurchasedDate() {
        return this.purchaseDate;
    }

    /**
     * This method is a getter for the original total value of the investment.
     * 
     * @return the total original value of the investment.
     */
    public BigDecimal totalOriginalValue() {
        return this.originalSingleValue.multiply(BigDecimal.valueOf(this.nBought));
    }

    /**
     * This method is a getter for the current total value of the investment.
     * 
     * @return the total current value of the investment.
     */
    public BigDecimal totalCurrentValue() {
        return this.currentSingleValue.multiply(BigDecimal.valueOf(this.nBought));
    }

    /**
     * This method is used to update the current value of the asset and assign the necessary modifications.
     */
    public void updateCurrentValue() {
        //TODO
    }

    /**
     * This method will be used to set the current value, and will be called by the updateCurrentValue
     * or by the asset when it updates, because it should always reflect the last update made.
     * 
     * @param newValue the new value.
     */
    public void setCurrentValue(final BigDecimal newValue) {
        this.currentSingleValue = newValue;
    }

    /**
     * This method is the getter for the price variation list, which we will use to make graphs.
     * 
     * @return the price variation list.
     */
    public List<BigDecimal> getPriceVariationList() {
        return List.copyOf(this.priceVariationList);
    }

    /**
     * This method will be used to calculate and return the total current profit made by the asset,
     * which is simply the difference in price for speculative assets, but needs to account for dividends
     * or interests in case of shares or loans. This also means that these two types of investments will need to
     * memorize the revenues they produce, but it will be a job to do on their specific classes and not on this one.
     * 
     * @return the Current total profit made using the ivestment.
     */
    public abstract BigDecimal getCurrentProfit();
}
