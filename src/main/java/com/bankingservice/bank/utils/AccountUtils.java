package com.bankingservice.bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_ALREADY_EXISTS = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This email already has an account associated with it";
    public static final String ACCOUNT_SUCCESSFUL_CREATION = "002";
    public static final String ACCOUNT_SUCCESSFUL_CREATION_MESSAGE = "Account has been successfully created";
    
    /**
     * curreent year followed by random 6 digits
     */
    public static String generateAccountNumber() {

    Year currentYear = Year.now();
    int min = 100000;
    int max = 999999;

    //generating random number between min and max
    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

    String year = currentYear.toString();
    String randomnum = Integer.toString(random_int);

    String accountNumber = year + randomnum;

    return accountNumber;
    }

  

}

