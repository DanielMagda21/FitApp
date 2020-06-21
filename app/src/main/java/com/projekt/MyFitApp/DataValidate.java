package com.projekt.MyFitApp;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidate {


    private static String Height;
    private static String Weight;
    private static String Email;
    private static String Password;
    private static String UserName;
    private static String Date;


    void setDate(String Date) {
        DataValidate.Date = Date;
    }

    void setHeight(String Height) {
        DataValidate.Height = Height;
    }

    void setWeight(String Weight) {
        DataValidate.Weight = Weight;
    }

    void setEmail(String Email) {
        DataValidate.Email = Email;
    }

    void setPassword(String Password) {
        DataValidate.Password = Password;
    }

    void setUserName(String UserName) {
        DataValidate.UserName = UserName;
    }


    /// Using Regular Expression to check Data from Login Fragment
    boolean LoginDataCheck() {
        String patternLog = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression  pattern
        Pattern patLog = Pattern.compile(patternLog); ///Compiling Patter with RegEX  pattern
        Matcher matcher = patLog.matcher(UserName); /// Matcher object for user Input
        Matcher matcher1 = patLog.matcher(Password);
        return matcher.matches() && matcher1.matches();
    }


    /// Regular Expression to check Data from Register Fragment
    boolean RegisterDataCheck() {
        String patternName = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression  pattern
        String patternPass = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression  pattern
        Pattern patName = Pattern.compile(patternName); ///Compiling Patter with RegEX pattern
        Pattern patPass = Pattern.compile(patternPass); ///Compiling Patter with RegEX pattern
        Matcher matcherF = patName.matcher(UserName); /// Matcher object for user Input
        Matcher matcherP = patPass.matcher(Password); /// Matcher object for user Input
        return matcherF.matches() && matcherP.matches();
    }
    ///Using Regular Expression to check Email from Register Fragment
    boolean EmailCheck() {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();

    }
    ///Using Regular Expression to check Date from Register Fragment
    boolean DateCheck() {
        String patternNumber = "(19[0-8][0-9]|199[0-9]|20[0-4][0-9]|2050)";
        Pattern patNumb = Pattern.compile(patternNumber);
        Matcher matcherNumb= patNumb.matcher(Date);
        return matcherNumb.matches();
    }
    ///Using Regular Expression to check Weight from Register Fragment
    boolean WeightCheck() {
        String patternWeight = "(3[5-9]|[4-9][0-9]|1[0-9]{2}|200)";
        Pattern patWeight = Pattern.compile(patternWeight);
        Matcher matcherWeight= patWeight.matcher(Weight);
        return matcherWeight.matches();
    }
    ///Using Regular Expression to check Height from Register Fragment
    boolean HeightCheck() {
        String patternHeight = "(1[0-9]{2}|2[0-4][0-9]|250)";
        Pattern patHeight = Pattern.compile(patternHeight);
        Matcher matcherHeight= patHeight.matcher(Height);
        return matcherHeight.matches();
    }
}

