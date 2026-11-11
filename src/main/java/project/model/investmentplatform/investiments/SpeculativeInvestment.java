package project.model.investmentplatform.investiments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import project.model.investmentplatform.assets.AssetInterface;
import project.utility.InputValidator;

/**
 * This class implements the InvestmentInterface and will be used for speculative
 * type of investments, because it contains everything needed for a basic investment
 * which does not provide any income besides its price variation.
 * It will also be used as a base to be extended by the other types of investment to
 * reuse the code for all the base implementation of the investment.
 */
public class SpeculativeInvestment implements InvestmentInterface {

    private final List<BigDecimal> priceVariationList;
    private final AssetInterface asset;
    private final LocalDate purchaseDate;
    private final BigDecimal originalSingleValue;
    private final int nBought;
    private BigDecimal currentSingleValue;

    /**
     * This method is the constructor for the speculative investments.
     * 
     * @param asset the asset to which this investment refers to.
     * @param originalSingleValue the original value for a single instance.
     * @param nBought the number of instances of the asset bought.
     * @param purchaseDate the date on which we purchased the investment.
     */
    public SpeculativeInvestment(
        final AssetInterface asset,
        final BigDecimal originalSingleValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {
        /* Input validation */
        InputValidator.checkArgument(asset, p -> true, "Asset");
        InputValidator.checkArgument(originalSingleValue, p -> p.compareTo(BigDecimal.ZERO) > 0, "Original single value");
        InputValidator.checkArgument(nBought, p -> p > 0, "Number bought");
        InputValidator.checkArgument(purchaseDate, p -> true, "Purchase date");
        /* Actual constructor */
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
    @Override
    public final AssetInterface getAsset() {
        return this.asset;
    }

    /**
     * This method is a getter for the original value of a single instance of the asset.
     * 
     * @return the original value for a single instance of the asset, as it was when the investment started.
     */
    @Override
    public final BigDecimal getOriginalSingleValue() {
        return this.originalSingleValue;
    }

    /**
     * This method is a getter for the number of instances of the assets we bought.
     * 
     * @return the number of assets we bought.
     */
    @Override
    public final int nBought() {
        return this.nBought;
    }

    /**
     * This method is a getter for the date of the purchase of the investment.
     * 
     * @return the date of the purchase of the investment.
     */
    @Override
    public final LocalDate getpurchasedDate() {
        return this.purchaseDate;
    }

    /**
     * This method is a getter for the original total value of the investment.
     * 
     * @return the total original value of the investment.
     */
    @Override
    public final BigDecimal totalOriginalValue() {
        return this.originalSingleValue.multiply(BigDecimal.valueOf(this.nBought));
    }

    /**
     * Method to get the value in gain or loss of the value of the investment,
     * regardless of interests or dividends that may have been obtained.
     * 
     * @return the value in gain or loss of the investiment in the current valute.
     */
    @Override
    public final BigDecimal totalVariationValue() {
        return this.totalCurrentValue().subtract(this.totalOriginalValue());
    }

    /**
     * This method is a getter for the current total value of the investment.
     * 
     * @return the total current value of the investment.
     */
    @Override
    public final BigDecimal totalCurrentValue() {
        return this.currentSingleValue.multiply(BigDecimal.valueOf(this.nBought));
    }

    /**
     * This method is used to update the current value of the asset and assign the necessary modifications.
     */
    @Override
    public final void updateCurrentValue() {
        this.asset.priceVariation();
    }

    /**
     * This method will be used to set the current value, and will be called by the updateCurrentValue
     * or by the asset when it updates, because it should always reflect the last update made.
     * 
     * @param newValue the new value.
     */
    @Override
    public final void setCurrentValue(final BigDecimal newValue) {
        this.currentSingleValue = newValue;
    }

    /**
     * This method is the getter for the price variation list, which we will use to make graphs.
     * 
     * @return the price variation list.
     */
    @Override
    public final List<BigDecimal> getPriceVariationList() {
        return List.copyOf(this.priceVariationList);
    }
}
