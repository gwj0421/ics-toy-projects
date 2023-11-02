package com.example.ics.service.programing_case_service;

public class PascalCaseConverter extends ProgramingCase{
    @Override
    public String convertOriginal(String pascal) {
        for (int i = 0; i < pascal.length(); i++) {
            char ch = pascal.charAt(i);
            if (i == 0 || Character.isLowerCase(ch)) {
                stringBuilder.append(ch);
            } else {
                stringBuilder.append(" ");
                stringBuilder.append(Character.toLowerCase(ch));
            }
        }
        String originalText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return originalText;
    }

    @Override
    public String convertCamel(String pascal) {
        stringBuilder.append(pascal);
        stringBuilder.setCharAt(0, Character.toLowerCase(stringBuilder.charAt(0)));
        String camelText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return camelText;
    }

    @Override
    public String convertKebab(String pascal) {
        for (int i = 0; i < pascal.length(); i++) {
            char ch = pascal.charAt(i);
            if (i == 0) {
                stringBuilder.append(Character.toLowerCase(ch));
            } else if (Character.isLowerCase(ch)) {
                stringBuilder.append(ch);
            } else {
                stringBuilder.append("-");
                stringBuilder.append(Character.toLowerCase(ch));
            }
        }
        String kebabText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return kebabText;
    }

    @Override
    public String convertSnake(String pascal, boolean isUp) {
        for (int i = 0; i < pascal.length(); i++) {
            char ch = pascal.charAt(i);
            if (i == 0 ||Character.isLowerCase(ch)) {
                stringBuilder.append(ch);
            } else {
                stringBuilder.append("_");
                stringBuilder.append(ch);
            }
        }
        String snakeText = stringBuilder.toString();
        stringBuilder.setLength(0);
        if (isUp) {
            snakeText = snakeText.toUpperCase();
        } else {
            snakeText = snakeText.toLowerCase();
        }
        return snakeText;
    }


}
