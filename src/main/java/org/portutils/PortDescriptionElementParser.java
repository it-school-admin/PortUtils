package org.portutils;

import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class PortDescriptionElementParser {

    public static final String INTERVALS_SEPARATOR = "-";
    public static final String STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE = "String with numbers shouldn't be empty.";
    public static final String FIRST_NUMBER_SHOULD_BE_LESS_THAN_SECOND_ONE = " First number should be less than second one.";

    public static SortedSet<Integer> parsePortElement(String portElementString)
    {
        String trimmedPortElementString = portElementString.trim();

        if(trimmedPortElementString.isEmpty())
            throw new IllegalArgumentException(STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE);

        SortedSet<Integer> result = new TreeSet<>();
        for (String potentialIntOrInterval: trimmedPortElementString.split(","))
        {
            addNumbers(result, potentialIntOrInterval.trim());
        }
        return result;

    }

    private static void addNumbers(SortedSet<Integer> result, String trimmedPotentialIntOrInterval) {
        if (!trimmedPotentialIntOrInterval.contains("-"))
        {
            result.add(parseInt(trimmedPotentialIntOrInterval));
        }
        else
        {
            int dashPos = trimmedPotentialIntOrInterval.indexOf(INTERVALS_SEPARATOR);
            int firstNumber = parseInt(trimmedPotentialIntOrInterval.substring(0, dashPos));
            int secondNumber = parseInt(trimmedPotentialIntOrInterval.substring(dashPos+1));
            if (firstNumber > secondNumber)
                throw new IllegalArgumentException(format("Wrong interval declaration: %s.", trimmedPotentialIntOrInterval) + FIRST_NUMBER_SHOULD_BE_LESS_THAN_SECOND_ONE);
            generateInterval(firstNumber, secondNumber, result);
        }
    }

    //TODO check that first is less than second
    private static void generateInterval(int firstNumber, int secondNumber, SortedSet<Integer> result) {
        for (int i = firstNumber; i <= secondNumber; i++)
        {
            result.add(i);
        }
    }
}
