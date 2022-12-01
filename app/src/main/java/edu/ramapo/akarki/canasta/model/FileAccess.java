package edu.ramapo.akarki.canasta.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class FileAccess {

	// holds the path to file path
	private Path mFilePath;

	// file
	private File mFile;

	// holds the path to data directory
	private final Path mDataFolderPath;

	/**
	 * constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public FileAccess()
	{
		Path currRelativePath = Paths.get("");

		// creating a data folding if it does not exist
		File dataDir = new File(
				currRelativePath.toAbsolutePath().toString() + "/data");
		if (!dataDir.exists())
		{
			dataDir.mkdirs();
		}

		// storing the data
		mDataFolderPath = dataDir.toPath();

		mFile = null;
	}

	/**
	 * copy constructor
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public FileAccess(final FileAccess aOther)
	{
		mFilePath = aOther.mFilePath;
		mDataFolderPath = aOther.mDataFolderPath;
	}

	/**
	 * To create a empty file with the passed filename and open it in read and
	 * write mode.
	 * 
	 * @param aFullfileName string, holds the filename of the file that that is
	 *                          to be created and is in "(cwd)/data/" directory.
	 * 
	 * @return true on successfull creation and opening; else false
	 */
	public boolean createFile(String aFullfileName)
	{
		Path passedFile = getAPathFromStr(aFullfileName);

		File newFile = new File(passedFile.toString());
		try
		{
			if (!newFile.createNewFile())
			{
				StringBuilder message = new StringBuilder("The Filename ");
				message.append(passedFile.getFileName().toString());
				message.append(" already exists");
				Message.addMessage(message.toString());
				return false;
			}

			mFile = newFile;
		}
		catch (IOException e)
		{
			// prints exception if any  
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * checks if the passed string exist or not, if yes saves it and its path
	 * 
	 * @param aFullfileName, string, holds the filename of the file that that is
	 *                           to be opened and is in "(cwd)/data/" directory.
	 * 
	 * @return true on passed file existing; else false
	 */
	public boolean openFile(String aFullfileName)
	{
		// getting a path from the passed stirng
		Path passedPath = getAPathFromStr(aFullfileName);
		String passedPathStr = passedPath.toString();
		File passedFile = new File(passedPathStr);

		// if the passed file does not exist return false
		if (!passedFile.exists())
		{
			Message.addMessage("File named "
					+ passedPath.getFileName().toString() + " does not exist!");
			return false;
		}

		mFile = passedFile;
		mFilePath = passedPath;
		return true;
	}

	/**
	 * read all the text in the file
	 * 
	 * @param none
	 * 
	 * @return if successfull Vector of String each string represent one line;
	 *         else returns empty Vector of String
	 */
	public Vector<String> readAllText()
	{
		Vector<String> allText = new Vector<String>(15);

		try (BufferedReader reader = new BufferedReader(new FileReader(mFile)))
		{
			String extractedLine;
			while ((extractedLine = reader.readLine()) != null)
			{
				allText.add(extractedLine);
			}
			return allText;
		}
		catch (Exception e)
		{
			System.out.print(e.getMessage());
			return new Vector<String>();
		}
	}

	/**
	 * Wrties a all the text to the file
	 * 
	 * @param aline, const a string passed by value, it holds the string that is
	 *                   to be added to the file
	 * 
	 * @return true on successfull insertion; else false
	 */
	public boolean writeAllText(final Vector<String> aLines)
	{

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(mFile)))
		{
			for (String string : aLines)
			{
				writer.write(string);
				writer.write("\n");
			}
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * To get a file path from the passed string
	 * 
	 * @param aFileName, string, holds the filename of the file that that is is
	 *                       in "(cwd)/data/" directory.
	 * 
	 * @return Path object with file name beign the stem of passed file name,
	 *         and parent_path beign the dataFolderPath
	 */
	protected Path getAPathFromStr(String aFilename)
	{
		Path passedPath = Paths.get(aFilename);

		// chekcing if the file is in dataFolder or not

		if (passedPath.getParent() == null || !passedPath.getParent().toString()
				.equals(mDataFolderPath.toString()))
		{
			passedPath = mDataFolderPath.resolve(passedPath.getFileName());
		}

		// chekcing if the file's extension is .txt or not
		String fileName = passedPath.getFileName().toString();

		int lastIndexOf = fileName.lastIndexOf(".");
		String extention = lastIndexOf != -1 ? fileName.substring(lastIndexOf)
				: "";

		if (!extention.equals(".txt"))
		{
			fileName = lastIndexOf == -1 ? fileName + ".txt"
					: fileName.substring(0, lastIndexOf) + ".txt";
			passedPath = mDataFolderPath.resolve(fileName);
		}

		return passedPath;
	}
}
