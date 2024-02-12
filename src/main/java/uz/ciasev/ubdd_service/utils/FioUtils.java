package uz.ciasev.ubdd_service.utils;

import javax.annotation.Nullable;

public class FioUtils {

    public static String buildFullFio(String firstName, @Nullable String secondName,  String lastName) {
        if (secondName != null && !secondName.isBlank()) {
            return String.join(" ", lastName, firstName, secondName);
        }

        return String.join(" ", lastName, firstName);
    }

    public static String buildShortFio(String firstName, @Nullable String secondName,  String lastName) {
        StringBuilder builder = new StringBuilder(lastName);
        builder.append(" ");
        builder.append(firstName.charAt(0));
        builder.append(".");

        if (secondName != null && !secondName.isBlank()) {
            builder.append(" ");
            builder.append(secondName.charAt(0));
            builder.append(".");
        }

        return builder.toString();
    }
}
