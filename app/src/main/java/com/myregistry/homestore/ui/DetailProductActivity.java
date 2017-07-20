package com.myregistry.homestore.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.myregistry.homestore.R;
import com.myregistry.homestore.custom.ProximaNovaTextView;
import com.myregistry.homestore.models.realm.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class DetailProductActivity extends BaseActivity {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.title) ProximaNovaTextView title;
    @BindView(R.id.price) ProximaNovaTextView price;
    @BindView(R.id.description) ProximaNovaTextView description;
    @BindView(R.id.button) ButtonRectangle buttonF;

    private Menu menu;
    private Product product;
    private static final String LINK = "link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        initializeToolbar();
        product = Realm.getDefaultInstance().where(Product.class).equalTo(LINK, getBundle().getString(LINK)).findFirst();
        if(product.isInCard()) {
            buttonF.setBackgroundColor(Color.parseColor("#867f7f"));
            buttonF.setClickable(false);
            buttonF.setFocusable(false);
        }
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty())
            Picasso.with(this).load(product.getImageUrl()).into(image);
        title.setText(product.getTitle() + "");
        price.setText(product.getPrice() + "");
        description.setText(product.getDescription() + "");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;
        int badgeCount = 0;
        RealmResults<Product> products = Realm.getDefaultInstance().where(Product.class).equalTo("inCard", true).findAll();

        if (products != null) {
            badgeCount = products.size();
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            product.setInCard(true);
            buttonF.setActivated(false);
        });
        RealmResults<Product> products = Realm.getDefaultInstance().where(Product.class).equalTo("inCard", true).findAll();
        if (products != null) {
            ActionItemBadge.update(menu.findItem(R.id.card_bag), products.size());
        }
        buttonF.setBackgroundColor(Color.parseColor("#867f7f"));
        buttonF.setClickable(false);
        buttonF.setFocusable(false);
    }
}
