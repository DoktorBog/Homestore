package com.myregistry.homestore.network.repositorys;

import com.myregistry.homestore.models.response.ProductResponse;
import com.myregistry.homestore.network.ApiService;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class RetrofitRestRepository implements Repository {

    private ApiService apiService;

    public RetrofitRestRepository() {
        String BASE_URL = "https://www.amazon.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    @Override
    public Observable<ProductResponse> getProducts() {
        return apiService.getProducts();
    }
}
