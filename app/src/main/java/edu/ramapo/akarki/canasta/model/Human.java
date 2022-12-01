package edu.ramapo.akarki.canasta.model;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;

public class Human extends Player {
	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public Human()
	{
		super();
	}

	/**
	 * construct a Human object and to populate its member variables mHumanHand
	 * is populated using the passed parameter.
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
	public Human(Integer aTotalScore, String aHandCards, String aMeldCards)
			throws ImproperMeldException
	{
		super(aTotalScore, aHandCards, aMeldCards);
	}

	/**
	 * To create a new Human object and copy the passed Human object's member
	 * variables data into the newly created Human object
	 * 
	 * @param aOtherHuman, a object of Human class passed. It holds a Human
	 *                         object to be copied.
	 * 
	 * @return none
	 */
	public Human(final Human aOther)
	{
		super(aOther);
	}
}
