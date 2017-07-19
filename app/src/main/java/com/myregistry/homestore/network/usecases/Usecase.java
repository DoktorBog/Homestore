package com.myregistry.homestore.network.usecases;

import io.reactivex.Observable;

public interface Usecase<T> {
    Observable<T> execute();
}
