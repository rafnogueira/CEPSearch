package com.orion.cepsearch.ui.settings;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orion.cepsearch.core.repository.SettingsRepository;

import io.reactivex.disposables.Disposable;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Boolean> disableSettingSwitch = null;
    private SettingsRepository settingsRepository = null;
    private Disposable disposable;

    public SettingsViewModel() {
        disableSettingSwitch = new MutableLiveData<Boolean>();
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

    public void dispose() {
        this.disposable.dispose();
    }
}