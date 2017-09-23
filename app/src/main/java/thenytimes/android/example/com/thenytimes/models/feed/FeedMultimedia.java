package thenytimes.android.example.com.thenytimes.models.feed;

import com.google.gson.annotations.Expose;

public class FeedMultimedia {

    @Expose
    String type;

    @Expose
    String subtype;

    @Expose
    String url;

    public String getSubType() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
