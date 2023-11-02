package com.example.ics.programing_case_converter;

import static com.example.ics.utils.StringUtil.*;

public class OriginalCaseConverter extends ProgramingCase {
    @Override
    public String convertCamel(String original) {
        original = removeSpecialCharacters(original);
        original = removeMultipleSpace(original).toLowerCase();
        String[] words = original.toLowerCase().split(" ");
        StringBuilder camelText = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            camelText.append(makeFirstLetter2UpperCase(stringBuilder,words[i]));
        }
        return camelText.toString();
    }

    @Override
    public String convertKebab(String original) {
        original = removeSpecialCharacters(original).toLowerCase();
        return original.replaceAll("\\s+", "-");
    }

    @Override
    public String convertSnake(String original, boolean isUp) {
        original = removeSpecialCharacters(original);
        if (isUp) {
            original = original.toUpperCase();
        } else {
            original = original.toLowerCase();
        }
        return original.replaceAll("\\s+", "_");
    }

    @Override
    public String convertPascal(String original) {
        return makeFirstLetter2UpperCase(stringBuilder,convertCamel(original));
    }
}
