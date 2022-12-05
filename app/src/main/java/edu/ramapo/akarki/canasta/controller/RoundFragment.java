package edu.ramapo.akarki.canasta.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.constraintlayout.widget.Group;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import edu.ramapo.akarki.canasta.R;
import edu.ramapo.akarki.canasta.exceptions.EmptyStockException;
import edu.ramapo.akarki.canasta.model.Card;
import edu.ramapo.akarki.canasta.model.Message;
import edu.ramapo.akarki.canasta.model.Pair;
import edu.ramapo.akarki.canasta.model.Player;
import edu.ramapo.akarki.canasta.model.Round;
import edu.ramapo.akarki.canasta.model.UtitlityFunc;

public class RoundFragment extends Fragment {

    // holds the list of computer hand
    private final Vector<Card> mCompHandCardList = new Vector<Card>();
    // holds the list of computer melds
    private final Vector<Card> mCompMelsCardList = new Vector<Card>();
    // holds the list of computer meld detail
    private final Vector<Card> mCompMelDetailCardList = new Vector<Card>();

    // holds the list of discard pile
    private final Vector<Card> mdiscardPileCardList = new Vector<Card>();
    // holds the list of stock pile
    private final Vector<Card> mStockPileCardList = new Vector<Card>();

    // holds the list of human hand
    private final Vector<Card> mHumanHandCardList = new Vector<Card>();
    // holds the list of human melds
    private final Vector<Card> mHumanMelsCardList = new Vector<Card>();
    // holds the list of human meld detail
    private final Vector<Card> mHumanMelDetailCardList = new Vector<Card>();

    // for computer hand
    private RecyclerView mCompHandRecyclerView;
    private CardListAdaptor mCompHandAdapter;

    // for computer melds
    private RecyclerView mCompMeldsRecyclerView;
    private CardListAdaptor mCompMeldsAdapter;

    // for computer meld Details
    private RecyclerView mCompMeldDetailsRecyclerView;
    private CardListAdaptor mCompMeldsDetailsdapter;

    // for discard pile
    private RecyclerView mDiscardPileRecyclerView;
    private CardListAdaptor mDiscardPileAdapter;

    // for stock pile
    private RecyclerView mStockPileRecyclerView;
    private CardListAdaptor mStockPileAdapter;

    // for player hand
    private RecyclerView mHumanHandRecyclerView;
    private CardListAdaptor mHumanHandAdapter;

    // for player melds
    private RecyclerView mHumanMeldsRecyclerView;
    private CardListAdaptor mHumanMeldsAdapter;

    // for player meld Details
    private RecyclerView mHumanMeldDetailsRecyclerView;
    private CardListAdaptor mHumanMeldsDetailsdapter;

    // saving all the view

    // top button that are avialable during the human's turn
    Button mSaveBtn;
    Button mHelpBtn;
    Button mMainMenuBtn;
    Button mLogBtn;
    Button mGoOutBtn;

    // top button that are avialable during the human's turn
    Button mDiscardBtn;
    Button mTakeOutBtn;
    Button mMeldBtn;

    Button mContinueBtn;
    Button mDrawBtn;
    Button mPickupBtn;

    ImageButton mCompMeldDetailReferesh;
    ImageButton mHumanMeldDetailReferesh;

    TextView mRoundNumTxt;
    TextView mCompScoreNumTxt;
    TextView mHumanScoreNumTxt;
    TextView mCurrPlayer;

    TextView mCompMeldDetailRankTxt;
    TextView mHumanMeldDetailRankTxt;
    TextView mMessageTxt;

    Group mSaveHelpGroup;
    Group mHumanContinuRoundBtnGroup;
    Group mDrawPickupGroup;

    Group mCompMeldDetailGroup;
    Group mHumanMeldDetailGroup;

    // round obj
    RoundController mRoundController;
    boolean mIsNewGame;
    String mAbsFilePath;

