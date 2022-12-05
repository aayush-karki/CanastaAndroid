package edu.ramapo.akarki.canasta.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import edu.ramapo.akarki.canasta.model.Card;
import edu.ramapo.akarki.canasta.model.Message;
import edu.ramapo.akarki.canasta.model.Pair;
import edu.ramapo.akarki.canasta.model.Player;
import edu.ramapo.akarki.canasta.model.Round;

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
    Group mSaveHelpGroup;

    // top button that are avialable during the human's turn
    Button mDiscardBtn;
    Button mTakeOutBtn;
    Button mMeldBtn;
    Group mHumanContinuRoundBtnGroup;

    Button mContinueBtn;
    Button mDrawBtn;
    Button mPickupBtn;

    ImageView mComputerIcon;
    ImageView mHumanIcon;
    ImageView mCompProfile;
    ImageView mHumanProfile;

    TextView mRoundNumTxt;
    TextView mCompScoreNumTxt;
    TextView mHumanScoreNumTxt;
    TextView mCurrPlayer;

    TextView mCompMeldDetailRankTxt;
    TextView mHumanMeldDetailRankTxt;
    TextView mMessageTxt;

    Group mCompMeldDetailGroup;
    Group mHumanMeldDetailGroup;
    Group mDrawPickupGroup;

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

        /*// TODO: delete  me and get the list of cards for all the recycle view
        // Put initial data into the word list.
        for (int i = 0; i < 5; i++) {
            mCompHandCardList.add( new Card("3S"));
        }
        mCompHandCardList.add(new Card("5S"));
        */
        // creating the round controller
        try {
            mRoundController = new RoundController(mIsNewGame, mAbsFilePath);
        } catch (Exception e) {
            Log.e("CanastaException", "Error while loading the file" + e.getMessage());
            e.printStackTrace();

            // naviage to main menu
            navToMainMenu();
        }


        // updating the list with data
        updateRecycleView();
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

        // updating the texts in the screen
        updateTextInfos();

        // computer hand
        initailizeRecycleView(mCompHandAdapter, mCompHandRecyclerView,
                view.findViewById(R.id.compHandCardRecyclerView), mCompHandCardList);

        // for computer melds
        initailizeRecycleView(mCompMeldsAdapter, mCompMeldsRecyclerView,
                view.findViewById(R.id.compMeldsRecyclerView), mCompMelsCardList);

        // for computer meld Details
        initailizeRecycleView(mCompMeldsDetailsdapter, mCompMeldDetailsRecyclerView,
                view.findViewById(R.id.compMeldDetailRecyclerView), mCompMelDetailCardList);

        // for discard pile
        initailizeRecycleView(mDiscardPileAdapter, mDiscardPileRecyclerView,
                view.findViewById(R.id.discardPileRecyclerView), mdiscardPileCardList);

        // for stock pile
        initailizeRecycleView(mStockPileAdapter, mStockPileRecyclerView,
                view.findViewById(R.id.stockPileRecyclerView), mStockPileCardList);

        /// human hand
        initailizeRecycleView(mHumanHandAdapter, mHumanHandRecyclerView,
                view.findViewById(R.id.humanHandCardRecyclerView), mHumanHandCardList);

        // for human melds
        initailizeRecycleView(mHumanMeldsAdapter, mHumanMeldsRecyclerView,
                view.findViewById(R.id.humanMeldsRecyclerView), mHumanMelsCardList);

        // for human meld Details
        initailizeRecycleView(mHumanMeldsDetailsdapter, mHumanMeldDetailsRecyclerView,
                view.findViewById(R.id.humanMeldDetailRecyclerView), mHumanMelDetailCardList);

        // setting up the buttons click listener
        setBtnClickListener();


        if (mRoundController.mRoundModel.getPlayerTurn().equals(Round.ENUM_PlayerTurn.TURN_UNINITAIZLIZED)) {
            determinePlayerOrder();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * creates adaptor for recycler view and binds the data to it
     *
     * @param aCardListAdaptor - the card list aCardListAdaptor to create
     * @param aRecycleView     - recycle view to bind the adaptor to
     * @param aRecycleViewId   - recycle view to bind the adaptor to that was found
     * @param aCardToAdd       - list of cards as data to bind to the adaptor
     */
    public void initailizeRecycleView(CardListAdaptor aCardListAdaptor, RecyclerView aRecycleView,
                                      RecyclerView aRecycleViewId, final Vector<Card> aCardToAdd) {
        // store a handle to the RecyclerView.
        aRecycleView = aRecycleViewId;
        // Create an adapter and supply the data to be displayed.
        aCardListAdaptor = new CardListAdaptor(requireActivity(), aCardToAdd);
        // Connect the adapter with the RecyclerView.
        aRecycleView.setAdapter(aCardListAdaptor);
        // Give the RecyclerView a default layout manager.
        aRecycleView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
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

        mComputerIcon = view.findViewById(R.id.computerIcon);
        mCompScoreNumTxt = view.findViewById(R.id.compScoreNumTxt);

        mHumanIcon = view.findViewById(R.id.humanIcon);
        mHumanScoreNumTxt = view.findViewById(R.id.humanScoreNumTxt);

        mCompProfile = view.findViewById(R.id.compProfile);
        mHumanProfile = view.findViewById(R.id.humanProfile);

        mCompMeldDetailRankTxt = view.findViewById(R.id.compMeldDetailRankTxt);
        mCompMeldDetailGroup = view.findViewById(R.id.compMeldDetailGroup);

        mHumanMeldDetailRankTxt = view.findViewById(R.id.humanMeldDetailRankTxt);
        mHumanMeldDetailGroup = view.findViewById(R.id.humanDetailGroup);

        mMessageTxt = view.findViewById(R.id.messageTxt);

        mCurrPlayer = view.findViewById(R.id.currPlayer);
        mDrawPickupGroup = view.findViewById(R.id.drawPickupGroup);
    }

    /**
     * Navigates back to the main menu
     */
    void navToMainMenu() {
        NavHostFragment.findNavController(RoundFragment.this).navigate(RoundFragmentDirections.actionRoundFragmentToMainMenuStartFragment());
    }

    /**
     * updates the all the UI
     */
    void updateRecycleView() {
        // copying computer hand
        mCompHandCardList.clear();
        for (Card handCard : mRoundController.mRoundModel.getComputerPlayer().getActualHand()) {
            mCompHandCardList.add(handCard);
        }

        // copying computer melds
        mCompMelsCardList.clear();
        for (Vector<Card> meld : mRoundController.mRoundModel.getComputerPlayer().getMelds()) {
            mCompMelsCardList.add(meld.firstElement());
        }

        // copying meldDetail
        // TODO: add meld details

        // copying discard pile
        mdiscardPileCardList.clear();
        for (Card discardCard : mRoundController.mRoundModel.getDiscardPile()) {
            mdiscardPileCardList.add(discardCard);
        }
        // copying stock pile
        mStockPileCardList.clear();
        for (Card stockPile : mRoundController.mRoundModel.getStockPile()) {
            mStockPileCardList.add(stockPile);
        }

        // copying human hand
        mHumanHandCardList.clear();
        for (Card handCard : mRoundController.mRoundModel.getHumanPlayer().getActualHand()) {
            mHumanHandCardList.add(handCard);
        }

        // copying human melds
        mHumanMelsCardList.clear();
        for (Vector<Card> meld : mRoundController.mRoundModel.getHumanPlayer().getMelds()) {
            mHumanMelsCardList.add(meld.firstElement());
        }

        // copying meldDetail
        // TODO: add meld details

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

            // setting up its button
            mContinueBtn.setVisibility(View.GONE);
            mHumanContinuRoundBtnGroup.setVisibility(View.GONE);
            mDrawPickupGroup.setVisibility(View.VISIBLE);
            mSaveHelpGroup.setVisibility(View.VISIBLE);

        } else {
            mCurrPlayer.setText("Curr: Computer");

            // setting up the button
            mContinueBtn.setVisibility(View.VISIBLE);
            mHumanContinuRoundBtnGroup.setVisibility(View.GONE);
            mDrawPickupGroup.setVisibility(View.GONE);
            mSaveHelpGroup.setVisibility(View.GONE);
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
                //TODO setup help
                if (mDrawPickupGroup.getVisibility() == View.VISIBLE) {
                    mRoundController.mRoundModel.turnStartLogic(1);
                } else if (mHumanContinuRoundBtnGroup.getVisibility() == View.VISIBLE) {
                    Vector<Integer> param = new Vector<Integer>();
                    param.add(1);
                    mRoundController.mRoundModel.turnContinueLogic(param);
                }
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
                //TODO do what discard is supposed to

                setNextPlayer();
                hasRoundEnded();
            }
        });

        mTakeOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup takeout
            }
        });

        mMeldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup meld
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup continue
            }
        });

        mDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup draw
            }
        });

        mPickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup pickup
            }
        });
    }
}
