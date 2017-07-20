package com.myregistry.homestore.models.response;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true, entry = "item")
    private List<Item> items;

}