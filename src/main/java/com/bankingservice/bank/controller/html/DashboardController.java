package com.bankingservice.bank.controller.html;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bankingservice.bank.dto.AccountInfo;
import com.bankingservice.bank.dto.EndPointResponse;
import com.bankingservice.bank.dto.EnquiryRequest;
import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.service.UserServiceImpl;

@Controller
public class DashboardController {

    @Autowired
    UserServiceImpl userService;

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



            model.addAttribute("name", fullName);
            model.addAttribute("chequings", chequings);
            model.addAttribute("savings", savings);
            model.addAttribute("credit", credit);

            return "dashboard"; // Return the Thymeleaf template
        } else {
            // Handle cases where the user is not authenticated or the session is not set.
            return "login"; // Redirect to the login page or show an error message.
        }
    }






    

}
