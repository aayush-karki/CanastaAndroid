package edu.ramapo.akarki.canasta.model;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import edu.ramapo.akarki.canasta.exceptions.EmptyStockException;
import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;
import edu.ramapo.akarki.canasta.model.Card.ENUM_CardType;

public class Round {
	// TODO test this class

	// list whose turn it could be
	public enum ENUM_PlayerTurn
	{
		// computer turn
		TURN_COMPUTER,

		// human's turn
		TURN_HUMAN,

		// default value
		TURN_UNINITAIZLIZED
	}

	// holds the current round number
	private Integer mCurrRoundNum;

	// indicates if the round is over or not by player going out
	private boolean mPlayerWentOut;

	// indicates if it is currently start of the round or not
	private boolean mRoundStart;

	// indicates if the player drew a red 3 which is also the
	// last card in stack
	private boolean mLastCardR3Drwan;

	// indicates if the both the player can not use the discard pile
	// to make a meld when the stock is empty
	private boolean mEndCausedCusDiscardPile;

	// mPlayerTurn holds whose turn it could be
	private ENUM_PlayerTurn mPlayerTurn;

	// mPlayerListholds the list of player playing the game,
	// contains 2 player object both of them are object of
	// classes that are derived form player class.
	private Vector<Player> mPlayerList;

	// mDeck holds deck object used for the game, contains
	// 2 decks in it with each containing 52 standard card
	// plus 2 jokers for a total of 108 cards.
	private Deck mDeck;

	// mDiscardPile holds cards that are discarded by players,
	// additional player can pick up the whole discarded pile if
	// the last added to the pile help them in making a meld.
	private Vector<Card> mDiscardPile;

	// holds the rnadom
	private final Random mRandom = new Random();

	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Round()
	{
		mCurrRoundNum = 1;
		mPlayerWentOut = false;
		mPlayerTurn = ENUM_PlayerTurn.TURN_UNINITAIZLIZED;
		mDeck = new Deck();
		mLastCardR3Drwan = false;
		mEndCausedCusDiscardPile = false;

		// initializing 2 players
		mPlayerList = new Vector<Player>(2);
		mPlayerList.add(new Computer());
		// mPlayerList.add(new Human());
		// TODO delete me
		mPlayerList.add(new Computer());

		// initialize the discard Pile
		mDiscardPile = new Vector<Card>(108);
	}

	/**
	 * constructor when paramaeters are passed
	 * 
	 * @param aCurrRoundNum,    Integer integer representing the current round
	 *                              number.
	 * @param aPlayerTurn,      ENUM_PlayerTurn represting whose turn it is
	 *                              right now.
	 * @param aCompTotalScore,  a unsinged integer containing the comp's total
	 *                              score.
	 * @param aCompActualHand,  a string containing the rank and suit of cards
	 *                              which represents comp's actual hand card.
	 *                              Each card is seperated by blank space.
	 * @param aCompMelds,       a string containing the rank and suit of cards
	 *                              which represents comp's meld. Each card is
	 *                              seperated by blank space and each meld is
	 *                              inside '[' and ']'
	 * @param aHumanTotalScore, a unsinged integer containing the human's total
	 *                              score.
	 * @param aHumanActualHand, a string containing the rank and suit of cards
	 *                              which represents human's actual hand card.
	 *                              Each card is seperated by blank space.
	 * @param aHumanMelds,      a string containing the rank and suit of cards
	 *                              which represents human's meld. Each card is
	 *                              seperated by blank space and each meld is
	 *                              inside '[' and ']'
	 * @param aStockCards,      a string containing the rank and suit of cards
	 *                              which represents stock in the deck. Each
	 *                              card is seperated by blank space. And the
	 *                              first rankSuit pair represent the card that
	 *                              is going to be dealt next.
	 * @param aDiscardPile,     a string containing the rank and suit of cards
	 *                              which represents discarded pile. Each card
	 *                              is seperated by blank space. And the first
	 *                              rankSuit pair represent the most recently
	 *                              discarded card
	 * 
	 * @return none
	 * 
	 * @throws ImproperMeldException
	 * @throws IllegalArgumentException
	 */
	public Round(Integer aCurrRoundNum, ENUM_PlayerTurn aPlayerTurn,
			Integer aCompTotalScore, String aCompActualHand, String aCompMelds,
			Integer aHumanTotalScore, String aHumanActualHand,
			String aHumanMelds, String aStockCards, String aDiscardPile)
			throws ImproperMeldException, IllegalArgumentException
	{

		mCurrRoundNum = aCurrRoundNum;
		mPlayerWentOut = false;
		mPlayerTurn = aPlayerTurn;
		mDeck = new Deck(aStockCards);
		mLastCardR3Drwan = false;
		mEndCausedCusDiscardPile = false;

		// initializing 2 players
		mPlayerList = new Vector<Player>(2);

		mPlayerList.add(
				new Computer(aCompTotalScore, aCompActualHand, aCompMelds));
		// mPlayerList.add(
		// new Human(aHumanTotalScore, aHumanActualHand, aHumanMelds));

		// TODO delete me
		mPlayerList.add(
				new Computer(aHumanTotalScore, aHumanActualHand, aHumanMelds));

		// initialize the discard Pile
		mDiscardPile = new Vector<Card>(108);

		for (String rankSuit : aDiscardPile.split((" +")))
		{
			if (rankSuit.trim().isEmpty())
			{
				continue;
			}

			mDiscardPile.add(new Card(rankSuit));
		}

		// TODO auto meld the hand for both player make this into a function
		/*
		 * THis funciton would follow the thing untill the computer decides to
		 * discard. this should also be used every where the test computer is
		 * used like in discard/draw choice funciton where the test comoputer
		 * picksup discard pile then it shuld do its thing as far as possible
		 */
		/*
		 * TODO (BUG): Currently wild cards are left in the hand. Possible
		 * solution 1: When the round finishes initializing, loop over the hand
		 * and add any wild cards in the hand to a meld. Possible solution 2:
		 * When the round finishes initializing, use the computer’s AI to do as
		 * much as possible until discard is left.
		 */
		/*
		 * When you do this also update the tally point test
		 */
	}

