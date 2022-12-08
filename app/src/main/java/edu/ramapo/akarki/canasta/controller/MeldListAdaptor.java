package edu.ramapo.akarki.canasta.controller;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import edu.ramapo.akarki.canasta.R;
import edu.ramapo.akarki.canasta.model.Card;

public class MeldListAdaptor extends CardListAdaptor{

    Vector<Vector<Card>> mHand;
    public MeldListAdaptor(Context aContext, Vector<Card> aCardList) {
        super(aContext, aCardList);
    }

    public Vector<Integer> getSelectedPos() {

        Vector<Integer> posList = new Vector<Integer>();
        for (Integer pos: mSelectedCardPos) {
            posList.add(++pos);
        }

        return posList;
    }

}
