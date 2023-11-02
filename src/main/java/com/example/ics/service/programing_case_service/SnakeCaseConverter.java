package com.example.ics.service.programing_case_service;

public class SnakeCaseConverter extends ProgramingCase{
    private final boolean isUp;

    public SnakeCaseConverter(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public String convertOriginal(String snake) {
        String originalText = snake.replace("_", " ");
        if (isUp) {
            originalText = originalText.toLowerCase();
        }
        stringBuilder.append(originalText);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        originalText = stringBuilder.toString();
        stringBuilder.setLength(0);

        return originalText;
    }

    @Override
    public String convertCamel(String snake) {
        if (isUp) {
            snake = snake.toLowerCase();
        }
        String[] words = snake.split("_");
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
    public String convertKebab(String snake) {
        if (isUp) {
            snake = snake.toLowerCase();
        }
        return snake.replace("_", "-");
    }

    @Override
    public String convertPascal(String snake) {
        if (isUp) {
            snake = snake.toLowerCase();
        }
        String[] words = snake.split("_");
        StringBuilder pascalText = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i]);
            stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
            pascalText.append(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return pascalText.toString();
    }
}
