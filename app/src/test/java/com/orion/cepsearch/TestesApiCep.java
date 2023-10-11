package com.orion.cepsearch;


import android.util.Log;

import com.orion.cepsearch.core.api.ApiCep;
import com.orion.cepsearch.core.api.CepAwesomeAPI;
import com.orion.cepsearch.core.api.ViaCepAPI;
import com.orion.cepsearch.core.service.remote.CEPService;
import com.orion.cepsearch.core.utils.AppConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mockwebserver3.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class TestesApiCep {

    //Servidor Mock
    private MockWebServer mockWebServer;
    private Retrofit clientApiCep;
    private Retrofit clientViaCep;
    private Retrofit clientCepAwesome;
    private Retrofit clientMock;

    //Chamadas da API
    private ViaCepAPI viaCepAPI;
    private ApiCep apiCep;
    private CepAwesomeAPI cepAwesomeAPI;

    private Retrofit clientFactory(String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Retrofit clientMockFactory() {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Before
    public void setUp() throws IOException {

        mockWebServer = new MockWebServer();
        mockWebServer.start();


        Retrofit clientApiCep = clientFactory(AppConstants.API_CEP_BASE_URL);
        Retrofit clientViaCep = clientFactory(AppConstants.VIA_CEP_BASE_URL);
        Retrofit clientCepAwesome = clientFactory(AppConstants.CEP_AWESOME_BASE_URL);
        Retrofit clientMock = clientMockFactory();

    }


    @After
    public void clear() throws IOException {
        mockWebServer.shutdown();
    }


    @Test
    public void testApiCep() throws IOException {

        setUp();
        viaCepAPI.searchCEP("08588590")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.e(AppConstants.APP_API_RUNTIME_ERROR, AppConstants.VIA_CEP_API_ERROR);
                }).map(
                        teste -> {

                            return teste.getComplemento();

                        }
                );

    }

    @Test
    public void testViaCep() {

    }

    @Test
    public void testAwesomeCep() {

    }


}

