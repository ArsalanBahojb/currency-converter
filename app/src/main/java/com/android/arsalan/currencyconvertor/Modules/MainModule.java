package com.android.arsalan.currencyconvertor.Modules;

import android.app.Application;

import com.android.arsalan.currencyconvertor.model.CurrencyListRepository;
import com.android.arsalan.currencyconvertor.model.CurrencyListRepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Arsalan
 * on 2017-05-29.
 */
@Module
public class MainModule {

    DaggerApplication app;

    public MainModule(DaggerApplication application) {
        app = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    CurrencyListRepository provideCurrencyListRepository() {
        return new CurrencyListRepositoryManager();
    }

}