	/**
	 * copy constructor
	 * 
	 * @param aOther, a constant object of Round class passed by reference. It
	 *                    holds the Round object to be copied.
	 * 
	 * @return none
	 */
	public Round(final Round aOther)
	{
		mCurrRoundNum = aOther.mCurrRoundNum;
		mPlayerWentOut = aOther.mPlayerWentOut;
		mPlayerTurn = aOther.mPlayerTurn;
		mDeck = new Deck(aOther.mDeck);
		mLastCardR3Drwan = aOther.mLastCardR3Drwan;
		mEndCausedCusDiscardPile = aOther.mEndCausedCusDiscardPile;

		// copying 2 players
		mPlayerList = new Vector<Player>(2);
		mPlayerList.add(new Computer((Computer) aOther.getComputerPlayer()));
		mPlayerList.add(new Human((Human) aOther.getHumanPlayer()));

		// copying the discard Pile
		mDiscardPile.setSize(aOther.mDiscardPile.size());
		Collections.copy(mDiscardPile, aOther.mDiscardPile);
	}

	/**
	 * getter function to get the current round number
	 * 
	 * @param none
	 * 
	 * @return Integer, holds the current round number
	 */
	public Integer getCurrRoundNum()
	{
		return mCurrRoundNum;
	}

	/**
	 * getter function to get the discard pile
	 * 
	 * @param none
	 * 
	 * @return vectors of card, holds all the card that are in discard pile
	 */
	public Vector<Card> getDiscardPile()
	{
		return mDiscardPile;
	}

	/**
	 * gets the rankSuit of the top card in the discard pile
	 * 
	 * @param none
	 * 
	 * @return String, holds the rankSuit of the the top card in the discard
	 *         pile
	 */
	public String getDiscardPileTopRankSuit()
	{
		return mDiscardPile.firstElement().getRankSuit();
	}

	/**
	 * gets the top card in the discard pile
	 * 
	 * @param none
	 * 
	 * @return Card, holds the card that is in the the top card in the discard
	 *         pile
	 */
	public Card getDiscardPileTopCard()
	{
		return mDiscardPile.firstElement();
	}

	/**
	 * get discard pile in string format
	 * 
	 * @param none
	 * 
	 * @return string, holds all the cards in the discard pile in the string
	 *         format
	 */
	public String getDiscardedPile()
	{
		return Card.getAllCardInPrintedFormat(mDiscardPile);
	}

	/**
	 * get stock in string format
	 * 
	 * @param none
	 * 
	 * @return string, holds all the cards in the stock in the string format
	 */
	public String getStockString()
	{
		return mDeck.getStockString();
	}

