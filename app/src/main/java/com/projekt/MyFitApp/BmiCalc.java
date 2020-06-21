package com.projekt.MyFitApp;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class BmiCalc {
    Context context;

    public BmiCalc(Context context) {
        this.context = context;
    }
    /// isBetween checking if double value is between defined data used in CalculateBMI method
    public boolean isBetween(double value, double lower, double upper) {
        return lower <= value && value <= upper;
    }

    /// Getting String values and parsing it and counting BMI value
    /// Using isBetween boolean for BMI double value and setting text on TextView
    public void CalculateBMI(String weight, String height) {
        double W = Integer.parseInt(weight);
        double H = Integer.parseInt(height);
        H = H * 0.01;
        double result = W / (H * H);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String result2 = decimalFormat.format(result);
        TextView BMIres = (TextView) ((Activity) context).findViewById(R.id.BMI);


        if (result < 15) {
            BMIres.setBackgroundResource(R.color.deep_blue);
            result2 = result2 + "\n You are Very severely underweight";
            BMIres.setText(result2);
        } else if (isBetween(result, 15, 16)) {
            BMIres.setBackgroundResource(R.color.blue);
            result2 = result2 + "\n You are Severely underweight";
            BMIres.setText(result2);
        } else if (isBetween(result, 16, 18.49)) {
            BMIres.setBackgroundResource(R.color.green1);
            result2 = result2 + "\n You are Underweight";
            BMIres.setText(result2);
        } else if (isBetween(result, 18.5, 24.99)) {
            BMIres.setBackgroundResource(R.color.yellow);
            result2 = result2 + "\n You are Normal (healthy weight)";
            BMIres.setText(result2);
        } else if (isBetween(result, 25.0, 29.99)) {
            BMIres.setBackgroundResource(R.color.yellow2);
            result2 = result2 + "\n You are Overweight";
            BMIres.setText(result2);
        } else if (isBetween(result, 30, 34.99)) {
            BMIres.setBackgroundResource(R.color.red1);
            result2 = result2 + "\n You are Obese Class I (Moderately obese)";
            BMIres.setText(result2);
        } else {
            BMIres.setBackgroundResource(R.color.red2);
            result2 = result2 + "\n You are Obese Class II (Severely obese)";
            BMIres.setText(result2);
        }


    }
    /// Taking String parsing it to int value counting BMR and Setting new Text View value
    public void CalculateBMR(String weight, String height, String gender, String date) {
        TextView Daily = (TextView) ((Activity) context).findViewById(R.id.Daily);
        if (gender=="Male") {
            String BMResult;
            int Height = Integer.parseInt(height);
            int Weight = Integer.parseInt(weight);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int date2 = Integer.parseInt(date);
            double result = (9.99 * Weight) + (6.25 * Height) - (4.92 * (year - date2) + 5);
            BMResult = String.valueOf(result);
            Daily.setText(BMResult);


        } else {
            String BMResult;
            int Height = Integer.parseInt(height);
            int Weight = Integer.parseInt(weight);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int date2 = Integer.parseInt(date);
            double result = (9.99 * Weight) + (6.25 * Height) - (4.92 * (year - date2) - 161);
            BMResult = String.valueOf(result);
            Daily.setText(BMResult);
        }
    }


}

