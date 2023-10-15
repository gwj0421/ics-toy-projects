package com.example.ics.controller;

import com.example.ics.dto.user.*;
import com.example.ics.service.UserManagementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Slf4j
public class SiteUserController {
    private final UserManagementService userManagementService;

    public SiteUserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/login")
    public String login(@SessionAttribute(name = "errorMsg", required = false) String errorMsg, Model model) {
        if (errorMsg != null) {
            model.addAttribute("errorMsg", errorMsg);
        }
        model.addAttribute("userLoginForm", new UserLoginForm());
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signupForm";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 맞지 않습니다");
        }
        try {
            userManagementService.createUser(userCreateForm.getUserID(), userCreateForm.getName(), userCreateForm.getPassword1(), ProviderType.LOCAL, RoleType.USER);
        } catch (Exception e) {
            bindingResult.rejectValue("signupFailed", e.getMessage());
            return "signupForm";
        }
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String userDashBoard(Model model) {
        model.addAttribute("users", userManagementService.findAll());
        return "userDashboardForm";
    }

}
