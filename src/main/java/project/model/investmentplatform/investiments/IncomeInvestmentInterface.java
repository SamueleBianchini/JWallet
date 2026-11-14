package project.model.investmentplatform.investiments;

import java.math.BigDecimal;

/**
 * This class extends the investment interface and serves as an interface for the IncomeInvestments,
 * which are the investments that produce income other than their price variation, such as bounds and stocks.
 */
public interface IncomeInvestmentInterface extends InvestmentInterface {

    /**
     * This method will be used to calculate and return the total current profit made by the investment,
     * which is the difference in price plus dividends (if it is a stock investment)  or interests
     * (if it is a bound investment) that may have been registered.
     * 
     * @return the Current total profit made by the ivestment.
     */
    BigDecimal getCurrentProfit();

    /**
     * This method is used to register a specific income, which can be dividend (if it is a stock investment)
     * or interest (if it is a bound investment).
     * 
     * @param amount the amount of the income
     */
    void registerIncome(BigDecimal amount);
}
