package edu.ramapo.akarki.canasta.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.ramapo.akarki.canasta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {

    Button mNewGameBtn;
    Button mLoadGameBtn;
    Button mExitGameBtn;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainMenuView =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        // geting all the view and saving them
        initializeView(mainMenuView);

        return mainMenuView;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNewGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passing true as new game was pressed
                boolean isNewGame = true;
                NavHostFragment.findNavController(MainMenuFragment.this).navigate(MainMenuFragmentDirections.actionMainMenuStartFragmentToCreateLoadFragment(isNewGame));
            }
        });

        mLoadGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passing false as load game was pressed
                boolean isNewGame = false;
                NavHostFragment.findNavController(MainMenuFragment.this).navigate(MainMenuFragmentDirections.actionMainMenuStartFragmentToCreateLoadFragment(isNewGame));
            }
        });

        mExitGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * saving the view on this fragments
     */
    private void initializeView(View view){
        mNewGameBtn =  view.findViewById(R.id.newGameBtn);
        mLoadGameBtn = view.findViewById(R.id.loadBtn);;
        mExitGameBtn = view.findViewById(R.id.exitBtn);;
    }
}