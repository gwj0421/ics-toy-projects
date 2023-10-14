package com.example.ics.controller;

import com.example.ics.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BaseController {
    private final UserManagementService userManagementService;
    @GetMapping("/")
    public String homepage(Model model) {
//        if (userAdapter != null) {
//            model.addAttribute("principalUsername", userAdapter.getUser().getUsername());
//            model.addAttribute("principalAuthorities", userAdapter.getAuthorities());
//        }

        return "index";
    }

}
