package com.projekt.MyFitApp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoggedFragment extends Fragment {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String UserName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ///Changing Toolbar Title
    ///Executing Class that provide data from API about current logged user
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).setActionBarTitle("Main Page");
        setHasOptionsMenu(true);
        ComputeBMI computeBMI = new ComputeBMI();
        computeBMI.execute();
        return inflater.inflate(R.layout.fragment_logged, container, false);
    }
    /// Getting Data for Calculate BMI of current User
    /// Calculating provided data to get User BMI and Setting TextView with user Name
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        UserData userData = new UserData();
        String weight = userData.getWeight();
        String height = userData.getHeight();
        String gender = userData.getGender();
        String date = userData.getDate();
        String Welcome = "Hey  " + userData.getUserName();
        TextView textView = view.findViewById(R.id.Welcome);
        final BmiCalc bmiCalc = new BmiCalc(getContext());
        bmiCalc.CalculateBMR(weight, height, gender, date);
        textView.setText(Welcome);
        super.onViewCreated(view, savedInstanceState);

    }

    ///Setting up Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    /// Handling Toolbar Menu Buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) /// Handle Menu item selection
        {
            case R.id.BMIScore:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_BMIFragment);
                return true;
            case R.id.Scanner:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_scannerFragment);
                return true;
            case R.id.Logout:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ///Class Getting current logged User Data from API
    public class ComputeBMI extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false; /// isSuccess Boolean

        @Override
        protected void onPreExecute() {
            UserData userData = new UserData();
            UserName = userData.getUserName();
            super.onPreExecute();
        }
        /// Handling responses by Toast
        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(getActivity(), "Welcome " + UserName, Toast.LENGTH_LONG).show();
            }
        }

        /// Connecting to API and getting Data
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String jsonString = "{\"UserName\":\"" + UserName + "\"}";
            URL url = null;
            try {
                url = new URL("http://192.168.0.100:3000/BMI"); ///Api URL
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
                    JSONArray jsonArray = new JSONArray(content);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String weight = jsonObject.getString("Weight");
                    String height = jsonObject.getString("Height");
                    String Date = jsonObject.getString("Date");
                    String Gender = jsonObject.getString("Gender");
                    UserData userData = new UserData();
                    userData.setWeight(weight);
                    userData.setGender(Gender);
                    userData.setHeight(height);
                    userData.setDate(Date);

                    isSuccess = true;

                } else
                    /// Handling other then 200-299 https Responses
                    switch (content) ///Handling Response Codes that are not successful
                    {
                        case "Not Found":
                            z = "User not Found"; ///Setting String that will be used for Toast
                            break;
                        case "Internal Server Error":
                            z = "Something went wrong"; ///Setting String that will be used for Toast
                            break;
                        case "Unauthorized":
                            z = "Wrong Password"; ///Setting String that will be used for Toast
                            break;
                        default:
                            z = "Try again Later"; ///Setting String that will be used for Toast
                            break;
                    }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                z = "Check your connection"; ///Setting String that will be used for Toast
            }
            return z; /// returning String Value to Toast
        }


    }
}