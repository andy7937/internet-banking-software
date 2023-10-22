package com.bankingservice.bank.service;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.dto.UserRequest;


public interface UserService {

    EndPointResponse createUser(UserRequest userRequest);
    EndPointResponse balanceEnquiry(EnquiryRequest Request);
    String nameEnquiry(EnquiryRequest Request);

}

