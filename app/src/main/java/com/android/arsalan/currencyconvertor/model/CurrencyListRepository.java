package com.android.arsalan.currencyconvertor.model;

import android.content.Context;

import com.android.arsalan.currencyconvertor.Modules.CurrencyConversionJson;

import java.util.List;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */

public interface CurrencyListRepository {

    List<Currency> getCurrencyList();

    List<Currency> updateLatestCurrencyRates(Context context, CurrencyConversionJson result);

    List<Currency> loadData(Context context);

    boolean isEmpty();
}
