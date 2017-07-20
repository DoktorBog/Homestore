package com.myregistry.homestore.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.myregistry.homestore.R;
import com.myregistry.homestore.adapter.ProductAdapter;
import com.myregistry.homestore.models.realm.Product;
import com.myregistry.homestore.network.repositorys.RetrofitRestRepository;
import com.myregistry.homestore.network.usecases.FetchProductsUsecase;
import com.myregistry.homestore.presenter.ProductPresenter;
import com.myregistry.homestore.view.ProductView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements ProductView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.content) RelativeLayout content;
    @BindView(R.id.progress) ProgressBar progress;
    @BindView(R.id.main) RelativeLayout main;

    private ProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeToolbar();
        initializePresenter();
    }

    private void initializeRecyclerView() {
        RealmResults<Product> realmResults = Realm.getDefaultInstance().where(Product.class).findAll();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ProductAdapter mAdapter = new ProductAdapter(this, realmResults);
        recyclerView.setAdapter(mAdapter);
    }

    public void initializePresenter() {
        productPresenter = new ProductPresenter(new FetchProductsUsecase(new RetrofitRestRepository()));
        productPresenter.onCreate();
        productPresenter.attachView(this);
    }

    public void initializeToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public void showContent() {

    }

    @Override
    public void onAny() {
        initializeRecyclerView();
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        generateSnack(main, productPresenter);
    }
}
