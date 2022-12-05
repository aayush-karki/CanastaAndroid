package edu.ramapo.akarki.canasta.controller;

import android.os.Environment;
import android.util.Log;

import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import edu.ramapo.akarki.canasta.R;
import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;
import edu.ramapo.akarki.canasta.model.CanastaGame;
import edu.ramapo.akarki.canasta.model.FileAccess;
import edu.ramapo.akarki.canasta.model.Message;
import edu.ramapo.akarki.canasta.model.Pair;
import edu.ramapo.akarki.canasta.model.Player;
import edu.ramapo.akarki.canasta.model.Round;
import edu.ramapo.akarki.canasta.model.UtitlityFunc;

public class RoundController {
    Round mRoundModel;

    // holds the file acess object that allows to open from file
    String mAbsFilePath;

    private final Pair<Boolean, Integer> mErrorCode = new Pair<Boolean, Integer>(
            false, 5);

    /**
     * default constructor
     */
    public RoundController() {
        mRoundModel = new Round();
    }

    /**
     * Constructor when filepath is passed
     *
     * @param aAbsFilePath
     */
    public RoundController(boolean aNewFile, String aAbsFilePath) throws Exception {
        mAbsFilePath = aAbsFilePath;

        if (!aNewFile) {
            // opening and loading the a file
            loadFormFile();
        } else {
            // creating a new round
            mRoundModel = new Round();
            mRoundModel.startNewRound();

            // opening of the a file
            saveToFile();
        }

        mAbsFilePath = aAbsFilePath;
        Log.i("CanastaRoundInfo", "Round: " +  mRoundModel.getCurrRoundNum());
        Log.i("CanastaRoundInfo", "Stock: " +  mRoundModel.getStockString());
        Log.i("CanastaRoundInfo", "discard Pile: " +  mRoundModel.getDiscardedPile().toString());

        Log.i("CanastaRoundInfo", "Computer: " +  mRoundModel.getComputerPlayer().toString());
        Log.i("CanastaRoundInfo", "Computer point: " +  mRoundModel.getComputerPlayer().getTotalPoint());
        Log.i("CanastaRoundInfo", "Human: " +  mRoundModel.getHumanPlayer().toString());
        Log.i("CanastaRoundInfo", "human point: " +  mRoundModel.getHumanPlayer().getTotalPoint());
    }

    /**
     * To create a new RoundController object and copy the passed RoundController
     * object's member variables data into the newly created RoundController object.
     *
     * @param aOther, a constant object of RoundController class passed by
     *                reference. It holds the RoundController object to be
     *                copied.
     */
    public RoundController(final RoundController aOther) {
        mRoundModel = new Round(aOther.mRoundModel);
    }

    /**
     * Saves the file to the file
     *
     * @return boolean value. false if the save was unsuccessfull; else true
     */
    public boolean saveToFile() {
        Vector<String> gameData = new Vector<String>(12);
        // saving the current round
        gameData.add("Round: " + mRoundModel.getCurrRoundNum().toString());

        // saving the computer information
        Player compPlayer = mRoundModel.getComputerPlayer();
        gameData.add("Computer:");
        gameData.add("Score: " + compPlayer.getTotalPoint());
        gameData.add("Hand: " + compPlayer.getActualHandString());
        gameData.add("Melds: " + compPlayer.getMeldsString());

        // saving the human information
        Player humanPlayer = mRoundModel.getHumanPlayer();
        gameData.add("Human:");
        gameData.add("Score: " + humanPlayer.getTotalPoint());
        gameData.add("Hand: " + humanPlayer.getActualHandString());
        gameData.add("Melds: " + humanPlayer.getMeldsString());

        // saving the stock information
        gameData.add("Stock: " + mRoundModel.getStockString());
        gameData.add("Discard Pile: " + mRoundModel.getDiscardedPile());

        StringBuilder nextPlayer = new StringBuilder("Next Player: ");

        switch (mRoundModel.getPlayerTurn()) {
            case TURN_COMPUTER: {
                nextPlayer.append("Computer");
                break;
            }
            case TURN_HUMAN: {
                nextPlayer.append("Human");
                break;
            }
            default: {
                // this should never happen
                nextPlayer.append("Uninitialized");
                break;
            }
        }

        gameData.add(nextPlayer.toString());

        if (!writeAllText(gameData)) {
            throw new RuntimeException("Could not save to a file");
        }

        Message.addMessage("Game successfully saved!");
        return true;
    }

