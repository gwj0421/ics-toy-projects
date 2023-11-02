package com.example.ics.programing_case_converter;

import static com.example.ics.utils.StringUtil.makeFirstLetter2UpperCase;

public class KebabCaseConverter extends ProgramingCase {
    @Override
    public String convertOriginal(String kebab) {
        return makeFirstLetter2UpperCase(stringBuilder, kebab.replace("-"," "));
    }

    @Override
    public String convertCamel(String kebab) {
        String[] words = kebab.toLowerCase().split("-");
        StringBuilder kebabText = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            kebabText.append(makeFirstLetter2UpperCase(stringBuilder,words[i]));
        }
        return kebabText.toString();
    }

    @Override
    public String convertSnake(String kebab, boolean isUp) {
        String snakeText = kebab.replace("-", "_");
        if (isUp) {
            snakeText = snakeText.toUpperCase();
        }

        return snakeText;
    }

    @Override
    public String convertPascal(String kebab) {
        String pascalText = convertCamel(kebab);
        return makeFirstLetter2UpperCase(stringBuilder, pascalText);
    }
}
