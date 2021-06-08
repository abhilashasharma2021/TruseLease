package com.truelease.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.truelease.ApiData.API;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {


    TextInputEditText sname, email, number, faddress, intro;
    TextInputLayout tAddress;
    TextView name;
    String strName = "", strEmail = "", strNumber = "", strAddress = "", strImage = "", strIntro = "";

    MaterialButton btnUpdate;
    ImageView prf, back;
    public static final int AUTOCOMPLETE_REQUEST_CODE_SEARCH = 1111;

    private static final String IMAGE_DIRECTORY = "/True Lease";
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    File f;
    LatLng latLng;

    String strLat = "";
    String strLng = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.name);
        sname = findViewById(R.id.sname);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        faddress = findViewById(R.id.address);
        intro = findViewById(R.id.intro);
        prf = findViewById(R.id.prf);
        tAddress = findViewById(R.id.tAddress);
        back = findViewById(R.id.back);
        btnUpdate = findViewById(R.id.btnUpdate);

        name.setText(UserData.getUserName(getApplicationContext()));

        back.setOnClickListener(v -> finish());

        btnUpdate.setOnClickListener(view -> {


            strName = sname.getText().toString().trim();
            strEmail = email.getText().toString().trim();
            strNumber = number.getText().toString().trim();
            strAddress = faddress.getText().toString().trim();
            strIntro = intro.getText().toString().trim();


            if (latLng != null && !strAddress.isEmpty()) {
                strLat = String.valueOf(latLng.latitude);
                strLng = String.valueOf(latLng.longitude);

                Log.e("kslks", strLat + "," + strLng);
            }

            updatePrf();


        });
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.getDefault());
        }


        prf.setOnClickListener(v -> showPictureDialog());


        faddress.setOnClickListener(view -> {

            List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                    .build(EditProfileActivity.this);

            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SEARCH);
        });


        sname.setText(UserData.getUserName(EditProfileActivity.this));
        email.setText(UserData.getUserEmail(EditProfileActivity.this));
        number.setText(UserData.getUserMobileNumber(EditProfileActivity.this));
        faddress.setText(UserData.getUserAddress(EditProfileActivity.this));
        intro.setText(UserData.getUserIntro(EditProfileActivity.this));


        if (!UserData.getUserPath(EditProfileActivity.this).equals("") && !UserData.getUserImage(EditProfileActivity.this).equals("")) {
            Picasso.get().load(UserData.getUserPath(EditProfileActivity.this) + UserData.getUserImage(EditProfileActivity.this)).placeholder(R.drawable.avatar).into(prf);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_SEARCH) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.e("oireuftoe", "Place: " + place.getName() + ", " + place.getLatLng() +
                            place.getAddress());

                    latLng = place.getLatLng();

                    try {
                        Geocoder geocoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());

                        if (latLng != null) {
                            SharedHelper.putKey(getApplicationContext(), AppConstats.SEARCH_LOCATION_LAT, latLng.latitude + "");
                            SharedHelper.putKey(getApplicationContext(), AppConstats.SEARCH_LOCATION_LNG, latLng.longitude + "");

                            Log.e("rtrere", "latlng" + latLng.latitude + "," + latLng.longitude);
                            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                            if (addressList != null) {

                                String address = addressList.get(0).getAddressLine(0);
                                faddress.setText(address);

                            } else {
                                Toast.makeText(this, addressList.toString() + "null", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (Exception e) {
                        Log.e("gfvdfrgvd", e.getMessage(), e);
                    }

                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.e("oireuftoe", status.getStatusMessage());
            }

        }


        if (requestCode == GALLERY) {

            if (data!=null){
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    prf.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {

            if (data!=null){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                prf.setImageBitmap(thumbnail);
                assert thumbnail != null;
                saveImage(thumbnail);
            }


        }


    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(EditProfileActivity.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(EditProfileActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.e("sjhdsd", f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public void updatePrf() {


        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, EditProfileActivity.this);

        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(getApplicationContext()))
                .addMultipartParameter("email", strEmail)
                .addMultipartParameter("mobile", strNumber)
                .addMultipartParameter("name", strName)
                .addMultipartFile("profile_image", f)
                .addMultipartParameter("address", strAddress)
                .addMultipartParameter("brief_intro", strIntro)
                .addMultipartParameter("latitude", strLat)
                .addMultipartParameter("longitude", strLng)
                .setTag("updatePrf")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            Log.e("resjhxs", response.toString());
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                String number = jsonObject.getString("mobile");
                                String address = jsonObject.getString("address");
                                String brief_intro = jsonObject.getString("brief_intro");
                                String profile_image = jsonObject.getString("profile_image");
                                String path = jsonObject.getString("path");
                                String verification_documents = jsonObject.getString("verification_documents");
                                String D_license_front = jsonObject.getString("D_license_front");
                                String D_license_back = jsonObject.getString("D_license_back");
                                String paspot = jsonObject.getString("paspot");
                                String adhar_card = jsonObject.getString("adhar_card");


                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_NAME, name);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_EMAIL, email);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_MOBILE, number);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ADDRESS, address);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_INTRO, brief_intro);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_IMAGE, profile_image);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PATH, path);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_FRONT, D_license_front);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_DRIVING_LICENSE_BACK, D_license_back);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_PASSPORT, paspot);
                                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_AADHAR, adhar_card);


                                Glide.with(EditProfileActivity.this).load(path + profile_image).into(prf);
                                startActivity(new Intent(EditProfileActivity.this, EditProfileActivity.class));
                                Animatoo.animateShrink(EditProfileActivity.this);
                                finish();


                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }
}