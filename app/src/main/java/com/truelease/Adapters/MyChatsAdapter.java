package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Activities.ChatActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyChatData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.DeleteChatsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChatsAdapter extends RecyclerView.Adapter<MyChatsAdapter.ViewHolder> {

    List<MyChatData> myChatDataList;
    Context context;

    public MyChatsAdapter(List<MyChatData> myChatDataList, Context context) {
        this.myChatDataList = myChatDataList;
        this.context = context;
    }

    public void setChats(List<MyChatData> myChatDataList) {
        this.myChatDataList = new ArrayList<>();
        this.myChatDataList = myChatDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mychats, parent, false);
        return new MyChatsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChatsAdapter.ViewHolder holder, int position) {

        MyChatData data = myChatDataList.get(position);

        if (data != null) {

            holder.name.setText(data.getMsgsSenderName());
            holder.msg.setText(data.getLast_msg());

            holder.time.setText(data.getMessage_time().split(" ")[1]);
            holder.date.setText(data.getMessage_time().split(" ")[0]);


            holder.relative.setOnClickListener(view -> {
                context.startActivity(new Intent(context, ChatActivity.class));
                SharedHelper.putKey(context, AppConstats.CHAT_MSG_SENDER_ID, data.getSenderID());
                SharedHelper.putKey(context, AppConstats.CHAT_CLICKED_ON, "MyChats");
            });


            holder.relative.setOnLongClickListener(v -> {
                data.setSelected(!data.isSelected());
                holder.mark.setVisibility(data.isSelected() ? View.VISIBLE : View.GONE);
                return true;
            });

        }






    }


    public MyChatData getChatPostion(int position){

        return myChatDataList.get(position);

    }


    @Override
    public int getItemCount() {
        return myChatDataList.size();
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

        RelativeLayout relative;
        ImageView prf, mark;
        TextView name, msg, time, date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.msg);
            relative = itemView.findViewById(R.id.relative);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            mark = itemView.findViewById(R.id.mark);

        }
    }


    public List<MyChatData> getSelected() {
        ArrayList<MyChatData> selected = new ArrayList<>();
        for (int i = 0; i < myChatDataList.size(); i++) {
            if (myChatDataList.get(i).isSelected()) {
                selected.add(myChatDataList.get(i));
            }
        }
        return selected;
    }



}


