package com.bankingservice.bank.service;

import com.bankingservice.bank.dto.AddRequest;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.dto.UserRequest;
import com.bankingservice.bank.dto.AdminAccountInfo;
import com.bankingservice.bank.dto.AdminEnquiry;
import com.bankingservice.bank.dto.AdminRequest;



public interface UserService {

    EndPointResponse createUser(UserRequest userRequest);
    EndPointResponse balanceEnquiry(EnquiryRequest Request);
    String nameEnquiry(EnquiryRequest Request);
    EndPointResponse tranferMoney(TranferRequest Request);
    EndPointResponse addMoney(AddRequest Request);
    AdminEnquiry adminEnquiry(AdminRequest Request);

}

