package com.bankingservice.bank.controller.html;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bankingservice.bank.entity.User;

@Controller
public class DashboardController {


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
            String fullName = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("name", fullName);

            return "dashboard"; // Return the Thymeleaf template
        } else {
            // Handle cases where the user is not authenticated or the session is not set.
            return "login"; // Redirect to the login page or show an error message.
        }
    }






    

}
