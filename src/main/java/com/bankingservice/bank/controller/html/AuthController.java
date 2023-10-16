package com.bankingservice.bank.controller.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This returns the "login.html" Thymeleaf template.
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // This returns the "signup.html" Thymeleaf template.
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        User user = userRepository.findByUsername(username);

        // Check if the user exists and the password matches
        if (user != null && password.trim().equals(user.getPassword().trim())) {
            // Passwords match; the user is authenticated.
            return "dashboard"; // Redirect to the dashboard.
        } else {
            model.addAttribute("error", "Incorrect username or password");
        }

        return "login";
    }
}
