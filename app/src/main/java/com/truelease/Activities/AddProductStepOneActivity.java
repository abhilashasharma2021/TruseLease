package com.truelease.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowCategoriesData;
import com.truelease.RetrofitModel.ShowConditionsModel;
import com.truelease.RetrofitModel.SubCategoriesModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductStepOneActivity extends AppCompatActivity {

    Button btnNext;
    ImageView back;

    TextView condition;
    EditText etDescription, etTitle, etaddress, etTags;
    String strDescription = "", strTitle = "", strAddress = "", strTags = "";
    public static final int REQUEST_CODE_FOR_ADDRESS = 4373;
    Spinner categorySpinner, subCategorySpinner;
    ArrayList<String> categoryName, categoryId, subCategoryName, subCategoryId;
    String selectedSubID = "", catID = "", mCondition = "";
    PopupMenu popup;
    ArrayList<String> conditionIDList, conditionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        back = findViewById(R.id.back);
        btnNext = findViewById(R.id.btnNext);
        condition = findViewById(R.id.condition);

        categorySpinner = findViewById(R.id.categorySpinner);
        subCategorySpinner = findViewById(R.id.subCategorySpinner);

        etDescription = findViewById(R.id.etDescription);
        etTitle = findViewById(R.id.etTitle);
        etaddress = findViewById(R.id.etaddress);
        etTags = findViewById(R.id.etTags);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.getDefault());
        }


        etaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                        .build(AddProductStepOneActivity.this);

                startActivityForResult(intent, REQUEST_CODE_FOR_ADDRESS);
            }
        });

        showCoditions();


        back.setOnClickListener(view -> finish());


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                catID = categoryId.get(i);
                Log.e("jiewuriuew", catID);
                showSubCategory(catID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        subCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedSubID = subCategoryId.get(i);
                Log.e("jiewuriuew", selectedSubID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        showCategory();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOR_ADDRESS && resultCode == RESULT_OK) {

            if (data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                LatLng latLng = place.getLatLng();

                if (latLng != null) {

                    SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_LATITUDE, String.valueOf(latLng.latitude));
                    SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_LONGITUDE, String.valueOf(latLng.longitude));

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                        if (addressList != null && addressList.size() > 0) {
                            String fulladdress = addressList.get(0).getAddressLine(0);
                            etaddress.setText(fulladdress);


                            final String placeId = place.getId();
                            final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);
                            final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);
                            PlacesClient placesClient = Places.createClient(this);
                            placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
                                final Place place2 = response.getPlace();

                                // Get the photo metadata.
                                final List<PhotoMetadata> metadata = place2.getPhotoMetadatas();
                                if (metadata == null || metadata.isEmpty()) {
                                    Log.e("uweyhuiwe", "No photo metadata.");
                                    return;
                                }

                                final PhotoMetadata photoMetadata = metadata.get(0);

                                final String attributions = photoMetadata.getAttributions();


                                final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                                        .setMaxWidth(500)
                                        .setMaxHeight(300)
                                        .build();

                                placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                                    Dialog dialog = new Dialog(this);
                                    dialog.setContentView(R.layout.address_image_layout);
                                    TextView txtAddress = dialog.findViewById(R.id.txtAddress);
                                    ImageView image = dialog.findViewById(R.id.image);
                                    txtAddress.setText(fulladdress);
                                    image.setImageBitmap(bitmap);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                }).addOnFailureListener((exception) -> {
                                    if (exception instanceof ApiException) {
                                        final ApiException apiException = (ApiException) exception;
                                        Log.e("oweiow", "Place not found: " + exception.getMessage());
                                        final int statusCode = apiException.getStatusCode();
                                        // TODO: Handle error with given status code.
                                    }
                                });
                            });

                        } else {
                            etaddress.setText("Enable to fetch location");
                        }


                    } catch (Exception e) {
                        Log.e("eiruir", e.getMessage());
                    }


                }

                Log.e("smlkds", latLng.latitude + "," + latLng.longitude);


            }


        }


        btnNext.setOnClickListener(view -> {

            strTitle = etTitle.getText().toString().trim();
            strDescription = etDescription.getText().toString().trim();
            strAddress = etaddress.getText().toString().trim();
            strTags = etTags.getText().toString().trim();

            if (TextUtils.isEmpty(strTitle) || TextUtils.isEmpty(strDescription) || TextUtils.isEmpty(strAddress)) {
                Toast.makeText(AddProductStepOneActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else if (strDescription.length() < 20) {
                Toast.makeText(this, "Description of atleast 20 words", Toast.LENGTH_SHORT).show();
            } else {

                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_TITLE, strTitle);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_DESCRIPTION, strDescription);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_ADDRESS, strAddress);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_CATEGORY_ID, catID);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_SUB_CATEGORY_ID, selectedSubID);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_CONDITION, mCondition);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_PRODUCT_TAG, strTags);

                startActivity(new Intent(getApplicationContext(), AddProductStepTwoActivity.class));
                Animatoo.animateZoom(AddProductStepOneActivity.this);

            }
        });

    }


    public void showCategory() {


        Call<ShowCategoriesData> call = APIClient.getAPIClient().showCategories();
        call.enqueue(new Callback<ShowCategoriesData>() {
            @Override
            public void onResponse(@NonNull Call<ShowCategoriesData> call, @NonNull Response<ShowCategoriesData> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                } else {
                    ShowCategoriesData categoriesData = response.body();

                    if (categoriesData != null) {
                        if (categoriesData.getResult()) {

                            categoryName = new ArrayList<>();
                            categoryId = new ArrayList<>();
                            List<ShowCategoriesData.Datum> dataList = categoriesData.getData();
                            for (ShowCategoriesData.Datum data : dataList) {
                                categoryId.add(data.getCategoryID());
                                categoryName.add(data.getName());
                                Log.e("kskldc", data.getName());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductStepOneActivity.this, android.R.layout.simple_list_item_1, categoryName);
                            categorySpinner.setAdapter(adapter);

                        } else {

                            Toast.makeText(getApplicationContext(), categoriesData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<ShowCategoriesData> call, @NonNull Throwable t) {
                Log.e("skdjs", t.getMessage());

            }
        });
    }


    public void showSubCategory(String catID) {


        Map<String, String> maps = new HashMap<>();
        maps.put("control", "sub category");
        maps.put("categoryID", catID);
        Call<SubCategoriesModel> call = APIClient.getAPIClient().showSubCategory(maps);
        call.enqueue(new Callback<SubCategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<SubCategoriesModel> call, @NonNull Response<SubCategoriesModel> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }

                SubCategoriesModel categoriesModel = response.body();

                if (categoriesModel != null) {

                    if (categoriesModel.getResult()) {

                        subCategoryId = new ArrayList<>();
                        subCategoryName = new ArrayList<>();

                        List<SubCategoriesModel.Datum> dataList = categoriesModel.getData();
                        for (SubCategoriesModel.Datum data : dataList) {

                            subCategoryId.add(data.getSubCategoryID());
                            subCategoryName.add(data.getName());

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductStepOneActivity.this, android.R.layout.simple_list_item_1, subCategoryName);
                        subCategorySpinner.setAdapter(adapter);

                    } else {

                        Toast.makeText(getApplicationContext(), categoriesModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SubCategoriesModel> call, @NonNull Throwable t) {
                Log.e("sndksj", t.getMessage(), t);

            }
        });

    }


    public void showCoditions() {
        Call<ShowConditionsModel> call = APIClient.getAPIClient().showConditions();
        call.enqueue(new Callback<ShowConditionsModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowConditionsModel> call, @NonNull Response<ShowConditionsModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(AddProductStepOneActivity.this);
                }

                ShowConditionsModel model = response.body();

                if (model != null) {

                    List<ShowConditionsModel.Datum> dataList = model.getData();

                    if (dataList.size() > 0) {
                        popup = new PopupMenu(AddProductStepOneActivity.this, condition);
                        for (ShowConditionsModel.Datum data : dataList) {

                            popup.getMenu().add(data.getCondition());
                        }

                        mCondition = "New";
                        condition.setOnClickListener(view -> {

                            popup.setOnMenuItemClickListener(item -> {
                                mCondition = item.getTitle().toString();
                                condition.setText(item.getTitle());
                                return true;
                            });

                            popup.show();
                        });




                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ShowConditionsModel> call, @NonNull Throwable t) {

            }
        });

    }
}