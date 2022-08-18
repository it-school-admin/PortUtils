package org.portutils;

import java.util.*;

import static org.portutils.PortDescriptionElementParser.parsePortElement;

public class PortDefaultImplementation implements Port{
    private final List<SortedSet<Integer>> portSequences;

    public PortDefaultImplementation(String[] numbersAndIntervals) {
        portSequences = new ArrayList<>();
        for(String numberOrInterval :numbersAndIntervals)
        {
            portSequences.add(parsePortElement(numberOrInterval));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortDefaultImplementation port = (PortDefaultImplementation) o;
        return portSequences.equals(port.portSequences);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        Iterator<List<Integer>> indexesIterator = getIndexes().iterator();
        while (indexesIterator.hasNext())
        {
            List<Integer> index = indexesIterator.next();
            result.append("[");
            Iterator<Integer> numbersIterator = index.iterator();
            while (numbersIterator.hasNext())
            {
                result.append(numbersIterator.next());
                if(numbersIterator.hasNext())
                    result.append(", ");
            }
            result.append("]");
            if (indexesIterator.hasNext())
                result.append(", ");
        }

        result.append("}");
        return result.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(portSequences);
    }

    @Override
    public List<List<Integer>> getIndexes() {
        return new IndexGenerator(portSequences).generate();
    }

    static class IndexGenerator {
        private final List<SortedSet<Integer>> portSequences;

        public IndexGenerator(List<SortedSet<Integer>> portSequences) {
            this.portSequences = portSequences;
        }

        public List<List<Integer>> generate() {
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
                        prefixes.add(Arrays.asList(number));
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

            public List<List<Integer>> getResult() {
                return indexes;
            }
        }
    }
}
