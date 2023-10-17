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
  
        User newUser = User.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .age(userRequest.getAge())
            .phoneNumber(userRequest.getPhoneNumber())
            .email(userRequest.getEmail())
            .username(userRequest.getUsername())
            .password(AccountUtils.hashPassword(userRequest.getPassword()))
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
