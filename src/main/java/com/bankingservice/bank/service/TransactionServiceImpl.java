package com.bankingservice.bank.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.entity.Transactions;
import com.bankingservice.bank.repository.TransferRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransferRepository TransactionRepository;

    @Override
    public EndPointResponse addTransactionHistory(TranferRequest transferRequest){

        Transactions transaction = new Transactions();

        BigDecimal bigDecimal = new BigDecimal(transferRequest.getAmount());

        transaction.setAccountReceiver(transferRequest.getAccountNumReceiver());
        transaction.setAccountSender(transferRequest.getAccountNumSender());
        transaction.setSendAmount(bigDecimal);
        transaction.setSendAccountType(transferRequest.getSendAccountType());
        transaction.setReceiveAccountType(transferRequest.getReceiveAccountType());

        TransactionRepository.save(transaction);

        return null;

    }
}
