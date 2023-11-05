package com.bankingservice.bank.controller.html;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bankingservice.bank.dto.AccountInfo;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.entity.Transactions;
import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.repository.UserRepository;
import com.bankingservice.bank.service.TransactionServiceImpl;
import com.bankingservice.bank.service.UserServiceImpl;

@Controller
public class DashboardController {

    @Autowired
    UserServiceImpl userService;

     @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/home")
    public String home() {
        return "home"; 
    }

    // set dashboard to information about the user
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // You have the user information, e.g., user.getFirstName(), user.getLastName(), etc.
            EnquiryRequest request = new EnquiryRequest();
            request.setAccountNumber(user.getAccountNumber());
            EndPointResponse accountInfo = userService.balanceEnquiry(request);
            AccountInfo accountInfoData = accountInfo.getAccountInfo();

            String fullName = userService.nameEnquiry(request);
            BigDecimal chequings = accountInfoData.getAccountChequings();
            BigDecimal savings = accountInfoData.getAccountSavings();
            BigDecimal credit = accountInfoData.getAccountCredit();

            List<Transactions> transactions = transactionService.getTransactionHistory(user.getAccountNumber());

            int num = 0;

            for (Transactions transaction : transactions) {
                num++;
                if (num > 3) break;

                // if the user is a receiver, means the user is receiving money, and therefore show the senders information
                if (transaction.getAccountReceiver() == user.getAccountNumber()){
                    User sender = userRepository.findByAccountNumber(transaction.getAccountSender());
                    model.addAttribute("transfer" + num, sender.getUsername());
                    model.addAttribute("transfer" + num + "name", sender.getFirstName());
                    model.addAttribute("tranfer" + num + "card", transaction.getAccountSender());
                    model.addAttribute("transfer" + num + "date", transaction.getCreationTime());
                    model.addAttribute("tranfer" + num + "amount", "+ " + transaction.getSendAmount());
                }

                // if the user is a sender, means the user is sending money, and therefore show the receivers information
                else{
                    User sender = userRepository.findByAccountNumber(transaction.getAccountReceiver());
                    model.addAttribute("transfer" + num, sender.getUsername());
                    model.addAttribute("transfer" + num + "name", sender.getFirstName());
                    model.addAttribute("tranfer" + num + "card", transaction.getAccountSender());
                    model.addAttribute("transfer" + num + "date", transaction.getCreationTime());
                    model.addAttribute("tranfer" + num + "amount", "+ " + transaction.getSendAmount());
                }
            }



            model.addAttribute("name", fullName);
            model.addAttribute("chequings", chequings);
            model.addAttribute("savings", savings);
            model.addAttribute("credit", credit);

            return "dashboard"; // Return the Thymeleaf template
        } else {

            return "redirect:/login"; // Redirect to the login page or show an error message.
        }
    }






    

}
