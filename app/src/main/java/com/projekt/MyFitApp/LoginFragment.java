package com.projekt.MyFitApp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/// Class Handle Login system Getting API Response Checking if user exist if yes Navigating to Logged part of App
public class LoginFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private EditText username, password; ///XML EditText in fragment_first Layout
    private DataValidate dataValidate = new DataValidate();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    /// Handling Register Login Buttons
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button LoginButton = view.findViewById(R.id.LoginButton);
        Button RegisterButton = view.findViewById(R.id.RegisterButton);
        username = view.findViewById(R.id.LoginUser);
        password = view.findViewById(R.id.LoginPassword);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                login.execute();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    ///Class connect to API to check if data that user provide exist in DataBase
    public class Login extends AsyncTask<String, String, String> {
        String ToastString = "";
        Boolean LoginValid = false;

        UserData userData = new UserData();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /// Responses By Toast
        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (LoginValid) {
                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_loggedFragment);
            }
        }

        /// Getting Data from Edit Texts and Checking if there is user with the same Username and Password
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String username = LoginFragment.this.username.getText().toString();
            String password = LoginFragment.this.password.getText().toString();
            dataValidate.setUserName(username);
            dataValidate.setPassword(password);
            ///Checking if Data send by user is not empty
            if (username.trim().equals("") || password.trim().equals("")) /// Checking if TextFields are not empty
            {
                ToastString = "Please enter Username and Password"; ///Setting String that will be used for Toast
                ///Using LoginDataCheck method (Regex) for data provided by user
            } else if (!dataValidate.LoginDataCheck()) {
                ToastString = "Please enter Valid Username and Password";
            } else {


                ///Connecting to Api checking if Login Data are Valid
                String jsonString = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\"}";
                URL url = null;
                try {
                    url = new URL("http://192.168.0.100:3000/Login"); ///Api URL
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
                /// Handling API Response
                try (Response res = client.newCall(request).execute()) {
                    String content = Objects.requireNonNull(res.body()).string();
                    if (res.isSuccessful()) {
                        userData.setUserName(username);
                        ToastString = "Welcome"; ///Setting String that will be used for Toast
                        LoginValid = true; /// isSuccess Boolean true if Api respond with good code

                    } else
                        /// Api response other than 200-299 http status code
                        switch (content) ///Handling Response Codes that are not successful
                        {
                            case "Not Found":
                                ToastString = "User not Found"; ///Setting String that will be used for Toast
                                break;
                            case "Internal Server Error":
                                ToastString = "Something went wrong"; ///Setting String that will be used for Toast
                                break;
                            case "Unauthorized":
                                ToastString = "Wrong Password"; ///Setting String that will be used for Toast
                                break;
                            default:
                                ToastString = "Try again Later"; ///Setting String that will be used for Toast
                                break;
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                    ToastString = "Check your connection"; ///Setting String that will be used for Toast

                }
            }

            return ToastString;
        }
    }
}