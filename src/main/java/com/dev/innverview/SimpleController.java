package com.dev.innverview;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String homePage() {
        return "Welcome to Innverview!";
    }

    @GetMapping("/hello")
    public String helloPage() {
        return "Hello from Innverview application!";
    }
}