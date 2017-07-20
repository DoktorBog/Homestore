package com.myregistry.homestore.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myregistry.homestore.R;
import com.myregistry.homestore.custom.ProximaNovaTextView;
import com.myregistry.homestore.models.realm.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import lombok.Getter;

public class ProductAdapter extends RealmRecyclerViewAdapter<Product, ProductAdapter.ViewHolder>{

    @Getter
    private Context context;

    public ProductAdapter(Context context, @Nullable OrderedRealmCollection<Product> data) {
        super(data, true);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = getItem(position);
        if(product.getImageUrl()!=null && !product.getImageUrl().isEmpty())
            Picasso.with(getContext()).load(product.getImageUrl()).into(holder.image);
        holder.price.setText(product.getPrice() + "");
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.price) ProximaNovaTextView price;
        @BindView(R.id.card_view) CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
