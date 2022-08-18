package org.portutils;

import java.util.List;

public class PortIOUtil {

    //TODO javadoc

    /**
     *
     * @param numbersAndIntervals
     * @return
     */
    public static Port generatePortFromStringList(String[] numbersAndIntervals)
    {
        return new PortDefaultImplementation(numbersAndIntervals);
    }

}
