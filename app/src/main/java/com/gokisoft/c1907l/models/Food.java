package com.gokisoft.c1907l.models;

/**
 * Created by Diep.Tran on 7/16/21.
 */

public class Food {
    String thumbnail, title, description;

    public Food() {
    }

    public Food(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Food(String thumbnail, String title, String description) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
}
