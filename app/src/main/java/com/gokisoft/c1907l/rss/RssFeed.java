package com.gokisoft.c1907l.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Diep.Tran on 8/4/21.
 */
@Root(name = "rss", strict = false)
public class RssFeed {
    @Element
    public RssChannel channel;

    @Override
    public String toString() {
        return "RssFeed{" +
                "channel=" + channel +
                '}';
    }
}
