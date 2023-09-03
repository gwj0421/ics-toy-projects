package com.example.ics.service;

import com.example.ics.dto.user.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class baseController {

    @GetMapping("/")
    public String homepage(Model model, @AuthenticationPrincipal UserAdapter userAdapter) {
        if (userAdapter != null) {
            model.addAttribute("principalUsername", userAdapter.getUsername());
            model.addAttribute("principalAuthorities", userAdapter.getAuthorities());
        }

        return "index";
    }
}
