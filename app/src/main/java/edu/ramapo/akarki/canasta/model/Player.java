package edu.ramapo.akarki.canasta.model;

import java.util.Arrays;
import java.util.Vector;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;
import edu.ramapo.akarki.canasta.model.Card.ENUM_CardType;

public class Player {
	// cards of the player
	protected Hand mPlayerHand;

	// holds the point of the player
	protected Integer mTotalPoints;

	// holds the state of to whether to show menu that
	// before the turn actually starts
	protected boolean mShowBeforeTurnMenu;

	// holds the state of current turn
	// is true if this is the start of the turn
	protected boolean mIsStartOfTurn;

	// holds the value of it the player went out
	protected boolean mWentOut;

	// holds if the wildCard Processing is in progress
	protected boolean mWildCardInProcess;

	// holds if the hand was empty in the previously
	protected boolean mHandEmptyPreviously;

	// pair error code
	protected final Pair<Integer, Vector<Integer>> mErrorPairCode = new Pair<Integer, Vector<Integer>>(
			10, new Vector<Integer>(Arrays.asList(10)));

	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Player()
	{
		mPlayerHand = new Hand();
		mTotalPoints = 0;
		mShowBeforeTurnMenu = true;
		mIsStartOfTurn = true;
		mWentOut = false;
		mWildCardInProcess = true;
		mHandEmptyPreviously = false;

	}

	/**
	 * construct a Player object and to populate its member variables
	 * mPlayerHand is populated using the passed parameter.
	 * 
	 * @param a_totalScore, a unsinged integer containing the player's total
	 *                          score.
	 * @param a_handCards,  a string containing the rank and suit of cards. Each
	 *                          card is seperated by blank space.
	 * @param a_meldCards,  a string containing the rank and suit of cards in
	 *                          the m_stock. Each card is seperated by blank
	 *                          space and each meld is inside '[' and ']'
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException
	 */
	public Player(Integer aTotalScore, String aHandCards, String aMeldCards)
			throws ImproperMeldException
	{
		mTotalPoints = aTotalScore;
		mShowBeforeTurnMenu = true;
		mIsStartOfTurn = true;
		mWentOut = false;
		mWildCardInProcess = true;
		mHandEmptyPreviously = false;
		mPlayerHand = new Hand(aHandCards, aMeldCards);
	}

	/**
	 * To create a new Player object and copy the passed Player object's member
	 * variables data into the newly created Player object
	 * 
	 * @param aOtherPlayer, a object of Player class passed. It holds a Player
	 *                          object to be copied.
	 * 
	 * @return none
	 */
	public Player(final Player aOtherPlayer)
	{
		mTotalPoints = aOtherPlayer.mTotalPoints;
		mShowBeforeTurnMenu = aOtherPlayer.mShowBeforeTurnMenu;
		mIsStartOfTurn = aOtherPlayer.mIsStartOfTurn;
		mWentOut = aOtherPlayer.mWentOut;
		mWildCardInProcess = aOtherPlayer.mWildCardInProcess;
		mHandEmptyPreviously = aOtherPlayer.mHandEmptyPreviously;

		try
		{
			mPlayerHand = new Hand(aOtherPlayer.getActualHandString(),
					aOtherPlayer.getMeldsString());
		}
		catch (ImproperMeldException e)
		{
			System.out.println("this is never gonna get an error");
		}
	}

	/**
	 * toString funciton
	 * 
	 * @param none
	 * 
	 * @return card in the hand in the printed format
	 */
	@Override
	public String toString()
	{
		StringBuilder playerString = new StringBuilder("Score: ");

		playerString.append(mTotalPoints);
		playerString.append("\n");
		playerString.append(mPlayerHand.toString());

		return playerString.toString();
	}

	/**
	 * gets the saved current point
	 * 
	 * @param none
	 * 
	 * @return total point of the player
	 */
	public int getTotalPoint()
	{
		return mTotalPoints;
	}

	/**
	 * Get players hand
	 * 
	 * @param none
	 * 
	 * @return Vector of vector of Cards object that holds player's hand
	 */
	public Vector<Vector<Card>> getHand()
	{
		return mPlayerHand.getHand();
	}

