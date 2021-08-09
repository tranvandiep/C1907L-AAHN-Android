package com.gokisoft.bt2113.model;

import com.gokisoft.c1907l.R;

import java.util.Random;

/**
 * Created by Diep.Tran on 8/9/21.
 */

public class Music {
    int id;
    int resId;
    String title, description;

    public Music() {
        randomThumb();
    }

    public Music(String title, String description) {
        randomThumb();
        this.title = title;
        this.description = description;
    }

    public Music(int id, int resId, String title, String description) {
        this.id = id;
        this.resId = resId;
        this.title = title;
        this.description = description;
    }

    public Music(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        randomThumb();
    }

    private void randomThumb() {
        int[] list = {R.drawable.thumb_1, R.drawable.thumb_2, R.drawable.thumb_3, R.drawable.thumb_4, R.drawable.thumb_5};
        Random random = new Random();
        int index = random.nextInt(list.length);

        this.resId = list[index];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
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
