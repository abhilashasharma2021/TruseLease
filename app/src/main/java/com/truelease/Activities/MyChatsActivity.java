package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.MyChatsAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyChatData;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.DeleteChatsModel;
import com.truelease.RetrofitModel.MyChatModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChatsActivity extends AppCompatActivity {

    public static RecyclerView myChatsRecycler;
    ImageView back, deleteMsg;
    List<MyChatData> myChatDataList;
    RelativeLayout rel_empty;
    MyChatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);

        myChatsRecycler = findViewById(R.id.myChatsRecycler);
        back = findViewById(R.id.back);
        deleteMsg = findViewById(R.id.deleteMsg);
        rel_empty = findViewById(R.id.rel_empty);

        back.setOnClickListener(v -> finish());

        myChatsRecycler.setHasFixedSize(true);
        myChatsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myChatsRecycler.setItemViewCacheSize(20);


        showMesages();


    }

    @Override
    protected void onResume() {
        super.onResume();

        showMesages();
    }

    public void showMesages() {

        Call<MyChatModel> call = APIClient.getAPIClient().showMyChats(UserData.getUserID(MyChatsActivity.this));
        call.enqueue(new Callback<MyChatModel>() {
            @Override
            public void onResponse(@NonNull Call<MyChatModel> call, @NonNull Response<MyChatModel> response) {


                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(MyChatsActivity.this);
                }


                MyChatModel model = response.body();
                if (model != null) {


                    if (model.getResult()) {

                        List<MyChatModel.Datum> resultList = model.getData();

                        if (resultList.size() > 0) {

                            myChatDataList = new ArrayList<>();

                            for (MyChatModel.Datum result : resultList) {

                                MyChatData myChatData = new MyChatData();
                                myChatData.setChatID(result.getChatID());
                                myChatData.setSenderID(result.getSanderID());
                                myChatData.setRecieverID(result.getReciverID());


                                myChatData.setMsz_count(result.getMszCount());

                                if (result.getSenderDetail() != null) {

                                    MyChatModel.Datum.SenderDetail senderDetail = result.getSenderDetail();
                                    myChatData.setMsgsSenderName(senderDetail.getName());
                                    myChatData.setProfile_image(senderDetail.getProfileImage());

                                }

                                MyChatModel.Datum.LastMaz lastMsg = result.getLastMaz();


                                myChatData.setMessage_time(lastMsg.getMessageTime());
                                myChatData.setLast_msg(lastMsg.getText());


                                myChatDataList.add(myChatData);

                            }
                            rel_empty.setVisibility(View.GONE);
                            adapter = new MyChatsAdapter(myChatDataList, MyChatsActivity.this);
                            myChatsRecycler.setAdapter(adapter);

                            deleteMsg.setOnClickListener(v -> {

                                if (adapter.getItemCount() > 0) {

                                    if (adapter.getSelected().size() > 0) {


                                        StringBuilder builder = new StringBuilder();

                                        for (int i = 0; i < adapter.getSelected().size(); i++) {

                                            String id = adapter.getSelected().get(i).getChatID();

                                            builder.append(id).append(",");
                                        }

                                        builder.deleteCharAt(builder.toString().length() - 1);

                                        Log.e("owpqaw", builder.toString() + "");
                                    }

                                }


                            });


                            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return false;
                                }

                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                                    MyChatData data = myChatDataList.get(viewHolder.getAdapterPosition());

                                    if (data != null) {

                                        Log.e("sjdksd", data.getSenderID());
                                        Log.e("sjdksd", data.getRecieverID());

                                        Map<String, String> map = new HashMap<>();
                                        map.put("delete_by", UserData.getUserID(MyChatsActivity.this));
                                        map.put("delete_to", data.getRecieverID());

                                        deleteChat(map,myChatDataList,data);
                                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());


                                    }


                                }
                            }).attachToRecyclerView(MyChatsActivity.myChatsRecycler);


                        } else {
                            rel_empty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ReturnErrorToast.showWarningToast(MyChatsActivity.this, model.getMessage());
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<MyChatModel> call, @NonNull Throwable t) {

            }
        });

    }


    private void deleteChat(Map<String, String> param,List<MyChatData> myChatDatal,MyChatData data) {

        Call<DeleteChatsModel> call = APIClient.getAPIClient().deleteChat(param);
        call.enqueue(new Callback<DeleteChatsModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteChatsModel> call, @NonNull Response<DeleteChatsModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(MyChatsActivity.this);
                } else {

                    DeleteChatsModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {

                            myChatDatal.remove(data);
                            Toast.makeText(MyChatsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MyChatsActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteChatsModel> call, @NonNull Throwable t) {

                Log.e("iiiio", "onFailure: "+t.getMessage());
            }
        });

    }

}