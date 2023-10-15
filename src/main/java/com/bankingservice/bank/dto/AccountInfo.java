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

public class AccountInfo {
    
    private String name;
    private String accountNumber;
    private BigDecimal accountBalance;
}
