package com.example.ics.controller;

import com.example.ics.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/oauth/redirect")
    public String checkOAuth2Login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String token) {
        oAuth2Service.oAuth2Login(request, response, token);
        return "redirect:/";
    }

}
