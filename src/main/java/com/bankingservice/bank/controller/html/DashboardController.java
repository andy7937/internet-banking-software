package com.bankingservice.bank.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @PostMapping("/logout")
    public String logout() {
        // Perform any necessary logout actions (e.g., clearing user session).
        return "home"; // Redirect to the "home.html" page.
    }
}
