package com.projekt.MyFitApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import okhttp3.MediaType;


public class BMIFragment extends Fragment {

    private EditText NewHeight, NewWeight;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        ///Setting Toolbar new Title
        ((MainActivity) requireActivity()).setActionBarTitle("Your BMI");  /// Setting new Title for This Fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserData userData = new UserData();
        final BmiCalc bmiCalc = new BmiCalc(getContext()); ///BmiCalc object
        Button calc = view.findViewById(R.id.Update);
        NewHeight = view.findViewById(R.id.heightUpdate);
        NewWeight = view.findViewById(R.id.weightUpdate);
        String height = userData.getHeight(); /// getting User Height
        String weight = userData.getWeight(); /// getting User Weight
        bmiCalc.CalculateBMI(weight, height); /// calling CalculateBMI method
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   /// Onclick Calling  CalculateBMI with user inserted data
                String h = NewHeight.getText().toString().trim();
                String w = NewWeight.getText().toString().trim();
                bmiCalc.CalculateBMI(w, h);

            }
        });


    }

    ///Setting up Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    /// Menu on toolbar handling click actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) /// Handle Menu item selection
        {

            case R.id.Main:
                NavHostFragment.findNavController(BMIFragment.this)
                        .navigate(R.id.action_BMIFragment_to_loggedFragment);
                return true;
            case R.id.Scanner:
                NavHostFragment.findNavController(BMIFragment.this)
                        .navigate(R.id.action_BMIFragment_to_scannerFragment);
                return true;
            case R.id.Logout:
                NavHostFragment.findNavController(BMIFragment.this)
                        .navigate(R.id.action_BMIFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}