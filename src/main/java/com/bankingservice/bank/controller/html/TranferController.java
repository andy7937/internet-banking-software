package com.bankingservice.bank.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TranferController {

    @GetMapping("/transfer")
    public String transfer(@RequestParam String type) {
        if ("savings".equals(type)) {
            // Handle transfer to savings logic
        } else if ("cheque".equals(type)) {
            // Handle transfer to cheque logic
        } else if ("credit".equals(type)) {
            // Handle transfer to credit logic
        } else {
            // Handle unknown transfer type or provide a default action
        }

        // Return the appropriate view or redirect

        return "transfer"; // This returns the "transfer.html" Thymeleaf template.
    }
}
