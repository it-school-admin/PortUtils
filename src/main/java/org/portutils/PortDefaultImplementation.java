package org.portutils;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

/**
 * This class has package local scope. For port creation use {@link PortDescriptionParser#parsePort(String[])}
 */
class PortDefaultImplementation implements Port {
    public static final String PORT_DESCRIPTION_SHOULDNT_BE_EMPTY = "Port description shouldn't be empty.";
    public static final String PORT_DESCRIPTION_SHOULDNT_BE_NULL = "Port description shouldn't be null.";
    private final List<SortedSet<Integer>> portIndexParts;
    private final SortedSet<List<Integer>> generatedIndexes;

    PortDefaultImplementation(String[] indexes) {
        if (indexes == null) {
            throw new IllegalArgumentException(PORT_DESCRIPTION_SHOULDNT_BE_NULL);
        }

        if (indexes.length == 0) {
            throw new IllegalArgumentException(PORT_DESCRIPTION_SHOULDNT_BE_EMPTY);
        }
        portIndexParts = createUnmodifiableCollection(generateNumberSequences(indexes));
        generatedIndexes = generateIndexes(portIndexParts);
    }

    private List<SortedSet<Integer>> createUnmodifiableCollection(List<SortedSet<Integer>> portIndexPartsModifiable) {
        final List<SortedSet<Integer>> portIndexParts;
        List<SortedSet<Integer>> unmodifiableLists = new ArrayList<>();
        for (SortedSet<Integer> numbers : portIndexPartsModifiable) {
            unmodifiableLists.add(unmodifiableSortedSet(numbers));
        }

        portIndexParts = unmodifiableList(unmodifiableLists);
        return portIndexParts;
    }

    private List<SortedSet<Integer>> generateNumberSequences(String[] indexes) {
        return stream(indexes).map(PortDescriptionElementParser::parsePortElement).collect(toList());
    }

    @Override
    public List<SortedSet<Integer>> getNumbers() {
        return portIndexParts;
    }

    @Override
    public SortedSet<List<Integer>> getIndexes() {
        return generatedIndexes;
    }

    //Due to big complexity of algorithm think about to make implementation with parallel processing
    public SortedSet<List<Integer>> generateIndexes(List<SortedSet<Integer>> portIndexParts) {
        IndexMultiplicator indexMultiplicator = new IndexMultiplicator();
        portIndexParts.forEach(indexMultiplicator::addNumbers);
        return indexMultiplicator.getResult();
    }

    private class IndexMultiplicator {
        private List<List<Integer>> indexes = new ArrayList<>();

        public void addNumbers(SortedSet<Integer> sequenceOfNumbers) {
            List<List<Integer>> prefixes = indexes;

            if (prefixes.size() == 0) {
                for (Integer number : sequenceOfNumbers) {
                    prefixes.add(singletonList(number));
                }
            } else {
                List<List<Integer>> newIndexes = new ArrayList<>();
                for (List<Integer> index : prefixes) {
                    for (Integer number : sequenceOfNumbers) {
                        List<Integer> newIndex = new ArrayList<>(index);
                        newIndex.add(number);
                        newIndexes.add(newIndex);
                    }
                }
                indexes = newIndexes;
            }

        }

        public SortedSet<List<Integer>> getResult() {
            List<List<Integer>> listWithUnmodifiableElements = new ArrayList<>();
            for (List<Integer> index : indexes) {
                listWithUnmodifiableElements.add(unmodifiableList(index));
            }

            //Tree set is used in order to give a interface with strict contract. In order to
            // increase performance it is possible not to use TreeSet, just to use list.
            //Using algorithm gives an ability to get sorted results wit simple List.
            //But it seems that decreasing of performance with using TreeSet don't affect performance dramatically.

            TreeSet<List<Integer>> result = new TreeSet<>((o1, o2) -> {
                for (int i = 0; i < o1.size(); i++) {
                    Integer firstNumber = o1.get(i);
                    Integer secondNumber = o2.get(i);
                    if (firstNumber > secondNumber) {
                        return 1;
                    } else if (firstNumber < secondNumber) {
                        return -1;
                    }
                }
                return 0;
            });


            result.addAll(listWithUnmodifiableElements);

            return unmodifiableSortedSet(result);
        }
    }

}
