package org.portutils;

/**
 * The class contains static method that gives an ability to create @{@link Port} object.
 */
public class PortDescriptionParser {

    /**
     * The method returns Port element that is build with given array of indexes.
     * @param indexes array of strings that describes the port indexes. Each element can contains a single value,
     *                comma-separated list or interval description with dash-separator.
     *                For example "90-95, 1, 4, 3,2"
     *
     * @return - created with given indexes @{@link Port} object
     */

    public static Port parsePort(/*@NotNull*/ String[] indexes)
    {
        return new PortDefaultImplementation(indexes);
    }

}
