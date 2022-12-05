package edu.ramapo.akarki.canasta.model;

import java.util.Vector;

public class Message {
	// mMessages holds the messages that player might need
	// as a result of their action
	private static Vector<String> mMessages = new Vector<String>();

	// mLog holds all the message that are displayed from
	// the start of game
	private static Vector<String> mLog = new Vector<String>();

	/**
	 * defalut constructor
	 * 
	 
	 * 
	 
	 */
	private Message()
	{}

	/**
	 * getter funciton to get the messages
	 * 
	 
	 * 
	 * @return Vector of String that returns the latest messages
	 */
	public static Vector<String> getMessages()
	{
		return mMessages;
	}

	/**
	 * getter funciton to get all the logged messages
	 * 
	 
	 * 
	 * @return Vector of String that returns all the logged messages
	 */
	public static Vector<String> getLog()
	{
		return mLog;
	}

	/**
	 * To adds the passed message to the back of the message list
	 * 
	 * @param aMessageToAdd a string. It holds message that is to be the message
	 *                          list. Return Value: true to indicate a success
	 * 
	 * @return true to indicate a success
	 */
	public static boolean addMessage(String aMessageToAdd)
	{
		mMessages.add(aMessageToAdd);

		return true;
	}

	/**
	 * To clears the latest message list and add it to logs
	 * 
	 
	 * 
	 * @return true to indicate a success
	 */
	public static boolean clearLatestMessages()
	{
		for (String message : mMessages)
		{
			mLog.add(message);
		}

		mMessages.clear();

		return true;
	}

	/**
	 * To clears the log list
	 * 
	 
	 * 
	 * @return true to indicate a success
	 */
	public static boolean clearLog()
	{
		mLog.clear();

		return true;
	}

	/**
	 * To print out all the latest message in the message vector.
	 * 
	 
	 * 
	 
	 */
	public void printLatestMessages()
	{
		if (!mMessages.isEmpty())
		{
			for (String message : mMessages)
			{
				System.out.println(message);
			}
		}
	}

	/**
	 * To print out all the log in the message vector.
	 * 
	 
	 * 
	 
	 */
	public void printLogMessages()
	{
		if (!mMessages.isEmpty())
		{
			for (String logMessage : mLog)
			{
				System.out.println(logMessage);
			}
		}
	}
}
