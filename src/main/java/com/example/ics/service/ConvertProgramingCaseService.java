package com.example.ics.service;

import com.example.ics.dto.programing_case.ProgramingCaseType;
import com.example.ics.service.programing_case_service.ProgramingCase;
import com.example.ics.service.programing_case_service.ProgramingCaseConverterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConvertProgramingCaseService {
    public String convert(ProgramingCaseType case1, ProgramingCaseType case2, String content) {
        ProgramingCase converter = ProgramingCaseConverterFactory.getProgramingCaseConverter(case1);
        return converter.convert(case2,content);
    }








}
