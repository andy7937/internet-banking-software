package com.bankingservice.bank.controller.html;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankingservice.bank.dto.AdminEnquiry;
import com.bankingservice.bank.dto.AdminRequest;
import com.bankingservice.bank.service.UserServiceImpl;

@Controller
public class AdminDashboardController {

    @Autowired
    UserServiceImpl userService;

    // Mapping GET requests to /admin/dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admindashboard"; // This returns the "admindashboard.html" Thymeleaf template.
    }

    // Mapping POST requests to /admin/login
    @PostMapping("/admin/login")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
                

        // Simplified authentication logic (should not be hardcoded)
        if ("admin".equals(username) && "admin".equals(password)) {
            // Admin authenticated; redirect to admin dashboard
            return "redirect:/admin/dashboard";
        } else {
            // Incorrect credentials; return to login page with error
            model.addAttribute("error", "Incorrect username or password");
            return "admin";
        }
    }

    // Mapping POST requests to /admin/enquiry
    @PostMapping("/admin/enquiry")
    public String adminDetail(
            @RequestParam String accountNumber,
            Model model, HttpSession session) {

        AdminRequest adminRequest = new AdminRequest(accountNumber);
        AdminEnquiry adminEnquiry = userService.adminEnquiry(adminRequest);

        if (adminEnquiry.getResponseCode().equals("003")){
            model.addAttribute("error", adminEnquiry.getResponseMessage());
            return "admindashboard";
        }

        model.addAttribute("firstName", adminEnquiry.getAdminAccountInfo().getFirstName());
        model.addAttribute("lastName", adminEnquiry.getAdminAccountInfo().getLastName());
        model.addAttribute("age", adminEnquiry.getAdminAccountInfo().getAge());
        model.addAttribute("phoneNumber", adminEnquiry.getAdminAccountInfo().getPhoneNumber());
        model.addAttribute("email", adminEnquiry.getAdminAccountInfo().getEmail());
        model.addAttribute("username", adminEnquiry.getAdminAccountInfo().getUsername());
        model.addAttribute("address", adminEnquiry.getAdminAccountInfo().getAddress());
        model.addAttribute("accountType", adminEnquiry.getAdminAccountInfo().getAccountType());
        model.addAttribute("accountNumber", adminEnquiry.getAdminAccountInfo().getAccountNumber());
        model.addAttribute("accountChequings", adminEnquiry.getAdminAccountInfo().getAccountChequings());
        model.addAttribute("accountSavings", adminEnquiry.getAdminAccountInfo().getAccountSavings());
        model.addAttribute("accountCredit", adminEnquiry.getAdminAccountInfo().getAccountCredit());

        return "admindashboard";
    }
}
