package com.gokisoft.c1907l.models;

/**
 * Created by Diep.Tran on 8/4/21.
 */

public class News {
    String guid, link, pubDate, category, title, description, content, thumbnail;

    public News() {
    }

    public News(String guid, String link, String pubDate, String category, String title, String description, String content, String thumbnail) {
        this.guid = guid;
        this.link = link;
        this.pubDate = pubDate;
        this.category = category;
        this.title = title;
        this.description = description;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
