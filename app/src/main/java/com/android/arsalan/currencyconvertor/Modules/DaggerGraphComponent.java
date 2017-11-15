package com.android.arsalan.currencyconvertor.Modules;

import com.android.arsalan.currencyconvertor.activities.CurrencyExchange;
import com.android.arsalan.currencyconvertor.view.CurrencyExchangeView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Arsalan
 * on 2017-05-29.
 */

@Singleton
@Component(modules = {MainModule.class})
public interface DaggerGraphComponent {

    void inject(CurrencyExchange currencyExchange);

    final class Initializer {
        private Initializer() {
        }

        public static DaggerGraphComponent init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}
