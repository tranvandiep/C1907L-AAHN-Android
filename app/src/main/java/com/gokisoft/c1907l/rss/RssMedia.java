package com.gokisoft.c1907l.rss;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Diep.Tran on 8/4/21.
 */
@Root(name = "content")
public class RssMedia {
    @Attribute(name = "medium")
    public String medium;

    @Attribute(name = "url")
    public String url;

    @Attribute(name = "width")
    public String width;

    @Attribute(name = "height")
    public String height;

    @Override
    public String toString() {
        return "RssMedia{" +
                "medium='" + medium + '\'' +
                ", url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
