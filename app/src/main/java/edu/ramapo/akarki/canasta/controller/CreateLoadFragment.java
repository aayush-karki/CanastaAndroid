package edu.ramapo.akarki.canasta.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import edu.ramapo.akarki.canasta.R;

import edu.ramapo.akarki.canasta.databinding.FragmentCreateLoadBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateLoadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateLoadFragment<FragmentCreateLoadBinding> extends Fragment {

    FragmentCreateLoadBinding binding;

    // holds the list of filename
    private final ArrayList<String> mFilenameList = new ArrayList<String>();

    boolean mIsCreateNewGame;

    private RecyclerView mFilenameRecyclerView;
    private FilenameListAdaptor mFilenamelistAdapter;

    Button mCreatLoadBtn;
    EditText mUserInputTxt;

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
     * @param aIsCreateNewGame true if new game is to be created
     * @return A new instance of fragment CreateLoadFragment.
     */
    public static CreateLoadFragment newInstance(boolean aIsCreateNewGame) {
        CreateLoadFragment fragment = new CreateLoadFragment();
        Bundle args = new Bundle();
        args.putBoolean("isCreateNewGame", aIsCreateNewGame);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsCreateNewGame = getArguments().getBoolean("isCreateNewGame");
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

        View creatLoadView = inflater.inflate(R.layout.fragment_create_load, container, false);

        // geting all the view and saving them
        initializeView(creatLoadView);

        // retriving the data indicating whether the new game was pressed or not
        mIsCreateNewGame = CreateLoadFragmentArgs.fromBundle(getArguments()).getANewGamePressed();

        // setting up the buttons
        mCreatLoadBtn = creatLoadView.findViewById(R.id.createNewBtn);
        mUserInputTxt = creatLoadView.findViewById(R.id.userInputTxtFeild);

        // if the create new game is true then set the text to that
        if(mIsCreateNewGame)
        {
            mCreatLoadBtn.setText(R.string.createBtnStr);
        }
        else
        {
            mCreatLoadBtn.setText(R.string.loadBtnStr);
        }


        // returning the Inflated layout for this fragment
        return creatLoadView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: this does not work currently
        populateFileList();

        // Get a handle to the RecyclerView.
        mFilenameRecyclerView = view.findViewById(R.id.filenameRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mFilenamelistAdapter = new FilenameListAdaptor(requireActivity(), mFilenameList);
        // Connect the adapter with the RecyclerView.
        mFilenameRecyclerView.setAdapter(mFilenamelistAdapter);
        // Give the RecyclerView a default layout manager.
        mFilenameRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        view.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CreateLoadFragment.this).navigate(CreateLoadFragmentDirections.actionCreateLoadFragmentToMainMenuStartFragment());
            }
        });

        view.findViewById(R.id.createNewBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // checking if the input field is empty then do nothing
                String userFile =  mUserInputTxt.getText().toString().trim();
                if(userFile.isEmpty())
                {
                    Toast emptyText = Toast.makeText(getActivity(), "enter a file name", Toast.LENGTH_SHORT);
                    emptyText.show();
                    return;
                }

                // checking if this button is the create new or laod button
                boolean isNewBtn = mCreatLoadBtn.getText().toString() != getResources().getString(R.string.createBtnStr);

                // TODO: if it was laod button, check if the file exist
                // TODO: if it was new button, check if file exist and only allow new file name

                NavHostFragment.findNavController(CreateLoadFragment.this).navigate(CreateLoadFragmentDirections.actionCreateLoadFragmentToRoundFragment(isNewBtn,userFile));
            }
        });

    }

    /**
     * Populates the file List vector
     */
    void populateFileList()
    {
        // emulated download storage
        String file_directory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";
        Log.i("fileDirec",file_directory);

        File directory = new File(file_directory);
        final File[] files_list = directory.listFiles();

        Integer count = files_list.length;
        Log.i("filelist",count.toString());


        Vector<String> files_name = new Vector<>(files_list.length);

        //getting the names of the file and add them to the list of files_name if the file's extension is .txt
        for (File file : files_list) {
            ++count;
            String file_name = file.getName();
            if (file_name.endsWith(".txt")){
                files_name.add(file_name);
                Log.i("Populate",file_name);
            }
        }


    }

    /**
     * saving the view on this fragments
     */
    private void initializeView(View view){
        mCreatLoadBtn = view.findViewById(R.id.createNewBtn);
        mUserInputTxt = view.findViewById(R.id.userInputTxtFeild);
    }

}