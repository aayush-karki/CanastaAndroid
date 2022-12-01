package edu.ramapo.akarki.canasta.exceptions;

public class EmptyStockException extends Exception {
	/**
	 * default constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public EmptyStockException(String str)
	{
		// calling the constructor of parent Exception
		super(str);
	}
}
