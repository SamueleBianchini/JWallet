package project.model.investmentplatform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import project.model.investmentplatform.assets.AssetInterface;
import project.model.investmentplatform.investiments.InvestmentInterface;
import project.persistence.GsonPersistenceManager;

/**
 * InvestmentPlatform is the basic implementation of {@link InvestmentPlatformInterface}
 * interface. Considering that the JSON file containing all the investments is still 
 * created, and for simplicity will read and load the investments from the file only
 * once, the creation and the implementation of the investments list will done on the
 * costructor. When needed, will called {@link saveInvestmentJSON} to save all the
 * investment on file, recommended before the deallocation of the InvestmentPlatform 
 * object.
 * 
 */
public class InvestmentPlatform implements InvestimentPlatformInterface{
    
    private final GsonPersistenceManager<InvestmentInterface> gson;
    private final List<InvestmentInterface> investments;

    public InvestmentPlatform(
        final String path
    ) {
        this.gson = new GsonPersistenceManager<>(path, InvestmentInterface.class);
        this.investments = new ArrayList<>();
        
    }
    /**
     * Method that returns all the investments associated
     * with the {@link BanckAccountImpl} used, indipenendtly
     * that the investment is open or closed. 
     * 
     * @return the list of investments
     */
    public  List<InvestmentInterface> getInvestmentsList() {

    }

    /**
     * Method that returns all the open investments associated
     * with the {@link BanckAccountImpl} used. 
     * 
     * @return the list of open investment
     */
    public List<InvestmentInterface> getOpenIvestmentList() {

    }

    /**
     * This method is used to open a new investment.
     * 
     * @param asset the asset to which this investment refers.
     * @param originalSingleValue the original value for a single instance of the asset.
     * @param nBought the number of instances of the asset that this investment refers to.
     * @param purchaseDate the date on which this investment was purchased.
     * @return true if all the operations went well, false otherwise.
     */
    public  boolean openNewInvestment(
        final AssetInterface asset,
        final BigDecimal originalSingleValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {

    }

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     * @return true if all the operations went well, false otherwise.
     */
    public boolean closeInvetment(final InvestmentInterface investment) {

    }

    /**
     * This method is used to save all the investment
     * on the json file. 
     * 
     * @return true if all the operations went well, false otherwise.
     */
    public boolean saveInvestmentJSON() {

    }


}
