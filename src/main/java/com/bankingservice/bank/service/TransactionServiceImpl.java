package com.bankingservice.bank.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.entity.Transactions;
import com.bankingservice.bank.repository.TransferRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransferRepository transferRepository;

    @Override
    public EndPointResponse addTransactionHistory(TranferRequest transferRequest){

        Transactions transaction = new Transactions();

        BigDecimal bigDecimal = new BigDecimal(transferRequest.getAmount());

        transaction.setAccountReceiver(transferRequest.getAccountNumReceiver());
        transaction.setAccountSender(transferRequest.getAccountNumSender());
        transaction.setSendAmount(bigDecimal);
        transaction.setSendAccountType(transferRequest.getSendAccountType());
        transaction.setReceiveAccountType(transferRequest.getReceiveAccountType());

        transferRepository.save(transaction);

        return null;

    }

    // get transaction history from sender or receiver based off of the creation time in most recent transactions first
    @Override
    public List<Transactions> getTransactionHistory(String accountNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "creationTime");
        return transferRepository.findByAccountSenderOrAccountReceiver(accountNumber, accountNumber, sort);
        
    }
}
