package com.bankingservice.bank.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankingservice.bank.dto.AccountInfo;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.UserRequest;
import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.repository.UserRepository;
import com.bankingservice.bank.utils.AccountUtils;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public EndPointResponse createUser(UserRequest userRequest) {
        /**
         * Creating new user account - save new user into the database
         * check if user already exists
         */

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_ALREADY_EXISTS)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                .accountInfo(null)
                .build();
        }

        String password = AccountUtils.hashPassword(userRequest.getPassword());

        User newUser = User.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .age(userRequest.getAge())
            .phoneNumber(userRequest.getPhoneNumber())
            .email(userRequest.getEmail())
            .username(userRequest.getUsername())
            .password(password)
            .address(userRequest.getAddress())
            .accountNumber(AccountUtils.generateAccountNumber())
            .accountBalance(BigDecimal.ZERO)
            .status("ACTIVE")
            .build();

            User savedUser = userRepository.save(newUser);
            return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_SUCCESSFUL_CREATION)
                .responseMessage(AccountUtils.ACCOUNT_SUCCESSFUL_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                    .accountBalance(savedUser.getAccountBalance())
                    .accountNumber(savedUser.getAccountNumber())
                    .name(savedUser.getFirstName() + " " + savedUser.getLastName())
                    .build())
                .build();

    }
    
}
