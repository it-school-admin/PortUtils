package org.portutils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.portutils.PortDescriptionParser.parsePort;

public class PortDescriptionParserTest {
    public static final String[] BASE_PORT_DESCRIPTION = {"1,3-5", "2", "3-4"};

    @Test
    void parsePortTest()
    {
        Port createdPort = parsePort(BASE_PORT_DESCRIPTION);
        assertNotNull(createdPort);
    }
}
