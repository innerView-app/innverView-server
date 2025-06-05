package com.dev.innverview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/videos")
    public String videos() {
        return "videos";
    }
}
