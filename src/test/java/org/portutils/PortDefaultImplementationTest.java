package org.portutils;

import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.portutils.PortDescriptionParser.parsePort;
import static org.portutils.PortDefaultImplementation.PORT_DESCRIPTION_SHOULDNT_BE_EMPTY;
import static org.portutils.PortDefaultImplementation.PORT_DESCRIPTION_SHOULDNT_BE_NULL;
import static org.portutils.PortDescriptionElementParser.STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE;

class PortDefaultImplementationTest {

    public static final String[] BASE_PORT_DESCRIPTION = {"1,3-5", "2", "3-4"};
    public static final String[] BASE_PORT_DESCRIPTION_WRONG_ORDER = {"3, 4-5, 1", "2", "4,3"};
    public static final List<List<Integer>> BASE_INDEXES_EXPECTED_RESULT = asList(
            asList(1, 2, 3),
            asList(1, 2, 4),
            asList(3, 2, 3),
            asList(3, 2, 4),
            asList(4, 2, 3),
            asList(4, 2, 4),
            asList(5, 2, 3),
            asList(5, 2, 4)
    );
    public static final List<List<Integer>> BASE_NUMBERS_EXPECTED_RESULT = asList(
            asList(1, 3, 4, 5),
            asList(2),
            asList(3, 4)
    );


    public static final String[] ONE_ELEMENT_PORT_DESCRIPTION = {"1,3-5"};
    public static final List<List<Integer>> ONE_ELEMENT_INDEXES_EXPECTED_RESULT = asList(
            asList(1),
            asList(3),
            asList(4),
            asList(5)
    );
    public static final List<List<Integer>> ONE_ELEMENT_NUMBERS_EXPECTED_RESULT = asList(
            asList(1,3,4,5)
    );


    public static final String[] EMPTY_PORT_DESCRIPTION = {""};

    public static final String[] EMPTY_PORT_DESCRIPTION_V2 = {};

    @Test
    void portCreationEmptyPortDescription() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parsePort(EMPTY_PORT_DESCRIPTION));
        assertEquals(STRING_WITH_NUMBERS_SHOULDNT_BE_EMPTY_MESSAGE, exception.getMessage());
    }

    @Test
    void portCreationTestEmptyPortDescription_v2() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parsePort(EMPTY_PORT_DESCRIPTION_V2));
        assertEquals(PORT_DESCRIPTION_SHOULDNT_BE_EMPTY, exception.getMessage());
    }

    @Test
    void portCreationTestEmpty_NotNull_check() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parsePort(null));
        assertEquals(PORT_DESCRIPTION_SHOULDNT_BE_NULL, exception.getMessage());
    }


    @Test
    void getIndexesBaseTest() {
        assertTrue(comparingIndexesResultSetWithGivenList(BASE_INDEXES_EXPECTED_RESULT, new PortDefaultImplementation(BASE_PORT_DESCRIPTION).getIndexes()));
    }

    @Test
    void getIndexesOrderingCheck() {
        assertTrue(comparingIndexesResultSetWithGivenList(BASE_INDEXES_EXPECTED_RESULT, parsePort(BASE_PORT_DESCRIPTION_WRONG_ORDER).getIndexes()));
    }

    @Test
    void getIndexesOneElementTest() {
        assertTrue(comparingIndexesResultSetWithGivenList(ONE_ELEMENT_INDEXES_EXPECTED_RESULT, parsePort(ONE_ELEMENT_PORT_DESCRIPTION).getIndexes()));
    }

    @Test
    void getIndexesModificationTest() {
        assertThrows(UnsupportedOperationException.class,
                () -> parsePort(BASE_PORT_DESCRIPTION).getIndexes().add(new ArrayList<>())
        );
    }

    @Test
    void getIndexesModificationTest_V2() {
        assertThrows(UnsupportedOperationException.class,
                () -> parsePort(BASE_PORT_DESCRIPTION).getIndexes().iterator().next().add(200)
        );
    }

    @Test
    void getNumbersBaseTest() {
        assertTrue(comparingNumbersListWithGivenList(BASE_NUMBERS_EXPECTED_RESULT, new PortDefaultImplementation(BASE_PORT_DESCRIPTION).getNumbers()));
    }

    @Test
    void getNumbersOrderingCheck() {
        assertTrue(comparingNumbersListWithGivenList(BASE_NUMBERS_EXPECTED_RESULT, parsePort(BASE_PORT_DESCRIPTION_WRONG_ORDER).getNumbers()));
    }

    @Test
    void getNumbersOneElementTest() {
        assertTrue(comparingNumbersListWithGivenList(ONE_ELEMENT_NUMBERS_EXPECTED_RESULT, parsePort(ONE_ELEMENT_PORT_DESCRIPTION).getNumbers()));
    }

    @Test
    void getNumbersModificationTest() {
        assertThrows(UnsupportedOperationException.class,
                () -> parsePort(BASE_PORT_DESCRIPTION).getNumbers().add(new TreeSet<>())
        );
    }

    @Test
    void getNumbersModificationTest_V2() {
        assertThrows(UnsupportedOperationException.class,
                () -> parsePort(BASE_PORT_DESCRIPTION).getNumbers().iterator().next().add(200)
        );
    }

    //for checking results
    private boolean comparingIndexesResultSetWithGivenList(List<List<Integer>> expectedResult, SortedSet<List<Integer>> result) {
        if (expectedResult.size() != result.size())
        {
            return false;
        }

        int i = 0;
        for (List<Integer> index: result)
        {
            if (!index.equals(expectedResult.get(i)))
            {
                return false;
            }

            i++;
        }

        return true;
    }

    //for checking results
    private boolean comparingNumbersListWithGivenList(List<List<Integer>> expectedResult, List<SortedSet<Integer>> result) {
        if (expectedResult.size() != result.size())
        {
            return false;
        }

        int i = 0;
        for (SortedSet<Integer> index: result)
        {
            ArrayList<Integer> indexAsList = new ArrayList<>(index);

            if (!indexAsList.equals(expectedResult.get(i)))
            {
                return false;
            }

            i++;
        }

        return true;
    }


}