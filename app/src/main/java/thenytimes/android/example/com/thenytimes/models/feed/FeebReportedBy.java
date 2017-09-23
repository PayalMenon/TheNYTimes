package thenytimes.android.example.com.thenytimes.models.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeebReportedBy {

    @SerializedName("original")
    @Expose
    private String reportedBy;

    public String getReportedBy() {
        return reportedBy;
    }
}
