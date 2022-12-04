package edu.ramapo.akarki.canasta.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.ramapo.akarki.canasta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateLoadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateLoadFragment extends Fragment {

    // holds the list of filename
    private final ArrayList<String> mFilenameList = new ArrayList<String>();

    private RecyclerView mFilenameRecyclerView;
    private FilenameListAdaptor mFilenamelistAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Default constructor
     */
    public CreateLoadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateLoadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateLoadFragment newInstance(String param1, String param2) {
        CreateLoadFragment fragment = new CreateLoadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: delete  me and get the list of filename
        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mFilenameList.add("Word " + i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_load, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get a handle to the RecyclerView.
        mFilenameRecyclerView = view.findViewById(R.id.filenameRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mFilenamelistAdapter = new FilenameListAdaptor(requireActivity(), mFilenameList);
        // Connect the adapter with the RecyclerView.
        mFilenameRecyclerView.setAdapter(mFilenamelistAdapter);
        // Give the RecyclerView a default layout manager.
        mFilenameRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

    }
}