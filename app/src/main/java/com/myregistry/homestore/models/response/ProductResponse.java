package com.myregistry.homestore.models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Root(name = "rss", strict = false)
public class ProductResponse {

    @Element(name = "channel")
    private Channel channel;
}
