package com.orion.cepsearch.core.repository;

import android.content.Context;

import com.orion.cepsearch.core.utils.AppConstants;
import com.orion.cepsearch.core.utils.PreferencesManager;

public class SettingsRepository {
    private PreferencesManager prefsManager = null;

    public SettingsRepository(Context mContext) {
        prefsManager = new PreferencesManager(mContext);
    }

    public void updateManualApiSetting(Boolean settingValue) {
        prefsManager.saveBooleanValue(AppConstants.USER_MANUAL_API_SETTINGS, settingValue);
    }
    public void updateApiCepSetting(Boolean settingValue) {
        prefsManager.saveBooleanValue(AppConstants.API_CEP_SWITCH, settingValue);
    }
    public void updateViaCepSetting(Boolean settingValue) {
        prefsManager.saveBooleanValue(AppConstants.VIA_CEP_SWITCH, settingValue);
    }
    public void updateAwesomeCepSetting(Boolean settingValue) {
        prefsManager.saveBooleanValue(AppConstants.AWESOME_CEP_SWITCH, settingValue);
    }
    public Boolean getApiCepSetting() {
        return prefsManager.getBoolean(AppConstants.API_CEP_SWITCH);
    }
    public Boolean getManualApiSetting() {
        return prefsManager.getBoolean(AppConstants.USER_MANUAL_API_SETTINGS);
    }
    public Boolean getViaCepSetting() {
        return prefsManager.getBoolean(AppConstants.VIA_CEP_SWITCH);
    }
    public Boolean getAwesomeCepSetting() {
        return prefsManager.getBoolean(AppConstants.AWESOME_CEP_SWITCH);
    }
}
