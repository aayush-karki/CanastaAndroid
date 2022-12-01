package edu.ramapo.akarki.canasta.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import org.junit.Test;

public class FileAccessTest {
	FileAccess mTestFileAcess = new FileAccess();

	/**
	 * Unit test the FileAccess's createFile() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testCreateFile()
	{
		assertTrue(mTestFileAcess.createFile("b"));
		assertFalse(mTestFileAcess.createFile("b"));

	}

	/**
	 * Unit test the FileAccess's getAPathFromStr() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testOpenFile()
	{
		assertTrue(mTestFileAcess.openFile("test"));
		assertFalse(mTestFileAcess.openFile("test1"));
	}

	/**
	 * Unit test the FileAccess's readAllText() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testReadAllText()
	{
		mTestFileAcess.openFile("test");

		Vector<String> expectedRead = new Vector<String>();
		expectedRead.add("hello");
		expectedRead.add("a");
		expectedRead.add("b");
		expectedRead.add("c");
		expectedRead.add("1");

		assertEquals(expectedRead, mTestFileAcess.readAllText());
	}

	/**
	 * Unit test the FileAccess's writeAllText() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testWriteAllText()
	{
		mTestFileAcess.openFile("a");

		Vector<String> expectedRead = new Vector<String>();
		expectedRead.add("hello");
		expectedRead.add("a");
		expectedRead.add("b");
		expectedRead.add("c");
		expectedRead.add("1");
		mTestFileAcess.writeAllText(expectedRead);
		assertEquals(expectedRead, mTestFileAcess.readAllText());
	}

	/**
	 * Unit test the FileAccess's getAPathFromStr() function
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	@Test
	public void testGetAPathFromStr()
	{
		// true when a.txt is passed
		Path expectedPath = Paths.get(
				"C:/data/OneDrive - RCNJ/data/Programming/java/canasta/data/a.txt");
		Path returnedPath = mTestFileAcess.getAPathFromStr("a.txt");
		assertEquals(expectedPath.toString(), returnedPath.toString());

		// true when a is passed
		returnedPath = mTestFileAcess.getAPathFromStr("a");
		assertEquals(expectedPath.toString(), returnedPath.toString());

		// when a.ez is passed
		// true when a is passed
		returnedPath = mTestFileAcess.getAPathFromStr("a.ez");
		assertEquals(expectedPath.toString(), returnedPath.toString());
	}
}
