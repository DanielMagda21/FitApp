package com.projekt.MyFitApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterFragment extends Fragment {
    private RadioGroup radioGroup;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    ///Setting Toolbar Title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).setActionBarTitle("Goal"); ///Setting up new Toolbar Title
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        radioGroup = view.findViewById(R.id.radioGroup);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    ///Handling Menu Buttons Checking if user choose something
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) /// Handle Menu item selection
        {
            case R.id.NextButton:
                GoalClass goal = new GoalClass();
                if (goal.checked()) {
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.action_registerFragment_to_registerFragment2);
                }
                else Toast.makeText(getActivity(),"Choose Something",Toast.LENGTH_LONG).show();
                return true;
            case R.id.BackButton:
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_registerFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /// Class check if user choose and save with setters
    public class GoalClass {
        UserData userData = new UserData();

        boolean checked() {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.first:
                    userData.setGoal("Lose weight");

                    return true;
                case R.id.second:
                    userData.setGoal("Maintain weight");

                    return true;

                case R.id.third:
                    userData.setGoal("Gain weight");
                    return true;

                default:

                    return false;
            }
        }
    }
}