	/**
	 * gets the current player
	 * 
	 * @param none
	 * 
	 * @return Player, holds the current active player
	 */
	public Player getCurrPlayer()
	{
		return mPlayerList.get(mPlayerTurn.ordinal());
	}

	/**
	 * gets not the current player
	 * 
	 * @param none
	 * 
	 * @return Player, holds the current inactive player
	 */
	public Player getOtherPlayer()
	{
		return mPlayerTurn == ENUM_PlayerTurn.TURN_COMPUTER
				? mPlayerList.lastElement()
				: mPlayerList.firstElement();
	}

	/**
	 * gets the computer player
	 * 
	 * @param none
	 * 
	 * @return Player, holds the computer player
	 */
	public Player getComputerPlayer()
	{
		return mPlayerList.firstElement();
	}

	/**
	 * gets the human player
	 * 
	 * @param none
	 * 
	 * @return Player, holds the human player
	 */
	public Player getHumanPlayer()
	{
		return mPlayerList.lastElement();
	}

	/**
	 * get the player turn
	 * 
	 * @param none
	 * 
	 * @return ENUM_PlayerTurn, enum with whose turn it is
	 */
	public ENUM_PlayerTurn getPlayerTurn()
	{
		return mPlayerTurn;
	}

	/**
	 * Checks if the round is over
	 * 
	 * @param none
	 * 
	 * @return true if the round is over else false
	 */
	public boolean isRoundOver()
	{
		return mPlayerList.firstElement().canGoOut()
				|| mPlayerList.lastElement().canGoOut();
	}

	/**
	 * increase the round number by one
	 * 
	 * @param none
	 * 
	 * @return true to signify the round number was increased
	 */
	public boolean incrementRoundNumber()
	{
		++mCurrRoundNum;
		return true;
	}

	/**
	 * Sets up a new round
	 * 
	 * @param none
	 * 
	 * @return true if a new round was setup
	 */
	public boolean startNewRound()
	{
		// resetting the round
		// empty discarded pile
		emptyDiscardPile();

		// resetting player's hand
		for (Player currPlayer : mPlayerList)
		{
			currPlayer.resetPlayerForNewRound();
		}

		// consolodating deck
		mDeck.consodilateDeck();

		// suffeling the deck
		mDeck.shuffel();

		// dealing 15 cards to each player
		for (Player currPlayer : mPlayerList)
		{
			for (Integer cardInHandNum = 0; cardInHandNum < 15; ++cardInHandNum)
			{
				// deal a card untill player gets a non red three card
				try
				{
					while (!currPlayer.addCardToHand(mDeck.dealCard()))
					{
					}
				}
				catch (EmptyStockException e)
				{
					e.printStackTrace();
				}
			}
		}

		// next dealt card is added to the discard pile to start the pile
		// keep adding a card to the pile until a natural or a black three is
		// found
		boolean wildOrRedThree = true;
		while (wildOrRedThree)
		{
			Card dealtCard;

			try
			{
				dealtCard = mDeck.dealCard();

				if (dealtCard.getCardType() == ENUM_CardType.CARDTYPE_NATURAL
						|| dealtCard
								.getCardType() == ENUM_CardType.CARDTYPE_BLACK_THREE)
				{
					wildOrRedThree = false;
				}

				// adding to discard pile
				mDiscardPile.add(dealtCard);
			}
			catch (EmptyStockException e)
			{
				e.printStackTrace();
			}
		}

		mPlayerTurn = ENUM_PlayerTurn.TURN_UNINITAIZLIZED;

		// round setup is complete

		mRoundStart = false;

		return true;
	}

