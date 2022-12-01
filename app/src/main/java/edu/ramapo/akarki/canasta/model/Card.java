package edu.ramapo.akarki.canasta.model;

import java.util.Vector;

public class Card implements Comparable<Card> {

	//
	// describes the type of a card in a deck
	//
	// And based on the type the card acts differently
	//
	// natural cards are a, 4, 5, 6, 7, 8, 9, x ( or 10 ),
	// j, q, and k. They have no "special powers"
	// wild cards are 2, and J ( or jokers ) and can be
	// substituted to any of the natural cards to make
	// a meld.
	// back three are 3 of club and 3 of spade. It freeze the
	// discard pile when discarded. The frozen discard
	// pile cannot be picked up until a natual card is
	// discarded.
	// red three are 3 of heart and 3 of diamond. It is
	// automatically melded and new card is drawn from
	// stock pile as replacement.
	//
	public enum ENUM_CardType
	{
		CARDTYPE_NATURAL, CARDTYPE_WILDCARD, CARDTYPE_BLACK_THREE,
		CARDTYPE_RED_THREE,
	}

	// mRank holds the rank of the card
	// can be one of a, 2, 3, 4, 5, 6, 7, 8, 9,
	// X ( 10 ), J, Q, K, or J ( joker )
	// joker and jack can only be distinguished
	// by looking at the rank
	// joker has a rank of 1 or 2, while
	// jack has rank of S, or D, or H, or D
	String mRank;

	// mSuit holds the suit of the card
	// can be one of C ( club ), D ( spade ),
	// H ( heart ), D ( diamond ), 1, or 2
	//
	// only the standard card rank has c, s, h, and d
	// as its suit. while only joker can have 1, and 2
	// as its suit
	String mSuit;

	// mPoint the score of each indivisual card
	// Card Points
	// 4, 5, 6, 7. Black 3 -> 5
	// 8, 9, 10, J, Q, K -> 10
	// 2, A -> 20
	// Joker -> 50
	// Red 3 -> 100
	int mPoint;

	// mCardType holds the card type.
	// It can be either natural, or wildcard, or
	// black three, or red three
	ENUM_CardType mCardType;

	/**
	 * default Contructor for Card Class.
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Card()
	{
		mRank = "A";
		mSuit = "C";
		mPoint = 20;
		mCardType = ENUM_CardType.CARDTYPE_NATURAL;
	}

	/**
	 * Paramatized Contructor for Card Class.
	 * 
	 * @param aRank, a character. It holds the rank of the card
	 * @param aSuit, a character. It holds the suit of the card
	 * 
	 * @return none
	 */
	public Card(String aRank, String aSuit)
	{
		aRank = aRank.toUpperCase();
		aSuit = aSuit.toUpperCase();

		// checking if a_rnak and Suit is valid or not
		if (!(ValidRankSuit.isRankValid(aRank))
				|| !(ValidRankSuit.isSuitValid(aSuit)))
		{
			throw new IllegalArgumentException(
					"Rank Suit passed is not valid!!!! " + mRank + mSuit);
		}

		mRank = aRank;
		mSuit = aSuit;

		// figuring out the card type and assigning it to the m_cardType
		if (mRank.equals("2")
				|| (mRank.equals("J") && mSuit.compareTo("A") < 0))
		{
			mCardType = ENUM_CardType.CARDTYPE_WILDCARD;
		}
		else if (mRank.equals("3"))
		{
			if ("HD".contains(mSuit))
			{
				mCardType = ENUM_CardType.CARDTYPE_RED_THREE;
			}
			else
			{
				mCardType = ENUM_CardType.CARDTYPE_BLACK_THREE;
			}
		}
		else
		{
			mCardType = ENUM_CardType.CARDTYPE_NATURAL;
		}

		// figuring out the point of the card and assigning it to mPoint
		if (mCardType == ENUM_CardType.CARDTYPE_RED_THREE)
		{
			mPoint = 100;
		}
		else if (mSuit.compareTo("A") < 0)
		{
			mPoint = 50;
		}
		else if ("2A".contains(mRank))
		{
			mPoint = 20;
		}
		else if ("89XJQK".contains(mRank))
		{
			mPoint = 10;
		}
		else
		{
			mPoint = 5;
		}
	}

