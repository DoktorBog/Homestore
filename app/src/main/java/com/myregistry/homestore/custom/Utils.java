package com.myregistry.homestore.custom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Utils {

    public static String getImageUrl(String html){
        Document doc  = Jsoup.parse(html);
        Elements urlA = doc.select("a.url");
        Elements img = urlA.select("img");
        return img.attr("src");
    }

    public static String getPrice(String html){
        return Jsoup.parse(html).select("strike").text();
    }

    public static String getDescription(String html){
        return Jsoup.parse(html).select("span.riRssContributor").text() ;
    }

}
