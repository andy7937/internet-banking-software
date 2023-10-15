package com.bankingservice.bank.service;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.UserRequest;


public interface UserService {

    EndPointResponse createUser(UserRequest userRequest);

}

