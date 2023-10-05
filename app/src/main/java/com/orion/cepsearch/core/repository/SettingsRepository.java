package com.orion.cepsearch.core.repository;

import android.content.Context;

import com.orion.cepsearch.core.model.local.Cep;
import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.core.service.remote.CEPService;
import com.orion.cepsearch.core.utils.AppConstants;
import com.orion.cepsearch.core.utils.PreferencesManager;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsRepository {
    private PreferencesManager prefsManager = null;
    private CEPService cepService = null;

    public SettingsRepository(Context mContext) {
        this.prefsManager = new PreferencesManager(mContext);
        this.cepService = new CEPService(mContext);
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

    public Completable saveCepLocal(CepResultItem cepItem) {
        return cepService.saveCepLocal(cepItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Cep>> getCepLocalList() {
        return cepService.getCepLocalList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
