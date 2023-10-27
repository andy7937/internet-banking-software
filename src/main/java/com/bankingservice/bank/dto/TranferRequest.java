package com.bankingservice.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TranferRequest {
    private String accountNumSender;
    private String accountNumReceiver;
    private String amount;
    private String sendAccountType;
    private String receiveAccountType;
}
