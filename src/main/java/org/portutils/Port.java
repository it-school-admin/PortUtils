package org.portutils;

import java.util.List;
import java.util.SortedSet;

public interface Port {

    //TODO javadoc
    /**
     *
     * @return unmodifiable sorted
     */
    SortedSet<List<Integer>> getIndexes();
    List<SortedSet<Integer>> getNumbers();
}
