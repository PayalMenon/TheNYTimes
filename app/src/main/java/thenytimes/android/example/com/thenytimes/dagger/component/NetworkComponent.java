package thenytimes.android.example.com.thenytimes.dagger.component;

import javax.inject.Singleton;
import dagger.Component;

import thenytimes.android.example.com.thenytimes.dagger.module.ApplicationModule;
import thenytimes.android.example.com.thenytimes.dagger.module.NetworkModule;
import thenytimes.android.example.com.thenytimes.dagger.module.SettingsModule;
import thenytimes.android.example.com.thenytimes.fragments.FilterFragment;
import thenytimes.android.example.com.thenytimes.fragments.ListFragment;
import thenytimes.android.example.com.thenytimes.activities.MainActivity;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, SettingsModule.class})
public interface NetworkComponent {

    void inject(ListFragment fragment);
    void inject(MainActivity activity);
    void inject(FilterFragment fragment);
}
