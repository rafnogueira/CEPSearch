package com.orion.cepsearch.ui.settings;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.core.repository.SettingsRepository;
import com.orion.cepsearch.core.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.disposables.Disposable;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Boolean> disableSettingSwitch = null;
    private SettingsRepository settingsRepository = null;
    private MutableLiveData<List<CepResultItem>> cepLocalList = null;
    private Disposable disposable;

    public SettingsViewModel() {
        disableSettingSwitch = new MutableLiveData<Boolean>();
        cepLocalList = new MutableLiveData<List<CepResultItem>>();
    }

    public void injectPrefsManagerContext(Context mContext) {
        settingsRepository = new SettingsRepository(mContext);
    }

    public void updateManualSettingsSwitch(Boolean checked) {
        this.disableSettingSwitch.setValue(checked);
    }

    public LiveData<Boolean> getManualSettingsSwitch() {
        return this.disableSettingSwitch;
    }

    public void saveManualSwitchSetting(Boolean checked) {
        this.settingsRepository.updateManualApiSetting(checked);
    }

    public void saveViaCepSetting(Boolean checked) {
        this.settingsRepository.updateViaCepSetting(checked);
    }

    public void saveApiCepSetting(Boolean checked) {
        this.settingsRepository.updateApiCepSetting(checked);
    }

    public void saveCepAwesomeSetting(Boolean checked) {
        this.settingsRepository.updateAwesomeCepSetting(checked);
    }

    public Boolean getManualSwitchSetting() {
        return settingsRepository.getManualApiSetting();
    }

    public Boolean getViaCepSwitchSetting() {
        return settingsRepository.getViaCepSetting();
    }

    public Boolean getApiCepSwitchSetting() {
        return settingsRepository.getApiCepSetting();
    }

    public Boolean getAwesomeCepSwitchSetting() {
        return settingsRepository.getAwesomeCepSetting();
    }

    public void saveCepTest(CepResultItem cepItem) {
        settingsRepository.saveCepLocal(cepItem)
                .subscribe(
                        () -> {
                            Log.e("", " ");

                        },
                        throwable -> {
                            Log.e("", " ");

                        }
                );

    }

    public LiveData<List<CepResultItem>> getCepLocalLiveData(){
        return this.cepLocalList;
    }

    public void getCepList() {
        settingsRepository.getCepLocalList()
                .subscribe(
                        resultList -> {
                            if (resultList != null && !resultList.isEmpty()) {
                                List<CepResultItem> list = resultList.stream().map(item ->
                                        new CepResultItem(item.getCep(),
                                                item.getAddress(),
                                                item.getDistrict(),
                                                item.getCity(),
                                                item.getCompl(),
                                                item.getSrcApiRef(),
                                                item.getLat(),
                                                item.getLng(),
                                                item.getId())
                                ).collect(Collectors.toList());
                                cepLocalList.postValue(list);
                            }
                        },
                        throwable -> {
                            Log.e(AppConstants.LOCAL_DB_ERROR, throwable.getLocalizedMessage());
                        }
                );

    }

    public void dispose(LifecycleOwner lifecycleOwner) {
        if (disposable != null)
            this.disposable.dispose();

        this.disableSettingSwitch.removeObservers(lifecycleOwner);
        this.cepLocalList.removeObservers(lifecycleOwner);
    }

}