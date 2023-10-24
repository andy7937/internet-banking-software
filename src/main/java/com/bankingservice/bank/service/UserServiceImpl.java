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
        String chooseAccount = Request.getSendAccount();
        BigDecimal tranferAmount = new BigDecimal(Request.getAmount());

        boolean isAccountSenderExists = userRepository.existsByAccountNumber(sender.getAccountNumber());
        boolean isAccountReceiverExists = userRepository.existsByAccountNumber(receiver.getAccountNumber());

         if (!isAccountSenderExists || !isAccountReceiverExists){ 
            return AccountUtils.ACCOUNT_NOT_FOUND_MESSAGE;
        }

        if ("cheque".equals(chooseAccount)){
            if (AccountUtils.isTranferPossible(sender.getAccountChequings(), tranferAmount)){
                sender.setAccountChequings(sender.getAccountChequings().subtract(tranferAmount));
                receiveAdd(tranferAmount, receiver, Request.getReceiveAccount());
                return AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE;
            }
        }
        else if ("savings".equals(chooseAccount)){
            if (AccountUtils.isTranferPossible(sender.getAccountSavings(), tranferAmount)){
                sender.setAccountSavings(sender.getAccountSavings().subtract(tranferAmount));
                receiveAdd(tranferAmount, receiver, Request.getReceiveAccount());
                return AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE;
            }
        }
        else{
            if (AccountUtils.isTranferPossible(sender.getAccountCredit(), tranferAmount)){
                sender.setAccountCredit(sender.getAccountCredit().subtract(tranferAmount));
                receiveAdd(tranferAmount, receiver, Request.getReceiveAccount());
                return AccountUtils.ACCOUNT_TRANSFER_SUCCESSFUL_MESSAGE;
            }

        }
        return AccountUtils.INSUFFICIENT_FUNDS_MESSAGE;
    }

    public void receiveAdd(BigDecimal amount, User receiver, String accountType){
        if ("cheque".equals(accountType)){
            receiver.setAccountChequings(receiver.getAccountChequings().add(amount));
        }
        else if ("savings".equals(accountType)){
            receiver.setAccountSavings(receiver.getAccountSavings().add(amount));
        }
        else{
            receiver.setAccountCredit(receiver.getAccountCredit().add(amount));
        }

    }
}
