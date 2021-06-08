package com.truelease.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.Status;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.truelease.Adapters.SliderAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.SliderImageData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.InternetConnection.InternetConnectionInterface;
import com.truelease.Others.InternetConnection.InternetConnectivity;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.Others.Utils;
import com.truelease.R;
import com.truelease.RetrofitModel.AddCartModel;
import com.truelease.RetrofitModel.AgreementModel;
import com.truelease.RetrofitModel.NeedHelpModel;
import com.truelease.RetrofitModel.ShowProductsModel;
import com.truelease.RetrofitModel.VerificationStatusModel;
import com.truelease.Room.RoomWishList.WishListViewmodel;
import com.truelease.Room.RoomWishList.Wishlist;
import com.truelease.User.UserData;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailsActivity extends AppCompatActivity {


    public static final String HOURS = "1";
    public static final String DAYS = "2";
    public static final String WEEK = "3";
    public static final String MONTH = "4";
    public static ShowProductsModel.Datum showData;
    SliderView imageSlider;
    TextView share, productName, productDescription, final_Amount, deposit, marketPrice, prCondition, dType, prMinMax, tenure;
    TextView prPower, prWeight, prBrand, prHeight, prWidth, prLength, maxMinPeriod, txtAddress, cityName, timeAv;
    Vibrator vibrator;
    RelativeLayout back, rel;
    TickerView tickerView;
    NestedScrollView scrollView;
    FloatingActionButton fab;
    Button btnAddtocart, btnSubmit;
    MaterialButton btnCalculate;
    ImageView sharing, heart_liked, heartI, more;
    String ownerId = "";
    AgreementModel mAgreementModel;
    ArrayList<SliderImageData> sliderImageDataList;
    EditText wNeed, name, phone, email, startDate, endDate;
    String strNeed = "", strName = "", strPhone = "", strEmail = "", strStartDate = "", strEndDate = "", strCityName = "";
    String strDiff = "";
    String mStartDate = "", mEndDate = "", totalAmount = "";
    VerificationStatusModel verificationStatusModel = null;

    Button btnStart;
    ProgressBar progressBarOne;
    TextView textViewProgressOne;
    int downloadIdOne;
    public static ImageView whatsApp, facebook;

    WishListViewmodel wishListViewmodel;

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        imageSlider = findViewById(R.id.imageSlider);
        btnAddtocart = findViewById(R.id.btnAddtocart);
        fab = findViewById(R.id.fab);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        heart_liked = findViewById(R.id.heart_liked);
        prCondition = findViewById(R.id.prCondition);
        dType = findViewById(R.id.dType);
        prMinMax = findViewById(R.id.prMinMax);
        maxMinPeriod = findViewById(R.id.maxMinPeriod);
        more = findViewById(R.id.more);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnStart = findViewById(R.id.btnStart);
        txtAddress = findViewById(R.id.txtAddress);

        progressBarOne = findViewById(R.id.progressBarOne);
        textViewProgressOne = findViewById(R.id.textViewProgressOne);
        cityName = findViewById(R.id.cityName);
        timeAv = findViewById(R.id.timeAv);


        back = findViewById(R.id.back);
        tickerView = findViewById(R.id.tickerView);
        scrollView = findViewById(R.id.scrollView);
        share = findViewById(R.id.share);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        final_Amount = findViewById(R.id.final_Amount);
        deposit = findViewById(R.id.deposit);
        marketPrice = findViewById(R.id.marketPrice);

        wNeed = findViewById(R.id.wNeed);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        btnSubmit = findViewById(R.id.btnSubmit);
        sharing = findViewById(R.id.sharing);
        heartI = findViewById(R.id.heartI);


        prPower = findViewById(R.id.prPower);
        prWeight = findViewById(R.id.prWeight);
        prBrand = findViewById(R.id.prBrand);
        prHeight = findViewById(R.id.prHeight);
        prWidth = findViewById(R.id.prWidth);
        prLength = findViewById(R.id.prLength);
        tenure = findViewById(R.id.tenure);
        rel = findViewById(R.id.rel);

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) {
            String link = data.toString();
            if (!link.equals("")) {
                String prId = link.replaceAll("[^0-9]", "");

                Log.e("csckknskc", prId + "," + UserData.getUserID(ProductDetailsActivity.this));
                Map<String, String> param = new HashMap<>();
                param.put("productID", prId);
                param.put("user_id", UserData.getUserID(ProductDetailsActivity.this));
                ShowProductDetail(param);
            }
        } else {

            Map<String, String> param = new HashMap<>();
            param.put("productID", UserData.getProductID(ProductDetailsActivity.this));
            param.put("user_id", UserData.getUserID(ProductDetailsActivity.this));

            ShowProductDetail(param);
        }


        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        wishListViewmodel = new ViewModelProvider(ProductDetailsActivity.this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WishListViewmodel.class);


        back.setOnClickListener(view -> finish());

        more.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShowImagesActivity.class)));


        imageSlider.setIndicatorAnimation(IndicatorAnimations.DROP);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        imageSlider.setIndicatorSelectedColor(Color.CYAN);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();

        showVerStatus();
        showAgreement();
        onClickListenerDownloadFile();


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                fab.hide();
            } else {
                fab.show();
            }
        });


        heartI.setOnClickListener(view -> {

            if (UserData.getUserID(getApplicationContext()).equals("")) {
                Toast.makeText(ProductDetailsActivity.this, "Login first", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, String> param = new HashMap<>();
                param.put("userID", UserData.getUserID(getApplicationContext()));
                param.put("productID", UserData.getProductID(getApplicationContext()));


                if (showData != null) {
                    String imageP = "";

                    if (showData.getImage() != null) {
                        for (ShowProductsModel.Datum.Image image : showData.getImage()) {
                            imageP = showData.getImage().get(0).getPath() + showData.getImage().get(0).getImage();
                        }
                        Log.e("kdslkd", UserData.getUserID(ProductDetailsActivity.this) + "");
                        Wishlist wishlist = new Wishlist(showData.getProductID(), UserData.getUserID(ProductDetailsActivity.this), showData.getProduct(), showData.getMarketPrice(), imageP, "1");
                        wishListViewmodel.insert(wishlist);
                        heart_liked.setVisibility(View.VISIBLE);
                        heartI.setVisibility(View.GONE);
                        Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


        startDate.setOnClickListener(view -> {

            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("True lease");
            CalendarConstraints.Builder con = new CalendarConstraints.Builder();
            CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
            builder.setCalendarConstraints(con.setValidator(dateValidator).build());
            MaterialDatePicker<Long> picker = builder.build();
            picker.show(getSupportFragmentManager(), picker.toString());

            picker.addOnPositiveButtonClickListener(selection -> {

                Date d = new Date(selection);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                startDate.setText(dateFormat.format(d));
                mStartDate = dateFormat.format(d);


            });



/*
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            CalendarConstraints.Builder con = new CalendarConstraints.Builder();
            CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
            builder.setCalendarConstraints(con.setValidator(dateValidator).build());
            builder.setTitleText("True lease");
            builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

            picker.show(getSupportFragmentManager(), picker.toString());


            picker.addOnPositiveButtonClickListener(selection -> {

                Long first = selection.first;
                Long sec = selection.second;
                Date d = new Date(first);
                Date d2 = new Date(sec);


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                startDate.setText(dateFormat.format(d));
                endDate.setText(dateFormat.format(d2));

                //mStart and mEndDate for month
                mStartDate = dateFormat.format(d);
                mEndDate = dateFormat.format(d2);
                ///////////////////////////////

                int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), dateFormat.format(d), dateFormat.format(d2));

                int daysDiff = dateDifference + 1;
                strDiff = String.valueOf(daysDiff);

                if (showData != null) {

                    String currencySymbol = "";

                    if (showData.getCurrencyDetail().size() > 0) {

                        for (ShowProductsModel.Datum.CurrencyDetail currencyDetail : showData.getCurrencyDetail()) {
                            currencySymbol = currencyDetail.getSymbol();

                        }

                    }


                    if (showData.getRentPerMonth().matches("[0-9]+")){

                        double famount = getTotalAmount(showData.getPrice_type(), Double.parseDouble(showData.getMinPeriod()), Double.parseDouble(showData.getMaxPeriod()), daysDiff, Double.parseDouble(showData.getRentPerMonth()));
                        final_Amount.setText(currencySymbol + " " + famount);
                        totalAmount = String.valueOf(famount);

                        Toast.makeText(this, strDiff + " Days Selected", Toast.LENGTH_SHORT).show();

                    }else {

                        ReturnErrorToast.showWarningToast(ProductDetailsActivity.this,"Invalid data");

                    }





                }


            });*/
        });


        endDate.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("True lease");
            CalendarConstraints.Builder con = new CalendarConstraints.Builder();
            CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
            builder.setCalendarConstraints(con.setValidator(dateValidator).build());
            MaterialDatePicker<Long> picker = builder.build();
            picker.show(getSupportFragmentManager(), picker.toString());


            picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {

                    Date d = new Date(selection);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    endDate.setText(dateFormat.format(d));
                    mEndDate = dateFormat.format(d);
                }
            });
        });


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStartDate.equals("") || mEndDate.equals("")) {
                    Toast.makeText(ProductDetailsActivity.this, "Please select dates", Toast.LENGTH_SHORT).show();
                } else {

                    int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd-MM-yyyy"), mStartDate, mEndDate);
                    int daysDiff = dateDifference + 1;
                    strDiff = String.valueOf(daysDiff);

                    SharedHelper.putKey(getApplicationContext(), AppConstats.START_DATE, mStartDate);
                    SharedHelper.putKey(getApplicationContext(), AppConstats.END_DATE, mEndDate);

                    if (showData != null) {

                        String currencySymbol = "";

                        if (showData.getCurrencyDetail().size() > 0) {

                            for (ShowProductsModel.Datum.CurrencyDetail currencyDetail : showData.getCurrencyDetail()) {
                                currencySymbol = currencyDetail.getSymbol();

                            }

                        }


                        if (showData.getRentPerMonth().matches("[0-9]+")) {

                            double famount = getTotalAmount(showData.getPrice_type(), Double.parseDouble(showData.getMinPeriod()), Double.parseDouble(showData.getMaxPeriod()), daysDiff, Double.parseDouble(showData.getRentPerMonth()));
                            final_Amount.setText(currencySymbol + " " + famount);
                            totalAmount = String.valueOf(famount);

                            Toast.makeText(ProductDetailsActivity.this, strDiff + " Days Selected", Toast.LENGTH_SHORT).show();

                        } else {

                            ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Invalid data");

                        }


                    }

                }

            }
        });

        fab.setOnClickListener(view -> {

            if (UserData.getUserID(getApplicationContext()).equals("")) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                Toast.makeText(ProductDetailsActivity.this, "login first", Toast.LENGTH_SHORT).show();
                finishAffinity();
            } else {

                String ownerId = UserData.getReceiverID(getApplicationContext());
                String userId = UserData.getUserID(getApplicationContext());

                if (userId.equals(ownerId)) {
                    Toast.makeText(this, "This is your product. You can't chat with yourself", Toast.LENGTH_SHORT).show();
                } else {
                    SharedHelper.putKey(getApplicationContext(), AppConstats.CHAT_CLICKED_ON, "MyChatsOnProduct");
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                }
            }

        });


        btnSubmit.setOnClickListener(view -> {

            strName = name.getText().toString().trim();
            strNeed = wNeed.getText().toString().trim();
            strEmail = email.getText().toString().trim();
            strPhone = phone.getText().toString().trim();


            if (strName.equals("")) {
                Toast.makeText(ProductDetailsActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
            } else if (strNeed.equals("")) {
                Toast.makeText(ProductDetailsActivity.this, "Need is empty", Toast.LENGTH_SHORT).show();
            } else if (strEmail.equals("")) {
                Toast.makeText(ProductDetailsActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
            } else if (strPhone.equals("")) {
                Toast.makeText(ProductDetailsActivity.this, "Mobile number is empty", Toast.LENGTH_SHORT).show();
            } else {
                help(strName, strPhone, strEmail, strNeed);
            }
        });


        sharing.setOnClickListener(view -> {
            try {

                if (showData != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "1");
                    sendIntent.putExtra(Intent.EXTRA_REFERRER, "refferal");
                    String title = "True Lease";
                    String shareMessage = "\nHave a look at this amazing product available on rent on this app called True Lease.\n" +
                            "Download from Google Playstore:";
                    shareMessage = title + " " + shareMessage + " " + "http://www.truelease.com/product" + "/" + showData.getProductID();
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                    try {
                        startActivity(Intent.createChooser(sendIntent, "Choose one"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "Please Install App", Toast.LENGTH_LONG).show();
                    }
                } else {
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "No data found about this item");
                }


            } catch (Exception e) {
                //e.toString();
            }
        });


        facebook = findViewById(R.id.facebook);
        ImageView twitter = findViewById(R.id.twitter);
        whatsApp = findViewById(R.id.whatsApp);

        back.setOnClickListener(view -> finish());

        twitter.setOnClickListener(view -> {
            String tweetUrl = "https://twitter.com/intent/tweet?text=WRITE YOUR MESSAGE HERE &url="
                    + "https://www.google.com";
            Uri uri = Uri.parse(tweetUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });


    }


    public void onClickListenerDownloadFile() {

        btnStart.setOnClickListener(view -> {


            InternetConnectionInterface internetConnectionInterface = new InternetConnectivity();

            if (internetConnectionInterface.isConnected(ProductDetailsActivity.this)) {


                if (mAgreementModel != null) {

                    if (!mAgreementModel.getPath().equals("") && !mAgreementModel.getData().equals("")) {
                        String pdf = mAgreementModel.getPath() + mAgreementModel.getData();
                        Log.e("slkmcxslkc", pdf + "");

                        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                            PRDownloader.pause(downloadIdOne);
                            return;
                        }

                        btnStart.setEnabled(false);
                        progressBarOne.setVisibility(View.VISIBLE);
                        progressBarOne.setIndeterminate(true);
                        progressBarOne.getIndeterminateDrawable().setColorFilter(
                                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                            PRDownloader.resume(downloadIdOne);
                            return;
                        }

                        File file = new File(Environment.getExternalStorageDirectory(), "TrueLease Agreement");

                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        downloadIdOne = PRDownloader.download(pdf, file.getPath(), "agreement.pdf")
                                .build()
                                .setOnStartOrResumeListener(() -> {
                                    progressBarOne.setIndeterminate(false);
                                    btnStart.setEnabled(true);
                                    btnStart.setText("Pause");

                                })
                                .setOnPauseListener(() -> btnStart.setText("Resume"))
                                .setOnCancelListener(() -> {
                                    btnStart.setText("Start");
                                    progressBarOne.setProgress(0);
                                    textViewProgressOne.setText("");
                                    downloadIdOne = 0;
                                    progressBarOne.setIndeterminate(false);
                                })
                                .setOnProgressListener(progress -> {
                                    long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                    progressBarOne.setProgress((int) progressPercent);
                                    textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                    progressBarOne.setIndeterminate(false);
                                })
                                .start(new OnDownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        btnStart.setEnabled(false);

                                        btnStart.setText("Completed");
                                        progressBarOne.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        btnStart.setText("Start");
                                        Toast.makeText(getApplicationContext(), "Some Error occured", Toast.LENGTH_SHORT).show();
                                        textViewProgressOne.setText("");
                                        progressBarOne.setProgress(0);
                                        downloadIdOne = 0;
                                        progressBarOne.setIndeterminate(false);
                                        btnStart.setEnabled(true);
                                    }
                                });

                    } else {

                        ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Corrupt file");
                    }
                }


            } else {
                Toast.makeText(ProductDetailsActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        });

    }

    public void ShowProductDetail(Map<String, String> param) {


        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ProductDetailsActivity.this);

        Log.e("wuewewuoied", UserData.getProductID(ProductDetailsActivity.this) + "");
        Log.e("userID..", UserData.getUserID(ProductDetailsActivity.this) + "");


        Call<ShowProductsModel> call = APIClient.getAPIClient().showProductDetail(param);
        call.enqueue(new Callback<ShowProductsModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowProductsModel> call, @NonNull Response<ShowProductsModel> response) {
                sliderImageDataList = new ArrayList<>();
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ProductDetailsActivity.this);
                    dialogInterface.hideDialog();
                }
                ShowProductsModel productsModel = response.body();
                if (productsModel != null) {
                    if (productsModel.getResult()) {

                        String currencySymbol = "";
                        List<ShowProductsModel.Datum> dataList = productsModel.getData();
                        for (ShowProductsModel.Datum data : dataList) {

                            showData = data;
                            ownerId = data.getUserID();
                            SharedHelper.putKey(getApplicationContext(), AppConstats.OWNER_ID, ownerId);
                            timeAv.setText("Time Availability : " + data.getAvailability_time() + "-" + data.getAvailability_date());

                            if (data.getCurrencyDetail().size() > 0) {

                                for (ShowProductsModel.Datum.CurrencyDetail currencyDetail : data.getCurrencyDetail()) {
                                    currencySymbol = currencyDetail.getSymbol();
                                }

                            }
                            Log.e("rentSta", data.getRent_status() + "");

                            if (!data.getRent_status().equals("")) {

                                if (!data.getRent_status().equals("0")) {
                                    btnAddtocart.setText(getString(R.string.onRent));
                                    btnAddtocart.setEnabled(false);
                                    btnAddtocart.setBackgroundColor(Color.parseColor("#E71F19"));
                                }
                            }
                            
                            wishListViewmodel.getProductByID(data.getProductID(), UserData.getUserID(ProductDetailsActivity.this)).observe(ProductDetailsActivity.this, new Observer<List<Wishlist>>() {
                                @Override
                                public void onChanged(List<Wishlist> wishlists) {


                                    if (wishlists.isEmpty()) {
                                        Log.e("scxklsjc", "null");
                                    } else {


                                        for (int i = 0; i < wishlists.size(); i++) {
                                            Log.e("scxklsjc", wishlists.get(i).getProductName());
                                            Log.e("scxklsjc", wishlists.get(i).getProductID());
                                            if (wishlists.get(i).getStatus().equals("1")) {
                                                heartI.setVisibility(View.GONE);
                                                heart_liked.setVisibility(View.VISIBLE);
                                            } else {
                                                heartI.setVisibility(View.VISIBLE);
                                                heart_liked.setVisibility(View.VISIBLE);
                                            }
                                        }


                                    }
                                }
                            });


                            Log.e("powpqw", data.getAddressOfProduct());

                            productName.setText(data.getProduct());


                            txtAddress.setText(data.getAddressOfProduct() + "");
                            productDescription.setText(data.getDescription());
                            final_Amount.setText(currencySymbol + " " + data.getRentPerMonth());
                            deposit.setText(currencySymbol + " " + data.getDeposit());
                            marketPrice.setText(currencySymbol + " " + data.getMarketPrice());


                            if (data.getPrice_type().equals("1")) {

                                maxMinPeriod.setText("Min(hour)/Max(hour) Rental Period");


                                tickerView.setText(currencySymbol + " " + data.getRentPerMonth() + "/hour");
                                tenure.setText("Minimum rental tenure is " + data.getMinPeriod() + " hours");

                                prMinMax.setText(data.getMinPeriod() + " hours" + "/" + data.getMaxPeriod() + " hours");

                            } else if (data.getPrice_type().equals("2")) {
                                maxMinPeriod.setText("Min(day)/Max(day) Rental Period");
                                tickerView.setText(currencySymbol + " " + data.getRentPerMonth() + "/day");
                                tenure.setText("Minimum rental tenure is " + data.getMinPeriod() + " day");
                                prMinMax.setText(data.getMinPeriod() + " day" + "/" + data.getMaxPeriod() + " day");

                            } else if (data.getPrice_type().equals("3")) {
                                maxMinPeriod.setText("Min(week)/Max(week) Rental Period");
                                tickerView.setText(currencySymbol + " " + data.getRentPerMonth() + "/week");
                                tenure.setText("Minimum rental tenure is " + data.getMinPeriod() + " week");
                                prMinMax.setText(data.getMinPeriod() + " week" + "/" + data.getMaxPeriod() + " week");

                            } else if (data.getPrice_type().equals("4")) {
                                maxMinPeriod.setText("Min(month)/Max(month) Rental Period");
                                tickerView.setText(currencySymbol + " " + data.getRentPerMonth() + "/month");
                                tenure.setText("Minimum rental tenure is " + data.getMinPeriod() + " month");
                                prMinMax.setText(data.getMinPeriod() + " month" + "/" + data.getMaxPeriod() + " month");

                            } else {
                                tickerView.setText("\u20B9 " + data.getRentPerMonth() + "N/A");
                            }


                            if (data.getPower().equals("")) {
                                prPower.setText("N/A");
                            } else {
                                prPower.setText(data.getPower());
                            }


                            if (data.getWeight().equals("")) {
                                prWeight.setText("N/A");
                            } else {
                                prWeight.setText(data.getWeight());
                            }


                            if (data.getHeight().equals("")) {
                                prHeight.setText("N/A");
                            } else {
                                prHeight.setText(data.getHeight());
                            }


                            if (data.getWidth().equals("")) {
                                prWidth.setText("N/A");
                            } else {
                                prWidth.setText(data.getWidth());
                            }

                            if (data.getLength().equals("")) {
                                prLength.setText("N/A");
                            } else {
                                prLength.setText(data.getLength());
                            }


                            if (data.getDelivery_type().equals("1")) {
                                dType.setText("Home Delivery");
                            } else {
                                dType.setText("Pickup");
                            }


                            Log.e("uiwwiew", "resnt Status::" + data.getRent_status());


                            if (data.getFavorite_status().equals("0")) {

                                heartI.setVisibility(View.VISIBLE);
                                heart_liked.setVisibility(View.GONE);
                            } else {
                                heart_liked.setVisibility(View.VISIBLE);
                                heartI.setVisibility(View.GONE);
                            }


                            SharedHelper.putKey(getApplicationContext(), AppConstats.PRODUCT_CART_STATUS, data.getCart_status());


                            String finalCurrencySymbol = currencySymbol;
                            btnAddtocart.setOnClickListener(view -> {

                                strStartDate = startDate.getText().toString().trim();
                                strEndDate = endDate.getText().toString().trim();


                                if (btnAddtocart.getText().equals("Already Added")) {
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Already Added", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                } else {

                                    if (UserData.getUserID(ProductDetailsActivity.this).equals("")) {
                                        Toast.makeText(ProductDetailsActivity.this, "Login first", Toast.LENGTH_SHORT).show();
                                    } else {

                                        if (strStartDate.equals("")) {
                                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Select start date", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } else if (strEndDate.equals("")) {
                                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Select end date", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } else if (final_Amount.getText().toString().equals(finalCurrencySymbol + " 0.0")) {
                                            ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Select dates by above conditions");
                                        } else if (!ownerId.equals("") && ownerId.equals(UserData.getUserID(ProductDetailsActivity.this))) {
                                            Toast.makeText(ProductDetailsActivity.this, "You cant't add your own product", Toast.LENGTH_SHORT).show();
                                        } else if (!strCityName.equals(UserData.getCityName(ProductDetailsActivity.this))) {
                                            Log.d("sdskksd", strCityName);
                                            Log.d("sdskksd", UserData.getCityName(ProductDetailsActivity.this));
                                            ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "This product is not for your location, You are currently in " + UserData.getCityName(ProductDetailsActivity.this));
                                        } else {

                                            if (!totalAmount.isEmpty()) {

                                                if (verificationStatusModel != null && verificationStatusModel.getProfileStatus().equals("1")) {
                                                    Log.e("sxjxjsn", mStartDate + "," + mEndDate);
                                                    addtoCart(UserData.getUserID(ProductDetailsActivity.this), UserData.getProductID(getApplicationContext()), strDiff, totalAmount, mStartDate, mEndDate);

                                                } else {

                                                    showNotVerifiedMessage(); } } } } } });


                            if (data.getCityDetail().size() > 0) {

                                for (ShowProductsModel.Datum.CityDetail cityDetail : data.getCityDetail()) {


                                    Log.e("osidop", cityDetail.getCity());

                                    if (!cityDetail.getCity().equals("")) {
                                        cityName.setText(cityDetail.getCity());
                                        strCityName = cityDetail.getCity();
                                    } else {
                                        cityName.setText("Not Available");
                                    }

                                }
                            }


                            if (data.getBrandDetail() != null && data.getBrandDetail().size() > 0) {

                                for (ShowProductsModel.Datum.BrandDetail brandDetail : data.getBrandDetail()) {

                                    if (brandDetail.getName().equals("")) {
                                        prBrand.setText("N/A");

                                    } else {
                                        prBrand.setText(brandDetail.getName());
                                    }

                                }

                            }


                            if (data.getConditionDetail() != null && data.getConditionDetail().size() > 0) {

                                for (ShowProductsModel.Datum.ConditionDetail conditionDetail : data.getConditionDetail()) {

                                    if (conditionDetail.getCondition().equals("")) {
                                        prCondition.setText("N/A");

                                    } else {
                                        prCondition.setText(conditionDetail.getCondition());
                                    }

                                }

                            }


                            sliderImageDataList = new ArrayList<>();
                            List<ShowProductsModel.Datum.Image> datanew = data.getImage();

                            if (datanew != null) {
                                for (ShowProductsModel.Datum.Image image : datanew) {
                                    SliderImageData sliderImageData = new SliderImageData();
                                    sliderImageData.setId(data.getProductID());
                                    sliderImageData.setPath(image.getPath());
                                    sliderImageData.setImage(image.getImage());
                                    Log.e("dfsdfsdf", image.getImage());
                                    sliderImageDataList.add(sliderImageData);

                                }

                                SliderAdapter sliderAdapter = new SliderAdapter(sliderImageDataList, ProductDetailsActivity.this);
                                imageSlider.setSliderAdapter(sliderAdapter);
                            }


                        }

                        dialogInterface.hideDialog();

                    } else {
                        dialogInterface.hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowProductsModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });


    }

    public void help(String pname, String pmobile, String pemail, String pdescription) {

        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ProductDetailsActivity.this);

        Map<String, String> param = new HashMap<>();
        param.put("name", pname);
        param.put("mobile", pmobile);
        param.put("email", pemail);
        param.put("discription", pdescription);
        Call<NeedHelpModel> call = APIClient.getAPIClient().help(param);
        call.enqueue(new Callback<NeedHelpModel>() {
            @Override
            public void onResponse(@NonNull Call<NeedHelpModel> call, @NonNull Response<NeedHelpModel> response) {
                if (!response.isSuccessful()) {

                    dialogInterface.hideDialog();
                    ReturnErrorToast.showToast(ProductDetailsActivity.this);
                }

                NeedHelpModel model = response.body();

                if (model != null) {
                    if (model.getResult()) {
                        dialogInterface.hideDialog();
                        //wNeed, name, phone, email
                        wNeed.setText("");
                        name.setText("");
                        phone.setText("");
                        email.setText("");
                        Toast.makeText(ProductDetailsActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    } else {
                        dialogInterface.hideDialog();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NeedHelpModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });
    }


    public void addtoCart(String userID, String productID, String differenceofDates, String totalAmount, String startDate, String endDate) {
        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ProductDetailsActivity.this);

        Log.e("xksjxs", startDate + "," + endDate);
        Map<String, String> params = new HashMap<>();
        params.put("userID", userID);
        params.put("productID", productID);
        params.put("total_selected_days", differenceofDates);
        params.put("total", totalAmount);
        params.put("start_date", startDate);
        params.put("end_date", endDate);


        Call<AddCartModel> call = APIClient.getAPIClient().addToCart(params);
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(@NonNull Call<AddCartModel> call, @NonNull Response<AddCartModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ProductDetailsActivity.this);
                    dialogInterface.hideDialog();
                }

                AddCartModel model = response.body();
                if (model != null) {
                    if (model.getMessage().equals("Added To Cart Successfully")) {
                        vibrator.vibrate(200);
                        btnAddtocart.setText("Already Added");
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Successfully Added", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Toast.makeText(ProductDetailsActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dialogInterface.hideDialog();
                } else {
                    dialogInterface.hideDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AddCartModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });
    }

    public double getTotalAmount(String type, double min, double max, double duration, double rentPerDuration) {


        switch (type) {
            case HOURS:

                if (duration < min) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select minimum " + min + " hour");
                } else if (duration > max) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select under max duration of " + max + " hour");
                } else {
                    return rentPerDuration * duration;
                }

                break;

            case DAYS:

                if (duration < min) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select minimum " + min + " days");
                } else if (duration > max) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select under max duration of " + max + " days");
                } else {
                    return rentPerDuration * duration;
                }

                break;

            case WEEK:

                min = min * 7;
                max = max * 7;
                if (duration < min) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select minimum " + min + " day's");
                } else if (duration > max) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select under max duration of " + max + " day's");
                } else {

                    double rentPerDay = rentPerDuration / 7;

                    return Double.parseDouble(new DecimalFormat("##.##").format(duration * rentPerDay));
                }

                break;


            case MONTH:


                if (!mStartDate.equals("") && !mEndDate.equals("")) {


                }

                min = min * 30;
                max = max * 30;

                if (duration < min) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select minimum " + min + " days");
                } else if (duration > max) {
                    startDate.setText("");
                    endDate.setText("");
                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Please select under max duration of " + max + " days");
                } else {

                    double rentPerDay = rentPerDuration / 30;


                    //int result = getMonthDifference(duration);
                    return Double.parseDouble(new DecimalFormat("##.##").format(duration * rentPerDay));
                }


                break;

            default:

                break;
        }


        return 0;
    }


    public void showAgreement() {


        Call<AgreementModel> call = APIClient.getAPIClient().showAgreement();
        call.enqueue(new Callback<AgreementModel>() {
            @Override
            public void onResponse(@NonNull Call<AgreementModel> call, @NonNull Response<AgreementModel> response) {

                if (!response.isSuccessful()) {

                    ReturnErrorToast.showToast(ProductDetailsActivity.this);
                } else {
                    AgreementModel agreementModel = response.body();
                    if (agreementModel != null) {

                        if (agreementModel.getResult()) {
                            mAgreementModel = agreementModel;

                            if (agreementModel.getData() != null) {

                                if (!agreementModel.getPath().equals("") && !agreementModel.getData().equals("")) {
                                    String pdf = agreementModel.getPath() + agreementModel.getData();
                                    Log.e("tytreerf", pdf);

                                } else {

                                    ReturnErrorToast.showWarningToast(ProductDetailsActivity.this, "Corrupt file");
                                }

                            }

                        }
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AgreementModel> call, @NonNull Throwable t) {

                Log.e("sjsnxcsc", t.getMessage());
            }
        });
    }


    public void showVerStatus() {

        Call<VerificationStatusModel> call = APIClient.getAPIClient().showVerificationStatus(UserData.getUserID(ProductDetailsActivity.this));
        call.enqueue(new Callback<VerificationStatusModel>() {
            @Override
            public void onResponse(@NonNull Call<VerificationStatusModel> call, @NonNull Response<VerificationStatusModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ProductDetailsActivity.this);
                }

                VerificationStatusModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {
                        verificationStatusModel = model;

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<VerificationStatusModel> call, @NonNull Throwable t) {
                Log.e("ewrew", "msg::" + t.getMessage());
            }
        });
    }


    private void showNotVerifiedMessage() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.not_verified_layout);
        MaterialButton btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> startActivity(new Intent(ProductDetailsActivity.this, UploadDocumentActivity.class)));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


}