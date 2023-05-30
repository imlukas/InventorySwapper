package dev.imlukas.bedwarsinventoryswapper.utils.text;

import net.md_5.bungee.api.ChatColor;

public class TextUtils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Capitalizes the first letter of a String
     *
     * @param toCapitalize The String to capitalize
     * @return The capitalized String
     */
    public static String capitalize(String toCapitalize) {
        return toCapitalize.substring(0, 1).toUpperCase() + toCapitalize.substring(1).toLowerCase();
    }

    /**
     * Capitalizes all the first letters of a String
     *
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
}

