package edu.ramapo.akarki.canasta.controller;

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
import android.widget.Toast;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.ramapo.akarki.canasta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateLoadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateLoadFragment<FragmentCreateLoadBinding> extends Fragment {

    FragmentCreateLoadBinding binding;

    // holds the absolute path to the data folder
    String mDataFolderPath;

    // holds the list of filename
    private final ArrayList<String> mFilenameList = new ArrayList<String>();

    // is true if this is create new game
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

        mDataFolderPath = getActivity().getExternalFilesDir(null).getAbsolutePath() + "/data";

        // creating the data folder if it does not exist
        File cwd = new File(mDataFolderPath);
        if (!cwd.exists()) {
            cwd.mkdirs();
        }

        populateFileList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View creatLoadView = inflater.inflate(R.layout.fragment_create_load, container, false);

        // geting all the view and saving them
        initizieView(creatLoadView);

        // retriving the data indicating whether the new game was pressed or not
        mIsCreateNewGame = CreateLoadFragmentArgs.fromBundle(getArguments()).getANewGamePressed();

        // setting up the buttons
        mCreatLoadBtn = creatLoadView.findViewById(R.id.createNewBtn);
        mUserInputTxt = creatLoadView.findViewById(R.id.userInputTxtFeild);

        // if the create new game is true then set the text to that
        if (mIsCreateNewGame) {
            mCreatLoadBtn.setText(R.string.createBtnStr);
        } else {
            mCreatLoadBtn.setText(R.string.loadBtnStr);
        }

        // returning the Inflated layout for this fragment
        return creatLoadView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: this does not work currently

        // Get a handle to the RecyclerView.
        mFilenameRecyclerView = view.findViewById(R.id.filenameRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mFilenamelistAdapter = new FilenameListAdaptor(requireActivity(), mFilenameList,
                mIsCreateNewGame, mUserInputTxt);
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
                String userFile = String.valueOf(mUserInputTxt.getText()).trim();
                if (userFile.isEmpty()) {
                    createToast("Please entry a file name...");
                    return;
                }

                // on new game-> checking if file exist and only allow new file name
                // on laod button press , check if the file exist
                boolean fileExists = false;
                userFile = getTxtFile(userFile);
                for (String fileName : mFilenameList) {
                    if (userFile.equals(fileName)) {
                        if (mIsCreateNewGame) {
                            createToast("Please entry a unique file name...");
                            return;
                        } else {
                            fileExists = true;
                        }
                    }
                }

                if(!mIsCreateNewGame && !fileExists  )
                {
                    createToast("Entered file does not exist");
                    mUserInputTxt.setText("");
                    return;
                }


                StringBuilder filePath = new StringBuilder(mDataFolderPath);
                filePath.append("/");
                filePath.append(getTxtFile(userFile));

                NavHostFragment.findNavController(CreateLoadFragment.this)
                        .navigate(CreateLoadFragmentDirections
                                .actionCreateLoadFragmentToRoundFragment(mIsCreateNewGame, filePath.toString()));
            }
        });
    }

    /**
     * Populates the file List vector
     */
    void populateFileList() {
        File folder = new File(mDataFolderPath);

        // lsiting all the files
        File[] files = folder.listFiles();
        Log.i("filesDir", folder.getAbsolutePath());

        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".txt")) {
                mFilenameList.add(fileName);
            }
        }
    }

    /**
     * saving the view on this fragments
     */
    private void initizieView(View view) {
        mCreatLoadBtn = view.findViewById(R.id.createNewBtn);
        mUserInputTxt = view.findViewById(R.id.userInputTxtFeild);
    }

    /**
     * Creates a toas to display a message for short time
     *
     * @param message string message to show in the toast
     */
    void createToast(String message) {
        Toast emptyText = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        emptyText.show();
    }

    /**
     * Converts the string to have .txt at the end
     * @param aFilename
     * @return
     */
    String getTxtFile(String aFilename)
    {
        if (aFilename.endsWith(".txt")) {
           return aFilename;
        }
        StringBuilder filePath = new StringBuilder(aFilename);
        filePath.append(".txt");
        return filePath.toString();
    }



}