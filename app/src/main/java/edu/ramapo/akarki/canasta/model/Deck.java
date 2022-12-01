package edu.ramapo.akarki.canasta.model;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import edu.ramapo.akarki.canasta.exceptions.EmptyStockException;

public class Deck {

	// stack of undelt cards
	private Vector<Card> mStock;

	// stack of delt cards, dealt cards are moved to here for later use
	private Vector<Card> mDealt;

	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Deck()
	{
		mStock = new Vector<Card>(108);
		mDealt = new Vector<Card>(108);

		String normalSuit = ValidRankSuit.S_SUIT_LIST.substring(0, 4);
		String noramlRank = ValidRankSuit.S_RANK_LIST.substring(0, 13);
		String jokerSuits = ValidRankSuit.S_SUIT_LIST.substring(4);
		String jokerRank = ValidRankSuit.S_RANK_LIST.substring(13);
		// creating the deck of cards
		for (Integer currDeckNum = 0; currDeckNum < 2; ++currDeckNum)
		{
			for (Character suitCharacter : normalSuit.toCharArray())
			{
				for (Character rankCharacter : noramlRank.toCharArray())
				{
					mStock.add(new Card(rankCharacter.toString(),
							suitCharacter.toString()));
				}
			}

			// creating a card for joker1 and joker2 and adding it to the
			// m_stock
			for (Character jokerSuit : jokerSuits.toCharArray())
			{
				mStock.add(new Card(jokerRank, jokerSuit.toString()));
			}
		}
	}

	/**
	 * To construct a Deck objectand to populate its member variables. m_stock
	 * is populated using the passed parameter. while the remaining cards not in
	 * the passed string are kept in m_dealt.
	 * 
	 * @param aStock aStock, a string containing the rank and suit of cards in
	 *                   the m_stock. Each card is seperated by blank space
	 * 
	 * @return none
	 */
	public Deck(String aStock)
	{
		this();

		Vector<Card> tempStocks = new Vector<Card>();
		for (String rankSuit : aStock.split((" +")))
		{
			if (rankSuit.trim().isEmpty())
			{
				continue;
			}

			tempStocks.add(new Card(rankSuit));

			// remove the first instance of the card from the mStock
			mStock.removeElement(tempStocks.lastElement());
		}

		// move all the card from mStock to mDealt
		mDealt.setSize(mStock.size());
		Collections.copy(mDealt, mStock);

		// move the cards from tempStocks to actual stock
		mStock.removeAllElements();
		mStock.setSize(tempStocks.size());
		Collections.copy(mStock, tempStocks);

		// clearing the stocks not needed but better to do it
		tempStocks.removeAllElements();
	}

	/**
	 * To create a new Deck object and copy the passed Deck object's member
	 * variables data into the newly created Deck object
	 * 
	 * @param aOtherDeck object of Deck class. It holds the Deck object to be
	 *                       copied. Return Value: none
	 * 
	 * @return none
	 */
	public Deck(Deck aOtherDeck)
	{
		this(aOtherDeck.toString());
	}

	/**
	 * toString funciton
	 * 
	 * @param none
	 * 
	 * @return all the card's rankSuit that are still in stock in the printed
	 *         format
	 */
	@Override
	public String toString()
	{
		return Card.getAllCardInPrintedFormat(mStock);
	}

	/**
	 * getter functions to get the stock
	 * 
	 * @param none
	 * 
	 * @return Vector of cards, holds all the cards still in stock
	 */
	public Vector<Card> getStock()
	{
		return this.mStock;
	}

	/**
	 * getter functions to get the dealt cards
	 * 
	 * @param none
	 * 
	 * @return Vector of cards, holds all the cards are already dealt
	 */
	public Vector<Card> getDealt()
	{
		return this.mDealt;
	}

	/**
	 * Getter function to get the stock in the string format
	 * 
	 * @param none
	 * 
	 * @return string, containing the rank and suit of cards which represents
	 *         stock in the deck. Each card is seperated by blank space. And the
	 *         first rankSuit pair represent the card that is going to be dealt
	 *         next.
	 */
	public String getStockString()
	{
		return Card.getAllCardInPrintedFormat(mStock);
	}

	/**
	 * returns true if m_stock is empty
	 * 
	 * @param none
	 * 
	 * @return true if m_stock is empty
	 */
	public boolean isStockEmpty()
	{
		return mStock.isEmpty();
	}

	/**
	 * to return a card from m_stock and move it from m_stock to m_dealt
	 * 
	 * @param none
	 * 
	 * @return a Card that was in the end of m_stock, returns by value
	 * 
	 * @throws EmptyStockException
	 */
	public Card dealCard() throws EmptyStockException
	{
		if (mStock.isEmpty())
		{
			throw new EmptyStockException("Can not deal, stock is empty");
		}

		mDealt.add(mStock.firstElement());
		return mStock.remove(0);
	}

	/**
	 * To randomly shuffle the order of cards in m_stock
	 * 
	 * @param none
	 * 
	 * @return true to indicate successful shuffel
	 */
	public boolean shuffel()
	{
		// moving all the card in m_dealt to m_stock
		consodilateDeck();

		Collections.shuffle(mStock, new Random(1));
		return true;
	}

	/**
	 * To move all the card in m_dealt to m_stock
	 * 
	 * @param none
	 * 
	 * @return true to indicate successful deck consodilatiion
	 */
	public boolean consodilateDeck()
	{

		// moving all the card in m_dealt to m_stock
		mStock.addAll(mDealt);
		mDealt.clear();

		return true;
	}
}
