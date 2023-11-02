package com.example.ics.service.programing_case_service;

import static com.example.ics.utils.StringUtil.removeMultipleSpace;
import static com.example.ics.utils.StringUtil.removeSpecialCharacters;

public class OriginalCaseConverter extends ProgramingCase {
    @Override
    public String convertCamel(String original) {
        original = removeSpecialCharacters(original);
        original = removeMultipleSpace(original).toLowerCase();
        String[] words = original.toLowerCase().split(" ");
        StringBuilder camelText = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            stringBuilder.append(words[i]);
            stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
            camelText.append(stringBuilder.toString());
            stringBuilder.setLength(0);
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
        stringBuilder.append(convertCamel(original));
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        String pascalText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return pascalText;
    }
}