	/**
	 * Get players actual hand
	 * 
	 * @param none
	 * 
	 * @return Vector of Cards object that holds player's actual hand
	 */
	public Vector<Card> getActualHand()
	{
		return mPlayerHand.getActualHand();
	}

	/**
	 * Get players melds
	 * 
	 * @param none
	 * 
	 * @return Vector of Vector Cards object that holds player's melds
	 */
	public Vector<Vector<Card>> getMelds()
	{
		return mPlayerHand.getMelds();
	}

	/**
	 * Get players actual hand in string format
	 * 
	 * @param none
	 * 
	 * @return String that holds player's actual hand in printed format
	 */
	public String getActualHandString()
	{
		return mPlayerHand.getActualHandString();
	}

	/**
	 * Get players melds in string format
	 * 
	 * @param none
	 * 
	 * @return String that holds player's meld in printed format
	 */
	public String getMeldsString()
	{
		return mPlayerHand.getMeldsString();
	}

	/**
	 * Get players went_out status
	 * 
	 * @param none
	 * 
	 * @return players went_out status
	 */
	public boolean getPlayerWentOutStatus()
	{
		return mWentOut;
	}

	/**
	 * get players before_turn_flag status
	 * 
	 * @param none
	 * 
	 * @return players before_turn_flag status
	 */
	public boolean getTurnStartFlag()
	{
		return mIsStartOfTurn;
	}

	/**
	 * getter funciton to get the actual hand in divided format where each ranks
	 * are seperated into its own list
	 * 
	 * @param none
	 * 
	 * @return Vector of Vector of Card where each Vector of Cards have the same
	 *         ranks. All the ranks are in ascending order.
	 */
	public Vector<Vector<Card>> getDividedAcualHandCardList()
	{
		return mPlayerHand.getDividedAcualHandCardList();
	}

	/**
	 * set players went_out status
	 * 
	 * @param aPlayerWentOut players went_out status
	 * 
	 * @return true to indicate success
	 */
	public boolean setPlayerWentOutStatus(boolean aPlayerWentOut)
	{
		mWentOut = aPlayerWentOut;
		return true;
	}

	/**
	 * set players before_turn_flag status
	 * 
	 * @param aShowBeforeTurnMenu players before_turn_flag status
	 * 
	 * @return true to indicate success
	 */
	public boolean setPlayerBeforeTurnMenuFlag(boolean aShowBeforeTurnMenu)
	{
		mShowBeforeTurnMenu = aShowBeforeTurnMenu;
		return true;
	}

	/**
	 * set players turn_start_flag status
	 * 
	 * @param aShowTurnStartMenu players turn_start_flag status
	 * 
	 * @return true to indicate success
	 */
	public boolean setTurnStartFlag(boolean aShowTurnStartMenu)
	{
		mIsStartOfTurn = aShowTurnStartMenu;
		return true;
	}

