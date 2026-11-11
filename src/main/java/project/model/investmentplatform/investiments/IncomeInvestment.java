package project.model.investmentplatform.investiments;

import java.math.BigDecimal;
import java.time.LocalDate;

import project.model.investmentplatform.assets.AssetInterface;
import project.utility.InputValidator;

/**
 * This class is the implementation for all the investments which produce income other than
 * their price variation, such as bounds and stocks. For this reason, it implements the
 * IncomeInvestmentInterface, but also extends the SpeculativeInvestment because its methods
 * are the same for this class.
 */
public final class IncomeInvestment extends SpeculativeInvestment implements IncomeInvestmentInterface {

    private BigDecimal dividendsReceived;

    /**
     * This method is the constructor for the StockInvestment.
     * 
     * @param asset the asset to which this investment refers.
     * @param originalSingleValue the original value for a single instance of the asset.
     * @param nBought the number of instances of the asset that this investment refers to.
     * @param purchaseDate the dato on which this investment was purchased.
     */
    public IncomeInvestment(
        final AssetInterface asset,
        final BigDecimal originalSingleValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {
        super(asset, originalSingleValue, nBought, purchaseDate);
        this.dividendsReceived = BigDecimal.ZERO;
    }

    /**
     * This method will be used to calculate and return the total current profit made by the investment,
     * which is the difference in price plus dividends (if it is a stock investment)  or interests
     * (if it is a bound investment) that may have been registered.
     * 
     * @return the Current total profit made by the ivestment.
     */
    @Override
    public BigDecimal getCurrentProfit() {
        return super.totalCurrentValue().subtract(super.totalOriginalValue()).add(dividendsReceived);
    }

    /**
     * This method is used to register a specific income, which can be dividend (if it is a stock investment)
     * or interest (if it is a bound investment).
     * 
     * @param amount the amount of the income
     */
    @Override
    public void registerIncome(final BigDecimal amount) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> p.compareTo(BigDecimal.ZERO) > 0, "Amount");
        /* Actual method */
        this.dividendsReceived = this.dividendsReceived.add(amount);
    }
}
