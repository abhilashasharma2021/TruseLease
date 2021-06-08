package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Model.ProductData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowProductAdapter extends RecyclerView.Adapter<ShowProductAdapter.ViewHolder> {

    List<ProductData> showProductDataList;
    Context context;
    private static CronetEngine cronetEngine;


    public ShowProductAdapter(List<ProductData> showProductDataList, Context context) {
        this.showProductDataList = showProductDataList;
        this.context = context;
        getCronetEngine(context);
        startNetLog();
    }

    @NonNull
    @Override
    public ShowProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list, parent, false);
        return new ShowProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowProductAdapter.ViewHolder holder, int position) {

        ProductData data = showProductDataList.get(position);

        if (data!=null){

            if (data.getProImage()!=null){

                Executor executor = Executors.newSingleThreadExecutor();
                UrlRequest.Callback callback = new SimpleUrlRequestCallback(holder.iv_image,
                        this.context);
                UrlRequest.Builder builder = cronetEngine.newUrlRequestBuilder(
                        data.getProPath() + data.getProImage(), callback, executor);
                ((SimpleUrlRequestCallback) callback).start = System.nanoTime();
                // Start the request
                builder.build().start();

              /*  Picasso.get().load(data.getProPath() + data.getProImage()).placeholder(R.drawable.logo2)
                        .resize(350,350)
                        .into(holder.iv_image);*/
            }



            if(data.getProName().length()>30){
                holder.txt_name.setText(data.getProName().substring(0,29)+"....");
            }else {
                holder.txt_name.setText(data.getProName());
            }



            holder.txt_price.setText("\u20B9"+data.getRentPerMonth());



            if (!data.getRentStatus().equals("0")){
                holder.txt_price.setText("On Rent");
                holder.txt_price.setTextColor(Color.parseColor("#FF0800"));

            }




            holder.relProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedHelper.putKey(context, AppConstats.PRODUCT_ID,data.getProId());
                    context.startActivity(new Intent(context, ProductDetailsActivity.class));
                }
            });
        }




    }

    static class SimpleUrlRequestCallback extends UrlRequest.Callback {

        private final ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream();
        private final WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived);
        private final ImageView imageView;
        public long start;
        private final Activity mainActivity;

        SimpleUrlRequestCallback(ImageView imageView, Context context) {
            this.imageView = imageView;
            this.mainActivity = (Activity) context;
        }

        @Override
        public void onRedirectReceived(
                UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
            android.util.Log.i("TAG", "****** onRedirectReceived ******");
            request.followRedirect();
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
            android.util.Log.i("TAG", "****** Response Started ******");
            android.util.Log.i("TAG", "*** Headers Are *** " + info.getAllHeaders());

            request.read(ByteBuffer.allocateDirect(32 * 1024));
        }

        @Override
        public void onReadCompleted(
                UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
            android.util.Log.i("TAG", "****** onReadCompleted ******" + byteBuffer);
            byteBuffer.flip();
            try {
                receiveChannel.write(byteBuffer);
            } catch (IOException e) {
                android.util.Log.i("TAG", "IOException during ByteBuffer read. Details: ", e);
            }
            byteBuffer.clear();
            request.read(byteBuffer);
        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            byte[] byteArray = bytesReceived.toByteArray();
            final Bitmap bimage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mainActivity.runOnUiThread(() -> {
                imageView.setImageBitmap(bimage);
                imageView.getLayoutParams().height = 400;
                imageView.getLayoutParams().width = bimage.getWidth();
            });
        }

        @Override
        public void onFailed(UrlRequest var1, UrlResponseInfo var2, CronetException var3) {
            android.util.Log.i("TAG", "****** onFailed, error is: " + var3.getMessage());
        }
    }



    private static synchronized CronetEngine getCronetEngine(Context context) {
        // Lazily create the Cronet engine.
        if (cronetEngine == null) {
            CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
            // Enable caching of HTTP data and
            // other information like QUIC server information, HTTP/2 protocol and QUIC protocol.
            cronetEngine = myBuilder
                    .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_IN_MEMORY, 100 * 1024)
                    .enableHttp2(true)
                    .enableQuic(true)
                    .build();
        }
        return cronetEngine;
    }

    /**
     * Method to start NetLog to log Cronet events
     */
    private void startNetLog() {
        File outputFile;
        try {
            outputFile = File.createTempFile("cronet", "log",
                    Environment.getExternalStorageDirectory());
            cronetEngine.startNetLogToFile(outputFile.toString(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return showProductDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relProduct;
        ImageView iv_image;
        TextView txt_name , txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relProduct = itemView.findViewById(R.id.relProduct);
            iv_image = itemView.findViewById(R.id.iv_image);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}

