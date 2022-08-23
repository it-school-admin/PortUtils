package org.portutils;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSortedSet;
import static org.portutils.PortDescriptionElementParser.parsePortElement;

/**
 * This class has package local scope. For port creation use {@link PortDescriptionParser#parsePort(String[])}
 */
class PortDefaultImplementation implements Port {
    public static final String PORT_DESCRIPTION_SHOULDNT_BE_EMPTY = "Port description shouldn't be empty.";
    public static final String PORT_DESCRIPTION_SHOULDNT_BE_NULL = "Port description shouldn't be null.";
    private final List<SortedSet<Integer>> portIndexParts;

    PortDefaultImplementation(String[] numbersAndIntervals) {
        if (numbersAndIntervals == null)
        {
            throw new IllegalArgumentException(PORT_DESCRIPTION_SHOULDNT_BE_NULL);
        }

        if (numbersAndIntervals.length == 0)
        {
            throw new IllegalArgumentException(PORT_DESCRIPTION_SHOULDNT_BE_EMPTY);
        }
        portIndexParts = new ArrayList<>();
        for(String numberOrInterval :numbersAndIntervals)
        {
            portIndexParts.add(parsePortElement(numberOrInterval));
        }
    }

    @Override
    public List<SortedSet<Integer>> getNumbers() {
        List<SortedSet<Integer>> unmodifiableLists = new ArrayList();
        for (SortedSet<Integer> numbers: portIndexParts)
        {
            unmodifiableLists.add(unmodifiableSortedSet(numbers));
        }
        return unmodifiableList(unmodifiableLists);
    }

    @Override
    public SortedSet<List<Integer>> getIndexes() {
        return new IndexGenerator(portIndexParts).generate();
    }


    static class IndexGenerator {
        private final List<SortedSet<Integer>> portSequences;

        public IndexGenerator(List<SortedSet<Integer>> portIndexes) {
            this.portSequences = portIndexes;
        }

        public SortedSet<List<Integer>> generate() {
            IndexMultiplicator indexMultiplicator = new IndexMultiplicator();
            for (SortedSet<Integer> sequenceOfNumbers: portSequences)
            {
                indexMultiplicator.addNumbers(sequenceOfNumbers);
            }

            return indexMultiplicator.getResult();
        }

        private class IndexMultiplicator {
            private List<List<Integer>> indexes = new ArrayList<>();

            public void addNumbers(SortedSet<Integer> sequenceOfNumbers) {
                List<List<Integer>> prefixes = indexes;

                if (prefixes.size() == 0)
                {
                    for (Integer number: sequenceOfNumbers) {
                        prefixes.add(asList(number));
                    }
                }
                else
                {
                    List<List<Integer>> newIndexes = new ArrayList<>();
                    for (List<Integer> index: prefixes)
                    {
                        for (Integer number: sequenceOfNumbers)
                        {
                            List<Integer> newIndex = new ArrayList<>();
                            newIndex.addAll(index);
                            newIndex.add(number);
                            newIndexes.add(newIndex);
                        }
                    }
                    indexes = newIndexes;
                }

            }

            public SortedSet<List<Integer>> getResult() {
                List<List<Integer>> listWithUnmodifiableElements = new ArrayList<>();
                for (List<Integer> index: indexes)
                {
                    listWithUnmodifiableElements.add(unmodifiableList(index));
                }

                TreeSet<List<Integer>> result = new TreeSet<>((o1, o2) -> {
                    for(int i = 0; i<o1.size(); i++)
                    {
                        Integer firstNumber = o1.get(i);
                        Integer secondNumber = o2.get(i);
                        if (firstNumber>secondNumber)
                        {
                            return 1;
                        }
                        else if (firstNumber<secondNumber)
                        {
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
}