	/**
	 * continues the round
	 * 
	 * @param none
	 * 
	 * @return pair of boolean value, boolean value. if first of the pair is
	 *         true exit to main menu was pressed else it was not. if second of
	 *         the pair is true save the game was pressed else it was not
	 */
	public Pair<Boolean, Boolean> continueRound()
	{
		// determining who goes first
		if (mPlayerTurn.equals(ENUM_PlayerTurn.TURN_UNINITAIZLIZED))
		{
			// TODO (refactor): the while loop can be removed form the determine
			// palyer order and make it return false in case of invalid input
			determinePlayerOrder();
		}

		printRound();

		// printing any messages
		for (String currMessage : Message.getMessages())
		{
			System.out.println(currMessage);
		}

		// clearing the messages
		Message.clearLatestMessages();

		if (mPlayerTurn == ENUM_PlayerTurn.TURN_COMPUTER && !mRoundStart)
		{
			System.out.print(
					"It is computer turn entry anything to procced a step: ");
			UtitlityFunc.getUserInput();
		}

		// check if the round is over
		//
		// round can end in three way
		// if one of the payers goes out . indicated by mPlayerWentOut
		// if last card drawn form the stock pile is read 3
		// if stock is empty and neight Player can use the top card of
		// discard pile in a meld
		if ((mPlayerWentOut || mLastCardR3Drwan || mEndCausedCusDiscardPile
				|| (mDeck.getStock().isEmpty()
						&& getCurrPlayer().getTurnStartFlag()))
				&& !mRoundStart)
		{
			// checking if the discard pile can be picked up if the stock is
			// empty
			if (!mPlayerWentOut && !mLastCardR3Drwan
					&& mDeck.getStock().isEmpty())
			{
				boolean pickUpSuccess = false;
				if (getCurrPlayer().canPickUpDiscardPile(mDiscardPile))
				{
					pickUpSuccess = true;
				}
				else if (!pickUpSuccess
						&& getOtherPlayer().canPickUpDiscardPile(mDiscardPile))
				{

					// setting curr player to other
					mPlayerTurn = (mPlayerTurn == ENUM_PlayerTurn.TURN_COMPUTER)
							? ENUM_PlayerTurn.TURN_HUMAN
							: ENUM_PlayerTurn.TURN_COMPUTER;
					pickUpSuccess = true;
				}

				if (pickUpSuccess)
				{
					// forcing the current player to pick up the discard pile
					turnStartLogic(2);

					StringBuilder message = new StringBuilder(
							" Stock is empty!\n");
					message.append(
							mPlayerTurn.equals(ENUM_PlayerTurn.TURN_COMPUTER)
									? "Computer"
									: "Human");
					message.append(
							" can pick up the discarded pile. So picking up the discard pile.");
					Message.addMessage(message.toString());

					return new Pair<Boolean, Boolean>(false, false);
				}

				StringBuilder message = new StringBuilder("Stock is empty!\n");
				message.append(
						"Neither Human or Computer can pick up the discarded pile. So Ending the game.");
				Message.addMessage(message.toString());
				Message.addMessage("");
			}

			// chekcing if the player wentout or not
			for (Player player : mPlayerList)
			{
				String playerName = player.getClass().getSimpleName();

				if (player.getPlayerWentOutStatus())
				{
					Message.addMessage(playerName
							+ " has gone out, and finished the round!");
					Message.addMessage("");
				}

				// calculating the round and total points
				Integer playersEarnedPoints = player.tallyHandPoint();
				player.addToTotalPoints(playersEarnedPoints);

				// printing the round points and total point that players earned
				// in this round
				Message.addMessage(playerName + "'s earned "
						+ playersEarnedPoints.toString() + " this round.");
				Message.addMessage(playerName + "'s total earned points is "
						+ player.getTotalPoint());
			}

			mRoundStart = true;

			// returning lastElement that exit menu was not pressed and save was
			// not pressed
			return new Pair<Boolean, Boolean>(false, false);
		}

		// this block of code is executed only when the the round is over
		// and prompting the player if they would like to continue or not
		//
		if (mRoundStart)
		{
			// asking the player if they would like to play another round or not
			System.out.print("Would you like to play another round? (y/n) : ");

			// used to get the userInput
			String userInputStr = UtitlityFunc.getUserInput();

			// validaing the userInput
			// a valid user input is 'y' or 'n'

			if (userInputStr.length() != 1
					|| !"yn".contains(userInputStr.toLowerCase()))
			{
				// invalid input
				Message.addMessage("Invalid Input!!");

				return new Pair<Boolean, Boolean>(false, false);
			}

			// input was valid

			if ("n".equalsIgnoreCase(userInputStr))
			{
				// no was pressed
				// quiting and printing winner
				Message.addMessage("Player chose not to continue playing!");
				Message.addMessage("Computer's total earned points is "
						+ mPlayerList.firstElement().getTotalPoint());
				Message.addMessage("Human's total earned points is "
						+ mPlayerList.lastElement().getTotalPoint());

				Message.addMessage("");
				if (mPlayerList.firstElement().getTotalPoint() > mPlayerList
						.lastElement().getTotalPoint())
				{
					Message.addMessage("It was a tie!");
				}
				else
				{
					StringBuilder winner = new StringBuilder(
							"Winner of the game is ");
					winner.append((mPlayerList.firstElement()
							.getTotalPoint() > mPlayerList.lastElement()
									.getTotalPoint()) ? "Comuputer" : "Human");

					Message.addMessage(winner.toString());
				}

				return new Pair<Boolean, Boolean>(true, false);
			}

			// yes was pressed

			// starting new round
			startNewRound();
			++mCurrRoundNum;

			Message.addMessage("New Round Started");

			// resetting the flags
			mPlayerWentOut = false;
			mRoundStart = false;
			mLastCardR3Drwan = false;
			mEndCausedCusDiscardPile = false;
			return new Pair<Boolean, Boolean>(false, false);
		}

		// starting the turn
		Pair<Integer, Vector<Integer>> currPlayerChoice;
		Player otherPlayer = getOtherPlayer();
		currPlayerChoice = getCurrPlayer()
				.playerTurnController(otherPlayer.getMelds(), mDiscardPile);

		// logic for what to execute depending on which menu currently is
		// displayed and what the choice was made
		switch (currPlayerChoice.getFirst())
		{
		// playerChice.first == 1, beforeTurnStartcontrol was executed
		case 1:
		{
			// logic for sub menus of before turn start control
			return beforeTurnLogic(currPlayerChoice.getSecond().firstElement());
		}
		case 2:
		{
			// logic for sub menus of before turn start control
			turnStartLogic(currPlayerChoice.getSecond().firstElement());
			break;
		}
		case 3:
		{
			// logic for sub menus of before turn start control
			turnContinueLogic(currPlayerChoice.getSecond());
			break;
		}
		// error code
		case 10:
		{
			// do nothing as the game will loop over
			break;
		}

		default:
		{
			break;
		}

		}

		// main menu was not pressed so first is false
		// save was not pressed so second is false
		return new Pair<Boolean, Boolean>(false, false);
	}

