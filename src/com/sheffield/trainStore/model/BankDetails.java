package com.sheffield.trainStore.model;

import java.time.LocalDate;
import java.util.Date;

public class BankDetails {

    private String userID;
    private String bankCardName;
    private String cardHolderName;
    private String cardNumber;
    private String cardExpiryDate;
    private String securityCode;

    public BankDetails(String userID, String bankCardName, String cardHolderName, String cardNumber,
                       String cardExpiryDate, String securityCode) {
        this.userID = userID;
        this.bankCardName = bankCardName;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.securityCode = securityCode;
    }

    public String getUserID() {
        return userID;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

}