	/**
	 * Contains the logic for start of the turn asks user for what to do
	 * 
	 * @param aOtherPlayerMeld vector of vector of cards that holds the melds
	 *                             that are in the other player's hand
	 * @param aDiscardPile     vector of cards that holds all the cards that are
	 *                             currently in discard pile
	 * 
	 * @return pair of < integer , Vector< Integer > >. The first interger
	 *         indicates where this was--that is beforeTurnStartLogic == 1,
	 *         turnStartLlogic == 2, or afterTurnStartLogic == 3 or 10 for
	 *         invalid input. the second is vector of unsinged integer whose
	 *         first element is always the sub menu index or 10 for invalid
	 *         input and every thing after that is what the it sub menus's
	 *         funciton needs
	 */
	public Pair<Integer, Vector<Integer>> playerTurnController(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{
		// before turn menu with function to save, take a turn
		if (mShowBeforeTurnMenu)
		{
			return beforeTurnStartControl();
		}
		else if (mIsStartOfTurn)
		{
			return turnStartControl(aDiscardPile);
		}

		return turnContinueControl(aOtherPlayerMeld, aDiscardPile);
	}

	/**
	 * ` Contains the controller for before a turn starts and asks the user to
	 * chose from "Save the game", "Take a turn" , or "Quit the game and go to
	 * main menu"
	 * 
	 * @param nones
	 * 
	 * @return Pair of < Integer ssss, Vector< Integer > >. The first interger
	 *         indicates this was--that is beforeTurnStartLogic so it is always
	 *         1. the second is vector of unsinged integer of size 1 and the the
	 *         vector's first element is the sub menu index.
	 */
	public Pair<Integer, Vector<Integer>> beforeTurnStartControl()
	{

		// now Player have the following choices:
		// 1) Save the game - > enter 1
		// 2) Take a turn - > enter 2
		// 3) Quit the game and go to main menu - > enter 3
		System.out.println("Menu:");
		System.out.println("\t1) Save the game - > enter 1");
		System.out.println("\t2) Take a turn - > enter 2");
		System.out
				.println("\t3) Quit the game and go to main menu - > enter 3");
		System.out.println();

		System.out.println("Enter a corresponding number: ");

		// used to get the userInput
		// object to read from console
		String userInputStr = UtitlityFunc.getUserInput();

		// validaing the userInput
		// a valid user input is 1, or 2, or 3
		int vadiatedNum = UtitlityFunc.validateNumber(userInputStr, 1, 3);
		if (vadiatedNum < 0)
		{
			Message.addMessage("Invalid Input!!");
			return mErrorPairCode;
		}

		return new Pair<Integer, Vector<Integer>>(1,
				new Vector<Integer>(Arrays.asList(vadiatedNum)));
	}

	/**
	 * Contains the controller for turn starts. this is executed when two is
	 * selected from beforeTurnStartControl. It asks user to chose from "draw a
	 * card from deck", "pick up the discard pile", "Ask for Help", "show
	 * discard pile", or "show stock card (for debugging)"
	 * 
	 * @param aDiscardPile, vector of cards that contains the discard pile
	 * 
	 * @return Pair of < Integer , Vector< Integer > >. The first interger
	 *         indicates this was--that is TurnStartconstrol so it is always 2.
	 *         the second is vector of unsinged integer of size 1 and the the
	 *         vector's first element is the sub menu index. Algorithm:
	 */
	public Pair<Integer, Vector<Integer>> turnStartControl(
			final Vector<Card> aDiscardPile)
	{
		// (2) Take a turn was pressed

		// at the start of the turn, player have 5 choices:
		// 1) ask for help - > enter 1
		// 2) draw a card from deck - > enter 2
		// 3) pick up the discard pile - > enter 3
		// 4) show discard pile - > enter 4
		// 5) show stock card (for debugging) - > enter 5

		System.out.println("Taking a turn:");
		System.out.println("\t1) Ask for help - > enter 1");
		System.out.println("\t2) Draw a card from deck - > enter 2");
		System.out.println("\t3) Pick up the discard pile - > enter 3");
		System.out.println("\t4) Show discard pile - > enter 4");
		System.out.println("\t5) Show stock card (for debugging) - > enter 5");
		System.out.println();

		System.out.println("Enter a corresponding number: ");

		// used to get the userInput
		// object to read from console
		String userInputStr = UtitlityFunc.getUserInput();

		// validaing the userInput
		// a valid user input is 1, or 2, or 3, or 4, or 5
		int vadiatedNum = UtitlityFunc.validateNumber(userInputStr, 1, 5);
		if (vadiatedNum < 0)
		{
			Message.addMessage("Invalid Input!!");
			return mErrorPairCode;
		}

		return new Pair<Integer, Vector<Integer>>(2,
				new Vector<Integer>(Arrays.asList(vadiatedNum)));
	}

	/**
	 * Contains the controller for turn to continue.aftere the player has either
	 * drawn a card or picked up the discard pile. It asks user to chose from
	 * "draw a card from deck", "pick up the discard pile", "Ask for Help",
	 * "show discard pile", "show stock card (for debugging)",
	 * 
	 * @param aDiscardPile, vector of cards that contains the discard pile
	 * 
	 * @return Pair of < Integer , Vector< Integer > >. The first interger
	 *         indicates this was--that is TurnStartconstrol so it is always 3.
	 *         the second is vector of unsinged integer the the vector's first
	 *         element is the sub menu index. rest of the element depends on the
	 *         submenu chosen
	 */
	public Pair<Integer, Vector<Integer>> turnContinueControl(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{

		// player has drawns a card or picked up the discard pile

		// now player have 8 choices until they discard or go out:
		// else it is not the start of the round so they have 5 choices
		// 1) Ask for help - > enter 1
		// 2) Add a card in hand to meld - > enter 2 < actualHandCardIdx >
		// < meldIdx >
		// 3) Discard a card from hand - > enter 3 < actualHandCardIdx >
		// 4) Go out - > enter 4
		// 5) Make a new meld - > enter 5 < actualHandCardIdx > <
		// actualHandCardIdx >
		// < actualHandCardIdx > ...
		// 6) Show discard pile - > enter 6
		// 7) Show stock card (for debugging) - > enter 7
		// 8) Take out wild card From meld - > enter 8 < meldCardIdx > < meldIdx
		// >

		System.out.println("Taking a turn:");
		System.out.println("\t1) Ask for help - > enter 1");
		System.out.println(
				"\t2) Add a card in hand to meld - > enter 2 <  actualHandCardIdx  > <  meldIdx >");
		System.out.println(
				"\t3) Discard a card from hand - > enter 3 <  actualHandCardIdx  >");
		System.out.println("\t4) Go out - > enter 4");
		System.out.println(
				"\t5) Make a new meld - > enter 5 <  actualHandCardIdx  > < actualHandCardIdx > < actualHandCardIdx > ...");
		System.out.println("\t6) Show discard pile - > enter 6");
		System.out.println("\t7) Show stock card( for debugging ) - > enter 7");
		System.out.println(
				"\t8) Take out wild card From meld - > enter 8 <  meldCardIdx  > < meldIdx >");
		System.out.println();

		System.out.println("Enter a corresponding number: ");

		// used to get the userInput
		// object to read from console

		String userInputStr = UtitlityFunc.getUserInput();

		// extracting user input
		String[] extractedUserInputStr = userInputStr.split("\\s+");

		// validaing the userInput
		// 1) a first valid user input is 1, or 2, or 3, or 4,
		// or 5, or 6, or 7, or 8
		// 2) then if it is 1, or 4, or 6, or 7 there should
		// not be any other number after it
		// 3) if it is 3 then it should have one number after
		// it that represents the card in actual hand
		// 4) if it is 2 then it should have 2 number after
		// it, these 2 number represents the card idx
		// in actual hand and meld idx in the hand
		// 5) if it is 5 then it should have atlease 3 number
		// after it, these number represents the
		// card idx in actual hands
		// 6) if it is 8 then it should have 2 number after
		// it, these 2 number represents the card idx
		// in meld and the and meld idx in the hand
		// holds the parsed user input if they are valid
		Vector<Integer> validUserInputVec = new Vector<Integer>(
				extractedUserInputStr.length);

		// validating 1) - > a first valid user input
		// is 1, or 2, or 3, or 4, or 5, or 6, or 7, or 8
		int vadiatedNum = UtitlityFunc.validateNumber(extractedUserInputStr[0],
				1, 8);

		if (vadiatedNum < 0)
		{
			Message.addMessage("Invalid Input!!");
			return mErrorPairCode;
		}

		// valid initial number
		validUserInputVec.add(vadiatedNum);

		boolean errorFound = false;

		// validating 2) - > then if it is 1, or 4, or 6, or 7
		// there should not be any other number after it
		if ("1467".contains(extractedUserInputStr[0])
				&& extractedUserInputStr.length == 1)
		{
			// valid input
			return new Pair<Integer, Vector<Integer>>(3, validUserInputVec);
		}
		// validating 3) - > if it is 3 then it should have one number after
		// it that represents the card in actual hand
		else if (validUserInputVec.firstElement().equals(3)
				&& extractedUserInputStr.length == 2)
		{
			// the max idx that it can be is the actual hand size - 1
			vadiatedNum = UtitlityFunc.validateNumber(
					extractedUserInputStr[extractedUserInputStr.length - 1], 0,
					mPlayerHand.getActualHand().size() - 1);

			if (vadiatedNum < 0)
			{
				Message.addMessage("Invalid actual hand index!!");
				errorFound = true;
			}
			else
			{
				validUserInputVec.add(vadiatedNum);
			}
		}

		// validating 4) - > if it is 2 then it should have 2 number after
		// it, these 2 number represents the card idx
		// in actual hand and meld idx in the hand
		else if (validUserInputVec.firstElement().equals(2)
				&& extractedUserInputStr.length == 3)
		{
			// validating the actualCardIdx which is the second number
			// the max idx that it can be is the actual hand size - 1
			vadiatedNum = UtitlityFunc.validateNumber(extractedUserInputStr[1],
					0, mPlayerHand.getTotalHandCardNum() - 1);

			// validating the meldIdx which is the last number
			// the max idx that it can be is the hand size - 1
			int vadiatedMeldNum = UtitlityFunc.validateNumber(
					extractedUserInputStr[extractedUserInputStr.length - 1], 0,
					mPlayerHand.getTotalMeldNum());

			if (vadiatedNum < 0)
			{
				Message.addMessage("Invalid actual hand index!!");
				errorFound = true;
			}
			else if (vadiatedMeldNum < 0)
			{
				Message.addMessage("Invalid meld index!!");
				errorFound = true;
			}
			else
			{
				validUserInputVec.add(vadiatedNum);
				validUserInputVec.add(vadiatedMeldNum);
			}
		}

		// validating 5) - > if it is 5 then it should have atlease 3 number
		// after it, these number represents the card idx in actual hands
		else if (validUserInputVec.firstElement().equals(5)
				&& extractedUserInputStr.length >= 4)
		{
			// validating all the passed the actualCardIndes

			for (Integer strVecidx = 1; strVecidx < extractedUserInputStr.length; ++strVecidx)
			{
				// the max idx that it can be is the actual hand size - 1
				vadiatedNum = UtitlityFunc.validateNumber(
						extractedUserInputStr[strVecidx], 0,
						mPlayerHand.getTotalHandCardNum() - 1);

				if (vadiatedNum < 0)
				{
					Message.addMessage("Invalid actual hand index!!");
					errorFound = true;
				}
				validUserInputVec.add(vadiatedNum);
			}
		}

		// validating 6) - > if it is 2 then it should have 2 number after
		// it, these 2 number represents the card idx
		// in meld and meld idx in the hand
		else if (validUserInputVec.firstElement().equals(8)
				&& extractedUserInputStr.length == 3)
		{

			// validating the meldIdx which is the last number
			// the max idx that it can be is the hand size - 1
			Integer validatedMeldIdx = UtitlityFunc.validateNumber(
					extractedUserInputStr[extractedUserInputStr.length - 1], 0,
					mPlayerHand.getTotalMeldNum());

			if (validatedMeldIdx < 0)
			{
				Message.addMessage("Invalid meld index!!");
				errorFound = true;
			}
			else
			{
				// validating the actualCardIdx which is the second number
				// the max idx that it can be is the actual hand size - 1
				vadiatedNum = UtitlityFunc.validateNumber(
						extractedUserInputStr[1], 0,
						mPlayerHand.getHand().elementAt(validatedMeldIdx).size()
								- 1);

				if (vadiatedNum < 0)
				{
					Message.addMessage("Invalid card index in the Meld!!");
					errorFound = true;
				}
				else
				{
					// adding the numbes in the same order
					validUserInputVec.add(vadiatedNum);
					validUserInputVec.add(validatedMeldIdx);
				}
			}
		}
		else
		{
			Message.addMessage(
					"Invalid number of input argument for the chosen menu!!");
			return mErrorPairCode;
		}

		if (errorFound)
		{
			return mErrorPairCode;
		}

		// valid input
		return new Pair<Integer, Vector<Integer>>(3, validUserInputVec);
	}

	/**
	 * adds the passed points to the total points
	 * 
	 * @param aPointsToAdd, integer. holds the points to add to the total
	 * 
	 * @return true to indicate success
	 */
	public boolean addToTotalPoints(int aPointsToAdd)
	{
		mTotalPoints += aPointsToAdd;
		return true;
	}

	/**
	 * tallys the hand Point
	 * 
	 * @param none
	 * 
	 * @return tallied hand Point
	 */
	public int tallyHandPoint()
	{
		return mPlayerHand.tallyPoints(mWentOut);
	}

	/**
	 * adds the drawn card to the deck
	 * 
	 * @param aDrawnCard, object of Card Class. card to add to hand class
	 * 
	 * @return false if the card to add was red three else true
	 */
	public boolean addCardToHand(final Card aDrawnCard)
	{
		return mPlayerHand.addCardToHand(aDrawnCard);
	}

	/**
	 * Tries to add the card at passed index to a already existing meld.
	 * 
	 * @param aHandCardIdx, a integer. It holds the index of the the card at
	 *                          actual hand that need to be moved
	 * @param aMeldIdx,     a integer. It holds the index of the meld to move
	 *                          the card to actual hand that need to be moved
	 * 
	 * @return a pair of < Boolean, String >, < true, "" > if the card was moved
	 *         to a meld successfully. else < false, "message string" >
	 */
	public Pair<Boolean, String> addToMeld(Integer aHandCardIdx,
			Integer aMeldIdx)
	{
		switch (mPlayerHand.getCardAtIdx(0, aHandCardIdx).getCardType())
		{
		case CARDTYPE_NATURAL:
		{
			return mPlayerHand.addNaturalCardToMeld(aHandCardIdx, aMeldIdx);
		}
		case CARDTYPE_WILDCARD:
		{
			return mPlayerHand.addWildCardToMeld(aHandCardIdx, aMeldIdx);
		}
		case CARDTYPE_BLACK_THREE:
		{
			return new Pair<Boolean, String>(false, "Cannot meld a Black 3");
		}
		default:
		{
			// technically this should never execute
			return new Pair<Boolean, String>(false, "Card Type not found.");
		}
		}
	}

	/**
	 * Tries to see if the passed card can be added to the meld or not
	 * 
	 * @param aCardToAddd, object of Card class. It holds a Card object that is
	 *                         used to check if it can be added or not
	 * 
	 * @return a pair of < Integer, string >, < meldIdx, "" > if the card can be
	 *         added to a meld then the first of the pair is the index of meld
	 *         where it can be added. else < -1, "message string" >
	 */
	public Pair<Integer, String> canAddToMeld(final Card aCardToAdd)
	{
		return mPlayerHand.canAddToMeld(aCardToAdd);
	}

	/**
	 * To make a vaild meld
	 * 
	 * @param aHandCardIdxList, a vector of integer. It holds the indexs of the
	 *                              card that are to be used to create a meld.
	 * 
	 * @return a pair of < Boolean, String >. < true, "" > if a meld was
	 *         successfully made. else < false, message string >
	 */
	public Pair<Boolean, String> makeNewMeld(Vector<Integer> aHandCardIdxList)
	{
		return mPlayerHand.makeMeld(aHandCardIdxList);
	}

	/**
	 * takes out wild card
	 * 
	 * @param aMeldcardIdx, a integer. It holds the index of the card in meld
	 *                          that is to be taken out
	 * @param aMeldIdx,     a integer. It holds the index of the meld from where
	 *                          the card is to be taken out from
	 * 
	 * @return a pair of < Boolean, String >, < true, "" > if wild card was
	 *         taken out successfully . else < false, "message string" >
	 */
	public Pair<Boolean, String> takeOutCardFromMeld(Integer aMeldcardIdx,
			Integer aMeldIdx)
	{
		return mPlayerHand.takeOutCardFromMeld(aMeldcardIdx, aMeldIdx);
	}

	/**
	 * Removes the card at a_handCardIdx and returns it. the index should be
	 * validated before hand
	 * 
	 * @param aHandCardIdx, a integer. It holds the index of the the card at
	 *                          actual hand that need to be removed
	 * 
	 * @return object of Card, holds the card that was discarded
	 */
	public Card discard(Integer aHandCardIdx)
	{
		return mPlayerHand.discard(aHandCardIdx);
	}

	/**
	 * Adds all the card in to the actual hand of the player
	 * 
	 * @param aDiscardPile, a vector of card passed by value. It hold all the
	 *                          cards discarded by both player that needs to be
	 *                          added to this player's actual hand
	 * 
	 * @return true to indicate that discard pile was successfully added to
	 *         player's actual hand
	 */
	public boolean pickUpDiscardPile(final Vector<Card> aDiscardPile)
	{
		for (int pileIdx = 0; pileIdx < aDiscardPile.size(); ++pileIdx)
		{
			mPlayerHand.addCardToHand(aDiscardPile.elementAt(pileIdx));
		}

		return true;
	}

	/**
	 * Checks to see if we can pick up the discarded pile
	 * 
	 * @param aDiscardPile a vector of cards that contains the discard pile
	 * 
	 * @return boolean value, returns true if we can pick up the discard pile
	 *         else returns false.
	 */
	public boolean canPickUpDiscardPile(final Vector<Card> aDiscardPile)
	{
		// see if we can meld the top of the discard pile
		if (canAddToMeld(aDiscardPile.firstElement()).getFirst() == -1)
		{
			// creating a temp computer to check if the top of the card can be
			// melded or not
			// and doing as much meld as we can
			Player tempComputer = new Player(this);

			tempComputer.pickUpDiscardPile(new Vector<Card>(
					Arrays.asList(new Card(aDiscardPile.firstElement()))));

			// TODO (REFACTOR) make this into its own funciton as this is also
			// used by the computer

			// diving out cards at hand according to its rank
			Vector<Vector<Card>> sameRankHandCardList = tempComputer
					.getDividedAcualHandCardList();

			// checking if we have a wildcard
			Boolean hasWildCard = mPlayerHand.actualHandHasWildCard();

			// checking if the there is any rank that has 3 cards in them or has
			// 2 card and we have a wild card at hand
			for (Vector<Card> rankVector : sameRankHandCardList)
			{
				if (rankVector.size() >= 3
						|| (rankVector.size() == 2 && hasWildCard))
				{
					// only procede if we are dealing with natural card
					if (rankVector.firstElement()
							.getCardType() != ENUM_CardType.CARDTYPE_NATURAL)
					{
						continue;
					}

					// checking if the meld is going to be made out of the
					// discard pile's top
					if (rankVector.firstElement().getRank()
							.equals(aDiscardPile.firstElement().getRank()))
					{
						return true;
					}
				}
			}

			// cannot be added to meld even when wildcards is present
			return false;
		}

		// can be added to meld
		return true;
	}

	/**
	 * Verifies that the passed meld index is valid or not
	 * 
	 * @param aMeldCardIdx, a integer. It holds the index of the card that is to
	 *                          be validated
	 * @param aMeldIdx,     a integer. It holds the index of the meld that the
	 *                          card needes to be validated for
	 * 
	 * @return true if the passed aCardIdx is a valid index of the passed meld
	 */
	public boolean canGoOut()
	{
		return mPlayerHand.isActualHandEmpty() && mPlayerHand.getHasCanasta();
	}

	/**
	 * Empties the cards in the hand
	 * 
	 * @param none
	 * 
	 * @return true to indicate success
	 */
	public boolean emptyHand()
	{
		mPlayerHand = new Hand();
		return true;
	}

	/**
	 * Resets playe's hand and flag to prepare for new round
	 * 
	 * @param none
	 * 
	 * @return ool to indicate a successfull reset
	 */
	public boolean resetPlayerForNewRound()
	{
		mIsStartOfTurn = true;
		mShowBeforeTurnMenu = true;
		mWentOut = false;

		// return the result of of the emptyHand
		return emptyHand();
	}

	// returns true if the passed aCardIdx is a valid index of the passed meld
	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return card in the hand in the printed format
	 */
	public boolean validateCardIdx(Integer aMeldIdx, Integer aCardIdx)
	{
		return mPlayerHand.validateCardIdx(aMeldIdx, aCardIdx);
	}

	/**
	 * Verifies that the passed meld index is valid or not
	 * 
	 * @param aMeldIdx, a integer. It holds the index of the meld that is to be
	 *                      validated
	 * 
	 * @return true if the passed meld idx is a valid meld index, else false
	 */
	public boolean validateMeldIdx(Integer aMeldIdx)
	{
		return mPlayerHand.validateMeldIdx(aMeldIdx);
	}
}
