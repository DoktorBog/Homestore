package com.myregistry.homestore.presenter;

import android.util.Log;

import com.myregistry.homestore.custom.Utils;
import com.myregistry.homestore.models.realm.Product;
import com.myregistry.homestore.models.response.Item;
import com.myregistry.homestore.network.usecases.FetchProductsUsecase;
import com.myregistry.homestore.view.ProductView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class ProductPresenter implements Presenter<ProductView> {

    private Disposable getProgramsSubscription;
    private ProductView productView;
    private FetchProductsUsecase fetchProductsUsecase;

    public ProductPresenter(FetchProductsUsecase fetchProductsUsecase) {
        this.fetchProductsUsecase = fetchProductsUsecase;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void getItems() {
        productView.showLoading();
        getProgramsSubscription = fetchProductsUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productResponse ->{
                    if(productResponse!=null && productResponse.getChannel().getItems()!=null){
                        Realm.getDefaultInstance().executeTransaction(realm -> {
                            for (Item item : productResponse.getChannel().getItems()) {
                                Product product = new Product();
                                product.setLink(item.getLink());
                                product.setTitle(item.getTitle());
                                product.setImageUrl(Utils.getImageUrl(item.getDescription()));
                                product.setDescription(Utils.getDescription(item.getDescription()));
                                product.setPrice(Utils.getPrice(item.getDescription()) + "");
                                if(product.getPrice().isEmpty())
                                    product.setPrice("$10");
                                realm.copyToRealmOrUpdate(product);
                            }
                        });
                        productView.showContent();
                        productView.onAny();
                    } else {
                        Log.e("errorE","null");
                        productView.showError();
                        productView.onAny();
                    }
                }, e->{
                    Log.e("errorE",e.toString());
                    productView.showError();
                    productView.onAny();
                });
    }

    @Override
    public void attachView(ProductView productView) {
        this.productView = productView;
        getItems();
    }
}
