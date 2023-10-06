package com.orion.cepsearch.ui.settings;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orion.cepsearch.R;
import com.orion.cepsearch.core.model.local.Cep;
import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.core.repository.SettingsRepository;
import com.orion.cepsearch.core.utils.AppConstants;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.disposables.Disposable;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Boolean> disableSettingSwitch = null;
    private SettingsRepository settingsRepository = null;
    private MutableLiveData<List<CepResultItem>> cepLocalList = null;
    private MutableLiveData<CepResultItem> deleteItemRv = null;
    private MutableLiveData<Integer> toastMessageById = null;
    private Disposable disposable;

    public SettingsViewModel() {
        disableSettingSwitch = new MutableLiveData<Boolean>();
        cepLocalList = new MutableLiveData<List<CepResultItem>>();
        toastMessageById = new MutableLiveData<Integer>();
        deleteItemRv = new MutableLiveData<CepResultItem>();
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

    public LiveData<List<CepResultItem>> getCepLocalLiveData() {
        return this.cepLocalList;
    }

    public void deleteItemFromRv(CepResultItem cepItem){
        this.deleteItemRv.postValue(cepItem);
    }

    public LiveData<CepResultItem> getDeleteItemRv(){
        return this.deleteItemRv;
    }
    public void deleteCepItemLocal(CepResultItem cepItem) {
        Cep cep = new Cep(cepItem.getId(),
                cepItem.getCep(),
                cepItem.getAddress(),
                cepItem.getDistrict(),
                cepItem.getCity(),
                cepItem.getCompl(),
                cepItem.getSrcApiRef(),
                cepItem.getLat(),
                cepItem.getLng());

        settingsRepository.deleteCepLocal(cep)
                .subscribe(
                        () -> {
                            deleteItemFromRv(cepItem);
                            toastMessageById.postValue(R.string.cep_deleted_successful);
                        },
                        throwable -> {
                            Log.e(AppConstants.LOCAL_DB_ERROR, throwable.getLocalizedMessage());
                            toastMessageById.postValue(R.string.cep_delete_fail);
                        }
                );

        }

    public void refreshCepList() {
        settingsRepository.getCepLocalList()
                .subscribe(resultList -> {
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
        this.toastMessageById.removeObservers(lifecycleOwner);
        this.deleteItemRv.removeObservers(lifecycleOwner);
    }

}