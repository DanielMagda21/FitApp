package com.projekt.MyFitApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class RegisterFragment4 extends Fragment {
    private EditText Height, Weight;

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
        ((MainActivity) requireActivity()).setActionBarTitle("Bmi Data"); ///Setting up new Toolbar Title
        return inflater.inflate(R.layout.fragment_register4, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Height = view.findViewById(R.id.editText3);
        Weight = view.findViewById(R.id.editText2);

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
                BMIData bmiData = new BMIData();
                if (bmiData.BmiData()) {
                    NavHostFragment.findNavController(RegisterFragment4.this)
                            .navigate(R.id.action_registerFragment4_to_registerFragment5);
                } else Toast.makeText(getActivity(), "Enter Valid Data", Toast.LENGTH_LONG).show();
                return true;
            case R.id.BackButton:
                NavHostFragment.findNavController(RegisterFragment4.this)
                        .navigate(R.id.action_registerFragment4_to_registerFragment3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /// Class check if user Provide data and save with setters
    public class BMIData {
        DataValidate dataValidate = new DataValidate();
        UserData userData = new UserData();
        String Height = RegisterFragment4.this.Height.getText().toString();
        String Weight = RegisterFragment4.this.Weight.getText().toString();

        boolean BmiData() {
            dataValidate.setHeight(Height);
            dataValidate.setWeight(Weight);
            if (Height.trim().equals("") || Height.trim().equals("")) {
                return false;
            } else if (dataValidate.WeightCheck()&&dataValidate.HeightCheck()) {
                userData.setHeight(Height);
                userData.setWeight(Weight);
                return true;}
                else {
                    return false;
                }
                }

            }
        }

