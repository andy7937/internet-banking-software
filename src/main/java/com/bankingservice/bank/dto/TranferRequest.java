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
    private AccountInfo accountSender;
    private AccountInfo accountReceiver;
    private String amount;
    private String sendAccount;
    private String receiveAccount;
}
