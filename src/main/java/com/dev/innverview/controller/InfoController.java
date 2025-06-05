package com.dev.innverview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Innverview!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Innverview application!";
    }
}
