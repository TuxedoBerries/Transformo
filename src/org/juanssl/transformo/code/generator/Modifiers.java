/*
 * Copyright (C) 2015 Juan Silva <juanssl@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.juanssl.transformo.code.generator;

/**
 *
 * @author Juan Silva
 */
public class Modifiers {

    private static final String CAPITAL_CASE = ":capital_case";
    private static final String SENTENCE_CASE = ":sentence_case";
    private static final String LOWER_CASE = ":lower_case";
    private static final String UPPER_CASE = ":upper_case";
    private static final String CAMEL_CASE = ":camel_case";
    private static final String SNEAK_CASE = ":sneak_case";
    private static final String CLASS_CASE = ":class_case";
    private static final String UNDERSCORE_SPACE = ":underscore_space";
    private static final String CLEAN_COMMA = ":clean";

    public static String apply(String src, int current, int total) {
        String returnData = src;

        if (returnData.contains(CLEAN_COMMA)) {
            if (current + 1 < total) {
                returnData = returnData.replace(CLEAN_COMMA, "");
            } else {
                returnData = "";
            }
            return returnData;
        }

        returnData = apply(returnData);
        return returnData;
    }

    public static String apply(String src) {
        String returnData = src;

        if (returnData.contains(CAPITAL_CASE)) {
            returnData = returnData.replace(CAPITAL_CASE, "");
            returnData = Modifiers.toCapitalCase(returnData);
            return returnData;
        }

        if (returnData.contains(SENTENCE_CASE)) {
            returnData = returnData.replace(SENTENCE_CASE, "");
            returnData = toSentenceCase(returnData);
            return returnData;
        }

        if (returnData.contains(LOWER_CASE)) {
            returnData = returnData.replace(LOWER_CASE, "");
            returnData = returnData.toLowerCase();
            return returnData;
        }

        if (returnData.contains(UPPER_CASE)) {
            returnData = returnData.replace(UPPER_CASE, "");
            returnData = returnData.toUpperCase();
            return returnData;
        }

        if (returnData.contains(CAMEL_CASE)) {
            returnData = returnData.replace(CAMEL_CASE, "");
            returnData = Modifiers.toCamelCase(returnData);
            return returnData;
        }

        if (returnData.contains(SNEAK_CASE)) {
            returnData = returnData.replace(SNEAK_CASE, "");
            returnData = toSneakCase(returnData);
            return returnData;
        }

        if (returnData.contains(CLASS_CASE)) {
            returnData = returnData.replace(CLASS_CASE, "");
            returnData = Modifiers.toClassCase(returnData);
            return returnData;
        }

        if (returnData.contains(UNDERSCORE_SPACE)) {
            returnData = returnData.replace(UNDERSCORE_SPACE, "");
            returnData = replaceUnderscore(returnData);
            return returnData;
        }

        return returnData;
    }

    public static String replaceUnderscore(String data) {
        String returnData = data;
        returnData = returnData.replace("_", " ");
        return returnData;
    }

    public static String toCapitalCase(String data) {
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if (containSpace) {
            result = toCapitalCase(data, " ");
        }
        if (containUnderscore) {
            result = toCapitalCase(result, "_");
        }
        if (!containSpace && !containUnderscore) {
            result = toSingleCapital(result);
        }
        return result;
    }

    public static String toCapitalCase(String data, String separator) {
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            String word = split[i].toLowerCase();
            builder.append(toSingleCapital(word));

            if (i + 1 < split.length) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }

    public static String toSentenceCase(String data) {
        return toSingleCapital(data);
    }

    public static String toSneakCase(String data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length(); ++i) {
            char c = data.charAt(i);
            if (i == 0) {
                builder.append(Character.toLowerCase(c));
            } else {
                if (Character.isUpperCase(c)) {
                    builder.append("_");
                }
                if (Character.isWhitespace(c)) {
                    builder.append("_");
                    continue;
                }
                builder.append(Character.toLowerCase(c));
            }
        }
        String prefinal = builder.toString();
        prefinal = prefinal.replace("__", "_");
        return prefinal;
    }

    public static String toCamelCase(String data) {
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if (containSpace) {
            result = toCamelCase(result, " ");
        }
        if (containUnderscore) {
            result = toCamelCase(result, "_");
        }
        return result;
    }

    public static String toCamelCase(String data, String separator) {
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            String word = split[i].toLowerCase();
            if (i == 0) {
                builder.append(word);
            } else {
                builder.append(toSingleCapital(word));
            }
        }

        return builder.toString();
    }

    public static String toClassCase(String data) {
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if (containSpace) {
            result = toClassCase(data, " ");
        }
        if (containUnderscore) {
            result = toClassCase(result, "_");
        }
        if (!containSpace && !containUnderscore) {
            result = toSingleCapital(data);
        }
        return result;
    }

    public static String toClassCase(String data, String separator) {
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; ++i) {
            String word = split[i].toLowerCase();
            builder.append(toSingleCapital(word));
        }
        return builder.toString();
    }

    public static String toSingleCapital(String word) {
        StringBuilder builder = new StringBuilder();
        builder.append(word.substring(0, 1).toUpperCase());
        builder.append(word.substring(1).toLowerCase());

        return builder.toString();
    }
}
