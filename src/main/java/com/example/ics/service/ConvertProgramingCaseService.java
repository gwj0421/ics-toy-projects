package com.example.ics.service;

import com.example.ics.dto.programing_case.ProgramingCaseType;
import com.example.ics.programing_case_converter.ProgramingCase;
import com.example.ics.programing_case_converter.ProgramingCaseConverterFactory;
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
