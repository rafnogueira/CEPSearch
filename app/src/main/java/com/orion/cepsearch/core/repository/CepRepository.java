package com.orion.cepsearch.core.repository;

import android.content.Context;
import android.util.Log;

import com.orion.cepsearch.core.model.local.CepResult;
import com.orion.cepsearch.core.model.remote.APICepJson;
import com.orion.cepsearch.core.model.remote.CepAwesomeJson;
import com.orion.cepsearch.core.model.remote.ViaCepJson;
import com.orion.cepsearch.core.service.remote.CEPService;
import com.orion.cepsearch.core.utils.AppConstants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CepRepository {
    private CEPService apiCepService = null;
    private boolean hasApiAvailable = true;

    public CepRepository(Context mContext) {
        apiCepService = new CEPService(mContext);
    }

    public CepResult viaCepJsonToCepResult(ViaCepJson viaCepJson) {
        return new CepResult(viaCepJson.getCep(),
                viaCepJson.getLogradouro(),
                viaCepJson.getBairro(),
                viaCepJson.getLocalidade(),
                viaCepJson.getComplemento(),
                AppConstants.VIA_CEP_BASE_URL,
                null,
                null
        );
    }

    public CepResult apiCepJsonToCepResult(APICepJson apiCepJson) {
        return new CepResult(apiCepJson.getCep(),
                apiCepJson.getAddress(),
                apiCepJson.getDistrict(),
                apiCepJson.getCity(),
                "",
                AppConstants.API_CEP_BASE_URL,
                null,
                null
        );
    }

    public CepResult awesomeCepToCepResult(CepAwesomeJson cepAwesomeJson) {
        return new CepResult(cepAwesomeJson.getCep(),
                cepAwesomeJson.getAddress(),
                cepAwesomeJson.getDistrict(),
                cepAwesomeJson.getCity(),
                "",
                AppConstants.CEP_AWESOME_BASE_URL,
                cepAwesomeJson.getLat(),
                cepAwesomeJson.getLng()
        );
    }

    public boolean hasApisToUse() {
        return this.hasApiAvailable;
    }

    public void resetApiFlags() {
        apiCepService.resetCurrentApi();
    }

    public Observable<CepResult> searchCep(String params) {
        switch (apiCepService.getCurrentApiEnum()) {
            case VIA_CEP:
                return apiCepService.getViaCepApi()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.VIA_CEP_API_ERROR);
                            apiCepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.VIA_CEP, true);
                        })
                        .map(this::viaCepJsonToCepResult);

            case API_CEP:
                return apiCepService.getApiCep()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.API_CEP_ERROR);
                            apiCepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.API_CEP, true);
                        })
                        .map(this::apiCepJsonToCepResult);

            case CEP_AWESOME:
                return apiCepService.getCepAwesomeApi()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.AWESOME_CEP_ERROR);
                            apiCepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.CEP_AWESOME, true);
                        })
                        .map(this::awesomeCepToCepResult);

            case NONE:
            default: {
                resetApiFlags();
                hasApiAvailable = false;
                return Observable.error(new Throwable(AppConstants.NO_APIS_AVAILABLE_MESSAGE));
            }
        }
    }


}
