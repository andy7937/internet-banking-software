package com.bankingservice.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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


        // Check if user exists in the database and the password/username are both correct
        if (userRepository.existsByUsername(username) && userRepository.existsByPassword(password)) {
            return "redirect:/dashboard"; // Redirect to a user dashboard page
        } else {
            model.addAttribute("error", "Incorrect username or password");
            return "login";
        }
    }

}
