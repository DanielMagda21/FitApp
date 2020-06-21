package com.projekt.MyFitApp;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/// Class gather all User data and Using API
/// check if User already exist if not then Create New User
public class RegisterFragment5 extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String email, password, name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    ///Setting Toolbar Title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Register"); /// Setting Toolbar Title
        return inflater.inflate(R.layout.fragment_register5, container, false);
    }
    /// Getting User Data from EditText fields
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText emailTxt = view.findViewById(R.id.EmailAddress);
        final EditText passwordTxt = view.findViewById(R.id.Password);
        final EditText nameTxt = view.findViewById(R.id.PersonName);
        Button registerButton = view.findViewById(R.id.Register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTxt.getText().toString();
                password = passwordTxt.getText().toString();
                name = nameTxt.getText().toString();
                Register register = new Register();
                register.execute();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    /// Handling Toolbar Button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) /// Handle Menu item selection
        {
            case R.id.NextButton:
                Toast.makeText(getActivity(), "Press Register Button", Toast.LENGTH_LONG).show();
                return true;
            case R.id.BackButton:
                NavHostFragment.findNavController(RegisterFragment5.this)
                        .navigate(R.id.action_registerFragment5_to_registerFragment4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /// Class Check if Data provided by User is Valid
    /// Then using API checks if user with this Data is not already in Base
    /// If not Creating new User in DataBase
    public class Register extends AsyncTask<String, String, String> {
        String z = "";

        Boolean isSuccess = false; /// isSuccess Boolean

        @Override
        protected void onPreExecute() {
            UserData userData = new UserData();
            userData.setEmail(email);
            userData.setPassword(password);
            userData.setUserName(name);
            super.onPreExecute();

        }

        /// Nav to login fragment if isSuccess Return True
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show(); ///Toast if isSuccess = false
            if (isSuccess) {
                Toast.makeText(getActivity(), "Register Successful", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(RegisterFragment5.this)
                        .navigate(R.id.action_registerFragment5_to_loginFragment);
            }
        }
        /// Gathering all Data from other Fragment and Sending Request to API
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            UserData userData = new UserData();
            String Goal = userData.getGoal();
            String Activity = userData.getActivity();
            String Gender = userData.getGender();
            String Date = userData.getDate();
            String Height = userData.getHeight();
            String Weight = userData.getWeight();
            String Email = userData.getEmail();
            String Password = userData.getPassword();
            String UserName = userData.getUserName();
            DataValidate dataValidate = new DataValidate();
            dataValidate.setUserName(UserName);
            dataValidate.setEmail(Email);
            dataValidate.setPassword(Password);
            if (!dataValidate.EmailCheck()) {
                z = "Email not valid";
            }
            else if (dataValidate.RegisterDataCheck()) {
                String jsonString = "{\"UserName\":\"" + UserName + "\",\"Email\":\"" + Email + "\",\"Password\":\"" + Password + "\",\"Goal\":\"" + Goal + "\",\"Activity\":\"" + Activity + "\",\"Gender\":\"" + Gender + "\",\"Date\":\"" + Date + "\",\"Height\":\"" + Height + "\",\"Weight\":\"" + Weight + "\"}";

                URL url = null;
                try {
                    url = new URL("http://192.168.0.100:3000/Register");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(jsonString, JSON);
                assert url != null;
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                try (Response res = client.newCall(request).execute()) {
                    String content = Objects.requireNonNull(res.body()).string();
                    if (res.isSuccessful()) {
                        z = "Welcome";  ///Handling Response Codes that are not successful
                        isSuccess = true;  /// isSuccess Boolean true if Api respond with good code
                    } else

                        switch (content) ///Handling Response Codes that are not successful
                        {
                            case "Conflict":
                                z = " "+userData.getGoal(); ///Setting String that will be used for Toast
                                break;
                            case "Internal Server Error":
                                z = "Something went wrong"; ///Setting String that will be used for Toast
                                break;
                            default:
                                z = "Try again Later"; ///Setting String that will be used for Toast
                                break;
                        }


                } catch (IOException e) {
                    z = userData.getGender();

                    ///Setting String that will be used for Toast
                    e.printStackTrace();
                }

            }


            return z;  /// returning String Value to Toast
        }


    }
}