	/**
	 * Contains the logic for Before Turn menu
	 * 
	 * @param aUserChoice, interger. Holds the user choice for the menu
	 * 
	 * @return pair of boolean value, boolean value. if first of the pair is
	 *         true exit to main menu was pressed else it was not. if second of
	 *         the pair is true save the game was pressed else it was not
	 */
	private Pair<Boolean, Boolean> beforeTurnLogic(Integer aUserChoice)
	{
		// at the strat of the turn, player have 5 choices:
		// 1) Save the game . enter 1
		// 2) Take a turn . enter 2
		// 3) Quit the game and go to main menu . enter 3

		// sub menus of before turn start menu
		switch (aUserChoice)
		{
		// save the game
		case 1:
		{
			return new Pair<Boolean, Boolean>(false, true);
		}
		// take a turn was pressed
		case 2:
		{
			getCurrPlayer().setPlayerBeforeTurnMenuFlag(false);
			break;
		}
		// quit the game and go to main menu was pressed
		case 3:
		{
			return new Pair<Boolean, Boolean>(true, false);
		}
		default:
		{
			// this should never run
			break;
		}
		}

		return new Pair<Boolean, Boolean>(false, false);
	}

	/**
	 * Contains the logic for Turn start menu
	 * 
	 * @param aUserChoice, interger. Holds the user choice for the menu
	 * 
	 * @return boolean value. it indicates whether a change was made to the the
	 *         player or not
	 */

