package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ValidRankSuitTest {

    /**
     * Test function to test the IsRankSuitValid() funciton of the ValidRankSuit
     * Class throwing exception.
     * 
     * @param none
     * 
     * @return none
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsRankSuitValidException()
    {
        ValidRankSuit.isRankSuitValid("");
        ValidRankSuit.isRankSuitValid(" ");
        ValidRankSuit.isRankSuitValid("\"");
        ValidRankSuit.isRankSuitValid("8S9C");
    }

    /**
     * Test function to test the IsRankSuitValid() funciton of the ValidRankSuit
     * Class.
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testIsRankSuitValid()
    {
        // true when rank is 2 3 4 5 6 7 8 9 X J Q K and suit is 1 2 S C D H
        // and the string only has 2 char as rank as first char and
        // suit as second char
        assertEquals(true, ValidRankSuit.isRankSuitValid("J1"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("J2"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("JS"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("2D"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("3H"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("5S"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("6H"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("8H"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("9C"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("XD"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("JH"));
        assertEquals(true, ValidRankSuit.isRankSuitValid("QH"));

        // false
        assertEquals(false, ValidRankSuit.isRankSuitValid("j1"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("j2"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("Js"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("2d"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("3h"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("6h"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("7d"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("8h"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("9c"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("Xd"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("Jh"));
        assertEquals(false, ValidRankSuit.isRankSuitValid("Kd"));

    }

    /**
     * Test function to test the isRankValid() funciton of the ValidRankSuit
     * Class throwing exception.
     * 
     * @param none
     * 
     * @return none
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsRankValidException()
    {
        ValidRankSuit.isRankValid("");
        ValidRankSuit.isRankValid("  ");
        ValidRankSuit.isRankValid("8S");
        ValidRankSuit.isRankValid("8S9C");
    }

    /**
     * Test function to test the IsRankValid() funciton of the ValidRankSuit
     * Class
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testIsRankValid()
    {
        // true for 2 3 4 5 6 7 8 9 X J Q K
        assertEquals(true, ValidRankSuit.isRankValid("2"));
        assertEquals(true, ValidRankSuit.isRankValid("3"));
        assertEquals(true, ValidRankSuit.isRankValid("4"));
        assertEquals(true, ValidRankSuit.isRankValid("5"));
        assertEquals(true, ValidRankSuit.isRankValid("6"));
        assertEquals(true, ValidRankSuit.isRankValid("7"));
        assertEquals(true, ValidRankSuit.isRankValid("8"));
        assertEquals(true, ValidRankSuit.isRankValid("9"));
        assertEquals(true, ValidRankSuit.isRankValid("X"));
        assertEquals(true, ValidRankSuit.isRankValid("J"));
        assertEquals(true, ValidRankSuit.isRankValid("Q"));
        assertEquals(true, ValidRankSuit.isRankValid("K"));

        // false for anything else
        assertEquals(false, ValidRankSuit.isRankValid(" "));
        assertEquals(false, ValidRankSuit.isRankValid("r"));
        assertEquals(false, ValidRankSuit.isRankValid("R"));
        assertEquals(false, ValidRankSuit.isRankValid("1"));
        assertEquals(false, ValidRankSuit.isRankValid("\""));
        assertEquals(false, ValidRankSuit.isRankValid("@"));
        assertEquals(false, ValidRankSuit.isRankValid("q"));
    }

    /**
     * Test function to test the isSuitValid() funciton of the ValidRankSuit
     * Class throwing exception.
     * 
     * @param none
     * 
     * @return none
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsSuitValidException()
    {
        ValidRankSuit.isSuitValid("");
        ValidRankSuit.isSuitValid("  ");
        ValidRankSuit.isSuitValid("8S");
        ValidRankSuit.isSuitValid("8S9C");
    }

    /**
     * Test function to test the IsSuitValid() funciton of the ValidRankSuit
     * Class
     * 
     * @param none
     * 
     * @return none
     */
    @Test
    public void testIsSuitValid()
    {
        // true for 1 2 S C D H
        assertEquals(true, ValidRankSuit.isSuitValid("1"));
        assertEquals(true, ValidRankSuit.isSuitValid("2"));
        assertEquals(true, ValidRankSuit.isSuitValid("S"));
        assertEquals(true, ValidRankSuit.isSuitValid("C"));
        assertEquals(true, ValidRankSuit.isSuitValid("D"));
        assertEquals(true, ValidRankSuit.isSuitValid("H"));

        // false for anything else
        assertEquals(false, ValidRankSuit.isSuitValid(" "));
        assertEquals(false, ValidRankSuit.isSuitValid("r"));
        assertEquals(false, ValidRankSuit.isSuitValid("R"));
        assertEquals(false, ValidRankSuit.isSuitValid("6"));
        assertEquals(false, ValidRankSuit.isSuitValid("\""));
        assertEquals(false, ValidRankSuit.isSuitValid("q"));
        assertEquals(false, ValidRankSuit.isSuitValid("h"));
    }
}