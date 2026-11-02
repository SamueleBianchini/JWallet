package project.model.assets;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.LinkedHashMap;

import project.utility.InputValidator;

/**
 * This abstract class implements the base functionality
 * of an asset. The method to upload the price variation
 * is still abstract. 
 * 
 */
public abstract class AbstractAsset implements AssetInterface {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
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
        /* Input validation */
        InputValidator.checkArgument(startingPrice, p -> p.compareTo(BigDecimal.ZERO) > 0, "Starting price");
        InputValidator.checkArgument(name, p -> !p.isBlank() && !p.isEmpty(), "Name");
        /* In questo metodo per il controllo imput del tipo, bisognerà ggiungere la verifica del tipo dell'asset
            in modo che la stringa corrisponda a una tra i tre tipi possibili. */
        InputValidator.checkArgument(type, p -> !p.isBlank() && !p.isEmpty(), "Type");
        /* Actual constructor */
        this.assetType = type;
        this.assetName = name;
        this.variation = new LinkedHashMap<>();
        this.lastUpdate = LocalDate.now();
        this.variation.put(this.lastUpdate, startingPrice);
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
