package com.gokisoft.c1907l.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Diep.Tran on 8/4/21.
 */
@Root(name = "channel", strict = false)
public class RssChannel {
    @Element
    public String lastBuildDate;

    @Element
    public String title;

    @Element
    public String description;

    @Element
    public String link;

    @ElementList(name = "item", inline = true, required = false)
    public List<RssItem> itemList;

    @Override
    public String toString() {
        return "RssChannel{" +
                "lastBuildDate='" + lastBuildDate + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", item=" + itemList +
                '}';
    }
}
