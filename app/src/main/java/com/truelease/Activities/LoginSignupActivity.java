package com.truelease.Activities;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.LoginData;
import com.truelease.RetrofitModel.SignupData;
import com.truelease.RetrofitModel.SocialLoginModel;
import com.truelease.User.UserData;

import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout relFB, relGoogle, rel_fullname, rel_Email, rel_promocode, rel_Password;
    Button btnSignup, btnLogin, btnSign, btnLogI;
    CardView card;
    View view3, view1;
    TextView text, alreadyRegister, termsConditions, mforgotPass;
    ImageView imageCLose, facebook;
    EditText fullname, email, mobile, promo, password;
    String strFullname = "", strEmail = "", strMobile = "", strPromoCode = "", strPassword = "", strLat = "", strLng = "", strFullAddress = "";
    ProgressDialog dialog;
    CountryCodePicker countryCodePicker;
    CallbackManager callbackManager;
    LoginButton loginButton;
    SignInButton signInButton;
    public static GoogleSignInClient mGoogleSignInClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location location;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    boolean isLocationPermission = false;


    public static final int RC_GOOGLE_SIGN_IN = 9999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        relFB = findViewById(R.id.relFB);
        rel_promocode = findViewById(R.id.rel_promocode);
        card = findViewById(R.id.card);
        relGoogle = findViewById(R.id.relGoogle);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnSign = findViewById(R.id.btnSign);
        rel_Email = findViewById(R.id.rel_Email);
        rel_fullname = findViewById(R.id.rel_fullname);
        view1 = findViewById(R.id.view1);
        view3 = findViewById(R.id.view3);
        btnLogI = findViewById(R.id.btnLogI);
        text = findViewById(R.id.text);
        alreadyRegister = findViewById(R.id.alreadyRegister);
        imageCLose = findViewById(R.id.imageCLose);
        termsConditions = findViewById(R.id.termsConditions);
        promo = findViewById(R.id.promo);
        password = findViewById(R.id.password);
        rel_Password = findViewById(R.id.rel_Password);
        mforgotPass = findViewById(R.id.forgotPass);
        countryCodePicker = findViewById(R.id.countryCodePicker);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        facebook = findViewById(R.id.facebook);

        dialog = new ProgressDialog(this);

        termsConditions.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TermAndConditionActivity.class)));

        googleSignIn();


        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
        getDeviceLocation();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.e("dcadfad", loginResult + "Success");
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    String facebook_auth_id = profile.getId();
                    String name = profile.getFirstName();
                    Log.e("ccvdcfadcf", profile.getId());
                    Log.e("ccvdcfadcf", "facebook");
                    Log.e("ccvdcfadcf", profile.getFirstName());
                    String regID = SharedHelper.getKey(LoginSignupActivity.this, AppConstats.REG_ID);

                }

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.e("uireuit", response.toString());
                            try {

                                long fb_id = object.getLong("id");//use this for logout
                                String email = object.getString("email");

                                Log.e("ywerweuy", fb_id + "");
                                Log.e("ywerweuy", email);

                            } catch (JSONException e) {
                                Log.e("ywerweuy", e.getMessage(), e);
                            }


                        });

                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.e("dcadfad", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("dcadfad", Objects.requireNonNull(exception.getMessage()));

            }
        });

        btnSignup.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class)));

        mforgotPass.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class)));

        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.O) {

            expands(card, 600, 1250);

            btnLogin.setOnClickListener(view -> {
                collapse(card, 600, 900);
                btnSign.setVisibility(View.VISIBLE);
                btnLogin.animate().alpha((float) 0).setDuration(1000).start();
                rel_Email.animate().alpha((float) 0).setDuration(1000).start();
                rel_fullname.animate().alpha((float) 0).setDuration(1000).start();
                rel_Email.setVisibility(View.GONE);
                rel_fullname.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                btnLogI.setVisibility(View.VISIBLE);
                rel_promocode.setVisibility(View.GONE);
                mforgotPass.setVisibility(View.VISIBLE);
                text.setText("Login");

                alreadyRegister.setText("Dont have an account?");

            });
            Log.e("sdkkls", sdkVersion + "greater");


            btnSign.setOnClickListener(view -> {

                expands(card, 600, 1250);
                btnLogin.animate().alpha((float) 1).setDuration(1000).start();
                btnSign.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                rel_Email.animate().alpha((float) 1).setDuration(1000).start();
                rel_fullname.animate().alpha((float) 1).setDuration(1000).start();
                rel_Email.setVisibility(View.VISIBLE);
                rel_fullname.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                btnLogI.setVisibility(View.GONE);
                rel_promocode.setVisibility(View.VISIBLE);
                mforgotPass.setVisibility(View.GONE);
                text.setText("Sign up");

                alreadyRegister.setText("Already Registered?");
            });
        }


        if (currentapiVersion <= Build.VERSION_CODES.O) {
            Log.e("sdkkls", sdkVersion + "less or equal");

            //900,600,900
            //600,400,600

            expands(card, 600, 1250);
            btnLogin.setOnClickListener(view -> {
                collapse(card, 600, 900);
                btnSign.setVisibility(View.VISIBLE);
                btnLogin.animate().alpha((float) 0).setDuration(1000).start();
                rel_Email.animate().alpha((float) 0).setDuration(1000).start();
                rel_fullname.animate().alpha((float) 0).setDuration(1000).start();
                rel_Email.setVisibility(View.GONE);
                rel_fullname.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                btnLogI.setVisibility(View.VISIBLE);
                rel_promocode.setVisibility(View.GONE);
                mforgotPass.setVisibility(View.VISIBLE);
                text.setText("Login");
                alreadyRegister.setText("Dont have an account?");

            });


            btnSign.setOnClickListener(view -> {

                expands(card, 600, 1250);
                btnLogin.animate().alpha((float) 1).setDuration(1000).start();
                btnSign.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                rel_Email.animate().alpha((float) 1).setDuration(1000).start();
                rel_fullname.animate().alpha((float) 1).setDuration(1000).start();
                rel_Email.setVisibility(View.VISIBLE);
                rel_fullname.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                btnLogI.setVisibility(View.GONE);
                rel_promocode.setVisibility(View.VISIBLE);
                mforgotPass.setVisibility(View.GONE);
                text.setText("Sign up");

                alreadyRegister.setText("Already Registered?");
            });
        }


        btnSignup.setOnClickListener(view -> {

            strFullname = fullname.getText().toString().trim();
            strMobile = mobile.getText().toString().trim();
            strEmail = email.getText().toString().trim();
            strPromoCode = promo.getText().toString().trim();
            strPassword = password.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";


            if (TextUtils.isEmpty(strFullname)) {

                Toast.makeText(LoginSignupActivity.this, "Fullname is empty", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(strMobile)) {

                Toast.makeText(LoginSignupActivity.this, "Mobile is empty", Toast.LENGTH_SHORT).show();

            } else if (strMobile.length() < 10) {

                Toast.makeText(LoginSignupActivity.this, "Mobile number must be of 10 digits", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(strEmail)) {

                Toast.makeText(LoginSignupActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();

            } else if (!strEmail.matches(emailPattern)) {
                Toast.makeText(LoginSignupActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(strPassword)) {

                Toast.makeText(LoginSignupActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();

            } else {
                String regID = UserData.getRegID(LoginSignupActivity.this);
                signUp(strFullname, strEmail, strMobile, regID, strPassword,strLat,strLng,strFullAddress);

            }


        });


        btnLogI.setOnClickListener(view -> {
            strMobile = mobile.getText().toString().trim();
            strPassword = password.getText().toString().trim();

            Log.e("countryCode", countryCodePicker.getSelectedCountryCode() + "");

            if (TextUtils.isEmpty(strMobile)) {

                Toast.makeText(LoginSignupActivity.this, "Mobile is empty", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(strPassword)) {
                Toast.makeText(LoginSignupActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();

            } else if (strMobile.length() < 10) {

                Toast.makeText(LoginSignupActivity.this, "Mobile number must be of 10 digits", Toast.LENGTH_SHORT).show();

            } else {

                Log.e("skndkjs", strPassword);

                login(strMobile, UserData.getRegID(LoginSignupActivity.this), strPassword,strLat,strLng,strFullAddress);

            }
        });

        imageCLose.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            finishAffinity();

        });


    }

    public void googleSignIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.googlesignin))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        relGoogle.setOnClickListener(view -> signIn());
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if (account != null) {
                String name = account.getDisplayName();
                String email = account.getEmail();
                String authID = account.getId();
                Uri imageURI = account.getPhotoUrl();
                String image = String.valueOf(imageURI);
                Log.e("wedwedwedwed", name + "," + email + "," + authID + "," + image);
                socialLogin(authID, "google", name, UserData.getRegID(LoginSignupActivity.this), email,strPassword,strLat,strLng,strFullAddress);
            }


        } catch (ApiException e) {

            Log.e("kjckjsc", "signInResult:failed code=" + e.getStatusCode());
        }
    }


    public static void expands(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public void signUp(String name, String emai, String mobi, String regID, String pass,String lat,String lng,String address) {

        dialog.setTitle("Register");
        dialog.setMessage("please wait....");
        dialog.setCancelable(false);
        dialog.show();

        Map<String, String> param = new HashMap<>();
        param.put("name", name);
        param.put("email", emai);
        param.put("mobile", countryCodePicker.getSelectedCountryCode() + mobi);
        param.put("regID", regID);
        param.put("password", pass);
        param.put("latitude", lat);
        param.put("longitude", lng);
        param.put("address", address);

        Call<SignupData> call = APIClient.getAPIClient().userRegistration(param);
        call.enqueue(new Callback<SignupData>() {
            @Override
            public void onResponse(@NonNull Call<SignupData> call, @NonNull Response<SignupData> response) {

                if (!response.isSuccessful()) {
                    dialog.dismiss();

                    ReturnErrorToast.showToast(LoginSignupActivity.this);
                }

                SignupData signupData = response.body();

                if (signupData != null) {

                    if (signupData.getResult()) {

                        SignupData.Data userData = signupData.getData();


                       /* SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, userData.getUserID());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, userData.getName());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, userData.getEmail());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, userData.getMobile());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.PROVIDER, "normal");
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, userData.getAddress());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, "");
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, userData.getProfileImage());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.VERIFICATION, userData.getProfile_status());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, userData.getD_license_front());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, userData.getD_license_back());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, userData.getPaspot());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, userData.getAdhar_card());

*/


                        fullname.setText("");
                        mobile.setText("");
                        email.setText("");
                        password.setText("");
                        promo.setText("");

                        verifyDialog();
                        dialog.dismiss();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginSignupActivity.this, signupData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SignupData> call, @NonNull Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void verifyDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.user_verification_dialog);
        MaterialButton btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", "xsx" + e.getMessage(), e);
            if ((this.dialog != null) && this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    public void login(String mobile, String regID, String password,String lat,String lng,String address) {

        dialog.setTitle("Login");
        dialog.setMessage("please wait....");
        dialog.setCancelable(false);

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", e.getMessage(), e);
        }


        Map<String, String> param = new HashMap<>();
        param.put("mobile", countryCodePicker.getSelectedCountryCode() + mobile);
        param.put("regID", regID);
        param.put("password", password);
        param.put("latitude", lat);
        param.put("longitude", lng);
        param.put("address", address);
        Call<LoginData> call = APIClient.getAPIClient().login(param);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                if (!response.isSuccessful()) {
                    dialog.hide();
                    ReturnErrorToast.showToast(LoginSignupActivity.this);
                }

                LoginData loginData = response.body();

                if (loginData != null) {

                    if (loginData.getResult()) {

                        LoginData.Data userData = loginData.getData();

                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, userData.getUserID());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, userData.getName());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, userData.getEmail());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, userData.getMobile());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, userData.getAddress());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, userData.getBrief_intro());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, userData.getProfileImage());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PATH, userData.getPath());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, userData.getD_license_front());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, userData.getD_license_back());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, userData.getPaspot());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, userData.getAdhar_card());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.VERIFICATION, userData.getProfile_status());
                        SharedHelper.putKey(getApplicationContext(), AppConstats.PROVIDER, "normal");
                        SharedHelper.putKey(getApplicationContext(), AppConstats.REFERAL_CODE, userData.getRefferal_code());


                        startActivity(new Intent(getApplicationContext(), SelectCityActivity.class));
                        finishAffinity();
                        dialog.hide();

                    } else {
                        dialog.hide();
                        Toast.makeText(LoginSignupActivity.this, loginData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {

                Log.e("snlkcxlsk", t.getMessage());
                dialog.hide();
            }
        });
    }


    public void socialLogin(String authID, String provider, String name, String regID, String email, String pass,String lat,String lng,String address) {


        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, LoginSignupActivity.this);

        Map<String, String> param = new LinkedHashMap<>();
        param.put("authID", authID);
        param.put("auth_provider", provider);
        param.put("name", name);
        param.put("regID", regID);
        param.put("email", email);
        param.put("latitude", lat);
        param.put("longitude", lng);
        param.put("address", address);
        Call<SocialLoginModel> call = APIClient.getAPIClient().socialLogin(param);

        call.enqueue(new Callback<SocialLoginModel>() {
            @Override
            public void onResponse(@NonNull Call<SocialLoginModel> call, @NonNull Response<SocialLoginModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(LoginSignupActivity.this);
                    dialogInterface.hideDialog();
                }

                SocialLoginModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {
                        dialogInterface.hideDialog();
                        if (model.getData() != null) {

                            SocialLoginModel.Data data = model.getData();

                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, data.getName());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, data.getEmail());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, data.getMobile());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, data.getAddress());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, data.getBriefIntro());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, data.getProfileImage());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PATH, data.getPath());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, data.getD_license_front());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, data.getD_license_back());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.PROVIDER, data.getAuthProvider());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.VERIFICATION, data.getProfile_status());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, data.getPaspot());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, data.getAdhar_card());

                            if (model.getMessage().equals("Login Successfully")) {
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, data.getUserID());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, data.getName());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, data.getEmail());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, data.getMobile());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, data.getAddress());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, data.getBriefIntro());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, data.getProfileImage());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PATH, data.getPath());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, data.getD_license_front());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, data.getD_license_back());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.PROVIDER, data.getAuthProvider());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.VERIFICATION, data.getProfile_status());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, data.getPaspot());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, data.getAdhar_card());

                                SharedHelper.putKey(getApplicationContext(), AppConstats.LOGIN_OR_SIGNUP, "login");
                                startActivity(new Intent(getApplicationContext(), SelectCityActivity.class));
                                finishAffinity();
                            } else {

                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, data.getName());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, data.getEmail());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, data.getMobile());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, data.getAddress());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, data.getBriefIntro());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, data.getProfileImage());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PATH, data.getPath());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, data.getD_license_front());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, data.getD_license_back());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.PROVIDER, data.getAuthProvider());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.VERIFICATION, data.getProfile_status());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, data.getPaspot());
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, data.getAdhar_card());


                                updateMobileNumber(data.getUserID());
                            }


                        }
                    } else {
                        dialogInterface.hideDialog();
                        Toast.makeText(LoginSignupActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dialogInterface.hideDialog();
                } else {
                    dialogInterface.hideDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SocialLoginModel> call, @NonNull Throwable t) {

                dialogInterface.hideDialog();
                Log.e("sjduwoiej", t.getMessage(), t);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == relGoogle) {
            signInButton.performClick();
        }


        if (v == facebook) {
            loginButton.performClick();
        }
    }


    private void updateMobileNumber(String userID) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.changenumber_popup);
        EditText etMobile = dialog.findViewById(R.id.etMobile);
        MaterialButton btnNext = dialog.findViewById(R.id.btnNext);
        CountryCodePicker countryCodePick = dialog.findViewById(R.id.countryCodePick);

        btnNext.setOnClickListener(view -> {

            String etNumber = etMobile.getText().toString().trim();
            countryCodePick.getSelectedCountryCode();

            if (etNumber.equals("")) {
                Toast.makeText(LoginSignupActivity.this, "Enter your Number", Toast.LENGTH_SHORT).show();
            } else {

                Map<String, String> param = new HashMap<>();
                param.put("userID", userID);
                param.put("mobile", countryCodePick.getSelectedCountryCode() + etNumber);
                updateMobile(param);
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }


    private void updateMobile(Map<String, String> param) {

        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, LoginSignupActivity.this);

        Call<SocialLoginModel> call = APIClient.getAPIClient().updateMobile(param);
        call.enqueue(new Callback<SocialLoginModel>() {
            @Override
            public void onResponse(@NonNull Call<SocialLoginModel> call, @NonNull Response<SocialLoginModel> response) {

                if (!response.isSuccessful()) {

                    dialogInterface.hideDialog();
                    ReturnErrorToast.showToast(LoginSignupActivity.this);
                } else {
                    dialogInterface.hideDialog();
                    SocialLoginModel model = response.body();

                    if (model != null) {

                        SocialLoginModel.Data data = model.getData();

                        if (data != null) {
                            SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, data.getMobile());
                            SharedHelper.putKey(getApplicationContext(), AppConstats.LOGIN_OR_SIGNUP, "signup");
                            startActivity(new Intent(getApplicationContext(), SelectCityActivity.class));
                            finishAffinity();

                        } else {

                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SocialLoginModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });
    }


    public void getDeviceLocation() {

        if (isLocationPermission) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    if (task.isSuccessful()) {
                        location = task.getResult();
                        if (location != null) {

                            Log.e("mLocation", location.getLatitude() + "," + location.getLongitude());


                            strLat = String.valueOf(location.getLatitude());
                            strLng = String.valueOf(location.getLongitude());

                            try {
                                Geocoder geocoder = new Geocoder(LoginSignupActivity.this, Locale.getDefault());

                                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                if (addressList != null) {
                                    String address = addressList.get(0).getAddressLine(0);
                                    Log.e("skcmskcs", address + "");
                                    strFullAddress = address + "";

                                }

                            } catch (Exception e) {
                                Log.e("scnsk", e.getMessage());
                            }


                        } else {
                            LatLng latLng = new LatLng(33.8688, 151.2093);


                        }
                    }
                }
            });


        }
    }


    public void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isLocationPermission = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isLocationPermission = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermission = true;
            }
        }

    }


}