package dev.imlukas.bedwarsinventoryswapper.utils.text;

import net.md_5.bungee.api.ChatColor;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]){6}");
    private static final Pattern illegalCharactersPattern = Pattern.compile("[^A-Za-z0-9]+");

    public static String color(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = hexPattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Capitalizes the first letter of a String
     * @param toCapitalize  The String to capitalize
     * @return The capitalized String
     */
    public static String capitalize(String toCapitalize) {
        return toCapitalize.substring(0, 1).toUpperCase() + toCapitalize.substring(1).toLowerCase();
    }

    /**
     * Capitalizes all the first letters of a String
     * @param text The String to capitalize
     * @return The capitalized String
     */
    public static String capitalizeAll(String text) {
        text = text.replaceAll("_", " ");
        String[] words = text.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            builder.append(capitalize(words[i]));
            if (i != words.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Parses a String to an integer, throwing an IllegalArgumentException if the String is not a valid integer.
     *
     * @param stringToParse The String to parse
     * @param predicate     A Predicate to test the parsed integer against
     * @return The parsed integer
     */
    public static int parseInt(String stringToParse, Predicate<Integer> predicate) { // not really a text utility, but it's used in a text utility
        int parsed = 1;
        try {
            parsed = Integer.parseInt(stringToParse);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number: " + stringToParse);
            return 1;
        }

        if (!predicate.test(parsed)) {
            System.err.println("Invalid number: " + stringToParse);
            return 1;
        }

        return parsed;
    }

}

