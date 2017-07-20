package com.myregistry.homestore.network.repositorys;

import com.myregistry.homestore.models.response.ProductResponse;

import io.reactivex.Observable;

public interface Repository {
    Observable<ProductResponse> getProducts();
}
