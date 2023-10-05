package com.orion.cepsearch.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.loader.ResourcesProvider;

public class PreferencesManager {
    private SharedPreferences prefs = null;

    public PreferencesManager(Context mContext) {
        this.prefs = mContext.getSharedPreferences(AppConstants.APP_PREFS_ID, Context.MODE_PRIVATE);
    }

    public void saveStringValue(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public void saveBooleanValue(String key, Boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public Boolean getBoolean(String key) {
        return this.prefs.getBoolean(key, false);
    }

    public String getStringValue(String key) {
        return this.prefs.getString(key, "");
    }

    private SharedPreferences.Editor getEditor() {
        return this.prefs.edit();
    }
}
