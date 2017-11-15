package com.android.arsalan.currencyconvertor.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.arsalan.currencyconvertor.Modules.CurrencyConversionJson;
import com.android.arsalan.currencyconvertor.Modules.DaggerApplication;
import com.android.arsalan.currencyconvertor.R;
import com.android.arsalan.currencyconvertor.model.Currency;
import com.android.arsalan.currencyconvertor.view.CurrencyExchangePresenter;
import com.android.arsalan.currencyconvertor.view.CurrencyExchangeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CurrencyExchange extends AppCompatActivity implements CurrencyExchangeView {
    protected static final String DATA_FORMAT_PATTERN = "###,###,###";
    private static final String TAG = "CurrencyExchange";

    @Inject
    protected CurrencyExchangePresenter presenter;

    @InjectView(R.id.countries_list)
    protected Spinner countriesList;
    @InjectView(R.id.user_input_box)
    protected TextView userInputView;
    @InjectView(R.id.convert_result)
    protected TextView convertResultView;

    DecimalFormat formatter = new DecimalFormat(DATA_FORMAT_PATTERN);

    private CountriesListAdaptor adaptor;
    private Subscription subscription;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        DaggerApplication.component().inject(this);
        ButterKnife.inject(this);

        adaptor = new CountriesListAdaptor(this);
        countriesList.setAdapter(adaptor);

        presenter.attachView(this);
        presenter.loadCurrencyList(context);

        countriesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updateCountryName(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Subscriber<CurrencyConversionJson> subscriber = new Subscriber<CurrencyConversionJson>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Uploading data has been completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error:" + e.getMessage(), e);
                Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(CurrencyConversionJson result) {
                presenter.updateRepositoryAndView(context,result);
                Log.d(TAG, "Result:" + result);
            }
        };

        subscription = getCurrencyConversionObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

    }

    @OnClick({R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_ac, R.id.button_c})
    public void buttonHandler(View view) {
        switch (view.getId()) {
            case R.id.button_0:
                presenter.addValue(0);
                break;
            case R.id.button_1:
                presenter.addValue(1);
                break;
            case R.id.button_2:
                presenter.addValue(2);
                break;
            case R.id.button_3:
                presenter.addValue(3);
                break;
            case R.id.button_4:
                presenter.addValue(4);
                break;
            case R.id.button_5:
                presenter.addValue(5);
                break;
            case R.id.button_6:
                presenter.addValue(6);
                break;
            case R.id.button_7:
                presenter.addValue(7);
                break;
            case R.id.button_8:
                presenter.addValue(8);
                break;
            case R.id.button_9:
                presenter.addValue(9);
                break;
            case R.id.button_ac:
                presenter.clearUserInput();
                break;
            case R.id.button_c:
                presenter.removeOneDigit();
                break;
        }
    }


    @Override
    public void displayCurrencyList(List<Currency> currencyList) {
        adaptor.setCurrencyList(currencyList);
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void noCurrencyListAvailableToShow() {
        Toast.makeText(this, "No data to show, Please try later", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setUserInputValue(long userInput) {
        userInputView.setText(formatter.format(userInput));
    }

    @Override
    public void showMessageUserInputOutOfRange() {
        Toast.makeText(this, "User input is to big", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateExchangeValue(long exchangeResult) {
        convertResultView.setText(formatter.format(exchangeResult));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public Observable<CurrencyConversionJson> getCurrencyConversionObservable() {
        return Observable.create(new Observable.OnSubscribe<CurrencyConversionJson>() {
            @Override
            public void call(Subscriber<? super CurrencyConversionJson> subscriber) {
                try {
                    subscriber.onNext(getGist());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                    subscriber.onError(e);
                }
            }
        });
    }

    @Nullable
    private CurrencyConversionJson getGist() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(CurrencyConversionJson.URL)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return new Gson().fromJson(response.body().string(), CurrencyConversionJson.class);
        }
        return null;
    }

}
