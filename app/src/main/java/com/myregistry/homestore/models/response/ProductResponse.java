package com.myregistry.homestore.models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Root(name = "channel")
public class ProductResponse {

    @Element(name = "link")
    private String link;

    @Element(name = "title")
    private String title;

    @Element(name = "description", data = true)
    private String description;

    @ElementList(inline = true, entry = "item")
    private List<Item> mItem;

    @Getter @Setter
    @Element(name = "item")
    public class Item {

        @Element(name = "link")
        private String link;

        @Element(name = "title")
        private String title;

        @Element(name = "description", data = true)
        private String description;
    }

}
