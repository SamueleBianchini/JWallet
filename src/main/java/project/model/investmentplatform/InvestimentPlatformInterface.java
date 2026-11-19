package project.model.investmentplatform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import project.model.investmentplatform.assets.AssetInterface;
import project.model.investmentplatform.investiments.InvestmentInterface;

/**
 * 
 * InvestimentPlatformInt is a platform used to open,
 * manage and close investments. 
 * 
 */
public interface InvestimentPlatformInterface {
    
    /**
     * Method that returns all the investments associated
     * with the {@link BanckAccountImpl} used, indipenendtly
     * that the investment is open or closed. 
     * 
     * @return the list of investments
     */
    List<InvestmentInterface> getInvestmentsList();

    /**
     * Method that returns all the open investments associated
     * with the {@link BanckAccountImpl} used. 
     * 
     * @return the list of open investment
     */
    List<InvestmentInterface> getOpenIvestmentList();

    /**
     * This method is used to open a new investment.
     * 
     * @param asset the asset to which this investment refers.
     * @param originalSingleValue the original value for a single instance of the asset.
     * @param nBought the number of instances of the asset that this investment refers to.
     * @param purchaseDate the date on which this investment was purchased.
     * @return true if all the operations went well, false otherwise.
     */
    boolean openNewInvestment(
        final AssetInterface asset,
        final BigDecimal originalSingleValue,
        final int nBought,
        final LocalDate purchaseDate
    );

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     * @return true if all the operations went well, false otherwise.
     */
    boolean closeInvetment(final InvestmentInterface investment);

    /**
     * This method is used to save all the investment
     * on the json file. 
     * 
     * @return true if all the operations went well, false otherwise.
     */
    boolean saveInvestmentJSON();
}
