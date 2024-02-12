package uz.ciasev.ubdd_service.utils;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {

    public static String first(String text, int maxLen) {
        if (text == null) {
            return null;
        }

        if (text.length() < maxLen) {
            return text;
        }

        return text.substring(0, maxLen);
    }

    public static String tail(String text, int maxLen) {
        if (text == null) {
            return null;
        }

        int length = text.length();
        if (length <= maxLen) {
            return text;
        }

        return text.substring(length - (maxLen + 1), length - 1);

    }

    public static boolean isEmptyOrNullString(@Nullable String s) {
        return Optional.ofNullable(s).map(String::isBlank).orElse(true);
    }

    public static String joinIgnoreNull(String s, String... strings) {
        if (strings == null) {
            return "";
        }

        return Stream.of(strings)
                .filter(Objects::nonNull)
                .collect(Collectors.joining());
    }

    public static String camelToUpperSnake(String str) {

        // Empty String
        String result = "";

        // Append first character(in lower case)
        // to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);

        // Traverse the string from
        // ist index to last index
        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);

            // Check if the character is upper case
            // then append '_' and such character
            // (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result
                        = result
                        + Character.toLowerCase(ch);
            }

            // If the character is lower case then
            // add such character into result string
            else {
                result = result + ch;
            }
        }

        // return the result
        return result.toUpperCase();
    }

    public static String splitAndGetLast(String text, String delimited) {
        if (text == null) {
            return null;
        }

        String[] lines = text.split(delimited);

        if (lines.length == 0) {
            return text;
        }

        return lines[lines.length - 1];
    }


    /**
     * removePrefixForCamelCase("isActive", 2) -> "active"
     * removePrefixForCamelCase("getCode", 3) -> "code"
     */
    public static String removePrefixForCamelCase(String name, int prefixLen) {
        return name.substring(prefixLen, prefixLen + 1).toLowerCase() + name.substring(prefixLen + 1);
    }
}
