package org.portutils;

public class PortDescriptionParser {

    //TODO javadoc

    /**
     *
     * @param numbersAndIntervals
     * @return
     */

    public static Port parsePort(/*@NotNull*/ String[] numbersAndIntervals)
    {
        return new PortDefaultImplementation(numbersAndIntervals);
    }

}
