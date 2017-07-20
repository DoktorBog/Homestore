package com.myregistry.homestore.models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Root(name = "item", strict = false)
public class Item {

    @Element(name = "link")
    private String link;

    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

}
