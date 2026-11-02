package project.model.assets;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.LinkedHashMap;

/**
 * This abstract class implements the base functionality
 * of an asset. The method to upload the price variation
 * is still abstract. 
 * 
 */
public abstract class AbstractAsset implements AssetInterface {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final String INPUT_ERROR_STRING = " is null or not valid.";
    private final String assetType;
    private final String assetName;
    private final Map<LocalDate, BigDecimal> variation;
    private LocalDate lastUpdate;

    /**
     * This is the costructor for the AbstractAsset class. 
     * 
     * @param startingPrice the very first price that is memorized
     *      on the variation
     * @param name the name of the asset
     * @param type the type of asset
     */
    public AbstractAsset(
        final BigDecimal startingPrice,
        final String name,
        final String type
    ) {
        this.checkArgument(startingPrice, bd -> ((BigDecimal) bd).compareTo(BigDecimal.ZERO) < 0, "Starting price");
        this.checkArgument(name, n -> ((String) n).isBlank(), "Name");
        this.checkArgument(type, t -> ((String) t).isEmpty(), "Type");
        this.assetType = type;
        this.assetName = name;
        this.variation = new LinkedHashMap<>();
        this.lastUpdate = LocalDate.now();
        this.variation.put(this.lastUpdate, startingPrice);
    }

    /**
     * Method used to avoid the repetation of the code in che costructor. 
     * Check for some imperfections on the costructor's parameters. 
     * 
     * @param obj the non nullable object
     * @param predicate second test to validate
     * @param name string to refere at the parameter name
     */
    private void checkArgument(final Object obj, final Predicate<Object> predicate, final String name) {
        if (Objects.isNull(obj) || predicate.test(obj)) {
            throw new IllegalArgumentException(name.concat(INPUT_ERROR_STRING));
        }
    }

    /**
     * This method calculate the new price of 
     * the asset.
     * 
     * @return the new price calculated
     */
    protected abstract BigDecimal getNewPrice();

    /**
     * This method returns the name of the asset.
     * 
     * @return the name of the asset.
     */
    @Override
    public String getAssetName() {
        return this.assetName;
    }

    /**
     * This method returns the current price for a single instance of the asset.
     * 
     * @return the current price for a single instance of the asset.
     */
    @Override
    public BigDecimal getSinglePrice() {
        return ((java.util.SequencedMap<LocalDate, BigDecimal>) this.variation)
            .lastEntry().getValue();
    }

    /**
     * This method returns the type of the asset.
     * 
     * @return the type of asset as a String.
     */
    @Override
    public String getAssetType() {
        return this.assetType;
    }

    /**
     * This method is used to update the price variation.
     * For semplicity, we update che price variation list
     * one time a day. 
     * 
     * @return the percentage of the price variation as a BigDecimal.
     */
    @Override
    public BigDecimal priceVariation() {
        final LocalDate now = LocalDate.now();
        final BigDecimal lastPrice = this.getSinglePrice();
        BigDecimal newPrice = lastPrice;

        if (now.compareTo(this.lastUpdate) != 0) {
            newPrice = this.getNewPrice();
            this.lastUpdate = now;
            this.variation.put(now, newPrice);
        }
        return newPrice.subtract(lastPrice).multiply(ONE_HUNDRED);
    }

    /**
     * This method is used to extract the price variation history to later show it on a graph.
     * 
     * @return the List of the recorded price variation.
     */
    @Override
    public Map<LocalDate, BigDecimal> getVariationList() {
        return new LinkedHashMap<>(this.variation); 
    }
}