	private boolean turnStartLogic(Integer aUserChoice)
	{
		// at the strat of the turn, player have 5 choices:
		// 1) ask for help . enter 1
		// 2) draw a card from deck . enter 2
		// 3) pick up the discard pile . enter 3
		// 4) show discard pile . enter 4
		// 5) show stock card (for debugging) . enter 5

		Player currPlayer = getCurrPlayer();
		String currPlayerName = currPlayer.getClass().getSimpleName();
		// sub menus of turn start menu
		switch (aUserChoice)
		{
		// ask for help
		case 1:
		{
			Message.addMessage("Asking computer for help!");
			Computer tempComp = new Computer(currPlayer);

			Pair<Pair<Integer, Vector<Integer>>, String> turnStartHelpReturn = tempComp
					.turnStartHelp(mDiscardPile);

			Message.addMessage(turnStartHelpReturn.getSecond());

			return false;
		}
		// draw a card from deck
		case 2:
		{
			// deal a card untill player gets a non red three card
			boolean dealtCardResult = false;
			while (!dealtCardResult)
			{
				try
				{
					Card dealtCard = mDeck.dealCard();
					dealtCardResult = currPlayer.addCardToHand(dealtCard);

					// concoting message to display
					StringBuilder dealtMessage = new StringBuilder(
							currPlayerName + " drew ");
					dealtMessage.append(dealtCard.getRankSuit());
					Message.addMessage(dealtMessage.toString());
				}
				catch (EmptyStockException e)
				{
					// checking if the stock is empty and last dealt card was
					// red
					// three
					mLastCardR3Drwan = !dealtCardResult;
				}
			}

			// setting the turn start flag to false
			currPlayer.setTurnStartFlag(false);
			break;
		}
		// pick up the discard pile
		case 3:
		{
			// player can not pick up the discard pile if the
			// the top card can not be used to be melded

			// see if we can meld the top of the discard pile into any
			// of the alredy existing melds
			if (currPlayer.canAddToMeld(mDiscardPile.firstElement())
					.getFirst() == -1
					&& !currPlayer.canPickUpDiscardPile(mDiscardPile))
			{
				// we can not meld the stack so our only option is to draw
				Message.addMessage(
						currPlayerName + "can not pick up discard pile ");
				return false;
			}

			String discardCardStr = Card
					.getAllCardInPrintedFormat(mDiscardPile);

			// adding all the card of the discard pile to the player
			for (Card discardCard : mDiscardPile)
			{
				currPlayer.addCardToHand(discardCard);
			}

			// clearing the discard pile
			emptyDiscardPile();

			// concoting message to display
			StringBuilder pickedupMessage = new StringBuilder(currPlayerName
					+ " picked up following cards from the discard pile\n");
			pickedupMessage.append(discardCardStr);
			Message.addMessage(pickedupMessage.toString());

			// setting the turn start flag to false
			currPlayer.setTurnStartFlag(false);
			break;
		}
		// show discard pile
		case 4:
		{
			Message.addMessage("Discard Pile:");
			Message.addMessage(Card.getAllCardInPrintedFormat(mDiscardPile));
			return false;
		}
		// show stock card (for debugging)
		case 5:
		{
			Message.addMessage("Stock:");
			Message.addMessage(
					Card.getAllCardInPrintedFormat(mDeck.getStock()));
			return false;
		}
		default:
		{
			// this should never run
			break;
		}
		}
		return true;
	}

