package com.myregistry.homestore.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.myregistry.homestore.R;
import com.myregistry.homestore.adapter.CardAdapter;
import com.myregistry.homestore.models.realm.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class CartActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private Menu menu;
    private CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initializeToolbar();
        initializeRecyclerView();
    }



    public void initializeToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(e -> {
            setResult(RESULT_OK);
            finish();
        });
    }


    private void initializeRecyclerView() {
        RealmResults<Product> realmResults = Realm.getDefaultInstance().where(Product.class).equalTo("inCard", true).findAll();
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new CardAdapter(this, realmResults);
        recyclerView.setAdapter(mAdapter);
    }


}
