package com.example.ics.controller;

import com.example.ics.dto.programing_case.ProgramingCaseType;
import com.example.ics.service.ConvertProgramingCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/text")
@RequiredArgsConstructor
@Slf4j
public class ConvertProgramingCaseController {
    private final ConvertProgramingCaseService converterService;

    @GetMapping("/convertCase")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String convertCase(Model model) {
        model.addAttribute("convertResut", "");
        return "convertCase";
    }

    @PostMapping("/convertCase")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String returnConvertedCase(Model model, @RequestParam("textContent") String textContet,@RequestParam("beforeCase") String beforeCase, @RequestParam("afterCase") String afterCase) {
        model.addAttribute("convertResult", converterService.convert(
                ProgramingCaseType.getCase(beforeCase.toUpperCase()),
                ProgramingCaseType.getCase(afterCase.toUpperCase()),
                textContet
        ));
        return "convertCase";
    }
}
