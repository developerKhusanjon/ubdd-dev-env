package uz.ciasev.ubdd_service.utils;

import java.util.Random;

public class GeneratorUtils {

    private static Random random = new Random();

    public static String generateNumericString(int len) {
        if (len == 0) {
            return "";
        }

        if (len == 1) {
            return String.valueOf(Math.abs(random.nextInt()) % 10);
        }

        return generateNumericString(1) + generateNumericString(len-1);
    }
}
