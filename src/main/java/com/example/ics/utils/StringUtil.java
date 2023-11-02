package com.example.ics.utils;

public class StringUtil {
    private StringUtil() {
        throw new IllegalArgumentException("StringUtil 클래스는 유틸 클래스");
    }

    public static String removeSpecialCharacters(String inputString) {
        return inputString.trim().replaceAll("[^a-zA-Z0-9^\\s]", "");
    }

    public static String removeMultipleSpace(String inputString) {
        return inputString.replaceAll("\\s+", " ");
    }

    public static char makeUpperCase(char lowerCase) {
        return Character.toUpperCase(lowerCase);
    }

    public static char makeLowerCase(char upperCase) {
        return Character.toLowerCase(upperCase);
    }

    public static boolean checkUpperCase(char content) {
        return Character.isUpperCase(content);
    }

    public static String convertDelimiterFromUpperCaseToLowerCaseAndCharacter(StringBuilder stringBuilder, String content, String delimiter) {
        // 대문자를 기준으로 나눠지는 경우, 특수문자를 구분자로 나누고 나뉜다음 기존의 대문자를 소문자로 바꿈
        for (int i = 1; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (checkUpperCase(ch)) {
                stringBuilder.append(delimiter);
                stringBuilder.append(makeLowerCase(ch));
            } else {
                stringBuilder.append(ch);
            }
        }
        String result = stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }

    public static String convertDelimiterFromUpperCaseToUpperCaseAndCharacter(StringBuilder stringBuilder, String content, String delimiter) {
        // 대문자를 기준으로 나눠지는 경우, 특수문자를 구분자로 나누고 나뉜다음 기존의 소문자를 대문자로 바꿈
        for (int i = 1; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (checkUpperCase(ch)) {
                stringBuilder.append(delimiter);
                stringBuilder.append(ch);
            } else {
                stringBuilder.append(makeUpperCase(ch));
            }
        }
        String result = stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }


    public static String makeFirstLetter2UpperCase(StringBuilder stringBuilder, String content) {
        stringBuilder.append(content);
        stringBuilder.setCharAt(0, makeUpperCase(stringBuilder.charAt(0)));
        content = stringBuilder.toString();
        stringBuilder.setLength(0);
        return content;
    }

    public static String makeFirstCharacter2LowerCase(StringBuilder stringBuilder, String content) {
        stringBuilder.append(content);
        stringBuilder.setCharAt(0, makeLowerCase(stringBuilder.charAt(0)));
        content = stringBuilder.toString();
        stringBuilder.setLength(0);
        return content;
    }
}
