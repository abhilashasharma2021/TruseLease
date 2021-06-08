package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.R;
import com.truelease.RetrofitModel.ShowChatModel;
import com.truelease.User.UserData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<ShowChatModel.Result> chatDataList;
    Context context;

    public ChatAdapter(List<ShowChatModel.Result> chatDataList, Context context) {
        this.chatDataList = chatDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        ShowChatModel.Result data = chatDataList.get(position);


        if (data != null) {

            if (data.getSanderID().equals(UserData.getUserID(context))) {

                holder.chatTwo.setVisibility(View.VISIBLE);
                holder.txt2.setText(data.getText());

                if (!data.getMessageTime().equals("")){

                    if (data.getMessageTime().contains(" ")){
                        String[] dTime=data.getMessageTime().split(" ");
                        holder.time2.setText(dTime[1]);
                    }

                }


            } else {
                holder.chatOne.setVisibility(View.VISIBLE);
                holder.txt1.setText(data.getText());
                if (!data.getMessageTime().equals("")){

                    if (data.getMessageTime().contains(" ")){
                        String[] dTime=data.getMessageTime().split(" ");
                        holder.time1.setText(dTime[1]);
                    }

                }
            }





        }


    }







    @Override
    public int getItemCount() {
        return chatDataList.size();
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

        RelativeLayout chatOne, chatTwo;
        ImageView prf, prf2;
        TextView txt1, txt2, time1, time2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatOne = itemView.findViewById(R.id.chatOne);
            chatTwo = itemView.findViewById(R.id.chatTwo);
            prf = itemView.findViewById(R.id.prf);
            prf2 = itemView.findViewById(R.id.prf2);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
        }
    }
}


