package thenytimes.android.example.com.thenytimes.models.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedHeadline {

    @SerializedName("main")
    @Expose
    String title;

    public String getTitle() {
        return title;
    }
}
