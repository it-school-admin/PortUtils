package org.portutils;

import org.junit.jupiter.api.Test;
import org.portutils.PortDefaultImplementation.IndexGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


//TODO remove???
public class IndexGeneratorTest {

    @Test
    void indexGeneratorTestEmptyList()
    {
        assertEquals(new ArrayList<List<Integer>>(), new IndexGenerator(new ArrayList<>()).generate());
    }
}
