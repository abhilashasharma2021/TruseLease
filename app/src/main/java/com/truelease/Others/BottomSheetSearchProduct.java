package com.truelease.Others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.truelease.Activities.MapSearchProductsActivity;
import com.truelease.Adapters.BottomSheetSearchProductAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.BottomSheetSearchProductData;
import com.truelease.R;
import com.truelease.RetrofitModel.BottomSheetSearchProductModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetSearchProduct extends RoundedBottomSheetDialogFragment {


    List<BottomSheetSearchProductData> bottomSheetSearchProductDataList;
    BottomSheetSearchProductAdapter adapter;
    RecyclerView searchProductRecycler;
    LinearLayout linearNotFound;

    protected Bitmap getCircularBitmap(Bitmap srcBitmap) {
        // Calculate the circular bitmap width with border
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());

        // Initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );


        Canvas canvas = new Canvas(dstBitmap);

        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);
        RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Calculate the left and top of copied bitmap
        float left = (squareBitmapWidth - srcBitmap.getWidth()) / 2;
        float top = (squareBitmapWidth - srcBitmap.getHeight()) / 2;
        canvas.drawBitmap(srcBitmap, left, top, paint);
        srcBitmap.recycle();


        return dstBitmap;
    }


    protected Bitmap addBorderToCircularBitmap(Bitmap srcBitmap, int borderWidth,
                                               int borderColor) {
        // Calculate the circular bitmap width with border
        int dstBitmapWidth = srcBitmap.getWidth() + borderWidth * 2;

        // Initialize a new Bitmap to make it bordered circular bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, borderWidth, borderWidth, null);

        // Initialize a new Paint instance to draw border
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getWidth() / 2, // cy
                canvas.getWidth() / 2 - borderWidth / 2, // Radius
                paint // Paint
        );

        // Free the native object associated with this bitmap.
        srcBitmap.recycle();

        // Return the bordered circular bitmap
        return dstBitmap;
    }

    // Custom method to add a shadow around circular bitmap
    protected Bitmap addShadowToCircularBitmap(Bitmap srcBitmap, int shadowWidth,
                                               int shadowColor) {
        // Calculate the circular bitmap width with shadow
        int dstBitmapWidth = srcBitmap.getWidth() + shadowWidth * 2;
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawBitmap(srcBitmap, shadowWidth, shadowWidth, null);

        // Paint to draw circular bitmap shadow
        Paint paint = new Paint();
        paint.setColor(shadowColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(shadowWidth);
        paint.setAntiAlias(true);

        // Draw the shadow around circular bitmap
        canvas.drawCircle(
                dstBitmapWidth / 2, // cx
                dstBitmapWidth / 2, // cy
                dstBitmapWidth / 2 - shadowWidth / 2, // Radius
                paint // Paint
        );
        srcBitmap.recycle();

        // Return the circular bitmap with shadow
        return dstBitmap;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_bottomsheet, container, false);
        searchProductRecycler = view.findViewById(R.id.searchProductRecycler);
        linearNotFound = view.findViewById(R.id.linearNotFound);
        searchProductRecycler.setHasFixedSize(true);
        searchProductRecycler.setItemViewCacheSize(20);
        searchProductRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        searchProduct();

        return view;
    }


    public void searchProduct() {


        Log.e("asdsdfdsf", UserData.getIneed(getActivity()));
        Log.e("asdsdfdsf", UserData.getIneedLat(getActivity()));
        Log.e("asdsdfdsf", UserData.getIneedLng(getActivity()));

        Map<String, String> params = new HashMap<>();
        params.put("word", UserData.getIneed(getActivity()));
        params.put("latitude", UserData.getIneedLat(getActivity()));
        params.put("longitude", UserData.getIneedLng(getActivity()));
        Call<BottomSheetSearchProductModel> call = APIClient.getAPIClient().searchProduct(params);

        call.enqueue(new Callback<BottomSheetSearchProductModel>() {
            @Override
            public void onResponse(@NonNull Call<BottomSheetSearchProductModel> call, @NonNull Response<BottomSheetSearchProductModel> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }

                BottomSheetSearchProductModel model = response.body();

                if (model != null) {
                    if (model.getResult()) {

                        bottomSheetSearchProductDataList = new ArrayList<>();
                        List<BottomSheetSearchProductModel.Datum> dataList = model.getData();


                        if (dataList.size()>0){
                            MapSearchProductsActivity.map.clear();
                            for (int i = 0; i < dataList.size(); i++) {
                                BottomSheetSearchProductModel.Datum data = dataList.get(i);

                                BottomSheetSearchProductData productData = new BottomSheetSearchProductData();
                                productData.setId(data.getProductID());
                                productData.setProductName(data.getProduct());
                                productData.setPrice(data.getRentPerMonth());
                                productData.setProductName(data.getProduct());


                                int heights = 150;
                                int widths = 150;

                                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

                                Bitmap smallMarkers = Bitmap.createScaledBitmap(icon, widths, heights, false);

                                smallMarkers = getCircularBitmap(smallMarkers);


                                double lat = Double.parseDouble(data.getLat());
                                double lng = Double.parseDouble(data.getLong());

                                Marker marker = MapSearchProductsActivity.map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(smallMarkers)).snippet(data.getAddressOfProduct()));
                                marker.setTitle(data.getProduct());
                                marker.showInfoWindow();


                                for (BottomSheetSearchProductModel.Datum.Image image : data.getImage()) {

                                    if (data.getImage() != null) {
                                        if (data.getImage().size() > 0 || data.getImage() != null) {

                                            productData.setPath(data.getImage().get(0).getPath());
                                            productData.setImage(data.getImage().get(0).getImage());
                                        }
                                    }


                                }

                                bottomSheetSearchProductDataList.add(productData);
                            }
                            searchProductRecycler.setAdapter(new BottomSheetSearchProductAdapter(bottomSheetSearchProductDataList, getActivity()));
                            linearNotFound.setVisibility(View.GONE);
                        }else {
                            linearNotFound.setVisibility(View.VISIBLE);
                        }




                    } else {
                        Log.e("uwedw", model.getMessage()+"");
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<BottomSheetSearchProductModel> call, @NonNull Throwable t) {
                Log.e("uwedw", t.getMessage()+"");
            }
        });


    }

}