	/**
	 * Contains the logic for Turn continue menu
	 * 
	 * @param aUserChoiceVec, vector of interger. Holds the user choices for the
	 *                            menu
	 * 
	 * @return none
	 */
	private boolean turnContinueLogic(Vector<Integer> aUserChoiceVec)
	{
		// else it is not the start of the round so they have 5 choices
		// 1) Ask for help . enter 1
		// 2) Add a card in hand to meld . enter 2 <actualHandCardIdx> <meldIdx>
		// 3) Discard a card from hand . enter 3 <actualHandCardIdx>
		// 4) Go out . enter 4
		// 5) Make a new meld . enter 5 <actualHandCardIdx> <actualHandCardIdx>
		// <actualHandCardIdx> ...
		// 6) Show discard pile . enter 6
		// 7) Show stock card (for debugging) . enter 7
		// 8) Take out wild card From meld . enter 8 <meldCardIdx> <meldIdx>

		Player currPlayer = getCurrPlayer();
		String currPlayerName = currPlayer.getClass().getSimpleName();

		// sub menus of turn continue menu
		switch (aUserChoiceVec.firstElement())
		{
		// Ask for help
		case 1:
		{
			Message.addMessage("Asking computer for help!");
			Computer tempComp = new Computer(currPlayer);

			Pair<Pair<Integer, Vector<Integer>>, String> message = tempComp
					.turnContinueHelp(getOtherPlayer().getMelds(),
							mDiscardPile);

			Message.addMessage(message.getSecond());

			return false;
		}
		// Add a card in hand to meld
		case 2:
		{
			// trying to adding the card to a meld
			Card cardToMeld = currPlayer.getActualHand()
					.elementAt(aUserChoiceVec.elementAt(1));
			Pair<Boolean, String> result = currPlayer.addToMeld(
					aUserChoiceVec.elementAt(1), aUserChoiceVec.elementAt(2));

			// if the first is false then error
			if (Boolean.FALSE.equals(result.getFirst()))
			{
				return false;
			}

			// concoting message to display
			StringBuilder displayMessage = new StringBuilder(currPlayerName);
			displayMessage.append(" added ");
			displayMessage.append(cardToMeld.getRankSuit());
			displayMessage.append(" to a meld");
			Message.addMessage(displayMessage.toString());
			return true;
		}
		// Discard a card from hand
		case 3:
		{
			// discarding and adding the discarded card to discard pile
			addToDiscardPile(currPlayer.discard(aUserChoiceVec.elementAt(1)));

			StringBuilder displayMessage = new StringBuilder(currPlayerName);
			displayMessage.append(" Disacrded " + getDiscardPileTopRankSuit());
			Message.addMessage(displayMessage.toString());

			// checking if the curr playercan go out
			if (currPlayer.canGoOut())
			{
				currPlayer.setPlayerWentOutStatus(true);
				mPlayerWentOut = true;

				// concoting message to display
				displayMessage = new StringBuilder(currPlayerName);
				displayMessage.append(" Goes out");
				Message.addMessage(displayMessage.toString());
			}

			// curr player discarded so its next players turn
			mPlayerTurn = (mPlayerTurn == ENUM_PlayerTurn.TURN_COMPUTER
					? ENUM_PlayerTurn.TURN_HUMAN
					: ENUM_PlayerTurn.TURN_COMPUTER);

			// clearing the menu flag
			currPlayer.setPlayerBeforeTurnMenuFlag(true);
			currPlayer.setTurnStartFlag(true);

			return true;

		}
		// Go out
		case 4:
		{
			// checking if the curr player can go out
			// concoting message to display
			StringBuilder displayMessage = new StringBuilder(currPlayerName);

			if (!currPlayer.canGoOut())
			{
				displayMessage.append(" Can not Go out");
				Message.addMessage(displayMessage.toString());
				return false;
			}

			currPlayer.setPlayerWentOutStatus(true);
			mPlayerWentOut = true;

			displayMessage.append(" Goes out");
			Message.addMessage(displayMessage.toString());

			return true;
		}
		// Make a new meld
		case 5:
		{

			// removing the menu idx form the user input
			aUserChoiceVec.remove(0);

			// trying to create a new meld
			Pair<Boolean, String> result = currPlayer
					.makeNewMeld(aUserChoiceVec);

			// if the first is false then error
			if (Boolean.FALSE.equals(result.getFirst()))
			{
				Message.addMessage(result.getSecond());
				return false;
			}

			Card cardToMeld = currPlayer.getMelds().lastElement()
					.firstElement();

			// concoting message to display
			StringBuilder displayMessage = new StringBuilder(currPlayerName);
			displayMessage.append(" made a new meld of ");
			displayMessage.append(cardToMeld.getRankSuit());
			Message.addMessage(displayMessage.toString());
			return true;
		}
		// show discard pile
		case 6:
		{
			Message.addMessage("Discard Pile:");
			Message.addMessage(Card.getAllCardInPrintedFormat(mDiscardPile));
			return false;
		}
		// show stock card (for debugging)
		case 7:
		{
			Message.addMessage("Stock:");
			Message.addMessage(
					Card.getAllCardInPrintedFormat(mDeck.getStock()));
			return false;
		}
		// Take out wild card From meld
		case 8:
		{
			// trying to take out a wild card
			Card wildCard = currPlayer.getHand()
					.elementAt(aUserChoiceVec.elementAt(2))
					.elementAt(aUserChoiceVec.elementAt(1));
			Pair<Boolean, String> result = currPlayer.takeOutCardFromMeld(
					aUserChoiceVec.elementAt(1), aUserChoiceVec.elementAt(2));

			// if the first is false then error
			if (Boolean.FALSE.equals(result.getFirst()))
			{
				Message.addMessage(result.getSecond());

				return false;
			}

			// concoting message to display
			StringBuilder displayMessage = new StringBuilder(currPlayerName);
			displayMessage.append(" took out ");
			displayMessage.append(wildCard.getRankSuit());
			displayMessage.append(" from the meld");
			Message.addMessage(displayMessage.toString());

			// checking if the meld was desolved or not
			if (!result.getSecond().isEmpty())
			{
				Message.addMessage(result.getSecond());
			}

			return true;
		}
		default:
		{
			// this should never run
			break;
		}
		}

		return false;
	}

	/**
	 * empties the discard pile
	 * 
	 * @param none
	 * 
	 * @return true to indicate that the vector was empited
	 */
	private boolean emptyDiscardPile()
	{
		mDiscardPile.clear();

		return mDiscardPile.isEmpty();
	}

