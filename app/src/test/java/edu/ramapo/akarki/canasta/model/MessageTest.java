package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {
	@Before
	public void resetMessage()
	{
		Message.clearLatestMessages();
		Message.clearLog();
	}

	/**
	 * Unit test the Message getMessages()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testgetMessage()
	{
		Vector<String> expectedMessage = new Vector<String>();
		assertEquals(expectedMessage, Message.getMessages());

		Message.addMessage("first");
		expectedMessage.add(new String("first"));
		assertEquals(expectedMessage, Message.getMessages());
	}

	/**
	 * Unit test the Message getLog()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetLog()
	{
		Vector<String> expectedLog = new Vector<String>();
		assertEquals(expectedLog, Message.getLog());

		// checking the messages and log after the messages are cleared
		Message.addMessage("first");
		Message.clearLatestMessages();
		expectedLog.add(new String("first"));

		assertEquals(expectedLog, Message.getLog());
		assertEquals(new Vector<String>(), Message.getMessages());
	}

	/**
	 * Unit test the Message addMessage()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testAddMessage()
	{
		Vector<String> expectedMessage = new Vector<String>();
		Message.addMessage("first");
		expectedMessage.add(new String("first"));
		assertEquals(expectedMessage, Message.getMessages());
	}

	/**
	 * Unit test the Message clearMessage()
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testClearLatestMessages()
	{
		Vector<String> expectedLog = new Vector<String>();

		// checking the messages and log after the messages are cleared
		Message.addMessage("first");
		Message.clearLatestMessages();
		expectedLog.add(new String("first"));

		assertEquals(expectedLog, Message.getLog());
		assertEquals(new Vector<String>(), Message.getMessages());
	}
}
