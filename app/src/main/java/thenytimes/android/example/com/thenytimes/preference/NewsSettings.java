package thenytimes.android.example.com.thenytimes.preference;

import android.content.Context;
import android.content.SharedPreferences;

import thenytimes.android.example.com.thenytimes.Application;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class NewsSettings {

    private static NewsSettings sInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private NewsSettings(Context context) {
        mPreferences = context.getSharedPreferences(Constants.NEWS_SETTINGS, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static synchronized NewsSettings getInstance() {
        if (sInstance == null) {
            sInstance = new NewsSettings(Application.getContext());
        }
        return sInstance;
    }

    public void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void setInteger(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public String getString(String key, String defaultVlaue) {
        return mPreferences.getString(key, defaultVlaue);
    }

    public int getInteger(String key, int defaultVlaue) {
        return mPreferences.getInt(key, defaultVlaue);
    }

    public void clearAll() {
        mEditor.clear();
        mEditor.commit();
    }
}
