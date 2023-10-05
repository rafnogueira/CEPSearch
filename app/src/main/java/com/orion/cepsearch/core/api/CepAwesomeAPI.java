package com.orion.cepsearch.core.api;

import com.orion.cepsearch.core.model.remote.CepAwesomeJson;
import com.orion.cepsearch.core.model.remote.ViaCepJson;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepAwesomeAPI {
    //    https://cdn.apicep.com/file/apicep/[cep].json
    @GET("json/{cepString}")
    Observable<CepAwesomeJson> searchCEP(@Path("cepString") String cepString);

}