	/**
	 * Paramatized Contructor for Card Class.
	 * 
	 * @param aRankSuit, a const string. It holds the rank as the 0th character
	 *                       and suit as the 1th character
	 * 
	 * @return none
	 */
	public Card(String aRankSuit)
	{
		this(aRankSuit.substring(0, 1), aRankSuit.substring(1));
	}

	/**
	 * Copy Contructor for Card Class.
	 * 
	 * @param aOtherCard, card to copy from
	 * 
	 * @return none
	 */
	public Card(Card aOtherCard)
	{
		this(aOtherCard.mRank, aOtherCard.mSuit);
	}

	/**
	 * toString funciton
	 * 
	 * @param none
	 * 
	 * @return rankSuit
	 */
	@Override
	public String toString()
	{
		return this.mRank + this.mSuit;
	}

	/**
	 * Overriding the hashcode method of the Object Class
	 * 
	 * @param none
	 * 
	 * @return int, the hascode for the object
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 3;

		hash = prime * hash
				+ ((this.mRank == null) ? 0 : this.mRank.hashCode());
		hash = prime * hash
				+ ((this.mSuit == null) ? 0 : this.mSuit.hashCode());
		return hash;
	}

	/**
	 * to check if this card is equal to the passed card
	 * 
	 * @param o object of Object class, it holds the object to check that it is
	 *              equal to this Card object or not.
	 * 
	 * @return boolean value. true if this card is equal to the other card.
	 *         false other wise.
	 */
	@Override
	public boolean equals(Object o)
	{
		// checking for self reference
		if (this == o)
		{
			return true;
		}

		// checking for null pointer passing
		if (o == null)
		{
			return false;
		}

		// checking if the passed Object is a instance of this class or not
		if (getClass() != o.getClass())
		{
			return false;
		}

		final Card otherCard = (Card) o;

		return mRank.equals(otherCard.mRank) && mSuit.equals(otherCard.mSuit);
	}

	/**
	 * to compare if this card is less than, or equal to, or more than equal to
	 * the passed card.
	 * 
	 * @param aOtherCard const card object, It holds a card object to compare
	 *                       to.
	 * 
	 * @return int value. 0 if this card is equal to the other card. < 0 if this
	 *         card is less than the other card. > 0 if this card is greater
	 *         than the other card.
	 */
	@Override
	public int compareTo(Card aOtherCard)
	{
		// The hierarchy is J (Joker) < 2 < 3 < 4 < 5 < 6
		// < 7 < 8 < 9 < x < J ( Jack ) < Q < K < A,
		// and the suit is C < D < H < S

		// checking if they are equal
		if (mRank.equals(aOtherCard.mRank) && mSuit.equals(aOtherCard.mSuit))
		{
			return 0;
		}

		// checking if the rank is equal then compare the suits
		// here we do not need to worry about "J" ( Joker ) and
		// "J" (Jack) because Joker can only have 1 or 2 as suit
		// which would be less than Jack"s suit as they are
		// alphabhets
		else if (mRank.equals(aOtherCard.mRank))
		{
			return mSuit.compareTo(aOtherCard.mSuit);
		}
		// from before if know that from now the ranks of lCard and
		// rCard is different
		// checking for Joker which is the smallest
		// if left hand is joker then return -1
		else if (mSuit.compareTo("A") < -1)
		{
			// this is a joker while other card is not
			return -1;
		}
		// if right hand is joker then return 1
		else if (aOtherCard.getSuit().compareTo("A") < -1)
		{
			return 1;
		}
		// checking for A which is the largest
		else if (mRank.equals("A"))
		{
			return 1;
		}
		else if (aOtherCard.mRank.equals("A"))
		{
			return -1;
		}
		// checking for K which is the next largest
		else if (mRank.equals("K"))
		{
			return 1;
		}
		else if (aOtherCard.mRank.equals("K"))
		{
			return -1;
		}
		// checking for Q which is the next largest
		else if (mRank.equals("Q"))
		{
			return 1;
		}
		else if (aOtherCard.mRank.equals("Q"))
		{
			return -1;
		}
		// checking for J (Jack) which is the next largest
		// here we have already checked for Joker so we
		// do not have to worry about that
		else if (mRank.equals("J"))
		{
			return 1;
		}
		else if (aOtherCard.mRank.equals("J"))
		{
			return -1;
		}
		// rest of them which are 2, 3, 4, 5, 6, 7, 8, 9, and X
		// order in Ascii follow our order
		else
		{
			return (mRank.compareTo(aOtherCard.mRank) < 0 ? -1 : 1);
		}
	}

