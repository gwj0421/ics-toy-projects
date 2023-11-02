package com.example.ics.service.programing_case_service;

public class KebabCaseConverter extends ProgramingCase{
    @Override
    public String convertOriginal(String kebab) {
        stringBuilder.append(kebab);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));

        String originalText = stringBuilder.toString().replace("-", " ");
        stringBuilder.setLength(0);
        return originalText;
    }
    @Override
    public String convertCamel(String kebab) {
        String[] words = kebab.toLowerCase().split("-");
        StringBuilder kebabText = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            stringBuilder.append(words[i]);
            stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
            kebabText.append(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return kebabText.toString();
    }

    @Override
    public String convertSnake(String kebab, boolean isUp) {
        String snakeText = kebab.replace("-", "_");
        if (isUp) {
            snakeText = snakeText.toUpperCase();
        } else {
            snakeText = snakeText.toLowerCase();
        }

        return snakeText;
    }

    @Override
    public String convertPascal(String kebab) {
        String pascalText = convertCamel(kebab);
        stringBuilder.append(pascalText);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));

        pascalText = stringBuilder.toString();
        stringBuilder.setLength(0);

        return pascalText;
    }
}
