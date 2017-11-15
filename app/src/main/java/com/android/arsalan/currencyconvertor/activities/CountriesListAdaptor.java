package com.android.arsalan.currencyconvertor.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.arsalan.currencyconvertor.R;
import com.android.arsalan.currencyconvertor.model.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */

public class CountriesListAdaptor extends BaseAdapter {


    private final LayoutInflater inflater;
    private List<Currency> currencyList = new ArrayList<>();

    public CountriesListAdaptor(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.spiner_item, null);
        TextView label = (TextView) view.findViewById(R.id.spinnerItem);
        if (currencyList.isEmpty()) {
            label.setText(R.string.not_available);
        } else {
            label.setText(currencyList.get(position).getSymbol());
        }
        return view;
    }

}
