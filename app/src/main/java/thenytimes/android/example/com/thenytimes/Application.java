package thenytimes.android.example.com.thenytimes;

import android.content.Context;

import thenytimes.android.example.com.thenytimes.dagger.component.DaggerNetworkComponent;
import thenytimes.android.example.com.thenytimes.dagger.component.NetworkComponent;
import thenytimes.android.example.com.thenytimes.dagger.module.ApplicationModule;
import thenytimes.android.example.com.thenytimes.dagger.module.NetworkModule;
import thenytimes.android.example.com.thenytimes.dagger.module.SettingsModule;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class Application extends android.app.Application {

    private NetworkComponent mNetworkComponent;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .settingsModule(new SettingsModule())
                .build();

        this.sContext = getApplicationContext();

    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public static Context getContext() {
        return sContext;
    }
}
