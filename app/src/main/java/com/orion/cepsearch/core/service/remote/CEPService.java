package com.orion.cepsearch.core.service.remote;

import android.content.Context;
import android.util.Log;

import com.orion.cepsearch.core.api.ApiCep;
import com.orion.cepsearch.core.api.CepAwesomeAPI;
import com.orion.cepsearch.core.api.ViaCepAPI;
import com.orion.cepsearch.core.utils.AppConstants;
import com.orion.cepsearch.core.utils.PreferencesManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CEPService {
    private Retrofit viaCepClient = null;
    private Retrofit apiCepClient = null;
    private Retrofit awesomeCepClient = null;

    private boolean userManualSettingStatus = false;
    private boolean viaCEPEnabled = true;
    private boolean apiCepEnabled = true;
    private boolean awesomeCepEnabled = true;
    private boolean viaCEPDown = false;
    private boolean apiCepDown = false;
    private boolean awesomeCepDown = false;
    private CURRENT_API_ENUM currentApi = null;
    private PreferencesManager prefsManager = null;

    public enum CURRENT_API_ENUM {
        VIA_CEP,
        API_CEP,
        CEP_AWESOME,
        NONE;
    }

    public CEPService(Context mContext) {
        prefsManager = new PreferencesManager(mContext);
        buildClients();
    }

    // Verifica as API habilitadas, e se há uma configuração manual feita pelo usuário nas prefs
    // caso sim usar senão, ignorar e manter o valor default ou usar a api que o código dectectou estar
    // funcionando ou não
    // Ex: Quando uma api falha o código precisa saber que aquela api não está ativa e pegar a próxima,
    // mas se a switch de config manual estiver ativa, irá priorizar uma api sobre outra
    public void checkWhichApiIsEnabled() {
        userManualSettingStatus = prefsManager.getBoolean(AppConstants.USER_MANUAL_API_SETTINGS);
        if (userManualSettingStatus) {
            if (viaCEPDown)
                viaCEPEnabled = false;
            else
                viaCEPEnabled = prefsManager.getBoolean(AppConstants.VIA_CEP_SWITCH);

            if (apiCepDown)
                apiCepEnabled = false;
            else
                apiCepEnabled = prefsManager.getBoolean(AppConstants.API_CEP_SWITCH);

            if (awesomeCepDown)
                awesomeCepEnabled = false;
            else
                awesomeCepEnabled = prefsManager.getBoolean(AppConstants.AWESOME_CEP_SWITCH);

        } else {
            viaCEPEnabled = !viaCEPDown;
            apiCepEnabled = !apiCepDown;
            awesomeCepEnabled = !awesomeCepDown;
        }

    }

    public void updateApiAtRuntime(CURRENT_API_ENUM whichAPI, Boolean flag) {
        if (whichAPI == CURRENT_API_ENUM.VIA_CEP) {
            viaCEPDown = flag;
        }
        if (whichAPI == CURRENT_API_ENUM.API_CEP) {
            apiCepDown = flag;
        }
        if (whichAPI == CURRENT_API_ENUM.CEP_AWESOME) {
            awesomeCepDown = flag;
        }
        checkWhichApiIsEnabled();
    }

    public void resetCurrentApi() {
        currentApi = null;
    }

    public CURRENT_API_ENUM getCurrentApiEnum() {
        if (currentApi == null) {
            checkWhichApiIsEnabled();
        }

        currentApi = selectDefaultApiPossible();

        return currentApi;
    }
    public CURRENT_API_ENUM selectDefaultApiPossible() {
        if (viaCEPEnabled && !viaCEPDown) {
            return currentApi = CURRENT_API_ENUM.VIA_CEP;
        }
        if (apiCepEnabled && !apiCepDown) {
            return currentApi = CURRENT_API_ENUM.API_CEP;
        }
        if (awesomeCepEnabled && !awesomeCepDown) {
            return currentApi = CURRENT_API_ENUM.CEP_AWESOME;
        } else {
            return currentApi = CURRENT_API_ENUM.NONE;
        }
    }
//
//    public void selectCurrentAPI(CURRENT_API_ENUM api) {
//        this.currentApi = api;
//    }


    private Retrofit clientFactory(String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void buildClients() {
        try {
            apiCepClient = clientFactory(AppConstants.API_CEP_BASE_URL);
            viaCepClient = clientFactory(AppConstants.VIA_CEP_BASE_URL);
            awesomeCepClient = clientFactory(AppConstants.CEP_AWESOME_BASE_URL);
        } catch (Exception ex) {
            Log.e(AppConstants.APP_API_DEBUG_ERROR, "Error ao criar cliente da API " + ex.getLocalizedMessage());
        }
    }

    public ViaCepAPI getViaCepApi() {
        return getViaCepClient().create(ViaCepAPI.class);
    }

    public ApiCep getApiCep() {
        return getApiCepClient().create(ApiCep.class);
    }

    public CepAwesomeAPI getCepAwesomeApi() {
        return getAwesomeCepClient().create(CepAwesomeAPI.class);
    }

    private Retrofit getApiCepClient() {
        return this.apiCepClient;
    }

    private Retrofit getViaCepClient() {
        return this.viaCepClient;
    }

    private Retrofit getAwesomeCepClient() {
        return this.awesomeCepClient;
    }

    public boolean getUserManualSettingStatus() {
        return userManualSettingStatus;
    }

    public boolean getViaCEPEnabled() {
        return viaCEPEnabled;
    }

    public boolean getApiCepEnabled() {
        return apiCepEnabled;
    }

    public boolean getAwesomeCepEnabled() {
        return awesomeCepEnabled;
    }
}

