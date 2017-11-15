package com.android.arsalan.currencyconvertor.Modules;

import android.app.Application;

import com.android.arsalan.currencyconvertor.model.Currency;
import com.android.arsalan.currencyconvertor.view.CurrencyExchangeView;

import java.util.List;

/**
 * Created by Arsalan
 * on 2017-05-29.
 */

public class DaggerApplication extends Application implements CurrencyExchangeView {
    private static DaggerGraphComponent graph;
    private static DaggerApplication instance;

    public static DaggerGraphComponent component() {
        return graph;
    }

    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    @Override
    public void displayCurrencyList(List<Currency> currencyList) {
        instance.displayCurrencyList(currencyList);
    }

    @Override
    public void noCurrencyListAvailableToShow() {
        instance.noCurrencyListAvailableToShow();
    }

    @Override
    public void setUserInputValue(long userInput) {
        instance.setUserInputValue(userInput);
    }

    @Override
    public void showMessageUserInputOutOfRange() {
        instance.showMessageUserInputOutOfRange();
    }

    @Override
    public void updateExchangeValue(long exchangeResult) {
        instance.updateExchangeValue(exchangeResult);
    }
}
