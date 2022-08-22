package org.portutils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.portutils.PortDescriptionElementParser.STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE;
import static org.portutils.PortDescriptionElementParser.parsePortElement;

class PortDescriptionElementParserTest {
    public static final String ONE_NUMBER_STRING = "90";
    public static final String ONE_INTERVAL = "1-4";
    public static final String EMPTY_STRING = "";
    public static final String EMPTY_STRING_WITH_SPACES = "  ";
    public static final String NUMBERS_ONLY = "1,4,90,91,92,93,94,95";
    public static final String INTERVALS_ONLY = "90-95,1-4";
    public static final String SINGLE_NUMBERS_AND_INTERVALS = "90-95,1,4,3,2";
    public static final String SINGLE_NUMBERS_AND_INTERVALS_WITH_SPACES = "90-95, 1, 4, 3,2";
    public static final String INTERVAL_WITH_WRONG_LIMITS = "95-90";


    SortedSet<Integer> FIRST_TEST_DATA = new TreeSet<>(asList(1, 4, 90, 91, 92, 93, 94, 95));
    SortedSet<Integer> SECOND_TEST_DATA = new TreeSet<>(asList(1, 2, 3, 4, 90, 91, 92, 93, 94, 95));
    SortedSet<Integer> ONE_INTEGER = new TreeSet<>(asList(90));
    SortedSet<Integer> ONE_INTERVAL_RESULT = new TreeSet<>(asList(1,2,3,4));

    @Test
    void parsePortElementOneInteger() {
        assertEquals(ONE_INTEGER, parsePortElement(ONE_NUMBER_STRING));
    }

    @Test
    void parsePortElementOneInterval() {
        assertEquals(ONE_INTERVAL_RESULT, parsePortElement(ONE_INTERVAL));
    }


    @Test
    void parsePortElementEmptyString() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parsePortElement(EMPTY_STRING));
        assertEquals(STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE, exception.getMessage());
    }

    @Test
    void parsePortElementEmptyStringWithSpaces() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parsePortElement(EMPTY_STRING_WITH_SPACES));
        assertEquals(STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE, exception.getMessage());
    }

    @Test
    void parsePortElementNumbersOnly() {
        assertEquals(FIRST_TEST_DATA, parsePortElement(NUMBERS_ONLY));
    }

    @Test
    void parsePortElementIntervalsOnly() {
        assertEquals(SECOND_TEST_DATA, parsePortElement(INTERVALS_ONLY));
    }

    @Test
    void parsePortElementSingleNumbersAndIntervals() {
        assertEquals(SECOND_TEST_DATA, parsePortElement(SINGLE_NUMBERS_AND_INTERVALS));
    }

    @Test
    void parsePortElementWithSpacesInsideSquareBrackets() {
        assertEquals(SECOND_TEST_DATA, parsePortElement(SINGLE_NUMBERS_AND_INTERVALS_WITH_SPACES));
    }

    @Test
    void parsePortElementWrongIntervalDeclaration() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->parsePortElement(INTERVAL_WITH_WRONG_LIMITS));
        assertEquals("Wrong interval declaration: 95-90. First number should be less than second one.", exception.getMessage());
    }


}