package edu.ramapo.akarki.canasta.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import edu.ramapo.akarki.canasta.R;
import edu.ramapo.akarki.canasta.model.Card;

public class RoundFragment extends Fragment {

    // holds the list of computer hand
    private final Vector<Card> mCompHandCardList = new  Vector<Card> ();
    // holds the list of computer melds
    private final Vector<Card> mCompMelsCardList = new  Vector<Card> ();
    // holds the list of computer meld detail
    private final Vector<Card> mCompMelDetailCardList = new  Vector<Card> ();

    // holds the list of discard pile
    private final Vector<Card> mdiscardPileCardList = new  Vector<Card> ();
    // holds the list of stock pile
    private final Vector<Card> mStockPileCardList = new  Vector<Card> ();

    // holds the list of human hand
    private final Vector<Card> mHumanHandCardList = new  Vector<Card> ();
    // holds the list of human melds
    private final Vector<Card> mHumanMelsCardList = new  Vector<Card> ();
    // holds the list of human meld detail
    private final Vector<Card> mHumanMelDetailCardList = new  Vector<Card> ();

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
    public static RoundFragment newInstance() {
        RoundFragment fragment = new RoundFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: delete  me and get the list of cards for all the recycle view
        // Put initial data into the word list.
        for (int i = 0; i < 5; i++) {
            mCompHandCardList.add( new Card("3S"));
        }
        mCompHandCardList.add(new Card("5S"));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_round, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        // Get a handle to the RecyclerView.
        mCompHandRecyclerView = view.findViewById(R.id.compHandCardRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mCompHandAdapter = new CardListAdaptor(requireActivity(), mCompHandCardList);
        // Connect the adapter with the RecyclerView.
        mCompHandRecyclerView.setAdapter(mCompHandAdapter);
        // Give the RecyclerView a default layout manager.
        mCompHandRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
*/
        // computer hand
        initailizeRecycleView(mCompHandAdapter, mCompHandRecyclerView,
                view.findViewById(R.id.compHandCardRecyclerView), mCompHandCardList );

        // for computer melds
        initailizeRecycleView(mCompMeldsAdapter, mCompMeldsRecyclerView,
                view.findViewById(R.id.compMeldsRecyclerView), mCompHandCardList );

        // for computer meld Details
        initailizeRecycleView(mCompMeldsDetailsdapter, mCompMeldDetailsRecyclerView,
                view.findViewById(R.id.compMeldDetailRecyclerView), mCompHandCardList );

        // for discard pile
        initailizeRecycleView(mDiscardPileAdapter, mDiscardPileRecyclerView,
                view.findViewById(R.id.discardPileRecyclerView), mCompHandCardList );

        // for stock pile
        initailizeRecycleView(mStockPileAdapter, mStockPileRecyclerView,
                view.findViewById(R.id.stockPileRecyclerView), mCompHandCardList );

        /// human hand
        initailizeRecycleView(mHumanHandAdapter, mHumanHandRecyclerView,
                view.findViewById(R.id.humanHandCardRecyclerView), mCompHandCardList );

        // for human melds
        initailizeRecycleView(mHumanMeldsAdapter, mHumanMeldsRecyclerView,
                view.findViewById(R.id.humanMeldsRecyclerView), mCompHandCardList );

        // for human meld Details
        initailizeRecycleView(mHumanMeldsDetailsdapter, mHumanMeldDetailsRecyclerView,
                view.findViewById(R.id.humanMeldDetailRecyclerView), mCompHandCardList );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * creates adaptor for recycler view and binds the data to it
     * @param aCardListAdaptor - the card list aCardListAdaptor to create
     * @param aRecycleView - recycle view to bind the adaptor to
     * @param aRecycleViewId - recycle view to bind the adaptor to that was found
     * @param aCardToAdd - list of cards as data to bind to the adaptor
     */
    public void initailizeRecycleView(CardListAdaptor aCardListAdaptor ,RecyclerView aRecycleView,
                                      RecyclerView aRecycleViewId, final Vector<Card> aCardToAdd)
    {
        // store a handle to the RecyclerView.
        aRecycleView = aRecycleViewId;
        // Create an adapter and supply the data to be displayed.
        aCardListAdaptor = new CardListAdaptor(requireActivity(), aCardToAdd);
        // Connect the adapter with the RecyclerView.
        aRecycleView.setAdapter(aCardListAdaptor);
        // Give the RecyclerView a default layout manager.
        aRecycleView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

}