package com.example.ics.programing_case_converter;

import com.example.ics.dto.programing_case.ProgramingCaseType;

public class ProgramingCaseConverterFactory {
    private ProgramingCaseConverterFactory() {
    }

    public static ProgramingCase getProgramingCaseConverter(ProgramingCaseType programingCase) {
        switch (programingCase) {
            case ORIGINAL: return new OriginalCaseConverter();
            case CAMEL: return new CamelCaseConverter();
            case KEBAB: return new KebabCaseConverter();
            case SNAKE_UP: return new SnakeCaseConverter(true);
            case SNAKE_DOWN: return new SnakeCaseConverter(false);
            case PASCAL: return new PascalCaseConverter();

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
