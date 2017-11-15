package com.android.arsalan.currencyconvertor.view;

import android.content.Context;

import com.android.arsalan.currencyconvertor.model.Currency;
import com.android.arsalan.currencyconvertor.model.CurrencyListRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Arsalan
 * on 2017-05-28.
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangePresenterTest {

    @Mock
    CurrencyListRepository currencyRepository;
    CurrencyExchangePresenter presenter;
    @Mock
    Context context;
    @Mock
    private CurrencyExchangeView view;

    @Before
    public void setUp() throws Exception {
        currencyRepository = mock(CurrencyListRepository.class);
        view = spy(CurrencyExchangeView.class);
        presenter = new CurrencyExchangePresenter(currencyRepository);
        presenter.attachView(view);
    }

    @Test
    public void shouldViewUpdateListViewForCountersListIfListIsNotEmpty() throws Exception {
        List<Currency> aList = Arrays.asList(new Currency("A", 1), new Currency("B", 2), new Currency("C", 3));
        when(currencyRepository.loadData(context)).thenReturn(aList);
        doNothing().when(view).displayCurrencyList(aList);

        presenter.loadCurrencyList(context);

        verify(view).displayCurrencyList(aList);
    }

    @Test
    public void shouldViewHandleIfTheCurrencyListIsNotReady() throws Exception {
        List<Currency> emptyList = new ArrayList<>();
        when(currencyRepository.loadData(context)).thenReturn(emptyList);
        doNothing().when(view).noCurrencyListAvailableToShow();

        presenter.loadCurrencyList(context);

        verify(view).noCurrencyListAvailableToShow();
    }

    @Test
    public void shouldNotUserInputValuePassLimit() throws Exception {
        long input = 999999999999999L;
        presenter.setUserInputValue(input);
        presenter.addValue(9);
        presenter.addValue(8);
        presenter.addValue(7);
        Assert.assertEquals(presenter.getUserInputValue(), input);
    }

    @Test
    public void shouldIgnoreSequenceOfZeros() throws Exception {
        presenter.setUserInputValue(0);
        presenter.addValue(0);
        presenter.addValue(0);
        presenter.addValue(0);
        Assert.assertEquals(presenter.getUserInputValue(), 0L);
    }

    @Test
    public void shouldClearButtonMakeUserInputZero() throws Exception {
        presenter.setUserInputValue(12213213213L);
        presenter.clearUserInput();
        Assert.assertEquals(presenter.getUserInputValue(), 0);
    }

    @Test
    public void showRemoveOneDigitFromEndOfUserInput() {
        long userInput = 1223234L;
        presenter.setUserInputValue(userInput);
        presenter.removeOneDigit();
        Assert.assertEquals(presenter.getUserInputValue(), (userInput / 10));
    }

}