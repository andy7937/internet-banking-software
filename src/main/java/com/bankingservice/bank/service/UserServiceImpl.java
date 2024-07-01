package com.bankingservice.bank.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankingservice.bank.dto.AccountInfo;
import com.bankingservice.bank.dto.AddRequest;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.dto.UserRequest;
import com.bankingservice.bank.dto.AdminAccountInfo;
import com.bankingservice.bank.dto.AdminEnquiry;
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
            .accountNumber(AccountUtils.generateAccountNumber(userRepository))
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
    public EndPointResponse tranferMoney(TranferRequest Request){

        User sender = userRepository.findByAccountNumber(Request.getAccountNumSender());
        User receiver = userRepository.findByAccountNumber(Request.getAccountNumReceiver());
        String chooseAccount = Request.getSendAccountType();
        String receiveAccount = Request.getReceiveAccountType();
        BigDecimal tranferAmount = new BigDecimal(Request.getAmount());

        boolean isAccountSenderExists = userRepository.existsByAccountNumber(sender.getAccountNumber());
        boolean isAccountReceiverExists = userRepository.existsByAccountNumber(receiver.getAccountNumber());

         if (!isAccountSenderExists || !isAccountReceiverExists){ 
                        return EndPointResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL)
        .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountChequings(sender.getAccountChequings())
            .accountSavings(sender.getAccountSavings())
            .accountCredit(sender.getAccountCredit())
            .accountNumber(sender.getAccountNumber())
            .name(sender.getFirstName() + " " + sender.getLastName())
            .build())
        .build();
        }

        if ("cheque".equals(chooseAccount)){
            if (AccountUtils.isTranferPossible(sender.getAccountChequings(), tranferAmount)){
                sender.setAccountChequings(sender.getAccountChequings().subtract(tranferAmount));
                AccountUtils.receiveAdd(tranferAmount, receiver, receiveAccount);
                userRepository.save(sender);
                userRepository.save(receiver);

                        return EndPointResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL)
        .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountChequings(sender.getAccountChequings())
            .accountSavings(sender.getAccountSavings())
            .accountCredit(sender.getAccountCredit())
            .accountNumber(sender.getAccountNumber())
            .name(sender.getFirstName() + " " + sender.getLastName())
            .build())
        .build();
            }
        }
        else if ("savings".equals(chooseAccount)){
            if (AccountUtils.isTranferPossible(sender.getAccountSavings(), tranferAmount)){
                sender.setAccountSavings(sender.getAccountSavings().subtract(tranferAmount));
                AccountUtils.receiveAdd(tranferAmount, receiver, receiveAccount);
                userRepository.save(sender);
                userRepository.save(receiver);

                        return EndPointResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL)
        .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountChequings(sender.getAccountChequings())
            .accountSavings(sender.getAccountSavings())
            .accountCredit(sender.getAccountCredit())
            .accountNumber(sender.getAccountNumber())
            .name(sender.getFirstName() + " " + sender.getLastName())
            .build())
        .build();
            }
        }
        else{
            if (AccountUtils.isTranferPossible(sender.getAccountCredit(), tranferAmount)){
                sender.setAccountCredit(sender.getAccountCredit().subtract(tranferAmount));
                AccountUtils.receiveAdd(tranferAmount, receiver, receiveAccount);
                userRepository.save(sender);
                userRepository.save(receiver);

                          return EndPointResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL)
        .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountChequings(sender.getAccountChequings())
            .accountSavings(sender.getAccountSavings())
            .accountCredit(sender.getAccountCredit())
            .accountNumber(sender.getAccountNumber())
            .name(sender.getFirstName() + " " + sender.getLastName())
            .build())
        .build();
            }
        }


        return EndPointResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS)
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .accountInfo(null)
                .build();
        

    }





    @Override
    public EndPointResponse addMoney(AddRequest Request){

        boolean isAccountExists = userRepository.existsByAccountNumber(Request.getAccountNumber());

        if (!isAccountExists){
            return EndPointResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_NOT_FOUND)
                .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND_MESSAGE)
                .accountInfo(null)
                .build();
        }
        
        User foundUser = userRepository.findByAccountNumber(Request.getAccountNumber());
        

        AccountUtils.receiveAdd(Request.getAmount(), foundUser, Request.getAccountType());
        userRepository.save(foundUser);
        

         return EndPointResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_ADD_SUCCESSFUL)
        .responseMessage(AccountUtils.ACCOUNT_ADD_SUCCESSFUL_MESSAGE)
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
    public AdminEnquiry adminEnquiry(AdminAccountInfo Request){

        boolean isAccountExists = userRepository.existsByAccountNumber(Request.getAccountNumber());

        if (!isAccountExists){
            return AdminEnquiry.builder()
                .responseCode(AccountUtils.ACCOUNT_NOT_FOUND)
                .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND_MESSAGE)
                .adminAccountInfo(null)
                .build();
        }

        User foundUser = userRepository.findByAccountNumber(Request.getAccountNumber());

        return AdminEnquiry.builder()
        .responseCode(AccountUtils.ACCOUNT_FOUND)
        .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
        .adminAccountInfo(AdminAccountInfo.builder()
            .firstName(foundUser.getFirstName())
            .lastName(foundUser.getLastName())
            .age(foundUser.getAge())
            .phoneNumber(foundUser.getPhoneNumber())
            .email(foundUser.getEmail())
            .username(foundUser.getUsername())
            .address(foundUser.getAddress())
            .accountNumber(AccountUtils.generateAccountNumber(userRepository))
            .accountChequings(foundUser.getAccountChequings())
            .accountSavings(foundUser.getAccountSavings())
            .accountCredit(foundUser.getAccountCredit())
            .accountType(foundUser.getAccountType())
        .build())
        .build();
        
    }
}
