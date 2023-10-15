package com.bankingservice.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.UserRequest;
import com.bankingservice.bank.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public EndPointResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
    
}
