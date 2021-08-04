package com.gokisoft.c1907l.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Diep.Tran on 8/4/21.
 */
@Root(name = "item", strict = false)
public class RssItem {
    @Element
    public String guid;

    @Element
    public String link;

    @Element
    public String pubDate;

    @Element
    public String category;

    @Element
    public String title;

    @Element
    public String description;

    @Element(name = "encoded")
    public String content;

    @Element(name = "content")
    public RssMedia media;

    @Override
    public String toString() {
        return "RssItem{" +
                "guid='" + guid + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", thumbnail='" + media + '\'' +
                '}';
    }
}
