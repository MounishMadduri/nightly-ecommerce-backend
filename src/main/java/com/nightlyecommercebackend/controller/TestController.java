package com.nightlyecommercebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/public")
    public String publicApi() {
        return "Public!";
    }

    @GetMapping("/api/secure")
    public String secureApi() {
        return "Secure!";
    }
}

