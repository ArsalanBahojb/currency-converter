package com.android.arsalan.currencyconvertor.view;

import com.android.arsalan.currencyconvertor.model.Currency;

import java.util.List;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */

public interface CurrencyExchangeView {

    void displayCurrencyList(List<Currency> currencyList);

    void noCurrencyListAvailableToShow();

    void setUserInputValue(long userInput);

    void showMessageUserInputOutOfRange();

    void updateExchangeValue(long exchangeResult);

}
