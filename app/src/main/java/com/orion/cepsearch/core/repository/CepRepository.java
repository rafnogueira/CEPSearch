package com.orion.cepsearch.core.repository;

import android.content.Context;
import android.util.Log;

import com.orion.cepsearch.core.model.local.Cep;
import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.core.model.remote.APICepJson;
import com.orion.cepsearch.core.model.remote.CepAwesomeJson;
import com.orion.cepsearch.core.model.remote.ViaCepJson;
import com.orion.cepsearch.core.service.remote.CEPService;
import com.orion.cepsearch.core.utils.AppConstants;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CepRepository {
    private CEPService cepService = null;
    private boolean hasApiAvailable = true;

    public CepRepository(Context mContext) {
        cepService = new CEPService(mContext);
    }

    public CepResultItem viaCepJsonToCepResult(ViaCepJson viaCepJson) {
        return new CepResultItem(viaCepJson.getCep(),
                viaCepJson.getLogradouro(),
                viaCepJson.getBairro(),
                viaCepJson.getLocalidade(),
                viaCepJson.getComplemento(),
                AppConstants.VIA_CEP_BASE_URL,
                null,
                null,
                null
        );
    }

    public CepResultItem apiCepJsonToCepResult(APICepJson apiCepJson) {
        return new CepResultItem(apiCepJson.getCep(),
                apiCepJson.getAddress(),
                apiCepJson.getDistrict(),
                apiCepJson.getCity(),
                "",
                AppConstants.API_CEP_BASE_URL,
                null,
                null,
                null
        );
    }

    public CepResultItem awesomeCepToCepResult(CepAwesomeJson cepAwesomeJson) {
        return new CepResultItem(cepAwesomeJson.getCep(),
                cepAwesomeJson.getAddress(),
                cepAwesomeJson.getDistrict(),
                cepAwesomeJson.getCity(),
                "",
                AppConstants.CEP_AWESOME_BASE_URL,
                cepAwesomeJson.getLat(),
                cepAwesomeJson.getLng(),
                null
        );
    }

    public Completable saveCepLocal(Cep cep) {
        return cepService.saveCepLocal(cep)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public boolean hasApisToUse() {
        return this.hasApiAvailable;
    }

    public void resetApiFlags() {
        cepService.resetCurrentApi();
    }

    public Observable<CepResultItem> searchCep(String params) {
        switch (cepService.getCurrentApiEnum()) {
            case VIA_CEP:
                return cepService.getViaCepApi()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.VIA_CEP_API_ERROR);
                            cepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.VIA_CEP, true);
                        })
                        .map(this::viaCepJsonToCepResult);

            case API_CEP:
                return cepService.getApiCep()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.API_CEP_ERROR);
                            cepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.API_CEP, true);
                        })
                        .map(this::apiCepJsonToCepResult);

            case CEP_AWESOME:
                return cepService.getCepAwesomeApi()
                        .searchCEP(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> {
                            Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.AWESOME_CEP_ERROR);
                            cepService.updateApiAtRuntime(CEPService.CURRENT_API_ENUM.CEP_AWESOME, true);
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
