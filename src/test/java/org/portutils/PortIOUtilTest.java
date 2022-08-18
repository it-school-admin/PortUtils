package org.portutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.portutils.PortIOUtil.generatePortFromStringList;

public class PortIOUtilTest {

    public static final String[] PORT_DESCRIPTION = {"1,3-5", "2", "3-4"};
    public static final String EXPECTED = "{[1, 2, 3], [1, 2, 4], [3, 2, 3], [3, 2, 4], [4, 2, 3], [4, 2, 4], [5, 2, 3], [5, 2, 4]}";


    @Test
    void toPortTest() {
        assertEquals(EXPECTED, generatePortFromStringList(PORT_DESCRIPTION).toString());
    }

}
