package thenytimes.android.example.com.thenytimes.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import thenytimes.android.example.com.thenytimes.preference.NewsSettings;

@Module
public class SettingsModule {

    @Provides
    @Singleton
    NewsSettings providePreference() {
        return NewsSettings.getInstance();
    }
}
