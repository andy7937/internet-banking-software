package com.bankingservice.bank.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminAccountInfo {

    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;
    private String username;
    private String address;
    private String accountType;
    private String accountNumber;
    private BigDecimal accountChequings;
    private BigDecimal accountSavings;
    private BigDecimal accountCredit;
    
}


