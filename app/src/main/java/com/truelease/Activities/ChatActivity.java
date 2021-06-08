package com.truelease.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.truelease.Adapters.ChatAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.SendMessageModel;
import com.truelease.RetrofitModel.ShowChatModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecycler;
    List<ShowChatModel.Result> chatDataList;
    ImageView back;
    CircleView send;
    EditText etMessage;
    String strMessage = "";

    BroadcastReceiver mBroadcastReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecycler = findViewById(R.id.chatRecycler);
        back = findViewById(R.id.back);
        send = findViewById(R.id.send);
        etMessage = findViewById(R.id.etMessage);
        chatRecycler.setHasFixedSize(true);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String result = intent.getStringExtra("action");

                if (!result.equals(result)) {
                } else {
                    if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChats")) {
                        Log.e("skcjks","bmyChat");
                        showChatsTwo();
                    } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChatsOnProduct")) {
                        Log.e("skcjks","bMyChatsOnProduct");
                        showChats();
                    } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyOrders")) {
                        Log.e("skcjks","bMyOrders");
                        showChatsMyOrder();
                    }

                }


            }
        };


        back.setOnClickListener(view -> finish());

        send.setOnClickListener(view -> {

            strMessage = etMessage.getText().toString().trim();


            if (strMessage.equals("")) {

            } else {

                Map<String, String> param = null;

                if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChats")) {

                    Log.e("coskdo", UserData.getChatClickedOn(ChatActivity.this) + "");
                    param = new HashMap<>();
                    param.put("sanderID", UserData.getUserID(getApplicationContext()));
                    param.put("userID", UserData.getUserID(getApplicationContext()));
                    param.put("reciverID", UserData.getChatMsgSenderID(getApplicationContext()));
                    param.put("text", strMessage);

                } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChatsOnProduct")) {
                    Log.e("coskdo", UserData.getChatClickedOn(ChatActivity.this) + "");
                    //MyChatsOnProduct
                    param = new HashMap<>();
                    param.put("sanderID", UserData.getUserID(getApplicationContext()));
                    param.put("userID", UserData.getUserID(getApplicationContext()));
                    param.put("reciverID", UserData.getReceiverID(getApplicationContext()));
                    param.put("text", strMessage);
                } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyOrders")) {

                    Log.e("coskdo", UserData.getChatClickedOn(ChatActivity.this) + "");
                    Log.e("coskdo", UserData.getMyOrderOwnerID(ChatActivity.this) + "");
                    param = new HashMap<>();
                    param.put("sanderID", UserData.getUserID(getApplicationContext()));
                    param.put("userID", UserData.getUserID(getApplicationContext()));
                    param.put("reciverID", UserData.getMyOrderOwnerID(getApplicationContext()));
                    param.put("text", strMessage);

                }

                sendMessage(param);
                etMessage.setText("");

            }

        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mBroadcastReciever, new IntentFilter("Check"));
        switch (UserData.getChatClickedOn(ChatActivity.this)) {
            case "MyChats":
                Log.e("iiiii", "myChats");
                showChatsTwo();
                break;
            case "MyChatsOnProduct":
                Log.e("iiiii", "MyChatsOnProduct");
                showChats();
                break;
            case "MyOrders":
                Log.e("iiiii", "MyOrders");
                showChatsMyOrder();
                break;
        }

    }


    public void showChats() {

        Map<String, String> params = new HashMap<>();
        params.put("sanderID", UserData.getUserID(getApplicationContext()));
        params.put("reciverID", UserData.getReceiverID(getApplicationContext()));

        Call<ShowChatModel> call = APIClient.getAPIClient().showChats(params);
        call.enqueue(new Callback<ShowChatModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowChatModel> call, @NonNull Response<ShowChatModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatActivity.this);
                }

                ShowChatModel chatModel = response.body();

                if (chatModel != null) {
                    chatDataList = new ArrayList<>();
                    List<ShowChatModel.Result> result = chatModel.getResult();

                    if (result.size() > 0) {

                        chatDataList.addAll(result);
                    }

                }

                chatRecycler.setAdapter(new ChatAdapter(chatDataList, ChatActivity.this));
                chatRecycler.scrollToPosition(chatDataList.size() - 1);
            }

            @Override
            public void onFailure(@NonNull Call<ShowChatModel> call, @NonNull Throwable t) {

            }
        });

    }


    public void showChatsTwo() {


        Log.e("dkclsdc", UserData.getUserID(getApplicationContext()));
        Log.e("dkclsdc", UserData.getChatMsgSenderID(getApplicationContext()));

        Map<String, String> params = new HashMap<>();

        params.put("sanderID", UserData.getUserID(getApplicationContext()));
        params.put("reciverID", UserData.getChatMsgSenderID(getApplicationContext()));

        Call<ShowChatModel> call = APIClient.getAPIClient().showChats(params);
        call.enqueue(new Callback<ShowChatModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowChatModel> call, @NonNull Response<ShowChatModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatActivity.this);
                }

                ShowChatModel chatModel = response.body();

                if (chatModel != null) {
                    chatDataList = new ArrayList<>();
                    List<ShowChatModel.Result> result = chatModel.getResult();

                    if (result.size() > 0) {

                        chatDataList.addAll(result);
                    }

                }

                chatRecycler.setAdapter(new ChatAdapter(chatDataList, ChatActivity.this));
                chatRecycler.scrollToPosition(chatDataList.size() - 1);

            }

            @Override
            public void onFailure(@NonNull Call<ShowChatModel> call, @NonNull Throwable t) {

            }
        });

    }


    public void showChatsMyOrder() {

        Map<String, String> params = new HashMap<>();

        params.put("sanderID", UserData.getUserID(getApplicationContext()));
        params.put("reciverID", UserData.getMyOrderOwnerID(getApplicationContext()));

        Call<ShowChatModel> call = APIClient.getAPIClient().showChats(params);
        call.enqueue(new Callback<ShowChatModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowChatModel> call, @NonNull Response<ShowChatModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatActivity.this);
                }

                ShowChatModel chatModel = response.body();

                if (chatModel != null) {
                    chatDataList = new ArrayList<>();
                    List<ShowChatModel.Result> result = chatModel.getResult();

                    if (result.size() > 0) {

                        chatDataList.addAll(result);
                    }

                }

                chatRecycler.setAdapter(new ChatAdapter(chatDataList, ChatActivity.this));
                chatRecycler.scrollToPosition(chatDataList.size() - 1);

            }

            @Override
            public void onFailure(@NonNull Call<ShowChatModel> call, @NonNull Throwable t) {

            }
        });

    }


    public void sendMessage(Map<String, String> param) {

        Call<SendMessageModel> call = APIClient.getAPIClient().sendMsg(param);
        call.enqueue(new Callback<SendMessageModel>() {
            @Override
            public void onResponse(@NonNull Call<SendMessageModel> call, @NonNull Response<SendMessageModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChatActivity.this);
                }

                SendMessageModel model = response.body();

                if (model != null) {


                    if (model.getMessage().equals("Message Sent Successfully")) {

                        Log.e("xksjxs", UserData.getChatClickedOn(ChatActivity.this));

                        if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChats")) {

                            showChatsTwo();
                        } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyChatsOnProduct")) {

                            showChats();
                        } else if (UserData.getChatClickedOn(ChatActivity.this).equals("MyOrders")) {

                            showChatsMyOrder();
                        }




                        MediaPlayer mediaPlayer = MediaPlayer.create(ChatActivity.this, R.raw.pop);
                        mediaPlayer.start();
                        Toast.makeText(ChatActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                        etMessage.setText("");
                    } else {
                        Toast.makeText(ChatActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SendMessageModel> call, @NonNull Throwable t) {

            }
        });
    }
}