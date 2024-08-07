package com.bankingservice.bank.utils;

import java.math.BigDecimal;
import java.time.Year;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.repository.UserRepository;

public class AccountUtils {
    
    public static final String ACCOUNT_ALREADY_EXISTS = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This email already has an account associated with it";
    public static final String ACCOUNT_SUCCESSFUL_CREATION = "002";
    public static final String ACCOUNT_SUCCESSFUL_CREATION_MESSAGE = "Account has been successfully created";
    public static final String ACCOUNT_NOT_FOUND = "003";
    public static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account does not exist";
    public static final String ACCOUNT_FOUND = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found";
    public static final String INSUFFICIENT_FUNDS = "005";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Insufficient funds to tranfer";
    public static final String ACCOUNT_TRANSFER_SUCCESSFUL = "006";
    public static final String ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE = "Account transfer successful";
    public static final String ACCOUNT_ADD_SUCCESSFUL = "007";
    public static final String ACCOUNT_ADD_SUCCESSFUL_MESSAGE = "Account add successful";


    
    /**
     * curreent year followed by random 6 digits
     */
    public static String generateAccountNumber(UserRepository userRepository) {

    Year currentYear = Year.now();
    int min = 100000;
    int max = 999999;

    //generating random number between min and max
    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

    String year = currentYear.toString();
    String randomnum = Integer.toString(random_int);

    String accountNumber = year + randomnum;

    boolean isAccountExists = userRepository.existsByAccountNumber(accountNumber);

    if (isAccountExists){
        return generateAccountNumber(userRepository);
    }
    return accountNumber;
    }

    // Hashing user password and storing the salt
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        
        // Combine the salt and hashed password into a single string
        String saltedHashedPassword = salt + hashedPassword;
        
        return saltedHashedPassword;
    }

    // Verifying user login
    public static boolean verifyPassword(String enteredPassword, String storedSaltedHashedPassword) {
        // Extract the salt from the stored saltedHashedPassword
        String storedSalt = storedSaltedHashedPassword.substring(0, 29); // The salt is the first 29 characters
        
        // Extract the stored hashed password (including the salt)
        String storedHashedPassword = storedSaltedHashedPassword.substring(29);
        
        // Use the extracted salt to hash the entered password
        String hashedPasswordToCheck = BCrypt.hashpw(enteredPassword, storedSalt);
        
        // Compare the newly generated hash with the stored hashed password
        return hashedPasswordToCheck.equals(storedHashedPassword);
    }

    public static Boolean isTranferPossible(BigDecimal storedMoney, BigDecimal takeMoney) {
        int comparison = storedMoney.compareTo(takeMoney);
        if (comparison >= 0) {
            return true; // storedMoney is greater than or equal to takeMoney
        }
        return false; // storedMoney is less than takeMoney
    }

        public static void receiveAdd(BigDecimal amount, User receiver, String accountType){
        if ("cheque".equals(accountType)){
            receiver.setAccountChequings(receiver.getAccountChequings().add(amount));
        }
        else if ("savings".equals(accountType)){
            receiver.setAccountSavings(receiver.getAccountSavings().add(amount));
        }
        else{
            receiver.setAccountCredit(receiver.getAccountCredit().add(amount));
        }

    }
}

