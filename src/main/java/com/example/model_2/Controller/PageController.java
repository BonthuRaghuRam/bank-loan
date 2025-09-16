package com.example.model_2.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String home() {
        return "customer-form"; // This maps to customer-form.html in templates
    }
    @GetMapping("/customer-form")
    public String customer() {
        return "customer-form"; // This maps to customer-form.html in templates
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // maps to login.html in templates
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // maps to login.html in templates
    }

}

