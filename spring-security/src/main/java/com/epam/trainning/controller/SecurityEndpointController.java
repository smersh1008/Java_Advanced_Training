package com.epam.trainning.controller;

import com.epam.trainning.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class SecurityEndpointController {
    private static final Random RANDOM = new Random();

    private final LoginAttemptService loginAttemptService;

    @GetMapping("info")
    public String info(final Model model) {
        model.addAttribute("number", RANDOM.nextInt(Integer.MAX_VALUE));
        return "info";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

    @GetMapping("admin")
    public String admin(final Model model) {
        model.addAttribute("blockedUsers", loginAttemptService.getBlockedUsers());
        return "admin";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}