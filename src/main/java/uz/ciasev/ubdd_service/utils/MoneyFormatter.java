package uz.ciasev.ubdd_service.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public final class MoneyFormatter {

    public static final String CURRENCY_NAME = "so'm";
    public static final String COIN_NAME = "tiyin";
    public static final String MINUS_NAME = "minus";
    public static final String ZERO_NAME = "nol";
    public static final String TRILLION_NAME = "trillion";
    public static final String BILLION_NAME = "milliard";
    public static final String MILLION_NAME = "million";
    public static final String THOUSAND_NAME = "ming";
    public static final String NINE_HUNDREDS_NAME = "to'qqiz yuz";
    public static final String EIGHT_HUNDRED_NAME = "sakkiz yuz";
    public static final String SEVEN_HUNDRED_NAME = "etti yuz";
    public static final String SIX_HUNDRED_NAME = "olti yuz";
    public static final String FIVE_HUNDRED_NAME = "besh yuz";
    public static final String FOUR_HUNDRED_NAME = "to'rt yuz";
    public static final String THREE_HUNDRED_NAME = "uch yuz";
    public static final String TWO_HUNDRED_NAME = "ikki yuz";
    public static final String ONE_HUNDRED_NAME = "bir yuz";
    public static final String NINETY_NAME = "to'qson";
    public static final String EIGHTY_NAME = "sakson";
    public static final String SEVENTY_NAME = "etmish";
    public static final String SIXTY_NAME = "oltmish";
    public static final String FIFTY_NAME = "ellik";
    public static final String FORTY_NAME = "qirq";
    public static final String THIRTY_NAME = "o'ttiz";
    public static final String TWENTY_NAME = "yigirma";
    public static final String NINETEEN_NAME = "o'n to'qqiz";
    public static final String EIGHTEEN_NAME = "o'n sakkiz";
    public static final String SEVENTEEN_NAME = "o'n etti";
    public static final String SIXTEEN_NAME = "o'n olti";
    public static final String FIFTEEN_NAME = "o'n besh";
    public static final String FOURTEEN_NAME = "o'n to'rt";
    public static final String THIRTEEN_NAME = "o'n uch";
    public static final String TWELVE_NAME = "o'n ikki";
    public static final String ELEVEN_NAME = "o'n bir";
    public static final String TEN_NAME = "o'n";
    public static final String NINE_NAME = "to'qqiz";
    public static final String EIGHT_NAME = "sakkiz";
    public static final String SEVEN_NAME = "etti";
    public static final String SIX_NAME = "olti";
    public static final String FIVE_NAME = "besh";
    public static final String FOUR_NAME = "to'rt";
    public static final String THREE_NAME = "uch";
    public static final String TWO_NAME = "ikki";
    public static final String ONE_NAME = "bir";


    public static final Long MAX_CONVERTED_VALUE = 100000000000000000L; // 10^15 сум
    public static final long[] THOUSANDS = new long[] {1000000000000L, 1000000000, 1000000, 1000, 1};
    public static final String[] THOUSAND_WORDS = new String[] {TRILLION_NAME, BILLION_NAME, MILLION_NAME, THOUSAND_NAME, CURRENCY_NAME};
    public static final int[] NUMBERS = new int[] {900, 800, 700, 600, 500, 400, 300, 200, 100, 90, 80, 70, 60, 50, 40, 30, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static final String[] NUMBER_WORDS = new String[] {
            NINE_HUNDREDS_NAME, EIGHT_HUNDRED_NAME, SEVEN_HUNDRED_NAME, SIX_HUNDRED_NAME, FIVE_HUNDRED_NAME, FOUR_HUNDRED_NAME, THREE_HUNDRED_NAME, TWO_HUNDRED_NAME, ONE_HUNDRED_NAME,
            NINETY_NAME, EIGHTY_NAME, SEVENTY_NAME, SIXTY_NAME, FIFTY_NAME, FORTY_NAME, THIRTY_NAME, TWENTY_NAME, NINETEEN_NAME, EIGHTEEN_NAME, SEVENTEEN_NAME, SIXTEEN_NAME, FIFTEEN_NAME, FOURTEEN_NAME, THIRTEEN_NAME, TWELVE_NAME, ELEVEN_NAME, TEN_NAME,
            NINE_NAME, EIGHT_NAME, SEVEN_NAME, SIX_NAME, FIVE_NAME, FOUR_NAME, THREE_NAME, TWO_NAME, ONE_NAME
    };

    private MoneyFormatter() {}

    public static String toWord(long value) {
        if (value >= MAX_CONVERTED_VALUE)
            return String.valueOf(value);

        StringBuffer resultBuffer = new StringBuffer();

        if (value < 0)
            resultBuffer.append(MINUS_NAME).append(" ");

        if (value == 0)
            resultBuffer.append(resultBuffer).append(ZERO_NAME).append(" ");

        long absValue = Math.abs(value);
        long currentValue = extractCurrency(absValue);
        int i = 0;

        while (currentValue > 0) {
            long currentThousand = THOUSANDS[i];

            if (currentValue >= currentThousand) {
                long currentNumberValue = currentValue / currentThousand;

                for (int j = 0; currentNumberValue > 0; j++) {

                    if (currentNumberValue < NUMBERS[j])
                        continue;

                    resultBuffer.append(NUMBER_WORDS[j]).append(" ");
                    currentNumberValue = currentNumberValue - NUMBERS[j];
                }
                resultBuffer.append(THOUSAND_WORDS[i]).append(" ");
            }
            currentValue = currentValue % currentThousand;
            i++;
        }

        // последний элемент в масиве уже и есть сумм
        if (i != 5)
            resultBuffer.append(CURRENCY_NAME).append(" ");

        resultBuffer
                .append(new DecimalFormat("00").format(extractCoins(absValue)))
                .append(" ")
                .append(COIN_NAME);

        return resultBuffer.toString();
    }

    public static long extractCoins(long value) {
        return value % 100;
    }

    public static long extractCurrency(long value) {
        return value / 100;
    }

    public static double coinToCurrency(Long value) {
        if (value == null) return 0;
        return Double.valueOf(value) / 100;
    }

    public static double coinToCurrency(double value) {
        return value / 100;
    }

    public static long currencyToCoin(double value) {
        return Math.round((value * 1000) / 10);
    }

    public static String coinToString(Long value) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00", symbols);

        return decimalFormat.format(coinToCurrency(value));
    }
}
