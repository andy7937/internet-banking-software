package com.bankingservice.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bankingservice.bank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPassword(String password);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByAccountNumber(String accountNumber);
    User findById(long id);
    User findByUsername(String username);
    User findByAccountNumber(String accountNumber);
}