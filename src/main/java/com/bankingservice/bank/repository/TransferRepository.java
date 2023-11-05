package com.bankingservice.bank.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bankingservice.bank.entity.Transactions;

@Repository
public interface TransferRepository extends JpaRepository<Transactions, Long> {

    Transactions findByAccountSender(String accountSender);
    Transactions findByAccountReceiver(String accountReceiver);

    // find transaction history from sender or receiver
       @Query("SELECT t FROM Transactions t WHERE t.accountSender = :accountSender OR t.accountReceiver = :accountReceiver")
    List<Transactions> findByAccountSenderOrAccountReceiver(@Param("accountSender") String accountSender, @Param("accountReceiver") String accountReceiver, Sort sort);
    
}