package com.bankingservice.bank.service;

import java.util.List;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.entity.Transactions;

public interface TransactionService {

    EndPointResponse addTransactionHistory(TranferRequest transferRequest);
    List<Transactions> getTransactionHistory(String accountNumber);

}
