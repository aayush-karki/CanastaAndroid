package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;

public class PlayerTest {
	private Player mPlayer = new Player();
	Player playerWithParam;
	private final String improperException = HandTest.improperException;

	/**
	 * Unit test the Player's defulat contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void playerWithParamDefaultContructor()
	{
		assertEquals(0, mPlayer.getTotalPoint());
		assertEquals(new Vector<Card>(), mPlayer.getActualHand());
	}

	/**
	 * Test function to test the player's constructor for exception.
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException this is when the meld has a card that is
	 *                                   not valid
	 */
	@Test(expected = ImproperMeldException.class)
	public void playerWithParamConstructorException()
			throws ImproperMeldException
	{
		try
		{
			new Player(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 5C 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			throw e;
		}
	}

	/**
	 * Unit test the Player's contructor with param
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void playerWithParamContructorWithParam()
	{
		try
		{
			Player mTestPlayerWithParam = new Player(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");

			// testing for Actual hand
			Vector<Card> expectedVec = new Vector<Card>();
			expectedVec.add(new Card("J1"));
			expectedVec.add(new Card("J2"));
			expectedVec.add(new Card("AD"));
			expectedVec.add(new Card("AS"));

			assertEquals(expectedVec, mTestPlayerWithParam.getActualHand());

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

			assertEquals(expectedMeldVec, mTestPlayerWithParam.getMelds());

			// testing for score
			assertEquals(100, mTestPlayerWithParam.getTotalPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's copy contructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void playerWithParamCopyContructor()
	{
		// copying empty player
		Player copyPlayer = new Player(mPlayer);

		Vector<Card> expectedVec = new Vector<Card>();
		assertEquals(expectedVec, mPlayer.getActualHand());
		assertEquals(expectedVec, copyPlayer.getActualHand());

		// checking for deep copy
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		copyPlayer.addCardToHand(cardToAdd);
		assertEquals(expectedVec, copyPlayer.getActualHand());
		assertNotEquals(expectedVec, mPlayer.getActualHand());

	}

	/**
	 * Unit test the Player's toString() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testToString()
	{
		assertEquals("Score: 0\nHand: \nMelds: ", mPlayer.toString());

		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C] [JH JC JS JD J1 J2 J1]  [3D] [3H]  ");

			assertEquals(
					"Score: 100\nHand: J1 J2 3S 4S AD AS\nMelds: [3H] [3D] [4S 4H 4H 4D 4C 4C] [JS JH JD JC J2 J1 J1]",
					playerWithParam.toString());

		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's getTotalPoint() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetTotalPoint()
	{
		assertEquals(0, mPlayer.getTotalPoint());

		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C] [JH JC JS JD J1 J2 J1]  [3D] [3H]  ");

			assertEquals(100, playerWithParam.getTotalPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's getActualHand() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetActualHand()
	{
		mPlayer.addCardToHand(new Card("XD"));

		Vector<Card> expectedVec = new Vector<Card>();
		expectedVec.add(new Card("XD"));
		Vector<Card> mPlayerVec = mPlayer.getActualHand();

		assertEquals(expectedVec, mPlayerVec);

		// testing to see if the it is mutable
		mPlayerVec.set(0, new Card("XC"));
		assertEquals(expectedVec, mPlayer.getActualHand());
	}

	/**
	 * Unit test the Player's getMelds() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetMelds()
	{
		try
		{
			Player mTestPlayerWithParam = new Player(100, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 4C 2D]  [6H 6C 6S 6D]  [8H 8C 8S 8D]  [JH JC JS JD]  [3D] [3H]  ");

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

			assertEquals(expectedMeldVec, mTestPlayerWithParam.getMelds());

			// testing for score
			assertEquals(100, mTestPlayerWithParam.getTotalPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's getActualHandString() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetActualHandString()
	{
		mPlayer.addCardToHand(new Card("XD"));
		mPlayer.addCardToHand(new Card("5C"));
		mPlayer.addCardToHand(new Card("3D"));
		mPlayer.addCardToHand(new Card("XC"));

		assertEquals("5C XC XD", mPlayer.getActualHandString());
	}

	/**
	 * Unit test the Player's getMeldsString() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetMeldsString()
	{
		mPlayer.addCardToHand(new Card("XD"));
		mPlayer.addCardToHand(new Card("5C"));
		mPlayer.addCardToHand(new Card("XC"));

		assertEquals("", mPlayer.getMeldsString());

		mPlayer.addCardToHand(new Card("3D"));
		assertEquals("[3D]", mPlayer.getMeldsString());

		try
		{
			playerWithParam = new Player(120, " AS AD    J1 J2",
					"[4H 4C 4S 4D 4H 4C 2D] [JH JC JS JD J1]  [3D] [3H]  ");

			assertEquals("[3H] [3D] [4S 4H 4H 4D 4C 4C 2D] [JS JH JD JC J1]",
					playerWithParam.getMeldsString());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's getPlayerWentOutStatus() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetPlayerWentOutStatus()
	{
		assertFalse(mPlayer.getPlayerWentOutStatus());
	}

	/**
	 * Unit test the Player's getTurnStartFlag() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetTurnStartFlag()
	{
		assertTrue(mPlayer.getTurnStartFlag());
	}

	/**
	 * Unit test the Player's addToTotalPoints() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetDividedAcualHandCardList()
	{
		mPlayer.addCardToHand(new Card("5C"));
		mPlayer.addCardToHand(new Card("XD"));
		mPlayer.addCardToHand(new Card("XC"));

		Vector<Vector<Card>> expectedVec = new Vector<Vector<Card>>();
		expectedVec.add(new Vector<Card>());
		expectedVec.get(0).add((new Card("5C")));
		expectedVec.add(new Vector<Card>());
		expectedVec.get(1).add((new Card("XC")));
		expectedVec.get(1).add((new Card("XD")));

		assertEquals(expectedVec, mPlayer.getDividedAcualHandCardList());
	}

	/**
	 * Unit test the Player's setPlayerWentOutStatus() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testSetPlayerWentOutStatus()
	{
		mPlayer.setPlayerWentOutStatus(true);
		assertTrue(mPlayer.getPlayerWentOutStatus());

		mPlayer.setPlayerWentOutStatus(false);
		assertFalse(mPlayer.getPlayerWentOutStatus());

	}

	/**
	 * Unit test the Player's setPlayerBeforeTurnMenuFlag() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testSetPlayerBeforeTurnMenuFlag()
	{
		mPlayer.setPlayerBeforeTurnMenuFlag(true);
		assertTrue(mPlayer.mShowBeforeTurnMenu);

		mPlayer.setPlayerBeforeTurnMenuFlag(false);
		assertFalse(mPlayer.mShowBeforeTurnMenu);
	}

	/**
	 * Unit test the Player's setTurnStartFlag() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testSetTurnStartFlag()
	{
		mPlayer.setTurnStartFlag(true);
		assertTrue(mPlayer.mIsStartOfTurn);

		mPlayer.setTurnStartFlag(false);
		assertFalse(mPlayer.mIsStartOfTurn);
	}

	/**
	 * Unit test the Player's beforeTurnStartControl() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testBeforeTurnStartControl()
	{
		// backup System.in to restore it later
		InputStream sysInBackup = System.in;

		// testing with invalid menu number
		String data = "9";
		Vector<String> expectedMessage = new Vector<String>();

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(mPlayer.mErrorPairCode, mPlayer.beforeTurnStartControl());

		// checking for error messages
		expectedMessage.add(new String("Invalid Input!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "1 5";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(mPlayer.mErrorPairCode, mPlayer.beforeTurnStartControl());

		// checking for error messages
		expectedMessage.add(new String("Invalid Input!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing for valid input
		data = "1";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		Pair<Integer, Vector<Integer>> expReturnVal = new Pair<Integer, Vector<Integer>>(
				1, new Vector<Integer>(Arrays.asList(1)));

		// testing for valid return
		assertEquals(expReturnVal, mPlayer.beforeTurnStartControl());

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// reset System.in to its original
		System.setIn(sysInBackup);
	}

	/**
	 * Unit test the Player's turnStartControl() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */

	@Test
	public void testTurnStartControl()
	{
		// backup System.in to restore it later
		InputStream sysInBackup = System.in;

		// testing with invalid menu number
		String data = "9";
		Vector<String> expectedMessage = new Vector<String>();

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(mPlayer.mErrorPairCode,
				mPlayer.turnStartControl(new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid Input!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "1 5";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(mPlayer.mErrorPairCode,
				mPlayer.turnStartControl(new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid Input!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing for valid input
		data = "1";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		Pair<Integer, Vector<Integer>> expReturnVal = new Pair<Integer, Vector<Integer>>(
				2, new Vector<Integer>(Arrays.asList(1)));

		// testing for valid return
		assertEquals(expReturnVal,
				mPlayer.turnStartControl(new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// reset System.in to its original
		System.setIn(sysInBackup);
	}

	/**
	 * Unit test the Player's turnContinueControl() function
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException
	 */
	@Test
	public void testTurnContinueControl() throws ImproperMeldException
	{
		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C] [JH JC JS JD J1 J2 J1]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// backup System.in to restore it later
		InputStream sysInBackup = System.in;

		// testing with invalid menu number
		String data = "9";
		Vector<String> expectedMessage = new Vector<String>();

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid Input!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// =========== testing for 1st menu ================
		data = "1";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		Pair<Integer, Vector<Integer>> expReturnVal = new Pair<Integer, Vector<Integer>>(
				3, new Vector<Integer>(Arrays.asList(1)));

		// testing for valid return
		assertEquals(expReturnVal, playerWithParam.turnContinueControl(
				new Vector<Vector<Card>>(), new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// testing with valid menu number but invlaid number of arguments
		data = "1 5";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String(
				"Invalid number of input argument for the chosen menu!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// =========== testing for 2nd menu ================

		data = "2 1 2";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		expReturnVal = new Pair<Integer, Vector<Integer>>(3,
				new Vector<Integer>(Arrays.asList(2, 1, 2)));

		// testing for valid return
		assertEquals(expReturnVal, playerWithParam.turnContinueControl(
				new Vector<Vector<Card>>(), new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// testing with valid menu number but invlaid meld index
		data = "2 1 8";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid meld index!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid acutal hand card index
		data = "2 10 3";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid actual hand index!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "2 1";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String(
				"Invalid number of input argument for the chosen menu!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// =========== testing for 3rd menu ================

		data = "3 3";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		expReturnVal = new Pair<Integer, Vector<Integer>>(3,
				new Vector<Integer>(Arrays.asList(3, 3)));

		// testing for valid return
		assertEquals(expReturnVal, playerWithParam.turnContinueControl(
				new Vector<Vector<Card>>(), new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// testing with valid menu number 3 but invlaid actual card index
		data = "3 10";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid actual hand index!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "3 3 3";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String(
				"Invalid number of input argument for the chosen menu!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// =========== testing for 5th menu ================

		data = "5 0 1 2";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		expReturnVal = new Pair<Integer, Vector<Integer>>(3,
				new Vector<Integer>(Arrays.asList(5, 0, 1, 2)));

		// testing for valid return
		assertEquals(expReturnVal, playerWithParam.turnContinueControl(
				new Vector<Vector<Card>>(), new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// testing with valid menu number 5 but invlaid actual card index
		data = "5 0 1 10";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid actual hand index!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "5 3 3";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String(
				"Invalid number of input argument for the chosen menu!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// =========== testing for 8th menu ================

		data = "8 1 0";
		System.setIn(new ByteArrayInputStream(data.getBytes()));

		expReturnVal = new Pair<Integer, Vector<Integer>>(3,
				new Vector<Integer>(Arrays.asList(8, 1, 0)));

		// testing for valid return
		assertEquals(expReturnVal, playerWithParam.turnContinueControl(
				new Vector<Vector<Card>>(), new Vector<Card>()));

		// checking for error messages
		assertEquals(expectedMessage, Message.getMessages());

		// testing with valid menu number 8 but invlaid meld index
		data = "8 10 1";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid card index in the Meld!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number 8 but invlaid card index in a valid
		// meld
		data = "8 0 10";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String("Invalid meld index!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// testing with valid menu number but invlaid number of arguments
		data = "8 3";

		// testing for error
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		assertEquals(playerWithParam.mErrorPairCode,
				playerWithParam.turnContinueControl(new Vector<Vector<Card>>(),
						new Vector<Card>()));

		// checking for error messages
		expectedMessage.add(new String(
				"Invalid number of input argument for the chosen menu!!"));
		assertEquals(expectedMessage, Message.getMessages());

		// resetting the message for further testing
		expectedMessage.clear();
		Message.clearLatestMessages();

		// reset System.in to its original
		System.setIn(sysInBackup);
	}

	/**
	 * Unit test the Player's addToTotalPoints() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testAddToTotalPoints()
	{
		assertEquals(0, mPlayer.getTotalPoint());
		mPlayer.addToTotalPoints(150);
		assertEquals(150, mPlayer.getTotalPoint());
	}

	/**
	 * Unit test the Player's tallyHandPoint() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testTallyHandPoint()
	{
		int expecetedPoint = 0;
		assertEquals(expecetedPoint, mPlayer.tallyHandPoint());

		mPlayer.addCardToHand(new Card("XD"));
		expecetedPoint = -10;
		assertEquals(expecetedPoint, mPlayer.tallyHandPoint());

		try
		{
			playerWithParam = new Player(2000, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC JH JC JH JC JH]  [3D] [3H]  ");
			expecetedPoint = (80 + 300) + (70 + 500) + 100 + 100 - 150;
			assertEquals(expecetedPoint, playerWithParam.tallyHandPoint());
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's addCardToHand() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testAddCardToHand()
	{
		Vector<Card> expectedVec = new Vector<Card>();
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		mPlayer.addCardToHand(cardToAdd);
		assertEquals(expectedVec, mPlayer.getActualHand());
	}

	/**
	 * Unit test the Player's addToMeld() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testAddToMeld()
	{
		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C] [JH JC JS JD J1 J2 J1]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing black three
		assertEquals(new Pair<Boolean, String>(false, "Cannot meld a Black 3"),
				playerWithParam.addToMeld(2, 2));

		// testing for wild card
		assertTrue(playerWithParam.addToMeld(0, 3).getFirst());

		// testing for natural card
		assertTrue(playerWithParam.addToMeld(2, 3).getFirst());
	}

	/**
	 * Unit test the Player's canAddToMeld() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCanAddToMeld()
	{
		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C] [JH JC JS JD J1 J2 J1]  [3D] [3H]  ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for non natual card--wildcard
		assertEquals("Card is  not a natural card",
				playerWithParam.canAddToMeld(new Card("2D")).getSecond());

		// testing for non natual card--black 3
		assertEquals("Card is  not a natural card",
				playerWithParam.canAddToMeld(new Card("3C")).getSecond());

		// testing for card that can not be melded
		assertEquals("Card can not be melded",
				playerWithParam.canAddToMeld(new Card("5D")).getSecond());

		// testing for card that can be melded
		assertEquals("",
				playerWithParam.canAddToMeld(new Card("JD")).getSecond());

	}

	/**
	 * Unit test the Player's makeNewMeld() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testMakeNewMeld()
	{
		// testing a valid meld call
		mPlayer.addCardToHand(new Card("XD"));
		mPlayer.addCardToHand(new Card("XS"));
		mPlayer.addCardToHand(new Card("XC"));
		mPlayer.addCardToHand(new Card("XH"));

		Vector<Integer> cardIdxs = new Vector<Integer>();

		cardIdxs.add(0);
		cardIdxs.add(1);
		cardIdxs.add(2);
		cardIdxs.add(3);

		mPlayer.makeNewMeld(cardIdxs);

		Vector<Vector<Card>> expectedVec = new Vector<Vector<Card>>();
		expectedVec.add(new Vector<Card>());
		expectedVec.add(new Vector<Card>());
		expectedVec.get(1).add(new Card("XS"));
		expectedVec.get(1).add(new Card("XH"));
		expectedVec.get(1).add(new Card("XD"));
		expectedVec.get(1).add(new Card("XC"));

		assertEquals(expectedVec, mPlayer.mPlayerHand.getHand());

		// testing for meld when there is only 2 elements

		mPlayer.addCardToHand(new Card("J1")); // 0
		mPlayer.addCardToHand(new Card("J2")); // 1
		mPlayer.addCardToHand(new Card("2H")); // 2
		mPlayer.addCardToHand(new Card("2S")); // 3
		mPlayer.addCardToHand(new Card("3S")); // 4
		mPlayer.addCardToHand(new Card("5H")); // 5
		mPlayer.addCardToHand(new Card("XC")); // 6
		mPlayer.addCardToHand(new Card("XD")); // 7
		mPlayer.addCardToHand(new Card("XS")); // 8

		cardIdxs.clear(); // cardIdxs->
		cardIdxs.add(6); // cardIdxs-> 6
		cardIdxs.add(7); // cardIdxs-> 6, 7
		assertEquals("Meld must have atleast three cards in it.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for when there black 3
		cardIdxs.add(4); // cardIdxs-> 6, 7, 4
		assertEquals("Meld can not have a Black 3.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for when there is repeat of indexs
		cardIdxs.remove(2); // cardIdxs-> 6, 7
		cardIdxs.add(7); // cardIdxs-> 6, 7, 7
		assertEquals("All the index of the passed card must be unique.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for more than 3 wild card
		cardIdxs.remove(2); // cardIdxs-> 6, 7
		cardIdxs.add(0); // cardIdxs-> 6, 7, 0
		cardIdxs.add(1); // cardIdxs-> 6, 7, 0, 1
		cardIdxs.add(2); // cardIdxs-> 6, 7, 0, 1, 2
		cardIdxs.add(3); // cardIdxs-> 6, 7, 0, 1, 2, 3
		assertEquals("Meld can only have at most three wildcards in it.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for atleast 2 natural cars
		cardIdxs.remove(2); // cardIdxs-> 6, 7, 1, 2, 3
		cardIdxs.remove(1); // cardIdxs-> 6, 1, 2, 3
		assertEquals("Meld should have atleat two natural card in it.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for 2 different rank of melds
		cardIdxs.add(5); // cardIdxs-> 6, 1, 2, 3, 5
		assertEquals(
				"All the ranks of natural card in the meld are not the same.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());

		// testing for already existing meld
		cardIdxs.remove(4); // cardIdxs-> 6, 1, 2, 3
		cardIdxs.add(7); // cardIdxs-> 6, 1, 2, 3, 7
		assertEquals("Meld of the same rank already exist.",
				mPlayer.makeNewMeld(cardIdxs).getSecond());
	}

	/**
	 * Unit test the Player's takeOutWildCard() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testTakeOutWildCard()
	{
		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC  J1] [5H 5C 5C J1]  [3D] [3H]  ");

		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// testing for non wildcard takeout
		assertEquals("Can not take out Red three card",
				playerWithParam.takeOutCardFromMeld(0, 1).getSecond());

		// testing for take out wild card
		assertEquals("", playerWithParam.takeOutCardFromMeld(6, 3).getSecond());

		// testing for disolving a meld
		assertEquals("Disolving the Meld of J",
				playerWithParam.takeOutCardFromMeld(2, 4).getSecond());

		// testing for take out natural card
		assertEquals("", playerWithParam.takeOutCardFromMeld(5, 3).getSecond());

		// taking out natural card
		assertEquals("", playerWithParam.takeOutCardFromMeld(2, 4).getSecond());

		// testing for disolving a meld when taking out natural card
		assertEquals("Disolving the Meld of 5",
				playerWithParam.takeOutCardFromMeld(1, 4).getSecond());
	}

	/**
	 * Unit test the Player's discard() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDiscard()
	{
		Vector<Card> expectedVec = new Vector<Card>();
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		mPlayer.addCardToHand(cardToAdd);
		assertEquals(expectedVec, mPlayer.getActualHand());

		// discarding the first element form the hand
		mPlayer.discard(0);
		expectedVec.clear();
		assertEquals(expectedVec, mPlayer.getActualHand());
	}

	/**
	 * Unit test the Player's pickUpDiscardPile() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testPickUpDiscardPile()
	{
		Vector<Card> discardVector = new Vector<Card>();
		discardVector.add(new Card("XS"));
		discardVector.add(new Card("XH"));
		discardVector.add(new Card("XD"));
		discardVector.add(new Card("XC"));
		discardVector.add(new Card("3C"));
		discardVector.add(new Card("3D"));
		discardVector.add(new Card("J1"));

		Vector<Vector<Card>> expectedVec = new Vector<Vector<Card>>();
		expectedVec.add(new Vector<Card>());
		expectedVec.get(0).add(new Card("J1"));
		expectedVec.get(0).add(new Card("3C"));
		expectedVec.get(0).add(new Card("XC"));
		expectedVec.get(0).add(new Card("XD"));
		expectedVec.get(0).add(new Card("XH"));
		expectedVec.get(0).add(new Card("XS"));
		expectedVec.add(new Vector<Card>());
		expectedVec.get(1).add(new Card("3D"));

		assertTrue(mPlayer.pickUpDiscardPile(discardVector));
		assertEquals(expectedVec, mPlayer.mPlayerHand.getHand());
	}

	/**
	 * Unit test the Player's canPickUpDiscardPile() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCanPickUpDiscardPile()
	{
		try
		{
			playerWithParam = new Player(100, " AS 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC  J1]  [3D] [3H] ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		Vector<Card> discardVector = new Vector<Card>();

		// when first card of the discard can be melded with 3 card of same rank
		discardVector.add(new Card("4S"));
		assertTrue(playerWithParam.canPickUpDiscardPile(discardVector));

		// when first card of the discard can be melded by using a wildcard
		discardVector.clear();
		discardVector.add(new Card("AS"));
		assertTrue(playerWithParam.canPickUpDiscardPile(discardVector));

		// when first card of discard pile cannot be melded
		discardVector.clear();
		discardVector.add(new Card("XS"));
		assertFalse(playerWithParam.canPickUpDiscardPile(discardVector));
	}

	/**
	 * Unit test the Player's canGoOut() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCanGoOut()
	{
		// false when actual hand is empty and has no canasta
		assertFalse(mPlayer.canGoOut());

		// false when actual hand is not empty and has no canasta
		mPlayer.addCardToHand(new Card("J1"));
		assertFalse(mPlayer.canGoOut());

		try
		{
			playerWithParam = new Player(100, " ",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC  J1]  [3D] [3H] ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		// true when actual hand is empty and has a canasta
		assertTrue(playerWithParam.canGoOut());

		// false when actual hand is not empty and has a canasta
		playerWithParam.addCardToHand(new Card("J1"));
		assertFalse(mPlayer.canGoOut());
	}

	/**
	 * Unit test the Player's emptyHand() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testEmptyHand()
	{
		Vector<Card> expectedVec = new Vector<Card>();
		Card cardToAdd = new Card("AC");
		expectedVec.add(cardToAdd);
		mPlayer.addCardToHand(cardToAdd);
		assertEquals(expectedVec, mPlayer.getActualHand());

		// clearing the hand
		mPlayer.emptyHand();
		expectedVec.clear();
		assertEquals(expectedVec, mPlayer.getActualHand());
	}

	/**
	 * Unit test the Player's resetPlayerForNewRound() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testResetPlayerForNewRound()
	{
		try
		{
			playerWithParam = new Player(100, " ",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC  J1]  [3D] [3H] ");
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		assertTrue(playerWithParam.resetPlayerForNewRound());

		assertEquals(
				new Vector<Vector<Card>>(Arrays.asList(new Vector<Card>())),
				playerWithParam.mPlayerHand.getHand());
	}

	/**
	 * Unit test the Player's validateCardIdx() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testValidateCardIdx()
	{
		assertFalse(mPlayer.validateCardIdx(0, 0));
		mPlayer.addCardToHand(new Card("XD"));
		assertTrue(mPlayer.validateCardIdx(0, 0));
		assertFalse(mPlayer.validateCardIdx(0, 1));

		// out of rang meldIdx
		assertFalse(mPlayer.validateCardIdx(1, 0));

		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC JH JC JH JC JH]  [3D] [3H]  ");

			// acutal Player
			assertTrue(playerWithParam.validateCardIdx(0, 0));

			// red 3s
			assertTrue(playerWithParam.validateCardIdx(1, 0));
			assertTrue(playerWithParam.validateCardIdx(2, 0));
			assertFalse(playerWithParam.validateCardIdx(2, 1));

			// noraml melds
			assertTrue(playerWithParam.validateCardIdx(3, 5));
			assertFalse(playerWithParam.validateCardIdx(3, 10));
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Player's validateCardIdx() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testValidateMeldIdx()
	{
		assertTrue(mPlayer.validateMeldIdx(0));
		assertFalse(mPlayer.validateMeldIdx(1));

		try
		{
			playerWithParam = new Player(100, " AS AD 4S   J1 J2 3S",
					"[4H 4C 4S 4D 4H 4C J1] [JH JC JH JC JH JC JH]  [3D] [3H]  ");

			assertTrue(playerWithParam.validateMeldIdx(0));
			assertTrue(playerWithParam.validateMeldIdx(3));
			assertTrue(playerWithParam.validateMeldIdx(4));
			assertFalse(playerWithParam.validateMeldIdx(10));
		}
		catch (ImproperMeldException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}
}