	/**
	 * adds a card tothe firstElement of the discardPile
	 * 
	 * @param none
	 * 
	 * @return boolean, true to indicate successful insertion
	 */
	private boolean addToDiscardPile(Card aCardToAdd)
	{
		mDiscardPile.insertElementAt(aCardToAdd, 0);
		return true;
	}

	/**
	 * simulates a coin toss
	 * 
	 * @param none
	 * 
	 * @return String, h if it is head; t if it is tail
	 */
	private String tossACoin()
	{
		return (mRandom.nextInt(2)) == 0 ? "h" : "t";
		// TODO delete me
		// return "t";
	}

	/**
	 * asks the player for a h or tails and does a coin toss
	 * 
	 * @param none
	 * 
	 * @return true to signify success
	 */
	private boolean determinePlayerOrder()
	{
		// seeing who goes first
		if (mPlayerList.firstElement().getTotalPoint() > mPlayerList
				.lastElement().getTotalPoint())
		{
			// computer has more points so it goes first
			mPlayerTurn = ENUM_PlayerTurn.TURN_COMPUTER;
			Message.addMessage(
					"Computer has more points! So computer starts first");
		}
		else if (mPlayerList.lastElement().getTotalPoint() > mPlayerList
				.firstElement().getTotalPoint())
		{
			// human has more points so it goes first
			mPlayerTurn = ENUM_PlayerTurn.TURN_HUMAN;
			Message.addMessage("Human has more points! So human starts first");
		}
		else
		{
			// its a tie. Toss a coin
			String userInput;
			boolean invalidInput = true;
			do
			{
				// clearing the window
				clearScreen();

				// printing the game tile
				printGameTitle();

				// printing any messages
				for (String currMessage : Message.getMessages())
				{
					System.out.println(currMessage);
				}

				// clearing the messages
				Message.clearLatestMessages();

				System.out.println(
						"Tossing a coin to decide who starts the Round.");
				System.out.println("Guess whether it is head or tails.");
				System.out.println("Enter h for head or t for tail: ");

				userInput = UtitlityFunc.getUserInput();

				if (userInput.length() == 1
						&& ("ht".contains(userInput.toLowerCase())))
				{
					invalidInput = false;
				}
				else
				{
					Message.addMessage("Invalid Input!! Try Again!!");
				}
			} while (invalidInput);

			// tossing a coin
			if (userInput.equalsIgnoreCase(tossACoin()))
			{
				mPlayerTurn = ENUM_PlayerTurn.TURN_HUMAN;
				Message.addMessage("You guess correctly! You start first");
			}
			else
			{
				mPlayerTurn = ENUM_PlayerTurn.TURN_COMPUTER;
				Message.addMessage(
						"You guess incorrectly! Computer starts first");
			}
		}
		return true;
	}

	/**
	 * Clears the terminal screen
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public void clearScreen()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * To print out the game title. It is goning to be the top most thing in
	 * game frame
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public void printGameTitle()
	{
		// as this is the top most thing in the frame
		// clearing the screen
		clearScreen();

		// printing game title
		System.out.println(String.format("%50s", " ").replace(' ', '*'));
		System.out.println(String.format("%31s %18s", "-Canasta-Game-", " ")
				.replace(' ', '*'));
		System.out.println(String.format("%50s", " ").replace(' ', '*'));
	}

	/**
	 * print the Game info
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public void printRound()
	{
		// printing round number
		System.out.println(String.format("%50s", " ").replace(' ', '-'));
		System.out.println(String.format("%28s %s", "Round: ", mCurrRoundNum));
		System.out.println(String.format("%50s", " ").replace(' ', '-'));

		// printing player 1 details
		System.out.println("Computer:");
		System.out.println(mPlayerList.firstElement());

		// printing the discard and stock pile
		System.out.println(String.format("%50s", " ").replace(' ', '-'));
		System.out.println("Discard pile:");
		System.out.println(Card.getAllCardInPrintedFormat(mDiscardPile));
		System.out.println(String.format("%30s ", " ").replace(' ', '-'));
		System.out.println(mDeck);
		System.out.println(String.format("%50s ", " ").replace(' ', '-'));

		// printing player 2 details
		System.out.println("Human:");
		System.out.println(mPlayerList.lastElement());

		// printing current player
		System.out.println(String.format("%30s ", " ").replace(' ', '-'));
		System.out.println("Current Player: "
				+ getCurrPlayer().getClass().getSimpleName());
	}
}
