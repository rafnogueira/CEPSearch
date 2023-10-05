package com.orion.cepsearch.core.api;

import com.orion.cepsearch.core.model.remote.ViaCepJson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepAPI {
//    Ex : https://viacep.com.br/ws/01001000/json/
    @GET("ws/{cepString}/json")
    Observable<ViaCepJson> searchCEP(@Path("cepString") String cepString);

}
