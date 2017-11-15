package com.android.arsalan.currencyconvertor.Modules;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Arsalan
 * on 2017-05-30.
 */

public class CurrencyConversionJson {

    public static final String URL = "http://api.fixer.io/latest?base=CAD";

    @SerializedName("base")
    public String base;

    @SerializedName("date")
    public String date;

    @SerializedName("rates")
    public JsonObject rates;
}
