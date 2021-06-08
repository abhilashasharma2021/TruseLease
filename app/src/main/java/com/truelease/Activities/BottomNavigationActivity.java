package com.truelease.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.truelease.ApiData.APIClient;
import com.truelease.Fragments.CartFragment;
import com.truelease.Fragments.CategoryFragment;
import com.truelease.Fragments.HomeFragment;
import com.truelease.Fragments.ProfileFragment;
import com.truelease.Interface.LoginDetail.LoginDetail;
import com.truelease.Interface.LoginDetail.UserLoginDetail;
import com.truelease.Others.InternetConnection.InternetConnectionInterface;
import com.truelease.Others.InternetConnection.InternetConnectivity;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.NewNotificationModel;
import com.truelease.RetrofitModel.VerificationStatusModel;
import com.truelease.Room.RoomDatabase.AddressActivity;
import com.truelease.User.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomNavigationActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    public static DrawerLayout drawer;
    RelativeLayout rel_home, rel_tc, rel_Chat, rel_wallet, rel_wishlist, rel_stuffs, rel_address, rel_help, rel_rent, agreement, rel_rentingItems, withDrawal, rel_history, rel_aboutUs;
    TextView name;
    ImageView prf, dotOrder, dotWallet, dotRenting;

    Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);


        drawer = findViewById(R.id.drawer);
        name = findViewById(R.id.name);
        rel_home = findViewById(R.id.rel_home);
        rel_tc = findViewById(R.id.rel_tc);
        rel_Chat = findViewById(R.id.rel_Chat);
        rel_wallet = findViewById(R.id.rel_wallet);
        rel_wishlist = findViewById(R.id.rel_wishlist);
        rel_stuffs = findViewById(R.id.rel_stuffs);
        rel_address = findViewById(R.id.rel_address);
        rel_help = findViewById(R.id.rel_help);
        rel_rent = findViewById(R.id.rel_rent);
        agreement = findViewById(R.id.agreement);
        rel_rentingItems = findViewById(R.id.rel_rentingItems);
        prf = findViewById(R.id.prf);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        withDrawal = findViewById(R.id.withDrawal);
        click = findViewById(R.id.click);
        rel_history = findViewById(R.id.rel_history);
        rel_aboutUs = findViewById(R.id.rel_aboutUs);
        dotRenting = findViewById(R.id.dotRenting);
        dotOrder = findViewById(R.id.dotOrder);
        dotWallet = findViewById(R.id.dotWallet);

        rel_home.setOnClickListener(this);
        rel_tc.setOnClickListener(this);
        rel_Chat.setOnClickListener(this);
        rel_wallet.setOnClickListener(this);
        rel_wishlist.setOnClickListener(this);
        rel_stuffs.setOnClickListener(this);
        rel_address.setOnClickListener(this);
        rel_help.setOnClickListener(this);
        rel_rent.setOnClickListener(this);
        agreement.setOnClickListener(this);
        rel_rentingItems.setOnClickListener(this);
        withDrawal.setOnClickListener(this);
        rel_history.setOnClickListener(this);
        rel_aboutUs.setOnClickListener(this);


        prf.setOnClickListener(view -> {
            Intent intent = new Intent(BottomNavigationActivity.this, EditProfileActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(BottomNavigationActivity.this, prf, ViewCompat.getTransitionName(prf));
            startActivity(intent, optionsCompat.toBundle());
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

                    break;

                case R.id.nav_product:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoryFragment()).commit();
                    break;

                case R.id.nav_cart:
                    if (UserData.getUserID(getApplicationContext()).equals("")) {
                        Toast.makeText(BottomNavigationActivity.this, "Login first", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                        finishAffinity();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();

                    }
                    break;

                case R.id.nav_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                    break;

                case R.id.nav_collection:
                    UserLoginDetail userLoginDetail = new LoginDetail(UserData.getUserID(getApplicationContext()));
                    if (userLoginDetail.isUserLogin()) {
                        showVerStatus();
                    } else {
                        Toast.makeText(BottomNavigationActivity.this, "Login First", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                        finishAffinity();
                    }
                    break;
            }
            return true;
        });


        InternetConnectionInterface connectivity = new InternetConnectivity();
        if (connectivity.isConnected(getApplicationContext())) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

            }
        } else {


            Toast.makeText(this, "Not connected to internet!!!", Toast.LENGTH_SHORT).show();

        }


        showNewNoti();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rel_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_tc:
                startActivity(new Intent(getApplicationContext(), TermAndConditionActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_Chat:
                startActivity(new Intent(getApplicationContext(), MyChatsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;


            case R.id.rel_wallet:
                startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_wishlist:
                startActivity(new Intent(getApplicationContext(), WishListActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_stuffs:

                startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_address:
                startActivity(new Intent(getApplicationContext(), AddressActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;


            case R.id.rel_help:
                startActivity(new Intent(getApplicationContext(), ShowHelpActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_rent:
                startActivity(new Intent(getApplicationContext(), MyPostsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.agreement:
                startActivity(new Intent(getApplicationContext(), AgreementActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_rentingItems:
                startActivity(new Intent(getApplicationContext(), MyRentingItemsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.withDrawal:
                startActivity(new Intent(getApplicationContext(), WithdrawalActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_history:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rel_aboutUs:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!UserData.getUserID(this).equals("")) {
            name.setText(UserData.getUserName(getApplicationContext()));
            if (!UserData.getUserPath(BottomNavigationActivity.this).equals("")) {
                Picasso.get().load(UserData.getUserPath(BottomNavigationActivity.this) +
                        UserData.getUserImage(BottomNavigationActivity.this)).placeholder(R.drawable.avatar).into(prf);

            }
        } else {

        }

    }


    public void showVerStatus() {
        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, BottomNavigationActivity.this);

        Call<VerificationStatusModel> call = APIClient.getAPIClient().showVerificationStatus(UserData.getUserID(BottomNavigationActivity.this));
        call.enqueue(new Callback<VerificationStatusModel>() {
            @Override
            public void onResponse(@NonNull Call<VerificationStatusModel> call, @NonNull Response<VerificationStatusModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(BottomNavigationActivity.this);
                    dialog.hideDialog();
                }

                VerificationStatusModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {

                        if (model != null && model.getProfileStatus().equals("1")) {
                            startActivity(new Intent(getApplicationContext(), AddProductStepOneActivity.class));
                            dialog.hideDialog();

                        } else {
                            dialog.hideDialog();
                            showNotVerifiedMessage();
                        }

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<VerificationStatusModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }


    private void showNewNoti() {

        Call<NewNotificationModel> call = APIClient.getAPIClient().showNewNoti(UserData.getUserID(getApplicationContext()));
        call.enqueue(new Callback<NewNotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NewNotificationModel> call, @NonNull Response<NewNotificationModel> response) {


                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(BottomNavigationActivity.this);
                } else {

                    NewNotificationModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {

                            NewNotificationModel.Data data = model.getData();
                            if (data != null) {

                                if (!data.getCurrentlyRenting().equals("0")) {
                                    dotRenting.setVisibility(View.VISIBLE);
                                } else if (!data.getWallet().equals("0")) {
                                    dotWallet.setVisibility(View.VISIBLE);
                                } else if (!data.getMyOrder().equals("0")) {
                                    dotOrder.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<NewNotificationModel> call, @NonNull Throwable t) {

                Log.e("popwwdd", t.getMessage() + "");
            }
        });
    }


    private void showNotVerifiedMessage() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.not_verified_layout);
        MaterialButton btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> startActivity(new Intent(BottomNavigationActivity.this, UploadDocumentActivity.class)));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}