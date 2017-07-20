package com.myregistry.homestore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.myregistry.homestore.R;
import com.myregistry.homestore.adapter.ProductAdapter;
import com.myregistry.homestore.models.realm.Product;
import com.myregistry.homestore.network.repositorys.RetrofitRestRepository;
import com.myregistry.homestore.network.usecases.FetchProductsUsecase;
import com.myregistry.homestore.presenter.ProductPresenter;
import com.myregistry.homestore.view.ProductView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements ProductView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.content) RelativeLayout content;
    @BindView(R.id.progress) ProgressBar progress;
    @BindView(R.id.main) RelativeLayout main;
    @BindView(R.id.search_view) MaterialSearchView searchView;
    @BindView(R.id.spinner) MaterialSpinner spinner;
    @BindView(R.id.searchItem) ImageView searchItem;
    @BindView(R.id.tools) RelativeLayout tools;

    private Menu menu;
    private ProductPresenter productPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeToolbar();
        initializePresenter();

        spinner.setItems("NOT SORTED", "LOWER PRICE FIRST");
        spinner.setOnItemSelectedListener((view, position, id, item) -> initializeRecyclerView(position == 1));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initializeRecyclerView(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
              tools.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                tools.setVisibility(View.VISIBLE);
                spinner.setSelectedIndex(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.main_menu, menu);
        int badgeCount = 0;
        RealmResults<Product> products = Realm.getDefaultInstance().where(Product.class).equalTo("inCard", true).findAll();

        if (products != null) {
            badgeCount = products.size();
            products.addChangeListener(products1 -> {
                ActionItemBadge.update(this, menu.findItem(R.id.card_bag),
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_card),
                        ActionItemBadge.BadgeStyles.PURPLE, products1.size());
            });
        }

        ActionItemBadge.update(this, menu.findItem(R.id.card_bag),
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_card),
                ActionItemBadge.BadgeStyles.PURPLE, badgeCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.card_bag:
                launchNewActivity(CartActivity.class, null, 10);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeRecyclerView(boolean sort) {
        RealmResults<Product> realmResults;
        if (sort)
            realmResults = Realm.getDefaultInstance().where(Product.class).findAllSorted("price");
        else realmResults = Realm.getDefaultInstance().where(Product.class).findAll();
        updateAdater(realmResults);
    }

    private void initializeRecyclerView(String search) {
        RealmResults<Product> realmResults;
        if (!search.isEmpty()) realmResults = Realm.getDefaultInstance().where(Product.class).contains("title", search.toLowerCase()).findAll();
        else realmResults = Realm.getDefaultInstance().where(Product.class).findAll();
        updateAdater(realmResults);
    }

    public void updateAdater(RealmResults<Product> realmResults){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ProductAdapter mAdapter = new ProductAdapter(this, realmResults);
        recyclerView.setAdapter(mAdapter);
        updateBadge();
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
        initializeRecyclerView(false);
        progress.setVisibility(View.GONE);
    }

    @OnClick(R.id.searchItem)
    public void onSearch(){
        searchView.showSearch();
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        generateSnack(main, productPresenter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            updateBadge();
        }
    }

    public void updateBadge() {
        RealmResults<Product> products = Realm.getDefaultInstance().where(Product.class).equalTo("inCard", true).findAll();
        if (products != null) {
            ActionItemBadge.update(menu.findItem(R.id.card_bag), products.size());
        }
    }
}
