package com.myregistry.homestore.network.usecases;

import com.myregistry.homestore.models.response.ProductResponse;
import com.myregistry.homestore.network.repositorys.Repository;

import io.reactivex.Observable;

public class FetchProductsUsecase implements Usecase<ProductResponse> {

    private Repository repository;

    public FetchProductsUsecase(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ProductResponse> execute() {
        return repository.getProducts();
    }

}
