package com.orion.cepsearch.ui.search;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orion.cepsearch.core.model.local.CepResult;
import com.orion.cepsearch.core.repository.CepRepository;
import com.orion.cepsearch.core.utils.AppConstants;

import io.reactivex.disposables.Disposable;

public class SearchCepViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Integer> toastMessageById;
    private MutableLiveData<String> cepSearchParams = null;
    private MutableLiveData<CepResult> results = null;
    private MutableLiveData<Boolean> showLoading = null;
    private CepRepository cepRepository = null;
    private Disposable disposable = null;

    public SearchCepViewModel() {
        cepSearchParams = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        toastMessageById = new MutableLiveData<>();
        results = new MutableLiveData<>();
        showLoading = new MutableLiveData<>();
    }
    public void injectCepRepositoryContext(Context mContext) {
        cepRepository = new CepRepository(mContext);
    }
    public void searchCepClick() {
        String cep = "08588590";
        disposable = cepRepository.searchCep(cep)
                        .doOnError(error -> {
                                    if (cepRepository.hasApisToUse()) {
                                        searchCepClick();
                                    }
                                }
                        )
                        .doFinally(() ->
                                cepRepository.resetApiFlags()
                        )
                        .subscribe(this::showResults,
                                throwable -> {
                                    showErrorMessage(AppConstants.UNKNOW_API_ERROR);
                                    Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.UNKNOW_API_ERROR + throwable.getLocalizedMessage());
                                });
    }
    public LiveData<CepResult> getResults() {
        return results;
    }

    public void showLoading(){
        showLoading.postValue(true);
    }
    public void hideLoading(){
        showLoading.postValue(false);
    }

    public LiveData<Boolean> getLoading(){
        return showLoading;
    }
    public void showResults(CepResult result){
        results.postValue(result);
    }
    public void showErrorMessage(String message) {
        errorMessage.postValue(message);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void sendToastMessageById(Integer id) {
        toastMessageById.postValue(id);
    }

    public LiveData<Integer> getToastMessageById() {
        return toastMessageById;
    }
    public void dispose() {
        if (this.disposable != null)
            this.disposable.dispose();
    }

}