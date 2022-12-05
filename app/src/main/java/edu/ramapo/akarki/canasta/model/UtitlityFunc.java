package edu.ramapo.akarki.canasta.model;

import java.io.InputStream;
import java.util.Scanner;

public class UtitlityFunc {
	private static Scanner cin = new Scanner(System.in);

	/**
	 * Constructor
	 * 
	 
	 * 
	 
	 */
	private UtitlityFunc()
	{

	}

	/**
	 * validate that rhe passed string is a number that is between lowerLimit
	 * and upperLimit inclusively. if yes return the integer else -1
	 * 
	 * @param aNumToValidate,       a string containing the number that is to be
	 *                                  validated
	 * @param aInclusiveLowerBound, Integer integer. lower bound that the passed
	 *                                  number can be. It includes itself.
	 * @param aInclusiveUpperBound, Integer integer. upper bound that the passed
	 *                                  number can be. It includes itself
	 * 
	 * @return interger. if the passed number is valid then it returns the
	 *         number as a interger. else returns -1
	 */
	public static Integer validateNumber(String aNumToValidate,
			Integer aInclusiveLowerBound, Integer aInclusiveUpperBound)
	{
		// checking for null input or empty string
		if (aNumToValidate == null || aNumToValidate.equals(""))
		{
			return -1;
		}

		int numToValidate;
		// checking if the number has only digit
		try
		{
			numToValidate = Integer.parseInt(aNumToValidate.trim());
		}
		catch (NumberFormatException e)
		{
			return -1;
		}

		// checking if the number is out of range or not
		if (numToValidate >= aInclusiveLowerBound
				&& numToValidate <= aInclusiveUpperBound)
		{
			return numToValidate;
		}

		return -1;
	}

	/**
	 * Gets user input
	 * 
	 * @return string, user input
	 */
	static public String getUserInput()
	{
		String userInput = cin.nextLine();

		return userInput;
	}
}
