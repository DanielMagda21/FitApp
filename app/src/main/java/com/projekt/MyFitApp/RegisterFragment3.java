package com.projekt.MyFitApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterFragment3 extends Fragment {
    private EditText date;
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
        ((MainActivity) requireActivity()).setActionBarTitle("You");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register3, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.radioGroup);
        date = view.findViewById(R.id.editText);
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
                UserDetails userDetails = new UserDetails();
                if (userDetails.checked()) {
                    if(userDetails.date()){
                    NavHostFragment.findNavController(RegisterFragment3.this)
                            .navigate(R.id.action_registerFragment3_to_registerFragment4);}
                    else Toast.makeText(getActivity(), "Enter Valid Year", Toast.LENGTH_LONG).show();
                } else Toast.makeText(getActivity(), "Choose Something", Toast.LENGTH_LONG).show();
                return true;
            case R.id.BackButton:
                NavHostFragment.findNavController(RegisterFragment3.this)
                        .navigate(R.id.action_registerFragment3_to_registerFragment2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /// Class check if user choose and save with setters
    public class UserDetails {
        UserData userData = new UserData();
        DataValidate dataValidate = new DataValidate();
        String date = RegisterFragment3.this.date.getText().toString();


        boolean checked() {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.first:
                    userData.setGender("M");

                    return true;
                case R.id.second:
                    userData.setGender("F");

                    return true;

                default:

                    return false;
            }
        }

        boolean date() {
            dataValidate.setDate(date);

            if (date.trim().equals("")) {
                return false;
            } else if (dataValidate.DateCheck()) {
                userData.setDate(date);
                return true;

            } else {
                return false;
            }
        }
    }


}
