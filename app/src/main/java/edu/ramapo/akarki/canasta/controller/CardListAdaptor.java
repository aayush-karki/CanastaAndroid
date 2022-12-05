package edu.ramapo.akarki.canasta.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.Vector;

import edu.ramapo.akarki.canasta.R;
import edu.ramapo.akarki.canasta.model.Card;

public class CardListAdaptor extends
        RecyclerView.Adapter<CardListAdaptor.CardsViewHolder> {

    private final Vector<Card> mCardList;
    private LayoutInflater mInflater;

    /**
     * defaultfor the Filename List Adaptor
     *
     * @param aContext Context, context of where this adaptor is created
     * @param aCardList  LinkedList<String>  list of file Names
     */
    public CardListAdaptor(Context aContext,
                           Vector<Card> aCardList) {
        mInflater = LayoutInflater.from(aContext);
        this.mCardList = aCardList;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent
     * an item.
     *
     * @param parent ViewGroup, The ViewGroup into which the new View will be added after it is
     *              bound to an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardListAdaptor.CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.card_item,
                parent, false);
        return new CardsViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdaptor.CardsViewHolder holder, int position) {
        Card mCurrent = mCardList.get(position);
//        holder.cardItemView.setText(mCurrent.getRankSuit());

        holder.cardItemView.setImageResource(convertRankSuitToID(mCurrent.getRankSuit()));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    class CardsViewHolder extends RecyclerView.ViewHolder {
        public final ImageView cardItemView;
        final CardListAdaptor mAdapter;

        public CardsViewHolder(View itemView, CardListAdaptor aAdapter) {
            super(itemView);
            cardItemView = itemView.findViewById(R.id.cardItem);
            this.mAdapter = aAdapter;
        }
    }

    /**
     * maps the rankSuit to its card file
     * @param aRankSuit rank and suit of a card
     * @return resource Id of the card
     */
    int convertRankSuitToID(String aRankSuit)
    {
        switch (aRankSuit) {
            case "2C":
            {
                return R.drawable.card_b_c2;
            }
            case "3C":
            {
                return R.drawable.card_b_c3;
            }
            case "4C":
            {
                return R.drawable.card_b_c4;
            }
            case "5C":
            {
                return R.drawable.card_b_c5;
            }
            case "6C":
            {
                return R.drawable.card_b_c6;
            }
            case "7C":
            {
                return R.drawable.card_b_c7;
            }
            case "8C":
            {
                return R.drawable.card_b_c8;
            }
            case "9C":
            {
                return R.drawable.card_b_c9;
            }
            case "XC":
            {
                return R.drawable.card_b_c10;
            }
            case "JC":
            {
                return R.drawable.card_b_cj;
            }
            case "QC":
            {
                return R.drawable.card_b_cq;
            }
            case "KC":
            {
                return R.drawable.card_b_ck;
            }
            case "AC":
            {
                return R.drawable.card_b_ca;
            }
            case "2S":
            {
                return R.drawable.card_b_s2;
            }
            case "3S":
            {
                return R.drawable.card_b_s3;
            }
            case "4S":
            {
                return R.drawable.card_b_s4;
            }
            case "5S":
            {
                return R.drawable.card_b_s5;
            }
            case "6S":
            {
                return R.drawable.card_b_s6;
            }
            case "7S":
            {
                return R.drawable.card_b_s7;
            }
            case "8S":
            {
                return R.drawable.card_b_s8;
            }
            case "9S":
            {
                return R.drawable.card_b_s9;
            }
            case "XS":
            {
                return R.drawable.card_b_s10;
            }
            case "JS":
            {
                return R.drawable.card_b_sj;
            }
            case "QS":
            {
                return R.drawable.card_b_sq;
            }
            case "KS":
            {
                return R.drawable.card_b_sk;
            }
            case "AS":
            {
                return R.drawable.card_b_sa;
            }
            case "2D":
            {
                return R.drawable.card_b_d2;
            }
            case "3D":
            {
                return R.drawable.card_b_d3;
            }
            case "4D":
            {
                return R.drawable.card_b_d4;
            }
            case "5D":
            {
                return R.drawable.card_b_d5;
            }
            case "6D":
            {
                return R.drawable.card_b_d6;
            }
            case "7D":
            {
                return R.drawable.card_b_d7;
            }
            case "8D":
            {
                return R.drawable.card_b_d8;
            }
            case "9D":
            {
                return R.drawable.card_b_d9;
            }
            case "XD":
            {
                return R.drawable.card_b_d10;
            }
            case "JD":
            {
                return R.drawable.card_b_dj;
            }
            case "QD":
            {
                return R.drawable.card_b_dq;
            }
            case "KD":
            {
                return R.drawable.card_b_dk;
            }
            case "AD":
            {
                return R.drawable.card_b_da;
            }
            case "2H":
            {
                return R.drawable.card_b_h2;
            }
            case "3H":
            {
                return R.drawable.card_b_h3;
            }
            case "4H":
            {
                return R.drawable.card_b_h4;
            }
            case "5H":
            {
                return R.drawable.card_b_h5;
            }
            case "6H":
            {
                return R.drawable.card_b_h6;
            }
            case "7H":
            {
                return R.drawable.card_b_h7;
            }
            case "8H":
            {
                return R.drawable.card_b_h8;
            }
            case "9H":
            {
                return R.drawable.card_b_h9;
            }
            case "XH":
            {
                return R.drawable.card_b_h10;
            }
            case "JH":
            {
                return R.drawable.card_b_hj;
            }
            case "QH":
            {
                return R.drawable.card_b_hq;
            }
            case "KH":
            {
                return R.drawable.card_b_hk;
            }
            case "AH":
            {
                return R.drawable.card_b_ha;
            }
            case "J1":
            {
                return R.drawable.card_b_joker_black;
            }
            case "J2":
            {
                return R.drawable.card_b_joker_red;
            }
            default:
            {
                return -1;
            }
        }
    }
}