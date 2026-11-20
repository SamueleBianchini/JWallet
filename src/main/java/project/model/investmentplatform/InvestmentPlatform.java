package project.model.investmentplatform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.NonNull;

import project.model.investmentplatform.assets.AssetInterface;
import project.model.investmentplatform.assets.AssetType;
import project.model.investmentplatform.investiments.IncomeInvestment;
import project.model.investmentplatform.investiments.InvestmentInterface;
import project.model.investmentplatform.investiments.SpeculativeInvestment;
import project.utility.InputValidator;

/**
 * InvestimentPlatformInt is a platform used to open,
 * manage and close investments. 
 * 
 */
public class InvestmentPlatform implements InvestimentPlatformInterface {
    private final List<InvestmentInterface> investments;

    /**
     * This is the costructor of the InvestingPlatform.
     * 
     * @param inv the initial investments list
     */
    public InvestmentPlatform(final List<InvestmentInterface> inv) {
        InputValidator.checkArgument(inv, i -> i.stream().allMatch(Objects::nonNull), "Investments");
        this.investments = new ArrayList<>(inv);
        sortInvList();
    }

    /**
     * This is the costructor of the InvestingPlatform.
     * It sets the investments list as an empty list.
     * 
     */
    public InvestmentPlatform() {
        this(new ArrayList<>());
    }

    /**
     * Simple private method to sort the investment list by
     * the open date.
     * 
     */
    private void sortInvList() {
        investments.sort(Comparator.comparing(
            (@NonNull InvestmentInterface investment) -> investment.getpurchasedDate()
        ));
    }

    /**
     * Method that returns all the investments associated
     * with the {@link BanckAccountImpl} used, indipenendtly
     * that the investment is open or closed. 
     * 
     * @return the list of investments
     */
    @Override
    public List<InvestmentInterface> getInvestmentsList() {
        return List.copyOf(investments);
    }

    /**
     * Method that returns all the open investments associated
     * with the {@link BanckAccountImpl} used. 
     * 
     * @return the list of open investment
     */
    @Override
    public List<InvestmentInterface> getOpenIvestmentList() {
        return investments.stream()
                          .filter(i -> i.getClosingDate().isEmpty())
                          .toList();
    }

    /**
     * This method is used to open a new investment.
     * 
     * @param asset the asset to which this investment refers.
     * @param originalAssetValue the original value for a single instance of the asset.
     * @param nBought the number of instances of the asset that this investment refers to.
     * @param purchaseDate the date on which this investment was purchased.
     */
    @Override
    public void openNewInvestment(
        final AssetInterface asset,
        final BigDecimal originalAssetValue,
        final int nBought,
        final LocalDate purchaseDate
    ) {
        InputValidator.checkArgument(asset, p -> true, "Asset");
        InputValidator.checkArgument(originalAssetValue, p -> p.compareTo(BigDecimal.ZERO) > 0, "Original single value");
        InputValidator.checkArgument(nBought, p -> p > 0, "Number bought");
        InputValidator.checkArgument(purchaseDate, p -> true, "Purchase date");
        if (asset.getAssetType() == AssetType.BOUND) {
            investments.add(new SpeculativeInvestment(
                asset,
                originalAssetValue,
                nBought,
                purchaseDate));
        } else {
            investments.add(new IncomeInvestment(
                asset, 
                originalAssetValue, 
                nBought, 
                purchaseDate));
        }
        sortInvList();
    }

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     * @param date the date on wich close the investments
     */
    @Override
    public void closeInvestment(final InvestmentInterface investment,
                                final LocalDate date) {
        InputValidator.checkArgument(investment, i -> i.getClosingDate().isEmpty(), "Investment");
        InputValidator.checkArgument(date, d -> investment.getLastUpdateDate().isBefore(date)
                                || investment.getLastUpdateDate().equals(date), null);
        investment.close(date);
    }

    /**
     * This method is used to close a particular investment.
     * 
     * @param investment the investment to close
     */
    @Override
    public void closeInvestment(final InvestmentInterface investment) {
        closeInvestment(investment, LocalDate.now());
    }

    /**
     * This method is used to close an investment on a 
     * particular index of the investment list.
     * 
     * @param index the index of the investment to close
     *              of the investment list.
     */
    @Override
    public void closeInvestment(final Integer index) {
        InputValidator.checkArgument(index, i -> Objects.nonNull(investments.get(i)), "Index");
        closeInvestment(investments.get(index));
    }

}
