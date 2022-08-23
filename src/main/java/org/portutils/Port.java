package org.portutils;

import java.util.List;
import java.util.SortedSet;

/**
 * Main interface that implements port functionality that can be created with {@link PortDescriptionParser}
 */
public interface Port {

    /**
     * @return unmodifiable sorted collection of indexes that is combination of given description.
     * Each element of resulting set is unmodifiable list.
     */
    SortedSet<List<Integer>> getIndexes();

    /**
     * @return unmodifiable list of sorted numbers that the result of the parsing given descriptions. Each element
     * of the list is the unmodifiable sorted set.
     */
    List<SortedSet<Integer>> getNumbers();
}
