package com.bankingservice.bank.service;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.TranferRequest;

public interface TransactionService {

    EndPointResponse addTransactionHistory(TranferRequest transferRequest);


}