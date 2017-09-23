package thenytimes.android.example.com.thenytimes.models.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedDetails {

    @SerializedName("web_url")
    @Expose
    private String webUrl;

    @Expose
    private String snippet;

    private FeedHeadline headline;

    @SerializedName("pub_date")
    @Expose
    private String datePublished;

    @SerializedName("new_desk")
    @Expose
    private String desk;

    @SerializedName("byline")
    @Expose
    FeebReportedBy reporter;

    private List<FeedMultimedia> multimedia;

    public FeedHeadline getHeadline() {
        return headline;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getDesk() {
        return desk;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public List<FeedMultimedia> getMultimedia() {
        return multimedia;
    }

    public FeebReportedBy getReporter() {
        return reporter;
    }
}
