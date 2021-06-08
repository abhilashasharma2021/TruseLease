package com.truelease.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.truelease.Adapters.ChatWithAdminAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ChatWithAdminModel;
import com.truelease.RetrofitModel.ShowAdminChatModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatWithAdminActivity extends AppCompatActivity {

    String strMessage = "";
    EditText etMessage;
    List<ShowAdminChatModel.Result> chatList;
    RecyclerView chatRecycler;

    ChatWithAdminAdapter adapter;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            showChats();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_admin);


        ImageView back = findViewById(R.id.back);
        chatRecycler = findViewById(R.id.chatRecycler);
        CircleView send = findViewById(R.id.send);
        etMessage = findViewById(R.id.etMessage);
        back.setOnClickListener(v -> finish());

        chatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatRecycler.setHasFixedSize(true);
        chatRecycler.setItemViewCacheSize(20);


        showChats();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strMessage = etMessage.getText().toString().trim();
                if (TextUtils.isEmpty(strMessage)) {

                } else {
                    sendMsgToAdmin(strMessage);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter("Check"));
    }



    public void sendMsgToAdmin(String msg) {

        Map<String, String> params = new HashMap<>();
        params.put("sanderID", UserData.getUserID(ChatWithAdminActivity.this));
        params.put("reciverID", "0");
        params.put("text", msg);

        Call<ChatWithAdminModel> call = APIClient.getAPIClient().support(params);
        call.enqueue(new Callback<ChatWithAdminModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatWithAdminModel> call, @NonNull Response<ChatWithAdminModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatWithAdminActivity.this);
                }

                ChatWithAdminModel model = response.body();
                if (model != null) {


                    if (model.getResult()) {

                        etMessage.setText("");
                        showChats();
                        Toast.makeText(ChatWithAdminActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChatWithAdminActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ChatWithAdminModel> call, @NonNull Throwable t) {

            }
        });
    }


    public void showChats() {
        Map<String, String> params = new HashMap<>();
        params.put("sanderID", UserData.getUserID(ChatWithAdminActivity.this));
        Call<ShowAdminChatModel> call = APIClient.getAPIClient().showSupportChat(params);
        call.enqueue(new Callback<ShowAdminChatModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowAdminChatModel> call, @NonNull Response<ShowAdminChatModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatWithAdminActivity.this);
                }
                ShowAdminChatModel model = response.body();

                if (model != null) {
                    chatList = new ArrayList<>();
                    List<ShowAdminChatModel.Result> resultList = model.getResult();
                    chatList.addAll(resultList);
                    adapter = new ChatWithAdminAdapter(chatList, ChatWithAdminActivity.this);
                    chatRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    chatRecycler.scrollToPosition(chatList.size() - 1);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ShowAdminChatModel> call, @NonNull Throwable t) {

            }
        });
    }
}