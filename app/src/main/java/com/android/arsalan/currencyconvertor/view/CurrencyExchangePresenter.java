package com.android.arsalan.currencyconvertor.view;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.android.arsalan.currencyconvertor.Modules.CurrencyConversionJson;
import com.android.arsalan.currencyconvertor.model.Currency;
import com.android.arsalan.currencyconvertor.model.CurrencyListRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */
public class CurrencyExchangePresenter {

    private static final long MAX_USER_INPUT_VALUE = (Long.MAX_VALUE / 10000);
    private static final String TAG = "Presenter";
    @Inject
    CurrencyListRepository currencyRepository;
    private CurrencyExchangeView view;
    @VisibleForTesting
    private long userInputValue;
    private long exchangeResult;
    private double exchangeRate;

    @Inject
    public CurrencyExchangePresenter(CurrencyListRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void attachView(CurrencyExchangeView view) {
        this.view = view;
    }

    public void loadCurrencyList(Context context) {
        List<Currency> currencyList = currencyRepository.loadData(context);
        Log.d(TAG, "List size:" + currencyList.size());
        if (currencyList.isEmpty() || currencyList.size() == 1) {
            view.noCurrencyListAvailableToShow();
        } else {
            view.displayCurrencyList(currencyList);
        }
        updateExchangeValue();
    }

    public void clearUserInput() {
        userInputValue = 0;
        updateExchangeValue();
    }


    public void removeOneDigit() {
        userInputValue = userInputValue / 10;
        updateExchangeValue();
    }

    public void addValue(int userInput) {
        if (userInputValue < MAX_USER_INPUT_VALUE) {
            setUserInputValue(userInputValue * 10 + userInput);
            view.setUserInputValue(getUserInputValue());
            updateExchangeValue();
        } else {
            view.showMessageUserInputOutOfRange();
        }
    }

    long getUserInputValue() {
        return userInputValue;
    }

    void setUserInputValue(long userInput) {
        userInputValue = userInput;
    }

    public void updateCountryName(int position) {
        Log.d(TAG, "Item selected:" + position);
        if (!currencyRepository.isEmpty()) {
            Currency currentCurrency = currencyRepository.getCurrencyList().get(position);
            exchangeRate = currentCurrency.getValue();
            Log.d(TAG, "Item Rate:" + exchangeResult);
            updateExchangeValue();
        }
    }

    protected void updateExchangeValue() {
        view.setUserInputValue(userInputValue);
        exchangeResult = convert(userInputValue, exchangeRate);
        view.updateExchangeValue(exchangeResult);
    }

    private long convert(long userInputValue, double exchangeRate) {
        return (long) (exchangeRate * userInputValue);
    }

    public void updateRepositoryAndView(Context context, CurrencyConversionJson result) {
        currencyRepository.updateLatestCurrencyRates(context, result);
        view.displayCurrencyList(currencyRepository.getCurrencyList());
        if (exchangeResult == 0 && !currencyRepository.isEmpty()) {
            exchangeRate = currencyRepository.getCurrencyList().get(0).getValue();
            exchangeResult = convert(userInputValue, exchangeRate);
        }
        updateExchangeValue();
    }
}