	/**
	 * Getter functions to get rank
	 * 
	 * @param none
	 * 
	 * @return aRankSuit, a const string. It holds the rank as the 0th character
	 *         and suit as the 1th character
	 */
	public String getRank()
	{
		return mRank;
	}

	/**
	 * Getter functions to get suit
	 * 
	 * @param none
	 * 
	 * @return aRankSuit, a const string. It holds the rank as the 0th character
	 *         and suit as the 1th character
	 */
	public String getSuit()
	{
		return mSuit;
	}

	/**
	 * Getter functions to get point
	 * 
	 * @param none
	 * 
	 * @return aRankSuit, a const string. It holds the rank as the 0th character
	 *         and suit as the 1th character
	 */
	public int getPoint()
	{
		return mPoint;
	}

	/**
	 * Getter functions to get card type
	 * 
	 * @param none
	 * 
	 * @return aRankSuit, a const string. It holds the rank as the 0th character
	 *         and suit as the 1th character
	 */
	public ENUM_CardType getCardType()
	{
		return mCardType;
	}

	/**
	 * Getter function to get the rank and suit as string
	 * 
	 * @param none
	 * 
	 * @return aRankSuit, a const string. It holds the rank as the 0th character
	 *         and suit as the 1th character
	 */
	public String getRankSuit()
	{
		return mRank + mSuit;
	}

	/**
	 * Static method to get the vector of card in string format
	 * 
	 * @param aCardVec vector of card object, it holds card list that needs to
	 *                     be converted to string format
	 * 
	 * @return string, containing all the cards that are in the passed vector in
	 *         string format. it is stored as "rs rs..."
	 */
	public static String getAllCardInPrintedFormat(Vector<Card> aCardVec)
	{
		// if the are no card in the hand return ""
		if (aCardVec.isEmpty())
		{
			return "";
		}

		// creating actual hand
		StringBuilder actualHandStr = new StringBuilder();
		for (Card card : aCardVec)
		{
			actualHandStr.append(card.toString() + " ");
		}

		// removing the last " "
		actualHandStr.deleteCharAt(actualHandStr.length() - 1);

		return actualHandStr.toString();
	}

	/**
	 * Static method to check if the passed type is in the passed cardList
	 * 
	 * @param aCardType type of card we are trying to find
	 * @param aCardVec  vector of card object, it holds card list is checked
	 *                      against
	 * 
	 * @return Integer if the type is found returns the position of the card;
	 *         else returns -1
	 */
	static public Integer hasTypeOfCard(ENUM_CardType aCardType,
			Vector<Card> aCardVec)
	{
		Integer cardIdx = 0;
		for (Card card : aCardVec)
		{
			if (card.getCardType().equals(aCardType))
			{
				return cardIdx;
			}

			++cardIdx;
		}

		return -1;
	}
}
