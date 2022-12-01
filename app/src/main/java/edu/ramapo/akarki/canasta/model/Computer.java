package edu.ramapo.akarki.canasta.model;

import java.util.Arrays;
import java.util.Vector;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;
import edu.ramapo.akarki.canasta.model.Card.ENUM_CardType;

public class Computer extends Player {
	/**
	 * default finalructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Computer()
	{
		super();
	}

	/**
	 * finalruct a Computer object and to populate its member variables
	 * mComputer Hand is populated using the passed parameter.
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
	public Computer(Integer aTotalScore, String aHandCards, String aMeldCards)
			throws ImproperMeldException
	{
		super(aTotalScore, aHandCards, aMeldCards);
	}

	/**
	 * To create a new Computer object and copy the passed Computer object's
	 * member variables data into the newly created Computer object
	 * 
	 * @param aOtherComputer , a object of Computer class passed. It holds a
	 *                           Computer object to be copied.
	 * 
	 * @return none
	 */
	public Computer(final Computer aOther)
	{
		super(aOther);
	}

	/**
	 * To create a new Computer object and copy the passed Computer object's
	 * member variables data into the newly created Computer object
	 * 
	 * @param aOtherComputer , a object of Player class passed. It holds a
	 *                           player object to be copied.
	 * 
	 * @return none
	 */
	public Computer(final Player aOther)
	{
		super(aOther);
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
	@Override
	public Pair<Integer, Vector<Integer>> beforeTurnStartControl()
	{
		return new Pair<Integer, Vector<Integer>>(1,
				new Vector<Integer>(Arrays.asList(2)));
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
	@Override
	public Pair<Integer, Vector<Integer>> turnStartControl(
			final Vector<Card> aDiscardPile)
	{
		// (2) Take a turn was pressed

		// at the start of the turn, computer has 2 choices:
		// 1) draw a card from deck -> enter 2
		// 2) pick up the discard pile -> enter 3

		Pair<Pair<Integer, Vector<Integer>>, String> startResult = turnStartHelp(
				aDiscardPile);
		Message.addMessage("Computer: " + startResult.getSecond());
		Message.addMessage("");

		return startResult.getFirst();
	}

	/**
	 * Contains the controller for turn to continue.aftere the player has either
	 * drawn a card or picked up the discard pile. It asks user to chose from
	 * "draw a card from deck", "pick up the discard pile", "Ask for Help",
	 * "show discard pile", "show stock card (for debugging)",
	 * 
	 * @param aDiscardPile, vector of cards that contains the discard pile
	 * 
	 * @return Pair of < Integer , Vector < Integer > >. The first interger
	 *         indicates this was--that is TurnStartconstrol so it is always 3.
	 *         the second is vector of unsinged integer the the vector's first
	 *         element is the sub menu index. rest of the element depends on the
	 *         submenu chosen
	 */
	@Override
	public Pair<Integer, Vector<Integer>> turnContinueControl(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{
		// player has drawns a card or picked up the discard pile

		// now computer can make 1 of 5 choices until they discard or go out:
		// else it is not the start of the round so they have 5 choices
		// 2) Add a card in hand to meld -> enter 2 <actualHandCardIdx>
		// <meldIdx>
		// 3) Discard a card from hand -> enter 3 <actualHandCardIdx>
		// 4) Go out -> enter 4
		// 5) Make a new meld -> enter 5 <actualHandCardIdx> <actualHandCardIdx>
		// <actualHandCardIdx> ...
		// 8) Take out wild card From meld -> enter 8 <meldCardIdx> <meldIdx>

		Pair<Pair<Integer, Vector<Integer>>, String> startResult = turnContinueHelp(
				aOtherPlayerMeld, aDiscardPile);
		Message.addMessage("Computer: " + startResult.getSecond());
		Message.addMessage("");

		return startResult.getFirst();
	}

	/**
	 * Decides what to do in the turn start menu. It choses between to pick up
	 * the discard pile and drawing
	 * 
	 * @param aDiscardPile, vector of cards that contains the discard pile
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >.
	 *         The inside pair contains the current submenu index which is 2 and
	 *         the choice. The out side pair's second contains the reasoning
	 *         behind its choice.
	 */
	public Pair<Pair<Integer, Vector<Integer>>, String> turnStartHelp(
			final Vector<Card> aDiscardPile)
	{
		// at the start of the turn, computer has 2 choices:
		// 1) draw a card from deck -> enter 2
		// 2) pick up the discard pile -> enter 3

		Vector<Integer> returnChoice;
		String returnMessage;

		// chekcing if hte pile is frozen
		if (aDiscardPile.firstElement()
				.getCardType() != ENUM_CardType.CARDTYPE_NATURAL)
		{
			returnChoice = new Vector<Integer>(Arrays.asList(2));
			returnMessage = "Can not pick up the pile as it is frozen. So draw card from the stock.";

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(2, returnChoice),
					returnMessage);
		}
		else if (!canPickUpDiscardPile(aDiscardPile))
		{
			// we can not meld the stack so our only option is to draw
			returnChoice = new Vector<Integer>(Arrays.asList(2));
			returnMessage = "Can not use top of the discard pile to make a meld. So draw a card from the stock.";

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(2, returnChoice),
					returnMessage);
		}

		// creating a temp computer and making it pick up the discard pile
		// and doing as much meld as we can
		Computer tempComputer = new Computer(this);

		// simulation picking up the pile
		tempComputer.pickUpDiscardPile(aDiscardPile);

		// TODO (REFACTOR): we should use the TurnContinueHelp() to make the
		// decision to wether to pickup or not
		// To do this we may have to bring the turnConatinueLogic inside the
		// player class

		// keeping track of if we can make a canasta
		Integer preCanastaCount = 0;
		for (Vector<Card> meld : tempComputer.getMelds())
		{
			// counting the number of casansta
			if (meld.size() >= 7)
			{
				++preCanastaCount;
			}
		}

		// looping over the card in the hand and seeing if we can meld any
		// thing
		// if yes then meld it
		int handCardIdx = 0;
		for (Card handCard : tempComputer.getActualHand())
		{
			// check for meld
			Pair<Integer, String> canMeldResult = tempComputer
					.canAddToMeld(handCard);

			// if meldable then meld it
			if (canMeldResult.getFirst() != -1)
			{
				tempComputer.addToMeld(handCardIdx, canMeldResult.getFirst());
			}

			++handCardIdx;
		}

		// chekcing if a canasta was made
		Integer postCanastaCount = 0;
		for (Vector<Card> meld : tempComputer.getMelds())
		{
			// counting the number of casansta
			if (meld.size() >= 7)
			{
				++postCanastaCount;
			}
		}

		// pick up the pile if the number of canasta was increased
		if (postCanastaCount > preCanastaCount)
		{
			returnChoice = new Vector<Integer>(Arrays.asList(3));
			returnMessage = "If the discard pile is picked up, by the end of the turn the number of canasta in the hand increases.";
		}

		// now we compare how much point did we gain or lost
		else if (tallyHandPoint() >= tempComputer.tallyHandPoint())
		{
			returnChoice = new Vector<Integer>(Arrays.asList(2));
			returnMessage = "Draw from the stock as if discard pile is picked up, it will decrease the current hand points.";
		}

		// we know that the hand point will end up increasing
		// but by how much. what is the average gain in point?
		else if ((tempComputer.tallyHandPoint() - tallyHandPoint())
				/ aDiscardPile.size() >= 1)
		{
			returnChoice = new Vector<Integer>(Arrays.asList(3));
			returnMessage = "If the discard pile is picked up, by the end of the turn on average each card will add 1 points.";
		}
		else
		{
			returnChoice = new Vector<Integer>(Arrays.asList(2));
			returnMessage = "Draw from the stock as there is nothing special about the discard pile for now.";
		}

		return new Pair<Pair<Integer, Vector<Integer>>, String>(
				new Pair<Integer, Vector<Integer>>(2, returnChoice),
				returnMessage);
	}

	/**
	 * Decides what to do in the turn continue menu. It choses between to pick
	 * up the discard pile and drawing
	 * 
	 * @param aOtherPlayerMeld, vector of vector of cards that contains the
	 *                              other player's melds
	 * @param aDiscardPile,     vector of cards that contains the discard pile
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >.
	 *         The inside pair contains the current submenu index which is 3 and
	 *         the choice. The out side pair's second contains the reasoning
	 *         behind its choice.
	 */
	public Pair<Pair<Integer, Vector<Integer>>, String> turnContinueHelp(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{
		// now player have 6 choices until they discard or go out:
		// else it is not the start of the round so they have 5 choices
		// 4) Go out -> enter 4
		// 2) Add a card in hand to meld -> enter 2 <actualHandCardIdx>
		// <meldIdx>
		// 5) Make a new meld no wildcard-> enter 5 <actualHandCardIdx>
		// <actualHandCardIdx> <actualHandCardIdx> ...
		// 5) Make a new meld yes windcard-> enter 5 <actualHandCardIdx>
		// <actualHandCardIdx> <actualHandCardIdx> ...
		// 8) Take out wild card From meld -> enter 8 <meldCardIdx> <meldIdx>
		// 3) Discard a card from hand -> enter 3 <actualHandCardIdx>

		Vector<Integer> returnChoice;
		String returnMessage;
		Pair<Pair<Integer, Vector<Integer>>, String> returnPair;

		// check if the player can go out
		if (canGoOut())
		{
			returnChoice = new Vector<Integer>(Arrays.asList(4));
			returnMessage = "Can go out and finish the round";

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		// checking if the actual hand is empty
		if (mPlayerHand.isActualHandEmpty() || mHandEmptyPreviously)
		{
			if (mHandEmptyPreviously)
			{
				// resetting it back to true so that we can process the wild
				// card next
				// time
				mWildCardInProcess = true;
				mHandEmptyPreviously = false;
				return discardLogic(aOtherPlayerMeld, aDiscardPile);
			}

			mHandEmptyPreviously = true;
			return emptyHandDiscardLogic(aOtherPlayerMeld, aDiscardPile);
		}

		// check if we can add to pre existing meld
		returnPair = canMeldCardFromHand();

		if (!returnPair.getFirst().equals(mErrorPairCode))
		{
			return returnPair;
		}

		// takeout all the wild card from the melds if a meld is not size seven
		// and is more than 3 and taking the meld will not break the meld

		// we want to only remove all the removable wildcards once
		if (mWildCardInProcess)
		{
			Pair<Boolean, Pair<Integer, Integer>> getMeldWithExtraWCReturn = getMeldWithExtraWC();

			if (Boolean.TRUE.equals(getMeldWithExtraWCReturn.getFirst()))
			{
				returnChoice = new Vector<Integer>(2);
				returnChoice.add(8);
				returnChoice
						.add(getMeldWithExtraWCReturn.getSecond().getFirst());
				returnChoice
						.add(getMeldWithExtraWCReturn.getSecond().getSecond());

				StringBuilder message = new StringBuilder(
						"Take out the wild card at index "
								+ getMeldWithExtraWCReturn.getSecond()
										.getFirst().toString());
				message.append(
						" from the meld at index " + getMeldWithExtraWCReturn
								.getSecond().getSecond().toString());
				message.append("\nDo this to process the melds better");

				return new Pair<Pair<Integer, Vector<Integer>>, String>(
						new Pair<Integer, Vector<Integer>>(3, returnChoice),
						message.toString());
			}
			else
			{
				mWildCardInProcess = false;
			}
		}

		// check if we can make a new meld
		returnPair = canMakeNewMeld();

		if (!returnPair.getFirst().equals(mErrorPairCode))
		{
			return returnPair;
		}

		// we can be sure that there are no 2 cards with same rank and have a
		// wild card in the hand
		// so add the wild cards to the melds with largest size
		returnPair = addWCToLargestPossibleMeld();

		if (!returnPair.getFirst().equals(mErrorPairCode))
		{
			return returnPair;
		}

		// discard a card as nothing else can be done

		// resetting it back to true so that we can process the wild card next
		// time
		mWildCardInProcess = true;
		mHandEmptyPreviously = false;
		return discardLogic(aOtherPlayerMeld, aDiscardPile);
	}

	/**
	 * Contains the logic for if we can add a card to existing melds
	 * 
	 * @param none
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >. If
	 *         it is true, the inside pair contains the current submenu index
	 *         which is 3 followed by card idx that can be melded and meldIdx
	 *         where it can be melded . The out side pair's second contains the
	 *         reasoning behind its choice; eles the inside pair contains the
	 *         error code and out size is an empty string
	 */
	protected Pair<Pair<Integer, Vector<Integer>>, String> canMeldCardFromHand()
	{
		// looping over the card in the hand and seeing if we can meld any thing
		// if yes then meld it

		Integer handCardIdx = 0;
		for (Card handCard : getActualHand())
		{
			// check for meld
			Pair<Integer, String> canMeldResult = canAddToMeld(handCard);

			if (canMeldResult.getFirst() == -1)
			{
				++handCardIdx;
				continue;
			}

			// if meldable then return saying meld
			Vector<Integer> returnChoice = new Vector<Integer>(
					Arrays.asList(2, handCardIdx, canMeldResult.getFirst()));
			String returnMessage = "Can meld the card at index "
					+ handCardIdx.toString() + " to the meld at index "
					+ canMeldResult.getFirst().toString();

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		return new Pair<Pair<Integer, Vector<Integer>>, String>(mErrorPairCode,
				"");
	}

	/**
	 * Contains the logic for if we can make new a card
	 * 
	 * @param none
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >. If
	 *         it is true, the inside pair contains the current submenu index
	 *         which is 3 followed by card idx that can be melded and meldIdx
	 *         where it can be melded . The out side pair's second contains the
	 *         reasoning behind its choice; eles the inside pair contains the
	 *         error code and out size is an empty string
	 */
	protected Pair<Pair<Integer, Vector<Integer>>, String> canMakeNewMeld()
	{
		// checking if the there is any rank that has 3 cards in them or has 2
		// card and we have a wild card at hand

		// divinging out cards at hand according to its rank
		Vector<Vector<Card>> sameRankHandCardList = getDividedAcualHandCardList();

		// checking if we have a wildcard at hand
		Boolean hasWildCard = false;
		Integer wildCardIdx = -1;

		for (Vector<Card> sameRankList : sameRankHandCardList)
		{
			Card firstCard = sameRankList.firstElement();

			hasWildCard = firstCard
					.getCardType() == ENUM_CardType.CARDTYPE_WILDCARD;

			if (Boolean.TRUE.equals(hasWildCard))
			{
				wildCardIdx = getActualHand().indexOf(firstCard);
				break;
			}
		}

		for (Vector<Card> sameRankList : sameRankHandCardList)
		{
			Card firstRankCard = sameRankList.firstElement();

			// only procede if there are 3 card of the same rank, or 2 card of a
			// same rank
			// and has wildcard, or we are dealing with natural card
			if (sameRankList.size() == 1
					|| (sameRankList.size() == 2 && !hasWildCard)
					|| firstRankCard
							.getCardType() != ENUM_CardType.CARDTYPE_NATURAL)
			{
				continue;
			}

			Vector<Integer> returnChoice = new Vector<Integer>(
					sameRankList.size() + 3);
			returnChoice.add(5);

			// calculating the indexs
			Integer idxOfFirstCard = getActualHand().indexOf(firstRankCard);

			StringBuilder allIndexStr = new StringBuilder();

			// add the index of all the cards in the ranklist
			Integer cardIndex = 0;
			for (Card card : sameRankList)
			{
				Integer idxOfCurrCard = idxOfFirstCard + cardIndex;
				returnChoice.add(idxOfCurrCard);
				allIndexStr.append(idxOfCurrCard.toString() + " ");

				++cardIndex;
			}

			if (sameRankList.size() == 2)
			{
				allIndexStr.append(wildCardIdx.toString() + " ");
				returnChoice.add(wildCardIdx);
			}

			String returnMessage = "Can make a new meld with the cards at indexs--"
					+ allIndexStr.toString();

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		return new Pair<Pair<Integer, Vector<Integer>>, String>(mErrorPairCode,
				"");
	}

	/**
	 * takes out the wild card from the meld if the meld is not the size of 7
	 * and more than 3 and taking out the wild card will not break the meld
	 * 
	 * @param none
	 * 
	 * @return a pair of < Boolean, pair of < integer , integer > >. If it is
	 *         true, returns a pair whose first element is true and second is a
	 *         pair of integer, integer where first is the wild card index in
	 *         the meld and second is the meld idx. Else, returns a false whose
	 *         first element is false and second is a pair of integer, integer
	 *         where first is -1 and second is also -1
	 */
	protected Pair<Boolean, Pair<Integer, Integer>> getMeldWithExtraWC()
	{
		// if a meld is more than 3 and it has a wild card then test for any 2
		// cards of same rank in actual hand
		boolean hasWildCard = false;
		Integer wildCardInMeldIdx = 0;
		Integer wildCardMeldIdx = 1;

		// checking for any wildcards in a meld where the meld size is more than
		// 3 but is not 7 as that is canasta
		// if yes we can safly take the wild card out
		for (Vector<Card> meld : getMelds())
		{
			if (meld.size() != 7 && meld.size() > 3 && meld.lastElement()
					.getCardType() == ENUM_CardType.CARDTYPE_WILDCARD)
			{
				hasWildCard = true;
				wildCardInMeldIdx = meld.size() - 1;
				break;
			}

			++wildCardMeldIdx;
		}

		if (!hasWildCard)
		{
			wildCardMeldIdx = -1;
			wildCardInMeldIdx = -1;
		}

		return new Pair<Boolean, Pair<Integer, Integer>>(hasWildCard,
				new Pair<Integer, Integer>(wildCardInMeldIdx, wildCardMeldIdx));
	}

	/**
	 * Contains the logic to add a wild card to the largest possible meld
	 * 
	 * @param none
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >. If
	 *         it is true, the inside pair contains the current submenu index
	 *         which is 3 followed by card idx that can be melded and meldIdx
	 *         where it can be melded . The out side pair's second contains the
	 *         reasoning behind its choice; eles the inside pair contains the
	 *         error code and out size is an empty string
	 */
	protected Pair<Pair<Integer, Vector<Integer>>, String> addWCToLargestPossibleMeld()
	{
		// we can be sure that there are no 2 cards with same rank and have a
		// wild card in the hand
		// so add the wild cards to the melds with largest size
		// checking if we have a wildcard in hand
		if (!mPlayerHand.actualHandHasWildCard())
		{
			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					mErrorPairCode, "");
		}

		// wildcard is always in the 0th position if they are present
		Integer wildCardInHandIdx = 0;

		// checking if we can add a wild card to a meld and tracking the
		// largest valid meld
		Integer largestMeldIdxToAddWC = largestValidMeldToAddWC(true);

		// checking if the largest Meld was not found
		if (largestMeldIdxToAddWC == -1)
		{
			largestMeldIdxToAddWC = largestValidMeldToAddWC(true);

			// no valid meld to add the wild card to
			if (largestMeldIdxToAddWC == -1)
			{
				return new Pair<Pair<Integer, Vector<Integer>>, String>(
						mErrorPairCode, "");
			}
		}

		Vector<Integer> returnChoice = new Vector<Integer>(
				Arrays.asList(2, wildCardInHandIdx, largestMeldIdxToAddWC));
		StringBuilder message = new StringBuilder(
				"Add the the wild card at index "
						+ wildCardInHandIdx.toString());
		message.append(
				" to the meld at index " + largestMeldIdxToAddWC.toString());
		message.append(
				"\nThis allows for mamimum chances of getting a canasta");

		return new Pair<Pair<Integer, Vector<Integer>>, String>(
				new Pair<Integer, Vector<Integer>>(3, returnChoice),
				message.toString());
	}

	/**
	 * Contains the discard logic
	 * 
	 * @param aOtherPlayerMeld, vector of vector of cards that contains the
	 *                              other player's melds
	 * @param aDiscardPile,     vector of cards that contains the discard pile
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >.
	 *         The inside pair contains the current submenu index which is 3 and
	 *         the choice. The out side pair's second contains the reasoning
	 *         behind its choice.
	 */
	protected Pair<Pair<Integer, Vector<Integer>>, String> discardLogic(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{
		Vector<Integer> returnChoice;
		String returnMessage;

		Vector<Vector<Card>> sameRankDivList = getDividedAcualHandCardList();

		// chekcing if the player has only one card in the hand and has a
		// canasta then discard that card and go out
		if (sameRankDivList.size() == 1 && mPlayerHand.getHasCanasta())
		{
			returnChoice = new Vector<Integer>(Arrays.asList(3, 0));
			returnMessage = "Discard the only card in the hand and go out";

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		// if the discard pile is frozen and we have a black 3 at hand throw it
		Integer blackThreeIdx = Card.hasTypeOfCard(
				ENUM_CardType.CARDTYPE_BLACK_THREE, getActualHand());

		if (!aDiscardPile.isEmpty()
				&& aDiscardPile.firstElement()
						.getCardType() == ENUM_CardType.CARDTYPE_BLACK_THREE
				&& blackThreeIdx != -1)
		{
			returnChoice = new Vector<Integer>(Arrays.asList(3, blackThreeIdx));
			returnMessage = "As the discard pile is frozen through the Black Three to keep it in frozen state at"
					+ blackThreeIdx.toString();

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		// get a list of card that the opponent has a meld of
		Vector<Card> oppMeldRanks = new Vector<Card>();

		for (Vector<Card> meld : aOtherPlayerMeld)
		{
			// skip red 3
			if (meld.size() == 1)
			{
				continue;
			}
			oppMeldRanks.add(meld.firstElement());
		}

		// discard a card if the oponent does not have a meld of it
		for (Vector<Card> sameRankList : sameRankDivList)
		{
			Card currRankCard = sameRankList.firstElement();

			// only process natual cards
			if (currRankCard.getCardType() != ENUM_CardType.CARDTYPE_NATURAL)
			{
				continue;
			}

			// check if the card is the meld of the opponent
			boolean oppHasMeldOfCurrRank = false;
			for (Card oppMeldRank : oppMeldRanks)
			{
				if (oppMeldRank.getRank().equals(currRankCard.getRank()))
				{
					oppHasMeldOfCurrRank = true;
					break;
				}
			}

			if (oppHasMeldOfCurrRank)
			{
				continue;
			}

			// throw the card as the opponent does not has a meld of it

			// calculating the indexs
			Integer idxOfFirstCard = getActualHand()
					.indexOf(sameRankList.firstElement());

			returnChoice = new Vector<Integer>(
					Arrays.asList(3, idxOfFirstCard));
			returnMessage = "Discard the card at index: "
					+ idxOfFirstCard.toString()
					+ " as the opponent does not have a meld of this rank";

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					returnMessage);
		}

		returnChoice = new Vector<Integer>(Arrays.asList(3, 0));
		returnMessage = "Discard a card that is largest";

		return new Pair<Pair<Integer, Vector<Integer>>, String>(
				new Pair<Integer, Vector<Integer>>(3, returnChoice),
				returnMessage);
	}

	/**
	 * Contains the discard logic when the hand is empty and can not go out
	 * 
	 * @param aOtherPlayerMeld, vector of vector of cards that contains the
	 *                              other player's melds
	 * @param aDiscardPile,     vector of cards that contains the discard pile
	 * 
	 * @return a pair of < pair of < integer , vector of integer >, string >.
	 *         The inside pair contains the current submenu index which is 3 and
	 *         the choice. The out side pair's second contains the reasoning
	 *         behind its choice.
	 */
	protected Pair<Pair<Integer, Vector<Integer>>, String> emptyHandDiscardLogic(
			final Vector<Vector<Card>> aOtherPlayerMeld,
			final Vector<Card> aDiscardPile)
	{
		// remove the card from the a meld from where a card can be be taken
		// out without the meld breaking

		Vector<Card> validCardToRemoveFromMeld = new Vector<Card>();
		Vector<Integer> validMeldIdxList = new Vector<Integer>();

		Integer meldIdx = 0;
		for (Vector<Card> meld : getMelds())
		{
			++meldIdx;
			Card firstCard = meld.firstElement();
			// ignore if the meld is a canasta
			// or meld's first element is not a natural card
			// or meld's size is more than 3 and the 3rd ard is not a
			// natural card
			if (meld.size() == 7
					|| firstCard.getCardType() != ENUM_CardType.CARDTYPE_NATURAL
					|| (meld.size() > 3 && meld.elementAt(2)
							.getCardType() != ENUM_CardType.CARDTYPE_NATURAL))
			{
				continue;
			}

			// add the meld to the valid meld list
			validCardToRemoveFromMeld.add(firstCard);
			validMeldIdxList.add(meldIdx);
		}

		// make a temp computer and see which card it suggest to discard
		try
		{

			Computer tempComputer = new Computer(0,
					Card.getAllCardInPrintedFormat(validCardToRemoveFromMeld),
					"");

			Pair<Pair<Integer, Vector<Integer>>, String> returnPair = tempComputer
					.discardLogic(aOtherPlayerMeld, aDiscardPile);

			Card tempCompSuggestedCard = tempComputer.getActualHand()
					.get(returnPair.getFirst().getSecond().lastElement());
			Integer validMeldIdx = validMeldIdxList.get(
					validCardToRemoveFromMeld.indexOf(tempCompSuggestedCard));

			Vector<Integer> returnChoice = new Vector<Integer>(2);
			returnChoice.add(8);
			returnChoice.add(0);
			returnChoice.add(validMeldIdx);

			StringBuilder message = new StringBuilder(
					"Take out the natural card");
			message.append(
					" from the meld at index " + validMeldIdx.toString());
			message.append("\nDo this to so that you can discard it");

			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					new Pair<Integer, Vector<Integer>>(3, returnChoice),
					message.toString());
		}
		catch (ImproperMeldException e)
		{
			// this is never gonna happen
			return new Pair<Pair<Integer, Vector<Integer>>, String>(
					mErrorPairCode, "");
		}
	}

	/**
	 * Contains the discard logic
	 * 
	 * @param aConsiderCanasta, boolean true to consider canasta to add the WC
	 *                              into
	 * 
	 * @return integer holding the index of the meld to insert the wildcard
	 *         into; -1 if not possible
	 */
	private Integer largestValidMeldToAddWC(boolean aConsiderCanasta)
	{
		if (!mPlayerHand.actualHandHasWildCard())
		{
			return -1;
		}

		Integer largestMeldIdxToAddWC = 0;
		Integer largestMeldSize = 0;
		Integer meldIdx = 1;
		for (Vector<Card> meld : getMelds())
		{
			if (aConsiderCanasta && meld.size() == 7)
			{
				continue;
			}
			Computer tempComp = new Computer(this);

			// adding a wildcard to the meld
			Pair<Boolean, String> addResult = tempComp.addToMeld(0, meldIdx);

			if (Boolean.TRUE.equals(addResult.getFirst())
					&& meld.size() > largestMeldSize)
			{
				// success
				largestMeldIdxToAddWC = meldIdx;
				largestMeldSize = meld.size();
			}

			++meldIdx;
		}

		return largestMeldIdxToAddWC;
	}
}
