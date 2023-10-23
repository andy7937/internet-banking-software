package com.bankingservice.bank.controller.html;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankingservice.bank.dto.UserRequest;
import com.bankingservice.bank.entity.User;
import com.bankingservice.bank.repository.UserRepository;
import com.bankingservice.bank.service.UserServiceImpl;
import com.bankingservice.bank.utils.AccountUtils;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This returns the "login.html" Thymeleaf template.
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // This returns the "signup.html" Thymeleaf template.
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model, HttpSession session) {

        User user = userRepository.findByUsername(username);
        // Check if the user exists and the password matches
        if (user != null && AccountUtils.verifyPassword(password, user.getPassword())) {
            // Passwords match; the user is authenticated.
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Incorrect username or password");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int age,
            @RequestParam String phoneNumber,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String address,
            @RequestParam String accountType,

            Model model
            ) {

        int check = 0;

        // Check if the email, username or phonenumber exists in the database
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("erroremail", "Email already registered");
            check = 1;
        }
        if (userRepository.existsByUsername(username)){
            model.addAttribute("errorusername", "Username already exists");
            check = 1;

        }
        if (userRepository.existsByPhoneNumber(phoneNumber)){
            model.addAttribute("errorphone", "Phone Number already registered");
            check = 1;

        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorpassword", "Passwords do not match");
            check = 1;

        }
        if (check == 0) {
             // make the first letter of the first name and last name uppercase
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
            
              UserRequest userRequest = new UserRequest();
                    userRequest.setFirstName(firstName);
                    userRequest.setLastName(lastName);
                    userRequest.setAge(age);
                    userRequest.setPhoneNumber(phoneNumber);
                    userRequest.setEmail(email);
                    userRequest.setUsername(username);
                    userRequest.setPassword(password);
                    userRequest.setAddress(address);
                    userRequest.setAccountType(accountType);
                    userService.createUser(userRequest); 
                    return "home"; // Redirect to the login page.
        }
        return "register";
      
    }
}
