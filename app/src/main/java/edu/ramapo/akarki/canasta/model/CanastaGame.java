package edu.ramapo.akarki.canasta.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import edu.ramapo.akarki.canasta.exceptions.ImproperMeldException;
import edu.ramapo.akarki.canasta.model.Round.ENUM_PlayerTurn;

public class CanastaGame {
    // holds the actual game logics and component
    private Round mRound;

    // holds boolean value to quit the game
    private boolean mExit;

    // holds boolean value show main menu or not
    private boolean mShowMainMenu;

    // holds the file acess object that allows to open from file
    private FileAccess mFileObj;

    private final Pair<Boolean, Integer> mErrorCode = new Pair<Boolean, Integer>(
            false, 5);

    // default contructor

    /**
     * To construct a Round CanastaGame and to populate its member variables.
     */
    public CanastaGame() {
        mExit = false;
        mShowMainMenu = true;
        mFileObj = new FileAccess();
        mRound = new Round();
    }

    /**
     * To create a new CanastaGame object and copy the passed CanastaGame
     * object's member variables data into the newly created CanastaGame object.
     *
     * @param aOther, a constant object of CanastaGame class passed by
     *                reference. It holds the CanastaGame object to be
     *                copied.
     */
    public CanastaGame(final CanastaGame aOther) {
        mExit = aOther.mExit;
        mShowMainMenu = aOther.mShowMainMenu;
        mFileObj = new FileAccess(aOther.mFileObj);
        mRound = new Round(aOther.mRound);
    }

    /**
     * runs the game. It contains the main loop for the game
     */
    public void runGame() {
        // starting the game
        // this is going to run till player eixts
        Pair<Boolean, Integer> playerMainMenuChoice;

        while (!mExit) {
            clearScreen();
            printGameTitle();

            // checking to see if we need to show main menu or not
            if (mShowMainMenu) {
                // printing any messages
                for (String currMessage : Message.getMessages()) {
                    System.out.println(currMessage);
                }

                // clearing the messages
                Message.clearLatestMessages();

                // executing the logic for main menu and checking
                // if quit was pressed
                playerMainMenuChoice = mainMenuController();

                // do not execute the rest of the loop if the player input is
                if (Boolean.FALSE.equals(playerMainMenuChoice.getFirst())) {
                    continue;
                }

                // executing the menu logic
                // if it is false then either player pressed quit or
                // player input was invalid either way we turn on the
                // main menu
                if (!mainMenuLogic(playerMainMenuChoice.getSecond())) {
                    mShowMainMenu = true;
                    continue;
                }
            }

            // at this point game is seted up whether by loading a saved
            // file or by creating a new game. So continue playing the
            // game

            // continue to play the game
            Pair<Boolean, Boolean> contRoundReturnVal;
            contRoundReturnVal = mRound.continueRound();

            // checking if exits to main menu was pressed
            mShowMainMenu = contRoundReturnVal.getFirst();

            // if save game was pressed
            if (Boolean.TRUE.equals(contRoundReturnVal.getSecond())) {
                saveToFile();
            }

            // now we know that player took the turn
            // so player can either
            // 1) pick up the discard pile if they can use the top
            // card in the discard pile to make a meld or add
            // to exiting meld
            // 2) draw a card from the stock pile
        }

        clearScreen();
        printGameTitle();

        System.out.println("Thank you for Playing Canasta!!");
    }

    /**
     * Contains the controller for main menu
     *
     * @return pair of < boolean value, integer >. first value hold if the
     * player input was valid or not. second value hold the player input
     * if valid else it holds 5 a garbage value
     */
    private Pair<Boolean, Integer> mainMenuController() {
        int userInputInt;

        // main menu have 3 options:
        // 1) start a new game,
        // 2) load a game
        // 3) quit

        System.out.println("Main Menu:");
        System.out.println("\t1) Start a new game");
        System.out.println("\t2) Load a game");
        System.out.println("\t3) Quit program");
        System.out.println();

        System.out.print("Enter a corresponding number: ");

        // used to get the userInput
        String userInputStr = UtitlityFunc.getUserInput();
        userInputInt = UtitlityFunc.validateNumber(userInputStr, 1, 3);

        // validaing the userInput
        // a valid user input is 1, or 2, or 3

        if (userInputInt != -1) {
            return new Pair<Boolean, Integer>(true, userInputInt);
        }

        Message.addMessage("Invalid Input!!");
        return mErrorCode;
    }

