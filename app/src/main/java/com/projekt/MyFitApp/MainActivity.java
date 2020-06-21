package com.projekt.MyFitApp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    /// Method that that allow to change Toolbar Title in Fragments
    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }
}
/// Class handles Setters Getter for UserData(Changing Fragments make that variables values are null in other Fragment)
class UserData {
    public static String Goal;
    private static String Activity;
    private static String Gender;
    private static String Date;
    private static String Height;
    private static String Weight;
    private static String Email;
    private static String Password;
    private static String UserName;
    private static String Name;
    private static String Kcal;
    private static String Fat;
    private static String Carbs;
    private static String Sugar;
    private static String WeightProduct;


    String getGoal() {
        return Goal;
    }

    void setGoal(String Goal) {
        UserData.Goal = Goal;
    }

    String getActivity() {
        return Activity;
    }

    void setActivity(String Activity) {
        UserData.Activity = Activity;
    }

    String getGender() {
        return Gender;
    }

    void setGender(String Gender) {
        UserData.Gender = Gender;
    }

    String getDate() {
        return Date;
    }

    void setDate(String Date) {
        UserData.Date = Date;
    }

    String getHeight() {
        return Height;
    }

    void setHeight(String Height) {
        UserData.Height = Height;
    }

    String getWeight() {
        return Weight;
    }

    void setWeight(String Weight) {
        UserData.Weight = Weight;
    }

    String getEmail() {
        return Email;
    }

    void setEmail(String Email) {
        UserData.Email = Email;
    }

    String getPassword() {
        return Password;
    }

    void setPassword(String Password) {
        UserData.Password = Password;
    }
    String getUserName() {
        return UserName;
    }

    void setUserName(String UserName) {
        UserData.UserName = UserName;
    }

    String getName() {
        return Name;
    }

    void setName(String Name) {
        UserData.Name = Name;
    }

    String getKcal() {
        return Kcal;
    }

    void setKcal(String Kcal) {
        UserData.Kcal = Kcal;
    }

    String getFat() {
        return Fat;
    }
    void setFat(String Fat) {
        UserData.Fat = Fat;
    }

    String getCarbs() {
        return Carbs;
    }

    void setCarbs(String Carbs) {
        UserData.Carbs = Carbs;
    }

    String getSugar() {
        return Sugar;
    }

    void setSugar(String Sugar) {
        UserData.Sugar = Sugar;
    }

    String getWeightProduct() {
        return WeightProduct;
    }

    void setWeightProduct(String WeightProduct) {
        UserData.WeightProduct = WeightProduct;
    }


}
