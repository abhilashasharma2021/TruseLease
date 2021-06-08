package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.truelease.Model.CurrencyData;
import com.truelease.R;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<CurrencyData> {

    public CurrencyAdapter(Context context, List<CurrencyData> currencyDataArrayList) {
        super(context, 0, currencyDataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.currency_list_layout, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        TextView text_symbol = convertView.findViewById(R.id.text_symbol);

        CurrencyData currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getCode());
            text_symbol.setText(currentItem.getSymbol());
        }

        return convertView;
    }
}