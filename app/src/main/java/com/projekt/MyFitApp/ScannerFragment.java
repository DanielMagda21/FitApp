package com.projekt.MyFitApp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

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
///Class Get Barcode value from Scanned Product
///Handles if product is already in Base if yes user can check Nutrition Data
/// If product if not in Base User Can Add it with Data he provide
public class ScannerFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private CodeScanner mCodeScanner;
    private String BarResult;
    private FrameLayout Scanner;
    private LinearLayout ProductData;
    private EditText Name, Kcal, Fat, Carbs, Sugar, Weight;
    private Button NewProduct;
    private Boolean IsNew = true;
    @Override
    /// Handles User BarCode Scanner
    /// Set Barcode value in Variable
    /// Using ScannerProduct Class to check if product is already in DataBase
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View v = inflater.inflate(R.layout.fragment_scanner, container, false);
        CodeScannerView scannerView = v.findViewById(R.id.scanner_view);
        assert activity != null;
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BarResult = result.getText();
                        ScannerProduct scannerProduct = new ScannerProduct();
                        scannerProduct.execute();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return v;
    }

    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        ProductData = view.findViewById(R.id.ProductData);
        Scanner = view.findViewById(R.id.Scanner);
        Button getData = view.findViewById(R.id.GetData);
        NewProduct = view.findViewById(R.id.NewProduct);
        Name = view.findViewById(R.id.Name);
        Kcal = view.findViewById(R.id.Kcal);
        Fat = view.findViewById(R.id.Fat);
        Carbs = view.findViewById(R.id.Carbs);
        Sugar = view.findViewById(R.id.Sugar);
        Weight = view.findViewById(R.id.Weight);
        /// If product is not in Base by clicking Button user can provide Data and Add Product Using API
        NewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scanner.setVisibility(View.INVISIBLE);
                ProductData.setVisibility(View.VISIBLE);
                if(!IsNew)
                {   ScannerNewProduct newProduct = new ScannerNewProduct();
                    newProduct.execute();

                }

            }
        });
        /// If product exist in Base getting product Data and Using ScannerData class
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();
                String name = userData.getName();
                String kcal = userData.getKcal();
                String fat = userData.getFat();
                String carbs = userData.getCarbs();
                String sugar = userData.getSugar();
                String weight = userData.getWeightProduct();
                final ScannerData scannerData = new ScannerData(getContext());
                scannerData.SetProductData(name, kcal, fat, carbs, sugar, weight);
            }
        });



        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

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
                NavHostFragment.findNavController(ScannerFragment.this)
                        .navigate(R.id.action_scannerFragment_to_BMIFragment);
                return true;
            case R.id.Main:
                NavHostFragment.findNavController(ScannerFragment.this)
                        .navigate(R.id.action_scannerFragment_to_loggedFragment);
                return true;
            case R.id.Logout:
                NavHostFragment.findNavController(ScannerFragment.this)
                        .navigate(R.id.action_scannerFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /// ScannerProduct Check if Product is in Base if yes Getting Data about it from API
    /// If not setting boolean value that allow user add this Product to Base
    public class ScannerProduct extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false; /// isSuccess Boolean

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }
        /// Hiding Scanner View and Showing View that user can preview Data
        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Scanner.setVisibility(View.INVISIBLE);
                ProductData.setVisibility(View.VISIBLE);
            }
        }

        /// Request for Api with Scanned BarCode
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String jsonString = "{\"BarCode\":\"" + BarResult + "\"}";
            URL url = null;
            try {
                url = new URL("http://192.168.0.100:3000/Product"); ///Api URL
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
                    String name = jsonObject.getString("Name");
                    String kcal = jsonObject.getString("Kcal");
                    String fat = jsonObject.getString("Fat");
                    String carbs = jsonObject.getString("Carbs");
                    String sugar = jsonObject.getString("Sugar");
                    String weight = jsonObject.getString("Weight");
                    UserData userData = new UserData();
                    userData.setName(name);
                    userData.setKcal(kcal);
                    userData.setFat(fat);
                    userData.setCarbs(carbs);
                    userData.setSugar(sugar);
                    userData.setWeightProduct(weight);
                    isSuccess = true;

                } else
                    /// Http Responses other than 200-299
                    switch (content) ///Handling Response Codes that are not successful
                    {
                        case "Conflict":
                            z = "Product Exist"; ///Setting String that will be used for Toast
                            break;
                        case "Internal Server Error":
                            z = "Something went wrong"; ///Setting String that will be used for Toast
                            break;
                        case "Unauthorized":
                            z = "Wrong Password"; ///Setting String that will be used for Toast
                            break;
                        default:
                            z = "Product not found"; ///Setting String that will be used for Toast
                            IsNew = false;
                            break;
                    }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                z = "Check your connection"; ///Setting String that will be used for Toast
            }
            return z; /// returning String Value to Toast
        }


    }
    /// IF product is new Adding it with User provided Data
    public class ScannerNewProduct extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false; /// isSuccess Boolean

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(getActivity(), "New product in Base", Toast.LENGTH_SHORT).show();
            }
        }

        /// Getting New Product Data
        /// Using API Inserting new Product in DataBase
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String Name = ScannerFragment.this.Name.getText().toString();
            String Kcal = ScannerFragment.this.Kcal.getText().toString();
            String Fat = ScannerFragment.this.Fat.getText().toString();
            String Carbs = ScannerFragment.this.Carbs.getText().toString();
            String Sugar = ScannerFragment.this.Sugar.getText().toString();
            String Weight = ScannerFragment.this.Weight.getText().toString();
            if (Name.isEmpty() || Kcal.isEmpty() || Fat.isEmpty() || Carbs.isEmpty() || Sugar.isEmpty() || Weight.isEmpty()) {
                z="Enter Data";
            } else {
                String jsonString = "{\"BarCode\":\"" + BarResult + "\",\"Name\":\"" + Name + "\",\"Kcal\":\"" + Kcal + "\",\"Fat\":\"" + Fat +
                        "\",\"Carbs\":\"" + Carbs + "\",\"Sugar\":\"" + Sugar + "\",\"Weight\":\"" + Weight + "\"}";
                URL url = null;
                try {
                    url = new URL("http://192.168.0.100:3000/ProductNew"); ///Api URL
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

                        isSuccess = true;

                    } else

                        switch (content) ///Handling Response Codes that are not successful
                        {
                            case "Conflict":
                                z = "Product Exist"; ///Setting String that will be used for Toast
                                break;
                            case "Internal Server Error":
                                z = "Something went wrong"; ///Setting String that will be used for Toast
                                break;
                            case "Unauthorized":
                                z = "Wrong Password"; ///Setting String that will be used for Toast
                                break;
                            default:
                                z = "error"; ///Setting String that will be used for Toast

                                break;
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                    z = "Check your connection"; ///Setting String that will be used for Toast
                }

            }
            return z; /// returning String Value to Toast
        }

    }
}