package com.orion.cepsearch.core.api;

import com.orion.cepsearch.core.model.remote.APICepJson;
import com.orion.cepsearch.core.model.remote.ViaCepJson;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCep {
//   Ex: https://cep.awesomeapi.com.br/json/05424020
    @GET("/file/apicep/{cepString}.json")
    Observable<APICepJson> searchCEP(@Path("cepString") String cepString);
}

