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
     * @param originalAssetValue the original value for a single instance of the asset.
     * @param nBought the number of instances of the asset that this investment refers to.
     * @param purchaseDate the date on which this investment was purchased.
     */
    void  openNewInvestment(
         AssetInterface asset,
         BigDecimal originalAssetValue,
         int nBought,
         LocalDate purchaseDate
    );

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     */
    void closeInvestment(InvestmentInterface investment);

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     * @param date the date on wich close the investments
     */
    void closeInvestment(InvestmentInterface investment,
                         LocalDate date);
    
    /**
     * This method is used to close an investment on a 
     * particular index of the investment list.
     * 
     * @param index
     */
    void closeInvestment(Integer index);
}
