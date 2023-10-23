package com.bankingservice.bank.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankingservice.bank.dto.AccountInfo;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.dto.TranferRequest;
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
            .accountChequings(BigDecimal.ZERO)
            .accountSavings(BigDecimal.ZERO)
            .accountCredit(BigDecimal.ZERO)
            .accountType(userRequest.getAccountType())
            .status("ACTIVE")
            .build();

            User savedUser = userRepository.save(newUser);
            return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_SUCCESSFUL_CREATION)
                .responseMessage(AccountUtils.ACCOUNT_SUCCESSFUL_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                    .accountChequings(savedUser.getAccountChequings())
                    .accountSavings(savedUser.getAccountSavings())
                    .accountCredit(savedUser.getAccountCredit())
                    .accountNumber(savedUser.getAccountNumber())
                    .name(savedUser.getFirstName() + " " + savedUser.getLastName())
                    .build())
                .build();

    }

    @Override
    public EndPointResponse balanceEnquiry(EnquiryRequest Request) {

        boolean isAccountExists = userRepository.existsByAccountNumber(Request.getAccountNumber());

        if (!isAccountExists){
            return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_NOT_FOUND)
                .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND_MESSAGE)
                .accountInfo(null)
                .build();
        }
        
        User foundUser = userRepository.findByAccountNumber(Request.getAccountNumber());
        return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                    .accountChequings(foundUser.getAccountChequings())
                    .accountSavings(foundUser.getAccountSavings())
                    .accountCredit(foundUser.getAccountCredit())
                    .accountNumber(foundUser.getAccountNumber())
                    .name(foundUser.getFirstName() + " " + foundUser.getLastName())
                    .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest Request) {
         boolean isAccountExists = userRepository.existsByAccountNumber(Request.getAccountNumber());

        if (!isAccountExists){
            return AccountUtils.ACCOUNT_NOT_FOUND;
        }
        User foundUser = userRepository.findByAccountNumber(Request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName();
    }

    @Override
    public String tranferMoney(TranferRequest Request){
        AccountInfo senderAccountInfo = (Request.getAccountSender());
        AccountInfo receiverAccountInfo = (Request.getAccountReceiver());

        User sender = userRepository.findByAccountNumber(senderAccountInfo.getAccountNumber());
        User receiver = userRepository.findByAccountNumber(receiverAccountInfo.getAccountNumber());
        String sendAccount = Request.getSendAccount();
        String receiveAccount = Request.getReceiveAccount();
        BigDecimal tranferAmount = new BigDecimal(Request.getAmount());



        boolean isAccountSenderExists = userRepository.existsByAccountNumber(Request.getAccountSender().getAccountNumber());
        boolean isAccountReceiverExists = userRepository.existsByAccountNumber(Request.getAccountReceiver().getAccountNumber());

         if (!isAccountSenderExists || !isAccountReceiverExists){ 
            return AccountUtils.ACCOUNT_NOT_FOUND;
        }


        return null;


    }

    
}
