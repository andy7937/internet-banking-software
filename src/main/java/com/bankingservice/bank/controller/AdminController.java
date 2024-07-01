package com.bankingservice.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bankingservice.bank.dto.AddRequest;
import com.bankingservice.bank.dto.AdminAccountInfo;
import com.bankingservice.bank.dto.AdminEnquiry;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.entity.Transactions;
import com.bankingservice.bank.repository.TransferRepository;
import com.bankingservice.bank.service.TransactionService;
import com.bankingservice.bank.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;
    
    @GetMapping("/adminEnquiry")
    public AdminEnquiry balanceEnquiry(@RequestBody AdminAccountInfo Request) {
        return userService.adminEnquiry(Request);
    }

    @PostMapping("/addMoney")
        public EndPointResponse addMoney(@RequestBody AddRequest userRequest) {
        return userService.addMoney(userRequest);
    }

    // @GetMapping("transactionEnquiry")
    // public transactionEnquiry getTransactionHistory(@RequestBody String accountNumber){

    // }


        

}
