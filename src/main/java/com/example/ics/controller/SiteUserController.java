package com.example.ics.controller;

import com.example.ics.dto.user.ProviderType;
import com.example.ics.dto.user.RoleType;
import com.example.ics.dto.user.UserCreateForm;
import com.example.ics.dto.user.UserLoginForm;
import com.example.ics.service.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class SiteUserController {
    private final UserManagementService userManagementService;
    private final AuthenticationManager authenticationManager;

    private static final String SIGNUP_FORM_VIEW = "signupForm";

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
        return SIGNUP_FORM_VIEW;
    }

    @PostMapping("/signup")
    public String signup(HttpServletRequest request, HttpServletResponse response,
                         @Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_FORM_VIEW;
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 맞지 않습니다");
        }
        try {
            userManagementService.createUser(userCreateForm.getUserID(), userCreateForm.getName(), userCreateForm.getPassword1(), ProviderType.LOCAL, RoleType.USER);
            // 추가한 부분
            if (request.getServletContext().getMajorVersion() >= 3) {
                request.login(userCreateForm.getUserID(), userCreateForm.getPassword1());
            } else {
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userCreateForm.getUserID(),
                                userCreateForm.getPassword1()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            bindingResult.rejectValue("signupFailed", e.getMessage());
            return SIGNUP_FORM_VIEW;
        }


        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String userDashBoard(Model model) {
        model.addAttribute("users", userManagementService.findAll());
        return "userDashboardForm";
    }

}
