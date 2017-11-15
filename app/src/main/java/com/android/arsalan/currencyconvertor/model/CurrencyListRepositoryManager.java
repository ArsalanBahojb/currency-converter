package com.android.arsalan.currencyconvertor.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.arsalan.currencyconvertor.Modules.CurrencyConversionJson;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */

public class CurrencyListRepositoryManager implements CurrencyListRepository {

    private static final String PREFERENCE_NAME = "currency_list";
    private static final String PREFERENCE_KEY = "data";

    private ArrayList<Currency> currencyArrayList = new ArrayList<>();

    public CurrencyListRepositoryManager() {
        currencyArrayList.add(new Currency("N/A", 0));
    }

    @Override
    public List<Currency> getCurrencyList() {
        return currencyArrayList;
    }

    @Override
    public List<Currency> loadData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(PREFERENCE_KEY, "");
        if (!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Currency>>() {
            }.getType();
            currencyArrayList = gson.fromJson(json, type);
        }
        return currencyArrayList;
    }

    @Inject
    @Override
    public List<Currency> updateLatestCurrencyRates(Context context, CurrencyConversionJson result) {
        currencyArrayList.clear();
        JsonObject list = result.rates;
        for (Map.Entry<String, JsonElement> key : list.entrySet()) {
            Double rate = list.get(key.getKey()) != null ? list.get(key.getKey()).getAsDouble() : 0;
            currencyArrayList.add(new Currency(key.getKey(), rate));
        }
        saveData(context);
        return currencyArrayList;
    }

    private void saveData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String jsonMemberships = gson.toJson(currencyArrayList);
        prefsEditor.putString(PREFERENCE_KEY, jsonMemberships);
        prefsEditor.apply();
    }

    @Override
    public boolean isEmpty() {
        return currencyArrayList.isEmpty();
    }
}
