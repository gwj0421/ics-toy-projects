package com.example.ics.programing_case_converter;

import static com.example.ics.utils.StringUtil.*;

public class CamelCaseConverter extends ProgramingCase {
    @Override
    public String convertOriginal(String camel) {
        stringBuilder.append(makeUpperCase(camel.charAt(0)));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, camel, " ");
    }

    @Override
    public String convertKebab(String camel) {
        stringBuilder.append(camel.charAt(0));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, camel, "-");
    }

    @Override
    public String convertSnake(String camel, boolean isUp) {
        if (isUp) {
            stringBuilder.append(makeUpperCase(camel.charAt(0)));
            return convertDelimiterFromUpperCaseToUpperCaseAndCharacter(stringBuilder, camel, "_");
        }
        stringBuilder.append(camel.charAt(0));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, camel, "_");
    }

    @Override
    public String convertPascal(String camel) {
        return makeFirstLetter2UpperCase(stringBuilder, camel);
    }

}
