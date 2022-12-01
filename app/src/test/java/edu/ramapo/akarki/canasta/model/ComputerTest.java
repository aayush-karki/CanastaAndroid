package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;

public class ComputerTest {
	private Computer mComputer = new Computer();
	Computer mComputerWithParam;
	private final String improperException = HandTest.improperException;

	/**
	 * Unit test the Computer's defulat contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void computerWithParamDefaultContructor()
	{
		assertEquals(0, mComputer.getTotalPoint());
		assertEquals(new Vector<Card>(), mComputer.getActualHand());
	}

	/**
	 * Test function to test the computer's constructor for exception.
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException this is when the meld has a card that is
	 *                                   not valid
	 */
	@Test(expected = ImproperMeldException.class)
	public void computerWithParamConstructorException()
			throws ImproperMeldException
	{
		try
		{
			new Computer(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 5C 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			throw e;
		}
	}

	/**
	 * Unit test the Computer's contructor with param
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void computerWithParamContructorWithParam()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");

			// testing for Actual hand
			Vector<Card> expectedVec = new Vector<Card>();
			expectedVec.add(new Card("J1"));
			expectedVec.add(new Card("J2"));
			expectedVec.add(new Card("AD"));
			expectedVec.add(new Card("AS"));

			assertEquals(expectedVec, mComputerWithParam.getActualHand());

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

			assertEquals(expectedMeldVec, mComputerWithParam.getMelds());

			// testing for score
			assertEquals(100, mComputerWithParam.getTotalPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Computer's copy contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void computerWithParamCopyContructor()
	{
		// copying empty computer
		Computer copyComputer = new Computer(mComputer);

		Vector<Card> expectedVec = new Vector<Card>();
		assertEquals(expectedVec, mComputer.getActualHand());
		assertEquals(expectedVec, copyComputer.getActualHand());

		// checking for deep copy
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		copyComputer.addCardToHand(cardToAdd);
		assertEquals(expectedVec, copyComputer.getActualHand());
		assertNotEquals(expectedVec, mComputer.getActualHand());
	}

	/**
	 * Unit test the Computer's beforeTurnStartControl() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testBeforeTurnStartControl()
	{
		Pair<Integer, Vector<Integer>> expectedResult = new Pair<Integer, Vector<Integer>>(
				1, new Vector<Integer>(Arrays.asList(2)));

		assertEquals(expectedResult, mComputer.beforeTurnStartControl());
	}

	/**
	 * Unit test the Computer's turnStartHelp() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testTurnStartHelp()
	{

	}

	/**
	 * Unit test the Computer's turnContinueHelp() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testTurnContinueHelp()
	{

	}

	/**
	 * Unit test the Computer's canMeldCardFromHand() function for valid result
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testValidCanMeldCardFromHand()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  6S  J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		// check if the canMeldCardFromHand says that we can add 6s to the meld
		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(2, 3, 4)));

		Pair<Pair<Integer, Vector<Integer>>, String> canMeldCardFromHandReturn = mComputerWithParam
				.canMeldCardFromHand();

		assertEquals(expReturn, canMeldCardFromHandReturn.getFirst());
	}

	/**
	 * Unit test the Computer's canMeldCardFromHand() function for invalid
	 * result
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testInValidCanMeldCardFromHand()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for invalid result

		Pair<Pair<Integer, Vector<Integer>>, String> canMeldCardFromHandReturn = mComputerWithParam
				.canMeldCardFromHand();

		assertEquals(mComputerWithParam.mErrorPairCode,
				canMeldCardFromHandReturn.getFirst());
	}

	/**
	 * Unit test the Computer's canMakeNewMeld() function when there are 3
	 * natural card of same rank
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCanMakeNewMeld3Card()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  AS ",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		// check if the canMakeNewMeld says that we can make a new card meld by
		// using card at idx 0 1 2
		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(5, 0, 1, 2)));

		Pair<Pair<Integer, Vector<Integer>>, String> makeNewMeldReturn = mComputerWithParam
				.canMakeNewMeld();

		assertEquals(expReturn, makeNewMeldReturn.getFirst());
	}

	/**
	 * Unit test the Computer's canMakeNewMeld() function when there are 2
	 * natural card of same rank and 1 wild card
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCanMakeNewMeld2Card1WD()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD   J1 3S 3S J2",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(5, 4, 5, 0)));

		Pair<Pair<Integer, Vector<Integer>>, String> makeNewMeldReturn = mComputerWithParam
				.canMakeNewMeld();

		assertEquals(expReturn, makeNewMeldReturn.getFirst());
	}

	/**
	 * Unit test the Computer's canMakeNewMeld() function when there is no valid
	 * make new meld
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testInvalidCanMakeNewMeld()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  ",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result
		Pair<Pair<Integer, Vector<Integer>>, String> makeNewMeldReturn = mComputerWithParam
				.canMakeNewMeld();

		assertEquals(mComputerWithParam.mErrorPairCode,
				makeNewMeldReturn.getFirst());
	}

	/**
	 * Unit test the Computer's getMeldWithExtraWC() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetMeldWithExtraWC()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  ",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D J1]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		Pair<Boolean, Pair<Integer, Integer>> getMeldWithExtraWCReturn = mComputerWithParam
				.getMeldWithExtraWC();

		assertTrue(getMeldWithExtraWCReturn.getFirst());
	}

	/**
	 * Unit test the Computer's getMeldWithExtraWC() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testinvalidGetMeldWithExtraWC()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  ",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		Pair<Boolean, Pair<Integer, Integer>> getMeldWithExtraWCReturn = mComputerWithParam
				.getMeldWithExtraWC();

		assertFalse(getMeldWithExtraWCReturn.getFirst());
	}

	/**
	 * Unit test the Computer's addWCToLargestPossibleMeld() function valid test
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testAddWCToLargestPossibleMeld()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  J1",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D 6D]  [8H 8H 8H J1 J1 J1]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result
		// check if the canMakeNewMeld says that we can make a new card meld by
		// using card at idx 0 1 2
		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(2, 0, 4)));

		Pair<Pair<Integer, Vector<Integer>>, String> addWCToLargestPossibleMeldReturn = mComputerWithParam
				.addWCToLargestPossibleMeld();

		assertEquals(expReturn, addWCToLargestPossibleMeldReturn.getFirst());
	}

	/**
	 * Unit test the Computer's addWCToLargestPossibleMeld() function invalid
	 * test
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testinvalidAddWCToLargestPossibleMeld()
	{
		try
		{
			mComputerWithParam = new Computer(100, " AS AD  ",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for valid result

		Pair<Pair<Integer, Vector<Integer>>, String> addWCToLargestPossibleMeldReturn = mComputerWithParam
				.addWCToLargestPossibleMeld();

		assertEquals(mComputerWithParam.mErrorPairCode,
				addWCToLargestPossibleMeldReturn.getFirst());
	}

	/**
	 * Unit test the Computer's discardLogic() function to see it suggests to
	 * throw the only card at hand
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDiscardLoigcGoOutOnlyCard()
	{

		try
		{
			mComputerWithParam = new Computer(100, " 5S",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		Vector<Vector<Card>> otherPlayerMeld = new Vector<Vector<Card>>();
		otherPlayerMeld.add(new Vector<Card>());
		// otherPlayerMeld.firstElement().add()

		Vector<Card> discardPile = new Vector<Card>();

		Pair<Pair<Integer, Vector<Integer>>, String> discardReturn = mComputerWithParam
				.discardLogic(otherPlayerMeld, discardPile);

		assertEquals("Discard the only card in the hand and go out",
				discardReturn.getSecond());
	}

	/**
	 * Unit test the Computer's discardLogic() function to see it suggests to
	 * throw when there are 2 or more card in the hand
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDiscardLoigcGoOut()
	{

		try
		{
			mComputerWithParam = new Computer(100, " 5S 7H",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		Vector<Vector<Card>> otherPlayerMeld = new Vector<Vector<Card>>();

		Vector<Card> discardPile = new Vector<Card>();

		Pair<Pair<Integer, Vector<Integer>>, String> discardReturn = mComputerWithParam
				.discardLogic(otherPlayerMeld, discardPile);

		assertEquals(
				"Discard the card at index: 0 as the opponent does not have a meld of this rank",
				discardReturn.getSecond());

		otherPlayerMeld.add(new Vector<Card>());
		otherPlayerMeld.firstElement().add(new Card("5S"));
		otherPlayerMeld.firstElement().add(new Card("5S"));
		otherPlayerMeld.firstElement().add(new Card("5S"));

		discardReturn = mComputerWithParam.discardLogic(otherPlayerMeld,
				discardPile);

		assertEquals(
				"Discard the card at index: 1 as the opponent does not have a meld of this rank",
				discardReturn.getSecond());

	}

	/**
	 * Unit test the Computer's discardLogic() function to see it suggest to
	 * discard black three when the discard pile is frozen
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDiscardLoigcBlack3()
	{

		try
		{
			mComputerWithParam = new Computer(100, " AS AD    J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// chekcing if the discard logic suggest to discard the black three
		// if the discard pile is frozen and we have a black 3 at hand
		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(3, 2)));

		Vector<Vector<Card>> otherPlayerMeld = null;
		Vector<Card> discardPile = new Vector<Card>(
				Arrays.asList(new Card("3S")));

		Pair<Pair<Integer, Vector<Integer>>, String> discardReturn = mComputerWithParam
				.discardLogic(otherPlayerMeld, discardPile);

		assertEquals(expReturn, discardReturn.getFirst());
	}

	/**
	 * Unit test the Computer's emptyHandDiscardLogic() function
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException
	 */
	@Test
	public void testEmptyHandDiscardLogic() throws ImproperMeldException
	{
		try
		{
			mComputerWithParam = new Computer(100, "",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		Vector<Vector<Card>> otherPlayerMeld = new Vector<Vector<Card>>();
		Vector<Card> discardPile = new Vector<Card>();

		Pair<Integer, Vector<Integer>> expReturn = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(8, 0, 4)));

		Pair<Pair<Integer, Vector<Integer>>, String> emptyHandDiscardLogicReturn = mComputerWithParam
				.emptyHandDiscardLogic(otherPlayerMeld, discardPile);

		assertEquals(expReturn, emptyHandDiscardLogicReturn.getFirst());

		// when opponent has a meld of our thing
		otherPlayerMeld.add(new Vector<Card>());
		otherPlayerMeld.firstElement().add(new Card("6C"));
		otherPlayerMeld.firstElement().add(new Card("6C"));
		otherPlayerMeld.firstElement().add(new Card("6C"));

		expReturn = new Pair<Integer, Vector<Integer>>(3,
				new Vector<Integer>(Arrays.asList(8, 0, 5)));

		emptyHandDiscardLogicReturn = mComputerWithParam
				.emptyHandDiscardLogic(otherPlayerMeld, discardPile);

		assertEquals(expReturn, emptyHandDiscardLogicReturn.getFirst());
	}
}
