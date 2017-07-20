package com.myregistry.homestore.network;


import com.myregistry.homestore.models.response.ProductResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("gp/rss/bestsellers/dvd")
    Observable<ProductResponse> getProducts();
}
