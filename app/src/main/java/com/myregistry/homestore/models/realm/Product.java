package com.myregistry.homestore.models.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product extends RealmObject {

    @PrimaryKey
    private String link;

    private String title;
    private String price;
    private String imageUrl;
    private String description;
    private boolean inCard;
}
