package com.example.ics.programing_case_converter;

import static com.example.ics.utils.StringUtil.*;

public class PascalCaseConverter extends ProgramingCase{
    @Override
    public String convertOriginal(String pascal) {
        stringBuilder.append(pascal.charAt(0));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, pascal, " ");
    }

    @Override
    public String convertCamel(String pascal) {
        return makeFirstCharacter2LowerCase(stringBuilder, pascal);
    }

    @Override
    public String convertKebab(String pascal) {
        stringBuilder.append(makeLowerCase(pascal.charAt(0)));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, pascal, "-");

    }

    @Override
    public String convertSnake(String pascal, boolean isUp) {
        if (isUp) {
            stringBuilder.append(pascal.charAt(0));
            return convertDelimiterFromUpperCaseToUpperCaseAndCharacter(stringBuilder, pascal, "_");
        }
        stringBuilder.append(makeLowerCase(pascal.charAt(0)));
        return convertDelimiterFromUpperCaseToLowerCaseAndCharacter(stringBuilder, pascal, "_");
    }
}