    /**
     * Default constructor
     */
    public RoundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateLoadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoundFragment newInstance(boolean aIsCreateNewGame, String aAbsFilePath) {
        RoundFragment fragment = new RoundFragment();
        Bundle args = new Bundle();
        args.putBoolean("isCreateNewGame", aIsCreateNewGame);
        args.putString("absFilePath", aAbsFilePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIsNewGame = getArguments().getBoolean("isCreateNewGame");
            mAbsFilePath = getArguments().getString("absFilePath");
        }

        // creating the round controller
        try {
            mRoundController = new RoundController(mIsNewGame, mAbsFilePath);
        } catch (Exception e) {
            Log.e("CanastaException", "Error while loading the file" + e.getMessage());
            e.printStackTrace();

            // naviage to main menu
            navToMainMenu();
        }

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View roundView = inflater.inflate(R.layout.fragment_round, container, false);

        // geting all the view and saving them
        initializeView(roundView);
        return roundView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // computer hand
        mCompHandRecyclerView = view.findViewById(R.id.compHandCardRecyclerView);
        mCompHandAdapter = new CardListAdaptor(requireActivity(), mCompHandCardList,
                true, false, true, false);
        mCompHandRecyclerView.setAdapter(mCompHandAdapter);
        mCompHandRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for computer melds
        mCompMeldsRecyclerView = view.findViewById(R.id.compMeldsRecyclerView);
        mCompMeldsAdapter = new CardListAdaptor(requireActivity(), mCompMelsCardList,
                true, false, false, true);
        mCompMeldsRecyclerView.setAdapter(mCompMeldsAdapter);
        mCompMeldsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for computer meld Details
        mCompMeldDetailsRecyclerView = view.findViewById(R.id.compMeldDetailRecyclerView);
        mCompMeldsDetailsdapter = new CardListAdaptor(requireActivity(), mCompMelDetailCardList,
                true, false, false, true);
        mCompMeldDetailsRecyclerView.setAdapter(mCompMeldsDetailsdapter);
        mCompMeldDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for discard pile
        mDiscardPileRecyclerView = view.findViewById(R.id.discardPileRecyclerView);
        mDiscardPileAdapter = new CardListAdaptor(requireActivity(), mdiscardPileCardList,
                false, false, false, false);
        mDiscardPileRecyclerView.setAdapter(mDiscardPileAdapter);
        mDiscardPileRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for stock pile
        mStockPileRecyclerView = view.findViewById(R.id.stockPileRecyclerView);
        mStockPileAdapter = new CardListAdaptor(requireActivity(), mStockPileCardList,
                false, false, false, false);
        mStockPileRecyclerView.setAdapter(mStockPileAdapter);
        mStockPileRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));


        /// human hand
        mHumanHandRecyclerView = view.findViewById(R.id.humanHandCardRecyclerView);
        mHumanHandAdapter = new CardListAdaptor(requireActivity(), mHumanHandCardList,
                true, true, true, false);
        mHumanHandRecyclerView.setAdapter(mHumanHandAdapter);
        mHumanHandRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for human melds
        mHumanMeldsRecyclerView = view.findViewById(R.id.humanMeldsRecyclerView);
        mHumanMeldsAdapter = new CardListAdaptor(requireActivity(), mHumanMelsCardList,
                true, true, false, true);
        mHumanMeldsRecyclerView.setAdapter(mHumanMeldsAdapter);
        mHumanMeldsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // for human meld Details
        mHumanMeldDetailsRecyclerView = view.findViewById(R.id.humanMeldDetailRecyclerView);
        mHumanMeldsDetailsdapter = new CardListAdaptor(requireActivity(), mHumanMelDetailCardList,
                true, true, false, true);
        mHumanMeldDetailsRecyclerView.setAdapter(mHumanMeldsDetailsdapter);
        mHumanMeldDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        // setting up the buttons click listener
        setBtnClickListener();

        // updating the uis
        if (mRoundController.mRoundModel.getPlayerTurn().equals(Round.ENUM_PlayerTurn.TURN_UNINITAIZLIZED)) {
            determinePlayerOrder();
        }

        // updating the list with data
        updateAllRecyclerUI();

        setNextPlayer(mRoundController.mRoundModel.getPlayerTurn());

        // updating the texts in the screen
        updateTextInfos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * saving the view on this fragments
     */
    private void initializeView(View view) {
        mSaveBtn = view.findViewById(R.id.saveBtn);
        mHelpBtn = view.findViewById(R.id.helpBtn);
        mMainMenuBtn = view.findViewById(R.id.mainMenuBtn);
        mLogBtn = view.findViewById(R.id.logBtn);
        mGoOutBtn = view.findViewById(R.id.gooutBtn);
        mSaveHelpGroup = view.findViewById(R.id.saveHelpGroup);

        // top button that are avialable during the human's turn
        mDiscardBtn = view.findViewById(R.id.discardBtn);
        mTakeOutBtn = view.findViewById(R.id.takeOutBtn);
        mMeldBtn = view.findViewById(R.id.meldBtn);
        mHumanContinuRoundBtnGroup = view.findViewById(R.id.humanContinuRoundBtnGroup);

        mContinueBtn = view.findViewById(R.id.continueBtn);
        mDrawBtn = view.findViewById(R.id.drawBtn);
        mPickupBtn = view.findViewById(R.id.pickupBtn);

        mRoundNumTxt = view.findViewById(R.id.roundNumTxt);

        mCompMeldDetailReferesh = view.findViewById(R.id.compMeldDetailReferesh);
        mHumanMeldDetailReferesh = view.findViewById(R.id.humanMeldDetailReferesh);

        mCompScoreNumTxt = view.findViewById(R.id.compScoreNumTxt);

        mHumanScoreNumTxt = view.findViewById(R.id.humanScoreNumTxt);

        mCompMeldDetailRankTxt = view.findViewById(R.id.compMeldDetailRankTxt);
        mCompMeldDetailGroup = view.findViewById(R.id.compMeldDetailGroup);

        mHumanMeldDetailRankTxt = view.findViewById(R.id.humanMeldDetailRankTxt);
        mHumanMeldDetailGroup = view.findViewById(R.id.humanDetailGroup);

        mMessageTxt = view.findViewById(R.id.messageTxt);

        mCurrPlayer = view.findViewById(R.id.currPlayer);
        mDrawPickupGroup = view.findViewById(R.id.drawPickupGroup);
    }

    /**
     * updates the all the recycler UI
     */
    void updateAllRecyclerUI() {
        // copying computer hand
        updateComphandCard();

        // copying computer melds
        updateCompMeldsUI();

        // copying comp meldDetail
        updateCompMeldDetailUI();

        // updating  humanHand pile
        updateHumanHandCard();

        // copying human melds
        updateHumanMeldsUI();

        // copying human meldDetail
        updateHumanMeldDetailUI();

        // updating  discard pile
        updateDiscardUI();

        // updating stock pile
        updateStockPilesUI();

/*
        // holds the list of computer meld detail
        private final Vector<Card> mCompMelDetailCardList = new  Vector<Card> ();

        // holds the list of human meld detail
        private final Vector<Card> mHumanMelDetailCardList = new  Vector<Card> ();*/
    }

    /**
     * updates the text based uis
     */
    void updateTextInfos() {
        mRoundNumTxt.setText(mRoundController.mRoundModel.getCurrRoundNum().toString());

        Integer num = mRoundController.mRoundModel.getComputerPlayer().getTotalPoint();
        mCompScoreNumTxt.setText(num.toString());

        num = mRoundController.mRoundModel.getHumanPlayer().getTotalPoint();
        mHumanScoreNumTxt.setText(num.toString());
    }

    /**
     * asks the player for a h or tails and does a coin toss
     *
     * @return true to signify success
     */
    boolean determinePlayerOrder() {
        String title = "Next Player";
        String message;
        // seeing who goes first
        if (mRoundController.mRoundModel.getComputerPlayer().getTotalPoint() > mRoundController.mRoundModel.getHumanPlayer().getTotalPoint()) {
            // computer has more points so it goes first
            setNextPlayer(Round.ENUM_PlayerTurn.TURN_COMPUTER);
            createDialogMessage(title, "Computer has more points! So computer starts first");
        } else if (mRoundController.mRoundModel.getComputerPlayer().getTotalPoint() < mRoundController.mRoundModel.getHumanPlayer().getTotalPoint()) {
            // human has more points so it goes first
            setNextPlayer(Round.ENUM_PlayerTurn.TURN_HUMAN);
            createDialogMessage(title, "Human has more points! So human starts first");
        } else {
            // its a tie. Toss a coin

            // Create the object of AlertDialog Builder class
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Set dialog box title
            builder.setTitle("Tossing a coin to determin the next player");

            // Set the message shown in the dialog box
            builder.setMessage("Choose head or tails");

            // Set Cancelable false for not dismissing the dialog box if the user clicks somewhere outside the dialog box
            builder.setCancelable(false);

            // Set the positive button to dismiss the dialog box
            builder.setPositiveButton("Head", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String coinToss = mRoundController.mRoundModel.tossACoin();
                    // If user clicks on ok then dialog box is dismissed.
                    dialog.dismiss();

                    Log.i("CanastaCoin", coinToss);

                    // creating another dialog box
                    if (coinToss.equals("h")) {
                        setNextPlayer(Round.ENUM_PlayerTurn.TURN_HUMAN);
                        createDialogMessage(title, "You guessed correctly. So human starts first");
                    } else {
                        setNextPlayer(Round.ENUM_PlayerTurn.TURN_COMPUTER);
                        createDialogMessage(title, "You guessed incorrectly. So Computer starts first");
                    }
                }
            });

            builder.setNegativeButton("Tail", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String coinToss = mRoundController.mRoundModel.tossACoin();
                    // If user clicks on ok then dialog box is dismissed.
                    dialog.dismiss();

                    Log.i("CanastaCoin", coinToss);

                    // creating another dialog box
                    if (coinToss.equals("t")) {
                        setNextPlayer(Round.ENUM_PlayerTurn.TURN_HUMAN);
                        createDialogMessage(title, "You guessed correctly. So human starts first");
                    } else {
                        setNextPlayer(Round.ENUM_PlayerTurn.TURN_COMPUTER);
                        createDialogMessage(title, "You guessed incorrectly. So Computer starts first");
                    }
                }
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }

        return true;
    }

    void setNextPlayer(Round.ENUM_PlayerTurn aPlayerTurn) {
        mRoundController.mRoundModel.setPlayerTurn(aPlayerTurn);

        if (aPlayerTurn.equals(Round.ENUM_PlayerTurn.TURN_HUMAN)) {
            mCurrPlayer.setText("Curr: Human");
            buttonMask(false, false, true);

        } else {
            mCurrPlayer.setText("Curr: Computer");
            buttonMask(true, false, false);
        }

    }

    /**
     * Sets next player
     */
    void setNextPlayer() {
        setNextPlayer((mRoundController.mRoundModel.getPlayerTurn() == Round.ENUM_PlayerTurn.TURN_COMPUTER
                ? Round.ENUM_PlayerTurn.TURN_HUMAN
                : Round.ENUM_PlayerTurn.TURN_COMPUTER));
    }


    /**
     * updtates message text view by displays message log
     */
    void displayMessages() {
        // printing any messages
        StringBuilder message = new StringBuilder();
        for (String currMessage : Message.getMessages()) {
            message.append(currMessage);
            message.append("\n");
        }

        mMessageTxt.setText(message.toString());

        // clearing the messages
        Message.clearLatestMessages();

    }

    /**
     * creates a dialog box that shows log
     */
    void createLogDialog() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View logView = li.inflate(R.layout.log_dialog, null);

        TextView logText = logView.findViewById(R.id.logs);

        StringBuilder alLLogs = new StringBuilder();

        for (String entry : Message.getLog()) {
            alLLogs.append(entry);
            alLLogs.append("\n");
        }
        logText.setText(alLLogs);

        AlertDialog.Builder log = new AlertDialog.Builder(getActivity());
        log.setTitle("LOG");
        log.setView(logView);

        // Set the positive button to dismiss the dialog box
        log.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user clicks on ok then dialog box is dismissed.
                dialog.dismiss();
            }
        });

        log.show();
    }

    /**
     * Creates a dialog message
     *
     * @param aTile    title of the dialog message
     * @param aMessage message to show in the dialog box
     */
    void createDialogMessage(String aTile, String aMessage) {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set dialog box title
        builder.setTitle(aTile);

        // Set the message shown in the dialog box
        builder.setMessage(aMessage);

        // Set Cancelable false for not dismissing the dialog box if the user clicks somewhere outside the dialog box
        builder.setCancelable(false);

        // Set the positive button to dismiss the dialog box
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user clicks on ok then dialog box is dismissed.
                dialog.dismiss();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    /**
     * ends dialog for when the round ends
     */
    void endDialog() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set dialog box title
        builder.setTitle("Round Ended");

        StringBuilder mainMessage = new StringBuilder();
        String message = "";
        // checking if the player went out or not
        for (Player player : mRoundController.mRoundModel.getPlayerList()) {
            String playerName = player.getClass().getSimpleName();

            if (player.getPlayerWentOutStatus()) {
                message = playerName + " has gone out, and finished the round!\n";
                Message.addMessage(message);
                mainMessage.append(message);
            }

            // calculating the round and total points
            Integer playersEarnedPoints = player.tallyHandPoint();
            player.addToTotalPoints(playersEarnedPoints);

            // printing the round points and total point that players earned
            // in this round
            message = playerName + "'s earned "
                    + playersEarnedPoints.toString() + " this round.";
            Message.addMessage(message);
            mainMessage.append(message + "\n");

            message = playerName + "'s total earned points is " + player.getTotalPoint();
            Message.addMessage(message);
            mainMessage.append(message + "\n");
        }

        // Set the message shown in the dialog box
        builder.setMessage(mainMessage.toString());

        // Set Cancelable false for not dismissing the dialog box if the user clicks somewhere outside the dialog box
        builder.setCancelable(false);

        // Set the positive button to dismiss the dialog box
        builder.setPositiveButton("Continue Playing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // starting new round
                mRoundController.mRoundModel.startNewRound();
                mRoundController.mRoundModel.incrementRoundNumber();

                dialog.dismiss();

                String message = "New Round Started";
                Message.addMessage(message);
                createDialogMessage("Message", message);

                updateAllRecyclerUI();
                displayMessages();
            }
        });

        // Set the positive button to dismiss the dialog box
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                StringBuilder message = new StringBuilder();

                int humanScore = mRoundController.mRoundModel.getHumanPlayer().getTotalPoint();
                int computerScore = mRoundController.mRoundModel.getComputerPlayer().getTotalPoint();


                if (computerScore == humanScore) {
                    message.append("It was a tie!");
                } else {
                    message.append("Winner of the game is ");
                    message.append((computerScore > humanScore) ? "Comuputer" : "Human");
                }
                Message.addMessage(message.toString());
                createDialogMessage("Result", message.toString());
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    /**
     * checks if the round has ended or not
     */
    void hasRoundEnded() {
        boolean playerWentoutStatus = mRoundController.mRoundModel.hasPlayerWentOut();
        boolean lastCardRedThreeStatus = mRoundController.mRoundModel.haslastCardRedThree();
        boolean endCauseDisCardPile = mRoundController.mRoundModel.hasEndCauseDisCardPile();
        boolean stockPileEmpty = mRoundController.mRoundModel.getStockPile().isEmpty();
        boolean turnStartFlag = mRoundController.mRoundModel.getCurrPlayer().getTurnStartFlag();
        boolean roundEnded = mRoundController.mRoundModel.hasRoundStartFlag();


        if ((playerWentoutStatus || lastCardRedThreeStatus || endCauseDisCardPile
                || (stockPileEmpty && turnStartFlag))
                && !roundEnded) {
            // checking if the discard pile can be picked up if the stock is
            // empty
            if (!playerWentoutStatus && !lastCardRedThreeStatus
                    && stockPileEmpty) {
                boolean pickUpSuccess = false;
                if (mRoundController.mRoundModel.getCurrPlayer().canPickUpDiscardPile(mRoundController.mRoundModel.getDiscardPile())) {
                    pickUpSuccess = true;
                } else if (!pickUpSuccess
                        && mRoundController.mRoundModel.getOtherPlayer().canPickUpDiscardPile(mRoundController.mRoundModel.getDiscardPile())) {

                    // setting curr player to other
                    setNextPlayer();
                    pickUpSuccess = true;
                }

                if (pickUpSuccess) {
                    // forcing the current player to pick up the discard pile
                    mRoundController.mRoundModel.turnStartLogic(2);

                    StringBuilder message = new StringBuilder(
                            " Stock is empty!\n");
                    message.append(
                            mRoundController.mRoundModel.getPlayerTurn().equals(Round.ENUM_PlayerTurn.TURN_COMPUTER)
                                    ? "Computer"
                                    : "Human");
                    message.append(
                            " can pick up the discarded pile. So picking up the discard pile.");
                    Message.addMessage(message.toString());
                    createDialogMessage("Force DiscardPile Pickup", message.toString());

                    playComputer();
                }

                StringBuilder message = new StringBuilder("Stock is empty!\n");
                message.append(
                        "Neither Human or Computer can pick up the discarded pile. So Ending the game.");
                Message.addMessage(message.toString());
                Message.addMessage("");
            }

            // checking if a player wentout
            endDialog();

            mRoundController.mRoundModel.setRoundStartedFlag(true);
        }
    }


    /**
     * Sets up the button click listener
     */
    void setBtnClickListener() {
        mMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToMainMenu();
            }
        });

        mHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message.clearLatestMessages();
                if (mDrawPickupGroup.getVisibility() == View.VISIBLE) {
                    mRoundController.mRoundModel.turnStartLogic(1);

                } else if (mHumanContinuRoundBtnGroup.getVisibility() == View.VISIBLE) {
                    Vector<Integer> param = new Vector<Integer>();
                    param.add(1);
                    mRoundController.mRoundModel.turnContinueLogic(param);
                }
                StringBuilder helpMessage = new StringBuilder();
                for (String message : Message.getMessages()) {
                    helpMessage.append(message + "\n");
                }
                createDialogMessage("Help", helpMessage.toString());

                displayMessages();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRoundController.saveToFile();
                createDialogMessage("Save", "Save was successfull");
            }
        });

        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLogDialog();
            }
        });

        mGoOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mRoundController.mRoundModel.getHumanPlayer().canGoOut()) {
                    createDialogMessage("Status", "Human can not go out");
                } else {
                    endDialog();
                }
            }
        });

        mDiscardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vector<Integer> handCardList = mHumanHandAdapter.getSelectedPos();

                Player humanPlayer = mRoundController.mRoundModel.getHumanPlayer();

                if (handCardList.size() != 1) {
                    // concoting message to display
                    String displayMessage = "Cannot make a discard, select only one card from the hand  to add to a discard";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }
                Card cardToAdd = humanPlayer.getActualHand().get(handCardList.get(0));

                // discarding and adding the discarded card to discard pile
                mRoundController.mRoundModel.addToDiscardPile(humanPlayer.discard(handCardList.elementAt(0)));

                // concoting message to display
                StringBuilder displayMessage = new StringBuilder(humanPlayer.getClass().getSimpleName());
                displayMessage.append(" discard ");
                displayMessage.append(cardToAdd.getRankSuit());
                Message.addMessage(displayMessage.toString());


                // copying discard pile
                updateDiscardUI();
                mHumanHandAdapter.unSelectAll();
                mHumanMeldsAdapter.unSelectAll();

                updateHumanHandCard();
                updateHumanMeldsUI();
                updateHumanMeldDetailUI();

                displayMessages();

                // checking if the curr player can go out
                if (humanPlayer.canGoOut()) {
                    humanPlayer.setPlayerWentOutStatus(true);
                    mRoundController.mRoundModel.setPlayerwentOut(true);
                }

                buttonMask(true, false, false);

                // clearing the menu flag
                humanPlayer.setPlayerBeforeTurnMenuFlag(true);
                humanPlayer.setTurnStartFlag(true);


                setNextPlayer();
                hasRoundEnded();

            }
        });

        mTakeOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vector<Integer> selMeldCardPos = mHumanMeldsDetailsdapter.getSelectedPos();
                Vector<Integer> selMeldPos = mHumanMeldsAdapter.getSelectedPos();

                if (selMeldPos.isEmpty()) {
                    // concoting message to display
                    String displayMessage = "Select a meld to takeout from";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                if (selMeldCardPos.isEmpty()) {
                    // concoting message to display
                    String displayMessage = "Select a card in the detail meld to takeout from";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                Player humanPlayer = mRoundController.mRoundModel.getHumanPlayer();

                // trying to take out a wild card
                Card wildCard = humanPlayer.getHand()
                        .elementAt(selMeldPos.elementAt(0))
                        .elementAt(selMeldCardPos.elementAt(0));
                Pair<Boolean, String> result = humanPlayer.takeOutCardFromMeld(
                        selMeldCardPos.elementAt(0), selMeldPos.elementAt(0));

                // if the first is false then error
                if (Boolean.FALSE.equals(result.getFirst())) {
                    Message.addMessage(result.getSecond());
                    // concoting message to display
                    String displayMessage = "Cannot takeout the card of rank" + wildCard.getRank();
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                // concoting message to display
                StringBuilder displayMessage = new StringBuilder("Human");
                displayMessage.append(" took out ");
                displayMessage.append(wildCard.getRankSuit());
                displayMessage.append(" from the meld of rank ");
                displayMessage.append(wildCard.getRank());
                Message.addMessage(displayMessage.toString());

                // checking if the meld was desolved or not
                if (!result.getSecond().isEmpty()) {
                    Message.addMessage(result.getSecond());
                }

                mHumanHandAdapter.unSelectAll();
                mHumanMeldsAdapter.unSelectAll();

                // on a successfull new meld
                updateHumanHandCard();
                updateHumanMeldsUI();
                updateHumanMeldDetailUI();
            }
        });

        mMeldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vector<Integer> handCardList = mHumanHandAdapter.getSelectedPos();
                Vector<Integer> meldCardList = mHumanMeldsAdapter.getSelectedPos();

                Player humanPlayer = mRoundController.mRoundModel.getHumanPlayer();

                // trying to create a new meld
                Pair<Boolean, String> result = humanPlayer.makeNewMeld(handCardList);

                // if the first is false then error
                if (Boolean.TRUE.equals(result.getFirst())) {

                    Message.addMessage(result.getSecond());
                    Card cardToMeld = humanPlayer.getMelds().lastElement()
                            .firstElement();

                    // concoting message to display
                    StringBuilder displayMessage = new StringBuilder(humanPlayer.getClass().getSimpleName());
                    displayMessage.append(" made a new meld of ");
                    displayMessage.append(cardToMeld.getRankSuit());
                    Message.addMessage(displayMessage.toString());

                    mHumanHandAdapter.unSelectAll();
                    mHumanMeldsAdapter.unSelectAll();

                    // on a successfull new meld
                    updateHumanHandCard();
                    updateHumanMeldsUI();
                    updateHumanMeldDetailUI();

                    displayMessages();
                    return;
                }

                if (meldCardList.size() == 0) {
                    // concoting message to display
                    String displayMessage = "Cannot make a meld, if you are trying to add to a meld choose a meld to add to";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }
                if (meldCardList.size() > 1) {
                    // concoting message to display
                    String displayMessage = "Cannot make a meld, if you are trying to add to a meld then choose only one meld to add to a meld";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }
                if (handCardList.size() != 1) {
                    // concoting message to display
                    String displayMessage = "Cannot make a meld, if you are trying to add to a meld then choose only one card from the hand  to add to a meld";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                Card cardToAdd = humanPlayer.getActualHand().get(handCardList.get(0));

                if (cardToAdd.getCardType().equals(Card.ENUM_CardType.CARDTYPE_NATURAL)) {
                    Pair<Integer, String> returnVal = humanPlayer.canAddToMeld(cardToAdd);

                    // trying to add to a meld
                    if (returnVal.getFirst().equals(-1)) {
                        // concoting message to display
                        String displayMessage = "Cannot make a meld, The selected hand card cannot be added to any meld";
                        Message.addMessage(displayMessage);
                        createDialogMessage("Invalid", displayMessage);
                        displayMessages();
                        return;
                    }
                    Card recommenedMeldToAddTo = humanPlayer.getHand().get(returnVal.getFirst()).firstElement();

                    if (returnVal.getFirst() != meldCardList.get(0)) {
                        // concoting message to display
                        String displayMessage = "Cannot make a meld, The selected hand card cannot be added to the selected meld. Try adding it to the meld of " + recommenedMeldToAddTo.getRank();
                        Message.addMessage(displayMessage);
                        createDialogMessage("Invalid", displayMessage);
                        displayMessages();
                        return;
                    }
                }

                // selected card can be added to a meld

                result = humanPlayer.addToMeld(
                        handCardList.elementAt(0), meldCardList.elementAt(0));

                // if the first is false then error
                if (Boolean.FALSE.equals(result.getFirst())) {
                    // concoting message to display
                    String displayMessage = "Cannot make a meld";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                // concoting message to display
                StringBuilder displayMessage = new StringBuilder(humanPlayer.getClass().getSimpleName());
                displayMessage.append(" added ");
                displayMessage.append(cardToAdd.getRankSuit());
                displayMessage.append(" to the meld of " + mRoundController.mRoundModel.getHumanPlayer().getHand().get(meldCardList.elementAt(0)).firstElement().getRank());
                Message.addMessage(displayMessage.toString());

                mHumanHandAdapter.unSelectAll();
                mHumanMeldsAdapter.unSelectAll();

                // on a successfull new meld
                updateHumanHandCard();
                updateHumanMeldsUI();
                updateHumanMeldDetailUI();

                displayMessages();
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup continue

                playComputer();

                setNextPlayer(Round.ENUM_PlayerTurn.TURN_HUMAN);

                displayMessages();

            }
        });

        mDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player humanPlayer = mRoundController.mRoundModel.getHumanPlayer();


                // deal a card untill player gets a non red three card
                boolean dealtCardResult = false;
                while (!dealtCardResult) {
                    try {
                        Card dealtCard = mRoundController.mRoundModel.getDeck().dealCard();
                        dealtCardResult = humanPlayer.addCardToHand(dealtCard);

                        // concoting message to display
                        StringBuilder dealtMessage = new StringBuilder(
                                humanPlayer.getClass().getSimpleName() + " drew ");
                        dealtMessage.append(dealtCard.getRankSuit());
                        Message.addMessage(dealtMessage.toString());
                    } catch (EmptyStockException e) {
                        // checking if the stock is empty and last dealt card was
                        // red
                        // three
                        mRoundController.mRoundModel.setLastCardR3Drwan(!dealtCardResult);
                    }
                }

                // setting the turn start flag to false
                humanPlayer.setTurnStartFlag(false);

                buttonMask(false, true, false);

                // updating the uis
                updateHumanHandCard();
                updateHumanMeldsUI();
                updateStockPilesUI();

                displayMessages();

            }
        });

        mPickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player humanPlayer = mRoundController.mRoundModel.getHumanPlayer();
                Vector<Card> discardPile = mRoundController.mRoundModel.getDiscardPile();

                // player can not pick up the discard pile if the
                // the top card can not be used to be melded

                // see if we can meld the top of the discard pile into any
                // of the alredy existing melds
                if (humanPlayer.canAddToMeld(discardPile.firstElement())
                        .getFirst() == -1
                        && !humanPlayer.canPickUpDiscardPile(discardPile)) {
                    // we can not meld the stack so our only option is to draw
                    // concoting message to display
                    String displayMessage = "Human can not pick up discard pile ";
                    Message.addMessage(displayMessage);
                    createDialogMessage("Invalid", displayMessage);
                    displayMessages();
                    return;
                }

                String discardCardStr = Card
                        .getAllCardInPrintedFormat(discardPile);

                // adding all the card of the discard pile to the player
                for (Card discardCard : discardPile) {
                    humanPlayer.addCardToHand(discardCard);
                }

                // clearing the discard pile
                mRoundController.mRoundModel.emptyDiscardPile();

                // concoting message to display
                StringBuilder displayMessage = new StringBuilder(" Human picked up following cards from the discard pile\n");
                displayMessage.append(discardCardStr);
                Message.addMessage(displayMessage.toString());
                createDialogMessage("Invalid", displayMessage.toString());
                displayMessages();

                // setting the turn start flag to false
                humanPlayer.setTurnStartFlag(false);

                buttonMask(false, true, false);

                // upating the human ui
                updateHumanHandCard();
                updateHumanMeldsUI();
                updateDiscardUI();

                displayMessages();
            }
        });

        mHumanMeldDetailReferesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHumanMeldDetailUI();
            }
        });

        mCompMeldDetailReferesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCompMeldDetailUI();
            }
        });
    }

    /**
     * masks the button
     *
     * @param showContinueBtn      shows only  the continue btn if true
     * @param showContinueRoundBtn shows help and continueRound buttons if true
     * @param showDrawPickup       shows help and drawPickup buttons if true
     */
    void buttonMask(boolean showContinueBtn, boolean showContinueRoundBtn, boolean showDrawPickup) {
        if (showContinueBtn) {
            mContinueBtn.setVisibility(View.VISIBLE);
            mSaveHelpGroup.setVisibility(View.INVISIBLE);
            mHumanContinuRoundBtnGroup.setVisibility(View.GONE);
            mDrawPickupGroup.setVisibility(View.GONE);
            return;
        }
        if (showContinueRoundBtn) {
            mContinueBtn.setVisibility(View.GONE);
            mSaveHelpGroup.setVisibility(View.VISIBLE);
            mHumanContinuRoundBtnGroup.setVisibility(View.VISIBLE);
            mDrawPickupGroup.setVisibility(View.GONE);
            return;
        }
        if (showDrawPickup) {
            mContinueBtn.setVisibility(View.GONE);
            mSaveHelpGroup.setVisibility(View.VISIBLE);
            mHumanContinuRoundBtnGroup.setVisibility(View.GONE);
            mDrawPickupGroup.setVisibility(View.VISIBLE);
            return;
        }
    }

    void playComputer() {
        // check if it is computer's turn
        if (mRoundController.mRoundModel.getPlayerTurn().equals(Round.ENUM_PlayerTurn.TURN_HUMAN)) {
            buttonMask(false, false, true);
        }

        // printing any messages
        displayMessages();

        // clearing the messages
        Message.clearLatestMessages();
        hasRoundEnded();

        Player currPlayer = mRoundController.mRoundModel.getCurrPlayer();
        currPlayer.setTurnStartFlag(true);
        currPlayer.setPlayerWentOutStatus(false);

        // starting the turn
        Pair<Integer, Vector<Integer>> currPlayerChoice;
        Player otherPlayer = mRoundController.mRoundModel.getOtherPlayer();

        boolean continuePlayer = true;

        while (continuePlayer) {
            currPlayerChoice = mRoundController.mRoundModel.getCurrPlayer()
                    .playerTurnController(otherPlayer.getMelds(), mRoundController.mRoundModel.getDiscardPile());

            // logic for what to execute depending on which menu currently is
            // displayed and what the choice was made
            switch (currPlayerChoice.getFirst()) {
                // playerChice.first == 1, beforeTurnStartcontrol was executed
                case 1: {
                    // logic for sub menus of before turn start control
                    mRoundController.mRoundModel.beforeTurnLogic(currPlayerChoice.getSecond().firstElement());
                }
                case 2: {
                    // logic for sub menus of before turn start control
                    mRoundController.mRoundModel.turnStartLogic(currPlayerChoice.getSecond().firstElement());
                    break;
                }
                case 3: {
                    // logic for sub menus of before turn start control
                    mRoundController.mRoundModel.turnContinueLogic(currPlayerChoice.getSecond());
                    break;
                }
                // error code
                case 10: {
                    // do nothing as the game will loop over
                    break;
                }
                default: {
                    break;
                }
            }

            if (currPlayerChoice.getFirst() == 3 && currPlayerChoice.getSecond().size() == 2 &&
                    currPlayerChoice.getSecond().firstElement() == 3) {
                continuePlayer = false;
                // do a discard
            }
        }
        hasRoundEnded();

        // updating the uis
        updateComphandCard();
        updateCompMeldsUI();
        updateCompMeldDetailUI();
        updateDiscardUI();
        updateStockPilesUI();

        // copy all the things that the computer did in to a dialog box

        LayoutInflater li = LayoutInflater.from(getActivity());
        View logView = li.inflate(R.layout.log_dialog, null);

        TextView logText = logView.findViewById(R.id.logs);

        StringBuilder alLLogs = new StringBuilder();

        for (String entry : Message.getMessages()) {
            alLLogs.append(entry);
            alLLogs.append("\n");
        }
        logText.setText(alLLogs);

        AlertDialog.Builder log = new AlertDialog.Builder(getActivity());
        log.setTitle("LOG");
        log.setView(logView);

        // Set the positive button to dismiss the dialog box
        log.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user clicks on ok then dialog box is dismissed.
                dialog.dismiss();
            }
        });

        log.show();
    }


    /**
     * Navigates back to the main menu
     */
    void navToMainMenu() {
        NavHostFragment.findNavController(RoundFragment.this).navigate(RoundFragmentDirections.actionRoundFragmentToMainMenuStartFragment());
    }


    void updateComphandCard() {
        mCompHandCardList.clear();
        mCompHandCardList.addAll(mRoundController.mRoundModel.getComputerPlayer().getActualHand());

        mCompHandAdapter.notifyDataSetChanged();
    }

    void updateHumanHandCard() {

        // copying human hand
        mHumanHandCardList.clear();
        mHumanHandCardList.addAll(mRoundController.mRoundModel.getHumanPlayer().getActualHand());


        mHumanHandAdapter.notifyDataSetChanged();
    }

    void updateCompMeldsUI() {
        mCompMelsCardList.clear();
        for (Vector<Card> meld : mRoundController.mRoundModel.getComputerPlayer().getMelds()) {
            mCompMelsCardList.add(meld.firstElement());
        }
        mCompMeldsAdapter.notifyDataSetChanged();

    }

    void updateHumanMeldsUI() {
        mHumanMelsCardList.clear();
        for (Vector<Card> meld : mRoundController.mRoundModel.getHumanPlayer().getMelds()) {
            mHumanMelsCardList.add(meld.firstElement());
        }

        mHumanMeldsAdapter.notifyDataSetChanged();
    }

    void updateStockPilesUI() {
        mStockPileCardList.clear();
        mStockPileCardList.addAll(mRoundController.mRoundModel.getStockPile());

        mStockPileAdapter.notifyDataSetChanged();
    }


    void updateDiscardUI() {
        // copying discard pile
        mdiscardPileCardList.clear();
        mdiscardPileCardList.addAll(mRoundController.mRoundModel.getDiscardPile());
        mDiscardPileAdapter.notifyDataSetChanged();
    }

    void updateHumanMeldDetailUI() {
        mHumanMeldsDetailsdapter.unSelectAll();
        mHumanMelDetailCardList.clear();
        Vector<Integer> selectedPos = mHumanMeldsAdapter.getSelectedPos();

        if (!selectedPos.isEmpty()) {
            int pos = selectedPos.firstElement();
            mHumanMelDetailCardList.addAll(mRoundController.mRoundModel.getHumanPlayer().getMelds().elementAt(pos));
        }
        mHumanMeldsDetailsdapter.notifyDataSetChanged();
    }

    void updateCompMeldDetailUI() {
        mCompMeldsDetailsdapter.unSelectAll();
        mCompMelDetailCardList.clear();

        Vector<Integer> selectedPos = mCompMeldsAdapter.getSelectedPos();

        if (!selectedPos.isEmpty()) {
            int pos = selectedPos.firstElement();
            mCompMelDetailCardList.addAll(mRoundController.mRoundModel.getComputerPlayer().getMelds().elementAt(pos));
        }

        mCompMeldsDetailsdapter.notifyDataSetChanged();
    }
}
