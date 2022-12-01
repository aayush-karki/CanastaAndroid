package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;

import edu.ramapo.akarki.canasta.exceptions.EmptyStockException;

import org.junit.Test;

public class DeckTest {
	private Deck mdeck = new Deck();
	private Deck mDeckWithParam = new Deck("AS AD    J1 J2");
	private Deck mDeckEmptyStock = new Deck("");
	private final String improperException = HandTest.improperException;

	/**
	 * Unit test the Deck Constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDeckConstructor()
	{
		assertEquals(108, mdeck.getStock().size());
		assertEquals(0, mdeck.getDealt().size());
	}

	/**
	 * Unit test the Deck Constructor with parameters
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDeckConstructorWithParam()
	{
		assertEquals(4, mDeckWithParam.getStock().size());
		assertEquals(104, mDeckWithParam.getDealt().size());
		assertEquals("AS AD J1 J2", mDeckWithParam.toString());
	}

	/**
	 * Unit test the Deck copy constructor Constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDeckCopyConstructor()
	{
		// test with no parameters
		Deck copyDeckNoParam = new Deck(mdeck);
		assertEquals(108, copyDeckNoParam.getStock().size());
		assertEquals(0, copyDeckNoParam.getDealt().size());

		try
		{
			// dealing a card from the copy and checking that the dealt card is
			// still in the orginal deck's stock
			assertEquals(copyDeckNoParam.dealCard(),
					mdeck.getStock().firstElement());
		}
		catch (EmptyStockException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}

		Deck mDeckWithParam = new Deck("AS AD    J1 J2");
		Deck copyDeckWithParam = new Deck(mDeckWithParam);

		try
		{
			// dealing a card from the copy and checking that the dealt card is
			// still in the orginal deck's stock
			assertEquals(copyDeckWithParam.dealCard(),
					mDeckWithParam.getStock().firstElement());
		}
		catch (EmptyStockException e)
		{
			assertEquals("Exception was thrown!!", improperException);
		}
	}

	/**
	 * Unit test the Deck toString()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testToString()
	{
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				mdeck.toString());
	}

	/**
	 * Unit test the Deck getStock()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetStock()
	{
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				Card.getAllCardInPrintedFormat(mdeck.getStock()));
		assertEquals("AS AD J1 J2",
				Card.getAllCardInPrintedFormat(mDeckWithParam.getStock()));
		assertEquals("",
				Card.getAllCardInPrintedFormat(mDeckEmptyStock.getStock()));
	}

	/**
	 * Unit test the Deck getDealt()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetDealt()
	{
		assertEquals("", Card.getAllCardInPrintedFormat(mdeck.getDealt()));
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				Card.getAllCardInPrintedFormat(mDeckWithParam.getDealt()));
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				Card.getAllCardInPrintedFormat(mDeckEmptyStock.getDealt()));
	}

	/**
	 * Unit test the Deck getStockString()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetStockString()
	{
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				mdeck.getStockString());
		assertEquals("AS AD J1 J2", mDeckWithParam.getStockString());
		assertEquals("", mDeckEmptyStock.getStockString());
	}

	/**
	 * Unit test the Deck isStockEmpty()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testIsStockEmpty()
	{
		assertEquals(false, mdeck.isStockEmpty());
		assertEquals(false, mDeckWithParam.isStockEmpty());
		assertEquals(true, mDeckEmptyStock.isStockEmpty());
	}

	/**
	 * Unit test the Deck dealCard() exception
	 * 
	 * @param none
	 * 
	 * @return none
	 * 
	 * @throws EmptyStockException
	 */
	@Test(expected = EmptyStockException.class)
	public void testDealCardException() throws EmptyStockException
	{
		try
		{
			mDeckEmptyStock.dealCard();
		}
		catch (EmptyStockException e)
		{
			throw e;
		}
	}

	/**
	 * Unit test the Deck dealCard()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testDealCard()
	{
		try
		{
			assertEquals(new Card("AS"), mDeckWithParam.dealCard());
			assertEquals(new Card("AD"), mDeckWithParam.dealCard());
			assertEquals(new Card("J1"), mDeckWithParam.dealCard());
			assertEquals(new Card("J2"), mDeckWithParam.dealCard());

		}
		catch (EmptyStockException e)
		{
			assertEquals("", mDeckWithParam.toString());
		}
	}

	/**
	 * Unit test the Deck shuffle()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testShuffel()
	{

	}

	/**
	 * Unit test the Deck consodilateDeck()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testConsodilateDeck()
	{
		mdeck.consodilateDeck();
		mDeckWithParam.consodilateDeck();
		mDeckEmptyStock.consodilateDeck();

		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				mdeck.getStockString());
		assertEquals(
				"AS AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				mDeckWithParam.getStockString());
		assertEquals(
				"2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2 2S 3S 4S 5S 6S 7S 8S 9S XS JS QS KS AS 2C 3C 4C 5C 6C 7C 8C 9C XC JC QC KC AC 2H 3H 4H 5H 6H 7H 8H 9H XH JH QH KH AH 2D 3D 4D 5D 6D 7D 8D 9D XD JD QD KD AD J1 J2",
				mDeckEmptyStock.getStockString());
	}
}
