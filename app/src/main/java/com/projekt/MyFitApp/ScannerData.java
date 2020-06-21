package com.projekt.MyFitApp;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

public class ScannerData {
    Context context;

    public ScannerData(Context context) {
        this.context = context;
    }
    /// Using Data provided By Api and Setting new EditText text values
    public void SetProductData(String name, String kcal, String fat, String carbs, String sugar, String weight) {
        EditText Name = (EditText) ((Activity) context).findViewById(R.id.Name);
        EditText Kcal = (EditText) ((Activity) context).findViewById(R.id.Kcal);
        EditText Fat = (EditText) ((Activity) context).findViewById(R.id.Fat);
        EditText Carbs = (EditText) ((Activity) context).findViewById(R.id.Carbs);
        EditText Sugar = (EditText) ((Activity) context).findViewById(R.id.Sugar);
        EditText Weight = (EditText) ((Activity) context).findViewById(R.id.Weight);
        Name.setText(name);
        Kcal.setText(kcal);
        Fat.setText(fat);
        Carbs.setText(carbs);
        Sugar.setText(sugar);
        Weight.setText(weight);


    }
}
