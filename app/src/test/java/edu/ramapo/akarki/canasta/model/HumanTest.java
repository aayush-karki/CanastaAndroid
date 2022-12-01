package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Vector;

import org.junit.Test;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;

public class HumanTest {
	private Human mHuman = new Human();
	Human humanWithParam;
	private final String improperException = HandTest.improperException;

	/**
	 * Unit test the Human's defulat contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void humanWithParamDefaultContructor()
	{
		assertEquals(0, mHuman.getTotalPoint());
		assertEquals(new Vector<Card>(), mHuman.getActualHand());
	}

	/**
	 * Test function to test the human's constructor for exception.
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException this is when the meld has a card that is
	 *                                   not valid
	 */
	@Test(expected = ImproperMeldException.class)
	public void humanWithParamConstructorException()
			throws ImproperMeldException
	{
		try
		{
			new Human(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 5C 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			throw e;
		}
	}

	/**
	 * Unit test the Human's contructor with param
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void humanWithParamContructorWithParam()
	{
		try
		{
			Human mTestHumanWithParam = new Human(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");

			// testing for Actual hand
			Vector<Card> expectedVec = new Vector<Card>();
			expectedVec.add(new Card("J1"));
			expectedVec.add(new Card("J2"));
			expectedVec.add(new Card("AD"));
			expectedVec.add(new Card("AS"));

			assertEquals(expectedVec, mTestHumanWithParam.getActualHand());

			// testing for meld
			Vector<Vector<Card>> expectedMeldVec = new Vector<Vector<Card>>();
			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(0).add(new Card("3H"));

			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(1).add(new Card("3D"));

			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(2).add(new Card("4S"));
			expectedMeldVec.get(2).add(new Card("4H"));
			expectedMeldVec.get(2).add(new Card("4H"));
			expectedMeldVec.get(2).add(new Card("4D"));
			expectedMeldVec.get(2).add(new Card("4C"));
			expectedMeldVec.get(2).add(new Card("4C"));
			expectedMeldVec.get(2).add(new Card("2D"));

			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(3).add(new Card("6S"));
			expectedMeldVec.get(3).add(new Card("6H"));
			expectedMeldVec.get(3).add(new Card("6D"));
			expectedMeldVec.get(3).add(new Card("6C"));

			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(4).add(new Card("8S"));
			expectedMeldVec.get(4).add(new Card("8H"));
			expectedMeldVec.get(4).add(new Card("8D"));
			expectedMeldVec.get(4).add(new Card("8C"));

			expectedMeldVec.add(new Vector<Card>());
			expectedMeldVec.get(5).add(new Card("JS"));
			expectedMeldVec.get(5).add(new Card("JH"));
			expectedMeldVec.get(5).add(new Card("JD"));
			expectedMeldVec.get(5).add(new Card("JC"));

			assertEquals(expectedMeldVec, mTestHumanWithParam.getMelds());

			// testing for score
			assertEquals(100, mTestHumanWithParam.getTotalPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Human's copy contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void humanWithParamCopyContructor()
	{
		// copying empty human
		Human copyHuman = new Human(mHuman);

		Vector<Card> expectedVec = new Vector<Card>();
		assertEquals(expectedVec, mHuman.getActualHand());
		assertEquals(expectedVec, copyHuman.getActualHand());

		// checking for deep copy
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		copyHuman.addCardToHand(cardToAdd);
		assertEquals(expectedVec, copyHuman.getActualHand());
		assertNotEquals(expectedVec, mHuman.getActualHand());
	}
}
