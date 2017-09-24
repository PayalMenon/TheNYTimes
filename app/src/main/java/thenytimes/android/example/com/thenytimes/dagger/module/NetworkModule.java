package thenytimes.android.example.com.thenytimes.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import thenytimes.android.example.com.thenytimes.services.FeedService;

@Module
public class NetworkModule {

    private String mBaseUrl;

    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build();
    }

    @Provides
    @Singleton
    FeedService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(FeedService.class);
    }

}
