package com.example.ics.service.programing_case_service;

import static com.example.ics.utils.StringUtil.*;

public class CamelCaseConverter extends ProgramingCase {
    @Override
    public String convertOriginal(String camel) {
        stringBuilder.append(makeUpperCase(camel.charAt(0)));

        for (int i = 1; i < camel.length(); i++) {
            char ch = camel.charAt(i);
            if (checkUpperCase(ch)) {
                stringBuilder.append(" ");
                stringBuilder.append(makeLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        String originalText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return originalText;
    }

    @Override
    public  String convertKebab(String camel) {
        stringBuilder.append(camel.charAt(0));
        for (int i = 1; i < camel.length(); i++) {
            char ch = camel.charAt(i);
            if (checkUpperCase(ch)) {
                stringBuilder.append("-");
                stringBuilder.append(makeLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        String kebabText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return kebabText;
    }

    @Override
    public String convertSnake(String camel, boolean isUp) {
        if (isUp) {
            stringBuilder.append(makeUpperCase(camel.charAt(0)));
            for (int i = 1; i < camel.length(); i++) {
                char ch = camel.charAt(i);
                if (Character.isLowerCase(ch)) {
                    stringBuilder.append(makeUpperCase(ch));
                } else {
                    stringBuilder.append("_");
                    stringBuilder.append(ch);
                }
            }
        } else {
            stringBuilder.append(camel.charAt(0));
            for (int i = 1; i < camel.length(); i++) {
                char ch = camel.charAt(i);
                if (checkUpperCase(ch)) {
                    stringBuilder.append("_");
                    stringBuilder.append(makeLowerCase(ch));
                } else {
                    stringBuilder.append(ch);
                }
            }
        }
        String snakeText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return snakeText;
    }

    @Override
    public String convertPascal(String camel) {
        stringBuilder.append(camel);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        String pascalText = stringBuilder.toString();
        stringBuilder.setLength(0);
        return pascalText;
    }

}
