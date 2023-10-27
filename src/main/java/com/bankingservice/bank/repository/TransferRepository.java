package com.bankingservice.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bankingservice.bank.entity.Transactions;

@Repository
public interface TransferRepository extends JpaRepository<Transactions, Long> {

    Transactions findByAccountSender(String accountSender);
    Transactions findByAccountReceiver(String accountReceiver);


}