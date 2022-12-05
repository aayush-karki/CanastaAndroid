package edu.ramapo.akarki.canasta.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.ramapo.akarki.canasta.R;

public class FilenameListAdaptor extends
        RecyclerView.Adapter<FilenameListAdaptor.FileNameViewHolder> {

    final boolean mIsCreateNewGame;
    private final ArrayList<String> mFilenameList;
    final private LayoutInflater mInflater;
    final EditText mUserInputTxt;

    /**
     * Constructor for the Filename List Adaptor
     * @param aContext Context, context of where this adaptor is created
     * @param aFileNameList  LinkedList<String>  list of file Names
     */
    public FilenameListAdaptor(Context aContext,
                               ArrayList<String> aFileNameList,
                               boolean aIsCreateNewGame,
                               EditText aUserInputTxt) {
        mInflater = LayoutInflater.from(aContext);
        this.mFilenameList = aFileNameList;
        this.mIsCreateNewGame = aIsCreateNewGame;
        this.mUserInputTxt = aUserInputTxt;
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
    public FilenameListAdaptor.FileNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.filename_item,
                parent, false);
        return new FileNameViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at
     * the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull FilenameListAdaptor.FileNameViewHolder holder, int position) {
        String mCurrent = mFilenameList.get(position);
        holder.fileNameItemView.setText(mCurrent);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mFilenameList.size();
    }

    /**
     * A FileNameViewHolder describes an filename_item view and metadata about its place within the
     * RecyclerView.
     */
    class FileNameViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // the view that this view holder will contain
        public final TextView fileNameItemView;

        //  adaptor that this view holder will be bound to
        final FilenameListAdaptor mAdapter;

        /**
         * Constructor
         * @param aItemView the view that this view holder will contain
         * @param aAdapter FilenameListAdaptor, adaptor that this view holder will be bound to
         */
        public FileNameViewHolder(View aItemView, FilenameListAdaptor aAdapter) {
            super(aItemView);
            fileNameItemView = aItemView.findViewById(R.id.filenameItem);
            this.mAdapter = aAdapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            String element = mFilenameList.get(mPosition);

            // on create new game do nothing
            if(mIsCreateNewGame)
            {
                return;
            }

            // on load a game
            // click a item sould set the userEdittxt text to itself
            mUserInputTxt.setText(element);

            // Notify the adapter that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();

        }
    }
}
