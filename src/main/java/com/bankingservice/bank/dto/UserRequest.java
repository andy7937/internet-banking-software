package com.bankingservice.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {

    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String address;
    private String accountType;

}
