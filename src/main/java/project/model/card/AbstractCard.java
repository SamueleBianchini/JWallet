package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.Transaction;

public abstract class AbstractCard implements CardInterface{
    protected final List<Transaction> cardTransactions;
    protected final String cardNumber;
    protected final String cardHolder;
    protected final LocalDate expiryDate;
    protected final String cvv;
    protected final String pin;
    protected boolean isFrozen;
    

    public AbstractCard(final String cardNumber, final String cardHolder, final LocalDate expiryDate, final String cvv, final String pin){
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.pin = pin;
        isFrozen = false;
        this.cardTransactions = new ArrayList<>();
    }

    public String getCardNumber(){
        //TODO
        throw new UnsupportedOperationException();
    }

    public String getMaskedCardNumber(){
        //TODO
        throw new UnsupportedOperationException();
    }

    public LocalDate getExpiryDate(){
        //TODO
        throw new UnsupportedOperationException();
    }

    public String getCardHolder(){
        //TODO
        throw new UnsupportedOperationException();
    }

    public boolean isFrozen(){
        return isFrozen;
    }

    public void freeze(){
        isFrozen = true;
    }

    public void unfreeze(){
        isFrozen = false;
    }

    public boolean verifyPin(String pin){
        //TODO
        throw new UnsupportedOperationException();
    }

    public abstract boolean executePayment(BigDecimal amount);
    
    public List<Transaction> getTransactionHistory(){
        return Collections.unmodifiableList(cardTransactions);
    }
}
