package com.bankingservice.bank.controller.html;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bankingservice.bank.dto.TranferRequest;
import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.service.UserServiceImpl;

@Controller
public class TranferController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/transfer")
    public TranferRequest transfer(@RequestParam(name = "accountTypeSend", required = false) String accountTypeSend,
    @RequestParam(name = "accountTypeReceive", required = false) String accountTypeReceive,
    @RequestParam(name = "amount", required = false) String amount,
    @RequestParam(name = "accountNumber", required = false) String accountNumber,
     Model model, HttpSession session) {



        User user = (User) session.getAttribute("user");

        System.out.println(user.getAccountNumber());
        System.out.println(accountNumber);
        System.out.println(accountTypeSend);
        System.out.println(accountTypeReceive);
        System.out.println(amount);
        

        TranferRequest tranferRequest = new TranferRequest();
        tranferRequest.setAccountNumSender(user.getAccountNumber());
        tranferRequest.setAccountNumReceiver(accountNumber);
        tranferRequest.setAmount(amount);
        tranferRequest.setSendAccount(accountTypeSend);
        tranferRequest.setReceiveAccount(accountTypeReceive);

        if (tranferRequest.getAccountNumReceiver() != null && tranferRequest.getAccountNumSender() != null && tranferRequest.getAmount() != null && tranferRequest.getSendAccount() != null && tranferRequest.getReceiveAccount() != null){
            userService.tranferMoney(tranferRequest);
        }

        return tranferRequest;
    }
}

