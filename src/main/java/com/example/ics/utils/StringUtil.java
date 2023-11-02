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
}