    /**
     * Contains the logic for main menu
     *
     * @param aUserChoice, interger. Holds the user choice for the menu
     * @return boolean value. false if the user quit the game or user input an
     * invalid filename; else ture.
     */
    private boolean mainMenuLogic(Integer aUserChoice) {
        // 3) quit
        if (aUserChoice == 3) {
            mExit = true;
            return false;
        }

        printGameTitle();

        System.out.print("Enter the file name: ");

        // used to get the userInput
        String userFileName = UtitlityFunc.getUserInput();

        // 1) start a new game,
        if (aUserChoice == 1) {
            // creating a file
            if (!mFileObj.createFile(userFileName)) {
                // creating the file failed
                mShowMainMenu = true;
                return false;
            }

            // creating a new round
            mRound = new Round();
            mRound.startNewRound();

            // opening of the a file
            if (!saveToFile()) {
                // file failed to save failed
                mShowMainMenu = true;
                Message.addMessage("Failed to save to file");
                return false;
            }
        }
        // 2) load a game
        else {

            // opening and loading the a file
            if (!mFileObj.openFile(userFileName) || !loadFormFile()) {
                // opening or loading of the file failed
                mShowMainMenu = true;
                Message.addMessage("Failed to load game from file");
                return false;
            }
        }

        mShowMainMenu = false;
        return true;
    }

    /**
     * Saves the file to the file
     *
     * @return boolean value. false if the save was unsuccessfull; else true
     */
    private boolean saveToFile() {
        Vector<String> gameData = new Vector<String>(12);
        // saving the current round
        gameData.add("Round: " + mRound.getCurrRoundNum().toString());

        // saving the computer information
        Player compPlayer = mRound.getComputerPlayer();
        gameData.add("Computer:");
        gameData.add("Score: " + compPlayer.getTotalPoint());
        gameData.add("Hand: " + compPlayer.getActualHandString());
        gameData.add("Melds: " + compPlayer.getMeldsString());

        // saving the human information
        Player humanPlayer = mRound.getHumanPlayer();
        gameData.add("Human:");
        gameData.add("Score: " + humanPlayer.getTotalPoint());
        gameData.add("Hand: " + humanPlayer.getActualHandString());
        gameData.add("Melds: " + humanPlayer.getMeldsString());

        // saving the stock information
        gameData.add("Stock: " + mRound.getStockString());
        gameData.add("Discard Pile: " + mRound.getDiscardedPile());

        StringBuilder nextPlayer = new StringBuilder("Next Player: ");

        switch (mRound.getPlayerTurn()) {
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

        if (!mFileObj.writeAllText(gameData)) {
            return false;
        }

        Message.addMessage("Game successfully saved!");
        return true;
    }

    /**
     * loads the game from file
     *
     * @return boolean value. false if the load was unsuccessfull; else true
     */
    private boolean loadFormFile() {
        String errorMessage = "Error While loading file";

        Vector<String> dataLineList = mFileObj.readAllText();

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
        ENUM_PlayerTurn playerTurn = ENUM_PlayerTurn.TURN_UNINITAIZLIZED;
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
                String nxtPlayer = savedData;

                if (computerStr.equals(nxtPlayer)) {
                    playerTurn = ENUM_PlayerTurn.TURN_COMPUTER;
                } else if (humanStr.equals(nxtPlayer)) {
                    playerTurn = ENUM_PlayerTurn.TURN_HUMAN;
                } else if ("Uninitialized".equals(nxtPlayer)) {
                    playerTurn = ENUM_PlayerTurn.TURN_UNINITAIZLIZED;
                } else {
                    Message.addMessage("Error While loading file");
                    return false;
                }
            }
        }

        // initializing the game
        try {
            mRound = new Round(currRoundNum, playerTurn, compTotalScore,
                    compActualHand, compMelds, humanTotalScore, humanActualHand,
                    humanMelds, stockCards, discardPile);
        } catch (ImproperMeldException e) {
            Message.addMessage(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Clears the terminal screen
     */
    private void clearScreen() {
        mRound.clearScreen();
    }

    /**
     * To print out the game title. It is goning to be the top most thing in
     * game frame
     */
    private void printGameTitle() {
        mRound.printGameTitle();
    }
}
