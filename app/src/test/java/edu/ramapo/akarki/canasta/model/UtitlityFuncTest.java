package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtitlityFuncTest {

	/**
	 * Unit test the Player's validateNumber() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testValidateNumber()
	{

		Integer returnVal = -1;
		assertEquals(returnVal, UtitlityFunc.validateNumber(null, 1, 9));
		assertEquals(returnVal, UtitlityFunc.validateNumber("as", 1, 9));
		assertEquals(returnVal, UtitlityFunc.validateNumber("9as", 1, 9));
		assertEquals(returnVal, UtitlityFunc.validateNumber("", 1, 9));
		assertEquals(returnVal, UtitlityFunc.validateNumber("10", 1, 9));

		returnVal = 9;
		assertEquals(returnVal, UtitlityFunc.validateNumber("9", 1, 9));
		returnVal = 1;
		assertEquals(returnVal, UtitlityFunc.validateNumber("1", 1, 9));
		returnVal = 5;
		assertEquals(returnVal, UtitlityFunc.validateNumber("5", 1, 9));
	}
}
