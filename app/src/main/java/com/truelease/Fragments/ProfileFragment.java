package com.truelease.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.card.MaterialCardView;
import com.truelease.Activities.BottomNavigationActivity;
import com.truelease.Activities.ChangePasswordActivity;
import com.truelease.Activities.ContactUsActivity;
import com.truelease.Activities.LoginSignupActivity;
import com.truelease.Activities.MainActivity;
import com.truelease.Activities.MyOrdersActivity;
import com.truelease.Activities.MyPostsActivity;
import com.truelease.Activities.NotificationsActivity;
import com.truelease.Activities.OffersActivity;
import com.truelease.Activities.ReferToFriendsActivity;
import com.truelease.Activities.SelectCityActivity;
import com.truelease.Activities.SettingsActivity;
import com.truelease.Activities.WishListActivity;
import com.truelease.Interface.LoginDetail.LoginDetail;
import com.truelease.Interface.LoginDetail.UserLoginDetail;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.User.UserData;


public class ProfileFragment extends Fragment {

    RelativeLayout rel_City, rel_offers, rel_contactUs, rel_notification,
            rel_subscription, rel_logout, rel, rel_refer, rel_setting, rel_wishlist, rel_myPost, rel_changePass,rel_myOrders;
    Button btnLogin;
    ProgressDialog dialog;
    MaterialCardView header;
    TextView cityName;
    ImageView menu;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rel_City = view.findViewById(R.id.rel_City);
        rel_offers = view.findViewById(R.id.rel_offers);
        rel_contactUs = view.findViewById(R.id.rel_contactUs);
        btnLogin = view.findViewById(R.id.btnLogin);
        rel_notification = view.findViewById(R.id.rel_notification);
        rel_logout = view.findViewById(R.id.rel_logout);
        rel = view.findViewById(R.id.rel);
        rel_refer = view.findViewById(R.id.rel_refer);
        header = view.findViewById(R.id.header);
        cityName = view.findViewById(R.id.cityName);
        menu = view.findViewById(R.id.menu);
        rel_setting = view.findViewById(R.id.rel_setting);
        rel_wishlist = view.findViewById(R.id.rel_wishlist);
        rel_myPost = view.findViewById(R.id.rel_myPost);
        rel_changePass = view.findViewById(R.id.rel_changePass);
        rel_myOrders = view.findViewById(R.id.rel_myOrders);


        userLoginStatus();


        rel_City.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), SelectCityActivity.class)));
        rel_myOrders.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), MyOrdersActivity.class)));


        rel_offers.setOnClickListener(view12 -> startActivity(new Intent(getActivity(), OffersActivity.class)));

        rel_contactUs.setOnClickListener(view13 -> startActivity(new Intent(getActivity(), ContactUsActivity.class)));


        rel_notification.setOnClickListener(view14 -> startActivity(new Intent(getActivity(), NotificationsActivity.class)));


        rel_wishlist.setOnClickListener(view15 -> startActivity(new Intent(getActivity(), WishListActivity.class)));


        rel_setting.setOnClickListener(view16 -> startActivity(new Intent(getActivity(), SettingsActivity.class)));


        rel_refer.setOnClickListener(view18 -> startActivity(new Intent(getActivity(), ReferToFriendsActivity.class)));


        rel_myPost.setOnClickListener(view19 -> startActivity(new Intent(getActivity(), MyPostsActivity.class)));

        menu.setOnClickListener(view110 -> BottomNavigationActivity.drawer.openDrawer(GravityCompat.START));

        rel_changePass.setOnClickListener(view111 -> startActivity(new Intent(getActivity(), ChangePasswordActivity.class)));


        onBack(view);




        return view;
    }




    public void forGoogleLogout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                SharedHelper.putKey(getActivity(), AppConstats.USER_ID, "");
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }).addOnFailureListener(e -> {

        });
    }

    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                return true;
            }
            return false;
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        cityName.setText(UserData.getCityName(getActivity()));
    }

    public void logout() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.logout);
        RelativeLayout relYes = dialog.findViewById(R.id.relYes);
        RelativeLayout relNo = dialog.findViewById(R.id.relNo);

        relYes.setOnClickListener(v -> {
            SharedHelper.putKey(getActivity(), AppConstats.USER_ID, "");
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finishAffinity();
            dialog.dismiss();
        });

        relNo.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", e.getMessage(), e);
        }


    }


    public void userLoginStatus() {
        UserLoginDetail userLoginDetail = new LoginDetail(UserData.getUserID(getActivity()));
        if (userLoginDetail.isUserLogin()) {
            rel.setVisibility(View.GONE);

            header.setVisibility(View.VISIBLE);
            rel_logout.setVisibility(View.VISIBLE);
            rel_logout.setOnClickListener(view -> {

                if (UserData.getProvider(getActivity()).equals("google")) {
                    forGoogleLogout();
                } else {
                    logout();
                }

            });
        } else {
            rel_myPost.setVisibility(View.GONE);
            rel_myOrders.setVisibility(View.GONE);
            rel_changePass.setVisibility(View.GONE);
            rel_notification.setVisibility(View.GONE);
            rel_setting.setVisibility(View.GONE);
            rel_wishlist.setVisibility(View.GONE);
            rel.setVisibility(View.VISIBLE);
            rel_logout.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            btnLogin.setOnClickListener(view -> {
                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Go to login");
                dialog.setMessage("please wait...");
                dialog.show();
                new Handler().postDelayed(() -> {
                    dialog.hide();
                    startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                    getActivity().finishAffinity();
                }, 1000);

            });

        }
    }

}