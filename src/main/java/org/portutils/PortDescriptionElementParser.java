package org.portutils;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

class PortDescriptionElementParser {

    public static final String INTERVALS_SEPARATOR = "-";
    public static final String STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE = "String with numbers shouldn't be empty.";
    public static final String FIRST_NUMBER_SHOULD_BE_LESS_THAN_SECOND_ONE = " First number should be less than second one.";

    static SortedSet<Integer> parsePortElement(String portElementString)
    {
        String trimmedPortElementString = portElementString.trim();

        if(trimmedPortElementString.isEmpty())
            throw new IllegalArgumentException(STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE);

        return stream(trimmedPortElementString.split(","))
                .map(potentialIntOrInterval -> addNumbers(potentialIntOrInterval.trim()))
                .flatMap(Collection::stream).collect(Collectors.toCollection(TreeSet::new));
    }

    private static List<Integer> addNumbers(String trimmedPotentialIntOrInterval) {
        if (!trimmedPotentialIntOrInterval.contains("-"))
        {
            return getSingleInteger(trimmedPotentialIntOrInterval);
        }
        else
        {
            return generateInterval(trimmedPotentialIntOrInterval);
        }
    }

    private static List<Integer> getSingleInteger(String trimmedPotentialIntOrInterval) {
        return singletonList(parseInt(trimmedPotentialIntOrInterval));
    }

    private static List<Integer> generateInterval(String trimmedPotentialIntOrInterval) {
        int dashPos = trimmedPotentialIntOrInterval.indexOf(INTERVALS_SEPARATOR);
        int firstNumber = parseInt(trimmedPotentialIntOrInterval.substring(0, dashPos));
        int secondNumber = parseInt(trimmedPotentialIntOrInterval.substring(dashPos+1));
        if (firstNumber > secondNumber)
            throw new IllegalArgumentException(format("Wrong interval declaration: %s.", trimmedPotentialIntOrInterval) + FIRST_NUMBER_SHOULD_BE_LESS_THAN_SECOND_ONE);
        return generateInterval(firstNumber, secondNumber);
    }

    private static List<Integer> generateInterval(int firstNumber, int secondNumber) {
        List<Integer> result = new ArrayList<>();
        for (int i = firstNumber; i <= secondNumber; i++)
        {
            result.add(i);
        }
        return result;
    }
}
