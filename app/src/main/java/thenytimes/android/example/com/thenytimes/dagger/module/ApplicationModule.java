package thenytimes.android.example.com.thenytimes.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

}
