package project.model.assets;

import java.math.BigDecimal;
import java.util.Random;

/**
 * RandomAsset extends the AbstractAsset
 * with the possibility to calculate the
 * new price as a random percentuage variation
 * of the last price.
 */
public class RandomAsset extends AbstractAsset {

    private static final double RAND_ORIGIN = -1.0;
    private static final double RAND_BOUND = 1.0;
    private final Random rand;

    /**
     * This is the costructor for the RandomAsset class. 
     * 
     * @param startingPrice the very first price that is memorized
     *      on the variation
     * @param name the name of the asset
     * @param type the type of asset
     */
    public RandomAsset(
        final BigDecimal startingPrice,
        final String name,
        final String type
    ) {
        super(startingPrice, name, type);
        this.rand = new Random();
    }

    /**
     * This private method return the BigDecimal 
     * istance of the next random double of the 
     * parameter rand. 
     * 
     * @return BigDecimal of the next random double
     */
    private BigDecimal getNextRandom() {
        return new BigDecimal(
            Double.toString(this.rand.nextDouble(RAND_ORIGIN, RAND_BOUND))
        );
    }

    /**
     * This method is used to update the price variation.
     * For semplicity, we update che price variation list
     * every day at 00:00 A.M.
     * The new value is a random percentuage variation of
     * the previous prive calculated.
     * 
     * @return the percentage of the price variation as a BigDecimal.
     */
    @Override
    public BigDecimal getNewPrice() {
        return this.getSinglePrice().multiply(this.getNextRandom());
    }
}
