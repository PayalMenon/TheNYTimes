package thenytimes.android.example.com.thenytimes.services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.QueryMap;
import thenytimes.android.example.com.thenytimes.models.feed.FeedResponse;

public interface FeedService {

    @GET("articlesearch.json?api-key=87aff2a109024e3d950b517313076641")
    Call<FeedResponse> getNewsFeed(@QueryMap Map<String, Object> options);
}
