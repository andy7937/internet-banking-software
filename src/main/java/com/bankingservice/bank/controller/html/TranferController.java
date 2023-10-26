package com.bankingservice.bank.controller.html;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TranferController {

    @GetMapping("/transfer")
    public String transfer(@RequestParam(name = "cardType", required = false) String cardType, Model model, HttpSession session) {

        if ("cheque".equals(cardType)) {
            // Handle transfer from chequings logic
        } else if ("savings".equals(cardType)) {
            // Handle transfer from savings logic
        } else if ("credit".equals(cardType)) {
            // Handle transfer from credit logic
        }

        return null;
    }
}
