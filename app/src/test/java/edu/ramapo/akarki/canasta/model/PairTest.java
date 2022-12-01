package edu.ramapo.akarki.canasta.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {
    private Pair<Boolean, String> testPair = new Pair<Boolean, String>(true,
            "Test");

    /**
     * Unit test the Pair's GetFirst() function
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testGetFirst()
    {
        assertEquals(true, testPair.getFirst());
    }

    /**
     * Unit test the Pair's GetFirst() function
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testGetSecond()
    {
        assertEquals("Test", testPair.getSecond());
    }

    /**
     * Unit test the Pair's GetFirst() function
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testSetFirst()
    {
        testPair.setFirst(false);
        assertEquals(false, testPair.getFirst());
    }

    /**
     * Unit test the Pair's GetFirst() function
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testSetSecond()
    {
        testPair.setSecond("falseTest");
        assertEquals("falseTest", testPair.getSecond());
    }
}
