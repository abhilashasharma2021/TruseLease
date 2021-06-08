package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.R;
import com.truelease.RetrofitModel.TransactionData;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    List<TransactionData.Datum> transactionDataList;
    Context context;

    public TransactionAdapter(List<TransactionData.Datum> transactionDataList, Context context) {
        this.transactionDataList = transactionDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_layout, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        TransactionData.Datum data = transactionDataList.get(position);


        if (data != null) {

            setPayThrough(holder.payThrough, data.getPayThrough());

            if (!data.getCraditAmount().equals("") && data.getDebitAmount().equals("0")) {
                holder.amount.setText("Rs." + data.getCraditAmount());
                holder.img.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.GONE);
            }


            if (!data.getDebitAmount().equals("") && data.getCraditAmount().equals("0")) {
                holder.amount.setText("- Rs." + data.getDebitAmount());
                holder.img.setVisibility(View.GONE);
                holder.img2.setVisibility(View.VISIBLE);

            }

            holder.date.setText(data.getTime()+"");

        }


    }


    private void setPayThrough(TextView textView, String payT) {
        switch (payT) {
            case "0":
                textView.setText("Payment Received from Admin");
                break;
            case "1":
                textView.setText("Pay through Paypal");
                break;
            case "2":
                textView.setText("Pay through Paytm");
                break;
        }
    }


    @Override
    public int getItemCount() {
        return transactionDataList.size();
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

        TextView payThrough, date, amount;
        ImageView img,img2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            payThrough = itemView.findViewById(R.id.payThrough);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            img = itemView.findViewById(R.id.img);
            img2 = itemView.findViewById(R.id.img2);
        }
    }
}


