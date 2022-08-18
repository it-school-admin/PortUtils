package org.portutils;

import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Integer.parseInt;

public class PortDescriptionElementParser {

    public static final String INTERVALS_SEPARATOR = "-";

    public static SortedSet<Integer> parsePortElement(String portElementString)
    {
        String trimmedPortElementString = portElementString.trim();

        if(trimmedPortElementString.isEmpty())
            throw new IllegalArgumentException("String with numbers between \"[]\" and \"]\" brackets shouldn't be empty.");

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
            //TODO use string with parameters
            //TODO test text
            if (firstNumber > secondNumber)
                throw new IllegalArgumentException("Wrong interval declaration: " + trimmedPotentialIntOrInterval + ". " +
                        "First number should be less than second one.");
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
