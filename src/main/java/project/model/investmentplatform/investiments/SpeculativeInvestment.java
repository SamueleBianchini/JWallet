package project.model.investmentplatform.investiments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedMap;
import java.util.LinkedHashMap;

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

    private final Map<LocalDate, BigDecimal> priceVariationList;
    private final AssetInterface asset;
    //private final LocalDate purchaseDate;
    private final BigDecimal originalAssetValue;
    private final int nBought;
    private Optional<LocalDate> closingDate;
    //private BigDecimal currentAssetValue;

    /**
     * This method is the constructor for the speculative investments.
     * 
     * @param asset the asset to which this investment refers to.
     * @param originalAssetValue the original value for a single instance.
     * @param nBought the number of instances of the asset bought.
     * @param purchaseDate the date on which we purchased the investment.
     */
    public SpeculativeInvestment(
        final AssetInterface asset,
        final BigDecimal originalAssetValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {
        /* Input validation */
        InputValidator.checkArgument(asset, p -> true, "Asset");
        InputValidator.checkArgument(originalAssetValue, p -> p.compareTo(BigDecimal.ZERO) > 0, "Original single value");
        InputValidator.checkArgument(nBought, p -> p > 0, "Number bought");
        InputValidator.checkArgument(purchaseDate, p -> true, "Purchase date");
        /* Actual constructor */
        this.priceVariationList = new LinkedHashMap<>();
        this.priceVariationList.put(purchaseDate, BigDecimal.ZERO);
        this.asset = asset;
        this.originalAssetValue = originalAssetValue;
        //this.currentAssetValue = originalAssetValue;
        this.nBought = nBought;
        this.closingDate = Optional.empty();
        //this.purchaseDate = purchaseDate;
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
     * This method is a getter for the closing date of the investment.
     * If the investment is still open, the Optional will be empty.
     * 
     * @return the optional of the closing date.
     */
    @Override
    public Optional<LocalDate> getClosingDate() {
        return this.closingDate;
    }

    /**
     * Private method to convert the price variation list to a SequencedMap.
     * Usefull for its methods firstEntry() and lastEntry().
     * 
     * @return the converted map
     */
    private SequencedMap<LocalDate, BigDecimal> pvLinkedToSequence() {
        return (SequencedMap<LocalDate, BigDecimal>) getPriceVariationMap();
    }

    /**
     * This method is a getter for the original value of a single instance of the asset.
     * 
     * @return the original value for a single instance of the asset, as it was when the investment started.
     */
    @Override
    public final BigDecimal getOriginalAssetValue() {
        return originalAssetValue;
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
        return pvLinkedToSequence().firstEntry().getKey();
    }

    /**
     * This method is a getter for the last time that was updated
     * the price variation list and so also the current value.
     * 
     * @return the date of the last update
     */
    @Override
    public final LocalDate getLastUpdateDate() {
        return pvLinkedToSequence().lastEntry().getKey();
    } 

    /**
     * This method is a getter for the original total value of the investment.
     * 
     * @return the total original value of the investment.
     */
    @Override
    public final BigDecimal totalOriginalValue() {
        return getOriginalAssetValue().multiply(BigDecimal.valueOf(this.nBought));
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
        return asset.getSinglePrice().multiply(BigDecimal.valueOf(this.nBought));
    }

    /**
     * This method is used to update the current value of the asset and add
     * the new value of gain/loss money paired with today date.
     * 
     */
    @Override
    public final void updateCurrentValue() {
        if (getClosingDate().isEmpty()) {
            this.priceVariationList.put(LocalDate.now(), totalVariationValue());
            //setCurrentValue(asset.getSinglePrice());
        }
    }

    // /**
    //  * This method will be used to set the current value, and will be called by the updateCurrentValue
    //  * or by the asset when it updates, because it should always reflect the last update made.
    //  * 
    //  * @param newValue the new value.
    //  */
    // @Override
    // public final void setCurrentValue(final BigDecimal newValue) {
    //     this.currentAssetValue = newValue;
    // }

    /**
     * This method is the getter for the price variation list, which we will use to make graphs.
     * 
     * @return the price variation list.
     */
    @Override
    public final Map<LocalDate, BigDecimal> getPriceVariationMap() {
        return Map.copyOf(this.priceVariationList);
    }

    /**
     * This method is used to set the closing date
     * to a specific date. It must be greater or equals
     * to the last date entered on the price variation. 
     * 
     * @param date the date to close the investment. 
     */
    @Override
    public final void close(final LocalDate date) {
       updateCurrentValue();
       this.closingDate = Optional.of(date);
    }

    /**
     * This method is used to set the closing date
     * to today.
     * 
     */
    @Override
    public final void close() {
        close(LocalDate.now());
    }

}
