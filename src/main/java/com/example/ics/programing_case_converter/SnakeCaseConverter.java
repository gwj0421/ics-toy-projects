package com.example.ics.programing_case_converter;

import static com.example.ics.utils.StringUtil.makeFirstLetter2UpperCase;

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
        return makeFirstLetter2UpperCase(stringBuilder,originalText);
    }

    @Override
    public String convertCamel(String snake) {
        if (isUp) {
            snake = snake.toLowerCase();
        }
        String[] words = snake.split("_");
        StringBuilder camelText = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; i++) {
            camelText.append(makeFirstLetter2UpperCase(stringBuilder,words[i]));
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
            pascalText.append(makeFirstLetter2UpperCase(stringBuilder,words[i]));
        }
        return pascalText.toString();
    }
}
