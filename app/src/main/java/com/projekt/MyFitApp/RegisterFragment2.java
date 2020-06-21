package com.projekt.MyFitApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class RegisterFragment2 extends Fragment {
    private RadioGroup radioGroup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    ///Setting Toolbar Title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) requireActivity()).setActionBarTitle("Activity"); ///Setting up new Toolbar Title
        return inflater.inflate(R.layout.fragment_register2, container, false);
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
                Activity activity = new Activity();
                if (activity.checked()) {
                    NavHostFragment.findNavController(RegisterFragment2.this)
                            .navigate(R.id.action_registerFragment2_to_registerFragment3);
                }
                else Toast.makeText(getActivity(),"Choose Something",Toast.LENGTH_LONG).show();
                return true;
            case R.id.BackButton:
                NavHostFragment.findNavController(RegisterFragment2.this)
                        .navigate(R.id.action_registerFragment2_to_registerFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }/// Class check if user choose and save with setters
    public class Activity {
        UserData userData = new UserData();

        boolean checked() {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.first:
                    userData.setActivity("Not Very Active");

                    return true;
                case R.id.second:
                    userData.setActivity("Lightly Active");

                    return true;

                case R.id.third:
                    userData.setActivity("Active");
                    return true;
                case R.id.four:
                    userData.setActivity("Very Active");
                    return true;

                default:

                    return false;
            }
        }
    }
}