    /**
     * loads the game from file
     *
     * @return boolean value. false if the load was unsuccessfull; else true
     */
    public boolean loadFormFile() throws ImproperMeldException {
        String errorMessage = "Error While loading file";

        Vector<String> dataLineList = readFile();

        if (dataLineList.isEmpty()) {
            Message.addMessage(errorMessage);
            return false;
        }

        // the 10 data tha we want to get in order are:
        // 1) round number
        // 2) computer score
        // 3) computer hand
        // 4) computer meld
        // 5) human score
        // 6) human hand
        // 7) human meld
        // 8) stock
        // 9) discard pile
        // 10) next player

        // get the next line untill it get non empty line
        Integer currRoundNum = 0;
        Round.ENUM_PlayerTurn playerTurn = Round.ENUM_PlayerTurn.TURN_UNINITAIZLIZED;
        Integer compTotalScore = 0;
        String compActualHand = "";
        String compMelds = "";
        Integer humanTotalScore = 0;
        String humanActualHand = "";
        String humanMelds = "";
        String stockCards = "";
        String discardPile = "";

        String currPlayerProces = "";

        String humanStr = "Human";
        String computerStr = "Computer";

        for (String string : dataLineList) {
            String currString = string.trim();
            if (currString.isEmpty()) {
                continue;
            }

            // find the position of :
            int colonPos = currString.indexOf(":");

            String descriptor = currString.substring(0, colonPos);
            String savedData = colonPos != currString.length()
                    ? currString.substring(colonPos + 1).trim()
                    : "";

            if ("Round".equals(descriptor)) {
                currRoundNum = UtitlityFunc.validateNumber(savedData, 0,
                        100000);
            } else if (computerStr.equals(descriptor)) {
                currPlayerProces = computerStr;
            } else if ("Score".equals(descriptor) || "Points".equals(descriptor)) {
                if (computerStr.equals(currPlayerProces)) {
                    compTotalScore = UtitlityFunc.validateNumber(savedData, 0,
                            100000);
                } else if (humanStr.equals(currPlayerProces)) {
                    humanTotalScore = UtitlityFunc.validateNumber(savedData, 0,
                            100000);
                } else {
                    Message.addMessage(errorMessage);
                    return false;
                }
            } else if ("Hand".equals(descriptor)) {
                if (computerStr.equals(currPlayerProces)) {
                    compActualHand = savedData;
                } else if (humanStr.equals(currPlayerProces)) {
                    humanActualHand = savedData;
                } else {
                    Message.addMessage(errorMessage);
                    return false;
                }
            } else if ("Melds".equals(descriptor)) {
                if (computerStr.equals(currPlayerProces)) {
                    compMelds = savedData;
                } else if (humanStr.equals(currPlayerProces)) {
                    humanMelds = savedData;
                } else {
                    Message.addMessage(errorMessage);
                    return false;
                }
            } else if (humanStr.equals(descriptor)) {
                currPlayerProces = humanStr;
            } else if ("Stock".equals(descriptor)) {
                stockCards = savedData;
            } else if ("Discard Pile".equals(descriptor)) {
                discardPile = savedData;
            } else if ("Next Player".equals(descriptor)) {
                if (computerStr.equals(savedData)) {
                    playerTurn = Round.ENUM_PlayerTurn.TURN_COMPUTER;
                } else if (humanStr.equals(savedData)) {
                    playerTurn = Round.ENUM_PlayerTurn.TURN_HUMAN;
                } else if ("Uninitialized".equals(savedData)) {
                    playerTurn = Round.ENUM_PlayerTurn.TURN_UNINITAIZLIZED;
                } else {
                    Message.addMessage("Error While loading file");
                    return false;
                }
            }
        }

        // initializing the game
        mRoundModel = new Round(currRoundNum, playerTurn, compTotalScore,
                compActualHand, compMelds, humanTotalScore, humanActualHand,
                humanMelds, stockCards, discardPile);


        return true;
    }

    /**
     * Reads all the text from the passed file
     *
     * @return
     */
    private Vector<String> readFile() {

        Vector<String> allText = new Vector<String>(15);

        try (BufferedReader reader = new BufferedReader(new FileReader(mAbsFilePath))) {
            String extractedLine;
            while ((extractedLine = reader.readLine()) != null) {
                allText.add(extractedLine);
            }
            return allText;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return new Vector<String>();
        }
    }

    /**
     * Wrties a all the text to the file
     *
     * @param aLines, const a string passed by value, it holds the string that is
     *                to be added to the file
     * @return true on successfull insertion; else false
     */
    public boolean writeAllText(final Vector<String> aLines) {

        StringBuilder gameSave = new StringBuilder();
        for (String string : aLines) {
            gameSave.append(string);
            gameSave.append("\n");
        }

        File file = new File(mAbsFilePath);

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(gameSave.toString());

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("CanastaException", "File write failed: " + e.toString());
            return false;
        }
        return true;

        /*
        // creating a new file
        try {

            File file = new File(mAbsFilePath);
            file.createNewFile();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }

        // writing to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mAbsFilePath))) {
            for (String string : aLines) {
                writer.write(string);
                writer.write("\n");
            }
            return true;
        } catch (Exception e) {
            Log.e("CanastaException", "File write failed: " + e.toString());
            return false;
        }*/
    }


}
