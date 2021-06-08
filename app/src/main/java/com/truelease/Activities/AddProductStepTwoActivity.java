package com.truelease.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.truelease.Adapters.CurrencyAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.CurrencyData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.CurrencyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductStepTwoActivity extends AppCompatActivity {

    EditText price, deposit, marketPrice, minPeriod, maxPeriod;
    String strPrice = "", strDeposit = "", strMarketPrice = "", strMinPeriod = "", strMaxPeriod = "", strID = "";
    Spinner durationSpinner, currencySpinner;
    String[] durationList = {"per day", "per week", "per month"};
    List<CurrencyData> currencyDataArrayList;
    Button outlinedButton;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_step_two);


        price = findViewById(R.id.price);
        deposit = findViewById(R.id.deposit);
        marketPrice = findViewById(R.id.marketPrice);
        minPeriod = findViewById(R.id.minPeriod);
        maxPeriod = findViewById(R.id.maxPeriod);
        durationSpinner = findViewById(R.id.durationSpinner);
        currencySpinner = findViewById(R.id.currencySpinner);
        outlinedButton = findViewById(R.id.outlinedButton);

        ImageView back = findViewById(R.id.back);
        Button btnNext = findViewById(R.id.btnNext);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        outlinedButton.setOnClickListener(v -> finish());


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, durationList);
        durationSpinner.setAdapter(arrayAdapter);


        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = (String) adapterView.getItemAtPosition(i);

                if (item.equals("per day")) {
                    strID = "2";
                    minPeriod.setHint("Min Period (day)");
                    maxPeriod.setHint("Max Period (day)");
                } else if (item.equals("per week")) {
                    strID = "3";
                    minPeriod.setHint("Min Period (week)");
                    maxPeriod.setHint("Max Period (week)");
                } else {
                    strID = "4";
                    minPeriod.setHint("Min Period (month)");
                    maxPeriod.setHint("Max Period (month)");
                }

                SharedHelper.putKey(AddProductStepTwoActivity.this, AppConstats.ADD_DURATION_OF_PRODUCT, strID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (currencyDataArrayList.size() > 0) {
                    String cid = currencyDataArrayList.get(position).getId();
                    String selectedCurrency = currencyDataArrayList.get(position).getCode();
                    String currencySym = currencyDataArrayList.get(position).getSymbol();

                    Log.e("sjsxdsslsyuey", cid + "," + selectedCurrency + "," + currencySym);
                    SharedHelper.putKey(AddProductStepTwoActivity.this, AppConstats.ADD_CURRENCY_CODE, cid);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




       /* List<String> l = new ArrayList<String>();
        l.add("8am");
        l.add("8pm");
        l.add("8am");
        l.add("1pm");
        l.add("3pm");
        l.add("2pm");
        Collections.sort(l, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                try {
                    return new SimpleDateFormat("hha").parse(o1).compareTo(new SimpleDateFormat("hha").parse(o2));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });*/


        showCurrency();

        btnNext.setOnClickListener(view -> {

            strPrice = price.getText().toString().trim();
            strDeposit = deposit.getText().toString().trim();
            strMarketPrice = marketPrice.getText().toString().trim();
            strMinPeriod = minPeriod.getText().toString().trim();
            strMaxPeriod = maxPeriod.getText().toString().trim();


            if (TextUtils.isEmpty(strPrice) || TextUtils.isEmpty(strDeposit) || TextUtils.isEmpty(strMarketPrice) || TextUtils.isEmpty(strMinPeriod) || TextUtils.isEmpty(strMaxPeriod)) {
                Toast.makeText(AddProductStepTwoActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                int maxPeriod = Integer.parseInt(strMaxPeriod);
                int minPeriod = Integer.parseInt(strMinPeriod);
                int mPrice = Integer.parseInt(strPrice);
                int mDeposit = Integer.parseInt(strDeposit);
                int mMarkerPrice = Integer.parseInt(strMarketPrice);


                if (strID.equals("1")) {

                    if (mDeposit > mPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Deposit should not exceed amount");

                    } else if (maxPeriod > 24) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please select less than 24 hours");
                    } else if (mPrice > mMarkerPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please check amount & market price");

                    } else if (minPeriod >= maxPeriod) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "min hours should be less than & not equal to max hours");

                    } else {
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_PRICE, strPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DEPOSIT, strDeposit);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MARKET_PRICE, strMarketPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MIN_PERIOD, strMinPeriod);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MAX_PERIOD, strMaxPeriod);
                        // SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DELIVER_IN, strDeliverIn);
                        startActivity(new Intent(getApplicationContext(), AddParametersActivity.class));
                        Animatoo.animateZoom(AddProductStepTwoActivity.this);
                    }
                } else if (strID.equals("2")) {


                    if (mDeposit > mPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Deposit should not exceed amount");

                    } else if (maxPeriod > 30) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please select less than 31 days");


                    } else if (mPrice > mMarkerPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please check amount & market price");

                    } else if (minPeriod >= maxPeriod) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "min days should be less than & not equal to max days");

                    } else if (minPeriod < 1) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "You have to select atleast 1 days");

                    } else {
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_PRICE, strPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DEPOSIT, strDeposit);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MARKET_PRICE, strMarketPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MIN_PERIOD, strMinPeriod);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MAX_PERIOD, strMaxPeriod);
                        // SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DELIVER_IN, strDeliverIn);
                        startActivity(new Intent(getApplicationContext(), AddParametersActivity.class));
                        Animatoo.animateZoom(AddProductStepTwoActivity.this);
                    }

                } else if (strID.equals("3")) {

                    if (mDeposit > mPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Deposit should not exceed amount");

                    } else if (maxPeriod > 4) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please select less than 4 weeks");


                    } else if (mPrice > mMarkerPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please check amount & market price");

                    } else if (minPeriod >= maxPeriod) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "min week should be less than & not equal to max week");

                    } else {
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_PRICE, strPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DEPOSIT, strDeposit);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MARKET_PRICE, strMarketPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MIN_PERIOD, strMinPeriod);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MAX_PERIOD, strMaxPeriod);
                        // SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DELIVER_IN, strDeliverIn);
                        startActivity(new Intent(getApplicationContext(), AddParametersActivity.class));
                        Animatoo.animateZoom(AddProductStepTwoActivity.this);
                    }


                } else {

                    if (mDeposit > mPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Deposit should not exceed amount");

                    } else if (maxPeriod > 12) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please select less and =to 12 months");


                    } else if (mPrice > mMarkerPrice) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "Please check amount & market price");

                    } else if (minPeriod >= maxPeriod) {
                        ReturnErrorToast.showWarningToast(AddProductStepTwoActivity.this, "min months should be less than & not equal to max months");

                    } else {
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_PRICE, strPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DEPOSIT, strDeposit);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MARKET_PRICE, strMarketPrice);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MIN_PERIOD, strMinPeriod);
                        SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_MAX_PERIOD, strMaxPeriod);
                        // SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DELIVER_IN, strDeliverIn);
                        startActivity(new Intent(getApplicationContext(), AddParametersActivity.class));
                        Animatoo.animateZoom(AddProductStepTwoActivity.this);
                    }

                }


            }


        });


    }


    public void showCurrency() {
        Call<CurrencyModel> call = APIClient.getAPIClient().showCurrencies();
        call.enqueue(new Callback<CurrencyModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrencyModel> call, @NonNull Response<CurrencyModel> response) {

                if (!response.isSuccessful()) {
                    Log.e("dkjsjdk", "iwsd" + response.code());
                }

                CurrencyModel model = response.body();

                if (model != null) {
                    if (model.getResult()) {
                        currencyDataArrayList = new ArrayList<>();
                        List<CurrencyModel.Datum> dataList = model.getData();

                        if (dataList.size() > 0) {

                            for (CurrencyModel.Datum data : dataList) {
                                CurrencyData currencyData = new CurrencyData();
                                currencyData.setId(data.getCurrencyCodeID());
                                currencyData.setCode(data.getCode());
                                currencyData.setSymbol(data.getSymbol());

                                currencyDataArrayList.add(currencyData);
                            }


                            Collections.sort(currencyDataArrayList, new Comparator<CurrencyData>() {
                                @Override
                                public int compare(CurrencyData o1, CurrencyData o2) {

                                    String currencyOne = o1.getCode().toUpperCase();
                                    String currencyTwo = o2.getCode().toUpperCase();
                                    return currencyOne.compareTo(currencyTwo);

                                }
                            });

                        }

                        CurrencyAdapter currencyAdapter = new CurrencyAdapter(AddProductStepTwoActivity.this, currencyDataArrayList);
                        currencySpinner.setAdapter(currencyAdapter);
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<CurrencyModel> call, @NonNull Throwable t) {

            }
        });
    }


}